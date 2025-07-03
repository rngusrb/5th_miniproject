import React, { useEffect, useState } from 'react';
import MainLayout from '../../components/layout/MainLayout';
import './DraftListPage.css';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../api/axiosInstance'; // 중앙 관리 인스턴스 사용

export default function DraftListPage() {
  const navigate = useNavigate();
  const authorId = localStorage.getItem('userId'); // 로컬 스토리지에서 동적으로 ID 가져오기

  const [drafts, setDrafts] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);

  const [showCoverModal, setShowCoverModal] = useState(false);
  const [generatedCoverUrl, setGeneratedCoverUrl] = useState('');
  const [isCoverLoading, setIsCoverLoading] = useState(false);

  const [showSummaryModal, setShowSummaryModal] = useState(false);
  const [generatedSummaries, setGeneratedSummaries] = useState([]);
  const [isSummaryLoading, setIsSummaryLoading] = useState(false);

  // 원고 목록을 불러오는 함수
  const fetchDrafts = async () => {
    if (!authorId) return;
    try {
      const res = await axiosInstance.get(`/manuscripts/${authorId}`);
      const filtered = res.data.filter(d => d.status !== 'TEMP');
      setDrafts(filtered);
      setSelectedIds([]);
    } catch (e) {
      console.error("원고 목록을 불러오지 못했습니다:", e);
      alert('원고 목록을 불러오지 못했습니다.');
    }
  };

  useEffect(() => {
    // 로그인 상태가 아니면 로그인 페이지로 이동
    if (!authorId) {
      alert('로그인이 필요합니다.');
      navigate('/login/author');
    } else {
      fetchDrafts();
    }
  }, [authorId, navigate]);

  const toggleSelect = id => {
    setSelectedIds(prev =>
      prev.includes(id) ? prev.filter(i => i !== id) : [...prev, id]
    );
  };

  const handleGenerateCover = async () => {
    if (selectedIds.length !== 1) {
      alert('표지를 생성할 원고 하나만 선택해주세요.');
      return;
    }
    const manuscriptId = Number(selectedIds[0]);
    if (isNaN(manuscriptId)) return;

    setIsCoverLoading(true);
    setGeneratedCoverUrl('');
    setShowCoverModal(true);

    try {
      const res = await axiosInstance.post(`/manuscripts/coverimage/${manuscriptId}`);
      const imageUrl = res.data?.bookCoverImage;
      if (imageUrl) {
        setGeneratedCoverUrl(imageUrl);
        fetchDrafts();
      } else {
        alert('유효한 커버 이미지 URL을 받지 못했습니다.');
        setShowCoverModal(false);
      }
    } catch (err) {
      console.error('커버 이미지 생성 실패:', err);
      alert('커버 이미지 생성에 실패했습니다.');
      setShowCoverModal(false);
    } finally {
      setIsCoverLoading(false);
    }
  };

  const handleGenerateSummaryAndCategory = async () => {
    if (selectedIds.length === 0) {
      alert('요약 및 카테고리를 생성할 원고를 선택해주세요.');
      return;
    }

    setIsSummaryLoading(true);
    setGeneratedSummaries([]);
    setShowSummaryModal(true);

    const results = [];
    for (const id of selectedIds) {
      const parsedId = Number(id);
      if (isNaN(parsedId)) continue;

      let summaryText = '요약 내용 없음';
      let categoriesList = ['카테고리 없음'];
      let errorOccurred = false;

      try {
        const summaryRes = await axiosInstance.post(`/manuscripts/summary/${parsedId}`);
        summaryText = summaryRes.data?.summary || '요약 내용 없음';
      } catch (e) {
        console.error(`AI 요약 생성 실패 (ID: ${id}):`, e);
        errorOccurred = true;
      }

      try {
        const categoryRes = await axiosInstance.post(`/manuscripts/category/${parsedId}`);
        categoriesList = categoryRes.data?.category ? [categoryRes.data.category] : ['카테고리 없음'];
      } catch (e) {
        console.error(`AI 카테고리 생성 실패 (ID: ${id}):`, e);
        errorOccurred = true;
      }

      results.push({
        id: parsedId,
        title: drafts.find(d => d.manuscriptId === parsedId)?.title || `ID: ${parsedId}`,
        summary: summaryText,
        categories: categoriesList,
        error: errorOccurred ? '생성 실패' : undefined,
      });
    }
    setGeneratedSummaries(results);
    setIsSummaryLoading(false);
    fetchDrafts();

    const failedCount = results.filter(r => r.error).length;
    if (failedCount === 0) {
      alert('AI 요약 및 카테고리 생성이 완료되었습니다.');
    } else {
      alert(`${failedCount}개의 원고 요약 및 카테고리 생성에 실패했습니다.`);
    }
  };

  const handleDeleteSelected = async () => {
    if (selectedIds.length === 0) {
      alert('삭제할 원고를 선택해주세요.');
      return;
    }
    if (!window.confirm(`선택된 원고 ${selectedIds.length}개를 정말로 삭제하시겠습니까?`)) {
      return;
    }

    let successCount = 0;
    let failCount = 0;

    for (const id of selectedIds) {
      try {
        await axiosInstance.delete(`/manuscripts/${id}`);
        successCount++;
      } catch (e) {
        console.error(`원고 삭제 실패 (ID: ${id}):`, e);
        failCount++;
      }
    }

    if (successCount > 0) {
      alert(`${successCount}개의 원고가 성공적으로 삭제되었습니다.`);
      fetchDrafts();
    }
    if (failCount > 0) {
      alert(`${failCount}개의 원고 삭제에 실패했습니다.`);
    }
  };

  useEffect(() => {
    const handleEscape = (event) => {
      if (event.key === 'Escape') {
        setShowCoverModal(false);
        setShowSummaryModal(false);
      }
    };
    window.addEventListener('keydown', handleEscape);
    return () => {
      window.removeEventListener('keydown', handleEscape);
    };
  }, []);

  return (
    <MainLayout>
      <div className="draft-container-wide">
        <div className="draft-header">
          <h2 className="draft-title">원고 목록</h2>
          <button className="btn back-btn small-btn" onClick={() => navigate(-1)}>
            ← 홈으로
          </button>
        </div>

        <ul className="draft-list">
          {drafts.length > 0 ? (
            drafts.map(draft => (
              <li key={draft.manuscriptId} className="draft-item">
                <span className="item-title">{draft.title}</span>
                <span className="item-date">{draft.modifyDate?.split('T')[0]}</span>
                <input
                  type="checkbox"
                  checked={selectedIds.includes(draft.manuscriptId)}
                  onChange={() => toggleSelect(draft.manuscriptId)}
                />
              </li>
            ))
          ) : (
            <p>작성된 원고가 없습니다.</p>
          )}
        </ul>

        <div className="btn-group">
          <button
            className="btn btn-primary btn-wide"
            disabled={selectedIds.length !== 1 || isCoverLoading}
            onClick={handleGenerateCover}
          >
            {isCoverLoading ? 'AI 표지 생성 중...' : 'AI 표지생성'}
          </button>
          <button
            className="btn btn-primary btn-wide"
            disabled={selectedIds.length === 0 || isSummaryLoading}
            onClick={handleGenerateSummaryAndCategory}
          >
            {isSummaryLoading ? 'AI 요약 & 카테고리 생성 중...' : 'AI 요약 & 카테고리 설정'}
          </button>
          <button
            className="btn btn-danger btn-wide"
            disabled={selectedIds.length === 0}
            onClick={handleDeleteSelected}
          >
            선택된 원고 삭제
          </button>
        </div>

        {showCoverModal && (
          <div className="modal-overlay" onClick={() => setShowCoverModal(false)}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
              <h3>AI 생성 표지 미리보기</h3>
              {isCoverLoading ? (
                <p>이미지 생성 중입니다... 잠시만 기다려주세요.</p>
              ) : generatedCoverUrl ? (
                <img src={generatedCoverUrl} alt="생성된 표지 이미지" style={{ maxWidth: '100%' }} />
              ) : (
                <p>이미지를 불러올 수 없거나 생성에 실패했습니다.</p>
              )}
              <button className="btn close-btn small-btn" onClick={() => setShowCoverModal(false)}>
                닫기
              </button>
            </div>
          </div>
        )}

        {showSummaryModal && (
          <div className="modal-overlay" onClick={() => setShowSummaryModal(false)}>
            <div className="modal-content" onClick={e => e.stopPropagation()}>
              <h3>AI 요약 & 카테고리 결과</h3>
              {isSummaryLoading ? (
                <p>요약 및 카테고리 생성 중입니다...</p>
              ) : (
                <div className="summary-results">
                  {generatedSummaries.map((result) => (
                    <div key={result.id} className="summary-item">
                      <h4>{result.title}</h4>
                      {result.error ? (
                        <p style={{ color: 'red' }}>{result.error}</p>
                      ) : (
                        <>
                          <p><strong>요약:</strong> {result.summary}</p>
                          <p><strong>카테고리:</strong> {result.categories.join(', ')}</p>
                        </>
                      )}
                    </div>
                  ))}
                </div>
              )}
              <button className="btn close-btn small-btn" onClick={() => setShowSummaryModal(false)}>
                닫기
              </button>
            </div>
          </div>
        )}
      </div>
    </MainLayout>
  );
}
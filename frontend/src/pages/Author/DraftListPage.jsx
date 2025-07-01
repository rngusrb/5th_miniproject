import React, { useEffect, useState } from 'react';
import MainLayout from '../../components/layout/MainLayout';
import './DraftListPage.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

export default function DraftListPage() {
  const navigate = useNavigate();
  const BASE_URL = 'http://localhost:8088/manuscripts';
  const AUTHOR_ID = 1;

  const API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE';

  const [drafts, setDrafts] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);

  const [showCoverModal, setShowCoverModal] = useState(false);
  const [generatedCoverUrl, setGeneratedCoverUrl] = useState('');
  const [isCoverLoading, setIsCoverLoading] = useState(false);

  const [showSummaryModal, setShowSummaryModal] = useState(false);
  const [generatedSummaries, setGeneratedSummaries] = useState([]);
  const [isSummaryLoading, setIsSummaryLoading] = useState(false);

  useEffect(() => {
    const fetchDrafts = async () => {
      try {
        const res = await axios.get(`${BASE_URL}/${AUTHOR_ID}`, {
          headers: {
            Authorization: `Bearer ${API_TOKEN}`,
          },
        });
        const filtered = res.data.filter(d => d.status !== 'TEMP');

        console.log('--- fetchDrafts 필터링된 데이터 확인 ---');
        filtered.forEach((d, index) => {
          console.log(`원고 ${index + 1}의 ID:`, d.manuscriptId);
          console.log(`원고 ${index + 1}의 ID 타입:`, typeof d.manuscriptId);
        });
        console.log('------------------------------------');

        setDrafts(filtered);
      } catch (e) {
        console.error("원고 목록을 불러오지 못했습니다:", e);
        alert('원고 목록을 불러오지 못했습니다.');
      }
    };
    fetchDrafts();
  }, [BASE_URL, API_TOKEN, AUTHOR_ID]);

  const toggleSelect = id => {
    setSelectedIds(prev => {
      if (prev.includes(id)) {
        return prev.filter(i => i !== id);
      } else {
        return [...prev, id];
      }
    });
  };

  const handleGenerateCover = async () => {
    console.log('handleGenerateCover 호출됨');
    console.log('현재 selectedIds:', selectedIds);
    console.log('selectedIds의 길이:', selectedIds.length);

    if (selectedIds.length === 0) {
      alert('표지를 생성할 원고를 선택해주세요.');
      return;
    }

    const manuscriptId = Number(selectedIds[0]);

    console.log('변환된 manuscriptId:', manuscriptId);
    console.log('변환된 manuscriptId의 타입:', typeof manuscriptId);

    if (isNaN(manuscriptId) || typeof manuscriptId !== 'number') {
      alert('유효한 원고 ID가 없습니다.');
      return;
    }

    setIsCoverLoading(true);
    setGeneratedCoverUrl('');
    setShowCoverModal(true);

    try {
      const res = await axios.post(
        `${BASE_URL}/coverimage/${manuscriptId}`,
        null,
        {
          headers: {
            Authorization: `Bearer ${API_TOKEN}`,
          },
        }
      );

      // 백엔드 ManuscriptController.java에 따라 bookCoverImage 필드 사용
      const imageUrl = res.data?.bookCoverImage;
      if (imageUrl) {
        setGeneratedCoverUrl(imageUrl);
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
      if (isNaN(parsedId) || typeof parsedId !== 'number') {
        console.error(`유효하지 않은 ID (요약/카테고리): ${id}`);
        results.push({
          id,
          title: drafts.find(d => d.manuscriptId === id)?.title || `ID: ${id}`,
          error: '유효하지 않은 ID'
        });
        continue;
      }

      let summaryText = '요약 내용 없음';
      let categoriesList = ['카테고리 없음'];
      let errorOccurred = false;

      // 1. 요약 생성 API 호출
      try {
        const summaryRes = await axios.post(`${BASE_URL}/summary/${parsedId}`, {}, {
          headers: {
            Authorization: `Bearer ${API_TOKEN}`,
          },
        });
        summaryText = summaryRes.data?.summary || '요약 내용 없음';
      } catch (e) {
        console.error(`AI 요약 생성 실패 (ID: ${id}):`, e);
        errorOccurred = true;
      }

      // 2. 카테고리 생성 API 호출
      try {
        const categoryRes = await axios.post(`${BASE_URL}/category/${parsedId}`, {}, {
          headers: {
            Authorization: `Bearer ${API_TOKEN}`,
          },
        });
        categoriesList = categoryRes.data?.category ? [categoryRes.data.category] : ['카테고리 없음']; // 백엔드 응답에 'category' 필드 가정
      } catch (e) {
        console.error(`AI 카테고리 생성 실패 (ID: ${id}):`, e);
        errorOccurred = true;
      }

      results.push({
        id: parsedId,
        title: drafts.find(d => d.manuscriptId === parsedId)?.title || `ID: ${parsedId}`,
        summary: summaryText,
        categories: categoriesList,
        error: errorOccurred ? '생성 실패' : undefined
      });
    }
    setGeneratedSummaries(results);
    setIsSummaryLoading(false);

    const failedCount = results.filter(r => r.error).length;
    if (failedCount === 0) {
      alert('AI 요약 및 카테고리 생성이 완료되었습니다.');
    } else if (failedCount === selectedIds.length) {
      alert('선택된 모든 원고의 요약 및 카테고리 생성에 실패했습니다.');
    } else {
      alert(`${failedCount}개의 원고 요약 및 카테고리 생성에 실패했습니다.`);
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
                <p>요약 및 카테고리 생성 중입니다... 잠시만 기다려주세요.</p>
              ) : (
                <div className="summary-results">
                  {generatedSummaries.length > 0 ? (
                    generatedSummaries.map((result) => (
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
                    ))
                  ) : (
                    <p>생성된 요약 및 카테고리가 없습니다.</p>
                  )}
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
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import MainLayout from '../../components/layout/MainLayout';
import axiosInstance from '../../api/axiosInstance'; // axiosInstance 사용
import './PublishRequest.css';

export default function PublishRequestPage() {
  const navigate = useNavigate();
  const authorId = localStorage.getItem('userId'); // 로컬 스토리지에서 ID 가져오기

  const [drafts, setDrafts] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!authorId) {
      alert('로그인이 필요합니다.');
      navigate('/login/author');
      return;
    }

    const fetchReadyDrafts = async () => {
      setLoading(true);
      setError(null);
      try {
        // axiosInstance 사용, 상대 경로 및 헤더 자동 처리
        const res = await axiosInstance.get(`/manuscripts/${authorId}`);

        // 'REGISTERED' 상태의 원고 중, bookCoverImage, summary, category가 모두 비어있지 않은 원고만 필터링
        const readyForPublish = res.data.filter(d =>
          d.status === 'REGISTERED' &&
          d.bookCoverImage && d.bookCoverImage.trim() !== '' &&
          d.summary && d.summary.trim() !== '' &&
          d.category && d.category.trim() !== ''
        );

        setDrafts(readyForPublish);
      } catch (err) {
        console.error("출간 준비된 원고 목록을 불러오는데 실패했습니다:", err);
        setError('출간 준비된 원고 목록을 불러오는데 실패했습니다.');
      } finally {
        setLoading(false);
      }
    };

    fetchReadyDrafts();
  }, [authorId, navigate]);

  const toggleSelect = id => {
    setSelectedIds(prev =>
      prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id]
    );
  };

  const handleSubmit = async () => {
    if (selectedIds.length === 0) {
      alert('출간할 원고를 선택해주세요.');
      return;
    }

    if (!window.confirm(`선택된 원고 ${selectedIds.length}개를 즉시 출간하시겠습니까?`)) {
      return;
    }

    const idsToPublish = selectedIds.join(',');

    try {
        // axiosInstance 사용
        await axiosInstance.post(`/manuscripts/publish/${idsToPublish}`);

        alert(`${selectedIds.length}개의 원고가 성공적으로 출간되었습니다.`);
        navigate('/main/author');
    } catch (err) {
        console.error(`원고 출간 실패:`, err.response ? err.response.data : err.message);
        alert(`원고 출간에 실패했습니다. 콘솔을 확인해주세요.`);
    }
  };

  return (
    <MainLayout>
      <div className="publish-request-container">
        <div className="publish-header">
          <h2>출간 요청</h2>
          <button className="btn back-btn small-btn" onClick={() => navigate(-1)}>
            ← 홈으로
          </button>
        </div>

        {loading ? (
          <p>원고 목록을 불러오는 중...</p>
        ) : error ? (
          <p style={{ color: 'red' }}>{error}</p>
        ) : drafts.length === 0 ? (
          <p>AI 표지, 요약, 카테고리가 모두 생성 완료된 'REGISTERED' 상태의 출간 준비 원고가 없습니다. <br/> `원고 목록`에서 AI 기능을 먼저 사용하거나 원고를 'REGISTERED' 상태로 전환해주세요.</p>
        ) : (
          <ul className="draft-list">
            {drafts.map(draft => (
              <li key={draft.manuscriptId} className="draft-item">
                <input
                  type="checkbox"
                  className="item-checkbox"
                  checked={selectedIds.includes(draft.manuscriptId)}
                  onChange={() => toggleSelect(draft.manuscriptId)}
                />
                <span className="item-title">{draft.title}</span>
                <span className="item-meta">
                  표지 ✔︎ 요약 ✔︎ 카테고리: {draft.category}
                </span>
              </li>
            ))}
          </ul>
        )}

        <button
          className="btn btn-primary"
          disabled={selectedIds.length === 0}
          onClick={handleSubmit}
        >
          출간하기
        </button>
      </div>
    </MainLayout>
  );
}
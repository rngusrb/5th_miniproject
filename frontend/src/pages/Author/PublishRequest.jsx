import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import MainLayout from '../../components/layout/MainLayout';
import './PublishRequest.css';  // 필요시 스타일 정의

export default function PublishRequestPage() {
  const navigate = useNavigate();

  // 더미 데이터: cover, summary, categories가 모두 있는 원고만 포함
  const allDrafts = [
    { id: 1, title: '해리포터', hasCover: true, hasSummary: true, categories: ['판타지','모험'] },
    { id: 2, title: '왕좌의 게임', hasCover: false, hasSummary: true, categories: ['드라마'] },
    { id: 3, title: '오징어 게임', hasCover: true, hasSummary: true, categories: ['스릴러','서바이벌'] },
  ];

  // 필터링하여 준비된 원고 목록만
  const [drafts, setDrafts] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);

  useEffect(() => {
    const ready = allDrafts.filter(d => d.hasCover && d.hasSummary && d.categories.length > 0);
    setDrafts(ready);
  }, []);

  const toggleSelect = id => {
    setSelectedIds(prev =>
      prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id]
    );
  };

  const handleSubmit = () => {
    // TODO: 실제 API 연동
    alert(`출간 요청 보낼 원고 ID: ${selectedIds.join(', ')}`);
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

        {drafts.length === 0 ? (
          <p>출간 준비된 원고가 없습니다.</p>
        ) : (
          <ul className="draft-list">
            {drafts.map(draft => (
              <li key={draft.id} className="draft-item">
                <input
                  type="checkbox"
                  className="item-checkbox"
                  checked={selectedIds.includes(draft.id)}
                  onChange={() => toggleSelect(draft.id)}
                />
                <span className="item-title">{draft.title}</span>
                <span className="item-meta">
                  표지 ✔︎ 요약 ✔︎ 카테고리: {draft.categories.join(', ')}
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
          출간 요청 보내기
        </button>
      </div>
    </MainLayout>
  );
}

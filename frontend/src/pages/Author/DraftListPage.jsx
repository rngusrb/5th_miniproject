import React, { useState } from 'react';
import MainLayout from '../../components/layout/MainLayout';
import './DraftListPage.css';
import { useNavigate } from 'react-router-dom';

export default function DraftListPage() {
  const navigate = useNavigate();
  const dummyDrafts = [
    { id: 1, title: '해리포터', updatedAt: '2025-06-29' },
    { id: 2, title: '왕좌의 게임', updatedAt: '2025-06-28' },
    { id: 3, title: '오징어 게임', updatedAt: '2025-06-27' },
  ];
  const dummyCategories = ['판타지', '모험'];  // → 2개만 표시

  const [drafts] = useState(dummyDrafts);
  const [selectedIds, setSelectedIds] = useState([]);
  const [showCoverModal, setShowCoverModal] = useState(false);
  const [showSummaryModal, setShowSummaryModal] = useState(false);

  const toggleSelect = id => {
  setSelectedIds(prev => (
    prev.includes(id)
      ? []      // 이미 선택된 항목이면 해제
      : [id]    // 아니면 그 항목만 선택
  ));
};

  return (
    <MainLayout>
      <div className="draft-container-wide">
        {/* 헤더: 제목 & 뒤로가기 버튼 */}
        <div className="draft-header">
          <h2 className="draft-title">원고 목록</h2>
          <button
            className="btn back-btn small-btn"
            onClick={() => navigate(-1)}
          >
            ← 홈으로
          </button>
        </div>
        <h2 className="draft-title"></h2>
        <ul className="draft-list">
          {drafts.map(draft => (
            <li key={draft.id} className="draft-item">
              <span className="item-title">{draft.title}</span>
              <span className="item-date">{draft.updatedAt}</span>
              <input
                type="checkbox"
                className="item-checkbox"
                checked={selectedIds.includes(draft.id)}
                onChange={() => toggleSelect(draft.id)}
              />
            </li>
          ))}
        </ul>

        <div className="btn-group">
          <button
            className="btn btn-primary btn-wide"
            disabled={selectedIds.length === 0}
            onClick={() => setShowCoverModal(true)}
          >
            AI 표지생성
          </button>
          <button
            className="btn btn-primary btn-wide"
            disabled={selectedIds.length === 0}
            onClick={() => setShowSummaryModal(true)}
          >
            AI 요약 & 카테고리 설정
          </button>
        </div>

        {showCoverModal && (
          <div className="modal-overlay" onClick={() => setShowCoverModal(false)}>
            <div className="modal-content" onClick={e => e.stopPropagation()}>
              <h3>AI 생성 표지 미리보기</h3>
              <img
                src="https://via.placeholder.com/200x300?text=AI+Cover"
                alt="AI Cover"
                className="modal-image"
              />
              <button
                className="btn close-btn small-btn"
                onClick={() => setShowCoverModal(false)}
              >
                닫기
              </button>
            </div>
          </div>
        )}

        {showSummaryModal && (
          <div className="modal-overlay" onClick={() => setShowSummaryModal(false)}>
            <div className="modal-content" onClick={e => e.stopPropagation()}>
              <h3>AI 요약 & 카테고리</h3>
              <a
                href="/dummy-summary.pdf"
                download="summary.pdf"
                className="btn download-btn small-btn"
              >
                AI 요약 PDF 다운로드
              </a>
              <ul className="category-list">
                {dummyCategories.map(cat => <li key={cat}>{cat}</li>)}
              </ul>
              <button
                className="btn close-btn small-btn"
                onClick={() => setShowSummaryModal(false)}
              >
                닫기
              </button>
            </div>
          </div>
        )}
      </div>
    </MainLayout>
  );
}





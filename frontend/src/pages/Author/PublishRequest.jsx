// PublishRequest.jsx 파일

import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import MainLayout from '../../components/layout/MainLayout';
import axios from 'axios';
import './PublishRequest.css';

export default function PublishRequestPage() {
  const navigate = useNavigate();

  const BASE_URL = 'http://localhost:8088/manuscripts';
  const AUTHOR_ID = 1; // 현재 사용자의 ID를 가정
  const API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE'; // 실제 토큰으로 대체 필요

  const [drafts, setDrafts] = useState([]);
  const [selectedIds, setSelectedIds] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchReadyDrafts = async () => {
      setLoading(true);
      setError(null);
      try {
        const res = await axios.get(`${BASE_URL}/${AUTHOR_ID}`, {
          headers: {
            Authorization: `Bearer ${API_TOKEN}`,
          },
        });

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
  }, [BASE_URL, AUTHOR_ID, API_TOKEN]);

  const toggleSelect = id => {
    setSelectedIds(prev =>
      prev.includes(id) ? prev.filter(x => x !== id) : [...prev, id]
    );
  };

  const handleSubmit = async () => {
    if (selectedIds.length === 0) {
      alert('출간 요청할 원고를 선택해주세요.');
      return;
    }

    if (!window.confirm(`선택된 원고 ${selectedIds.length}개를 출간 요청하시겠습니까?`)) {
      return;
    }

    let successCount = 0;
    let failCount = 0;

    for (const id of selectedIds) {
      try {
        // 백엔드 API에 PATCH 요청을 보내 원고의 상태를 'REQUESTED'로 변경
        // 이 방식은 'edit' 엔드포인트를 사용하여 status 필드만 업데이트하며,
        // 다른 필드들은 영향을 받지 않습니다.
        await axios.patch(`${BASE_URL}/edit/${id}`, { status: 'REQUESTED' }, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${API_TOKEN}`,
          },
        });
        successCount++;
      } catch (err) {
        console.error(`원고 ID ${id} 출간 요청 실패:`, err);
        failCount++;
      }
    }

    if (successCount > 0) {
      alert(`${successCount}개의 원고 출간 요청이 성공적으로 처리되었습니다.`);
      navigate('/main/author'); // 요청 후 작가 메인 페이지로 이동
    }
    if (failCount > 0) {
      alert(`${failCount}개의 원고 출간 요청에 실패했습니다. 콘솔을 확인해주세요.`);
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
          출간 요청 보내기
        </button>
      </div>
    </MainLayout>
  );
}
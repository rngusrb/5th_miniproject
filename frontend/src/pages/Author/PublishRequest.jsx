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
  const [loading, setLoading] = useState(true); //
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
      alert('출간할 원고를 선택해주세요.');
      return;
    }

    if (!window.confirm(`선택된 원고 ${selectedIds.length}개를 즉시 출간하시겠습니까?`)) {
      return;
    }

    // 선택된 ID들을 콤마로 구분된 문자열로 변환 (예: "1,2,3")
    const idsToPublish = selectedIds.join(',');

    try {
        // 백엔드 API의 `/publish/{ids}` 엔드포인트에 POST 요청을 보내 원고들을 즉시 출간합니다.
        // 이 엔드포인트는 ManuscriptController.java의 publishManuscript 메서드에 해당합니다.
        // 해당 API가 요청 본문(body)을 필요로 하지 않는다면, 두 번째 인자는 null로 전달합니다.
        await axios.post(`${BASE_URL}/publish/${idsToPublish}`, null, {
          headers: {
            'Content-Type': 'application/json', // 백엔드가 JSON을 기대하는 경우 유지
            'Authorization': `Bearer ${API_TOKEN}`,
          },
        });

        alert(`${selectedIds.length}개의 원고가 성공적으로 출간되었습니다.`); // 메시지 변경
        navigate('/main/author'); // 출간 후 작가 메인 페이지로 이동
    } catch (err) {
        console.error(`원고 출간 실패:`, err.response ? err.response.data : err.message); // 오류 상세 출력
        alert(`원고 출간에 실패했습니다. 콘솔을 확인해주세요.`); // 메시지 변경
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
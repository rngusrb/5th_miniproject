import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import MainLayout from '../../components/layout/MainLayout';
import './DraftListPage.css';  // 기존 스타일 재활용

export default function PublishConfirmationPage() {
  const navigate = useNavigate();

  // 더미 출간 요청 내역
  const dummyRequests = [
    { id: 1, title: '해리포터', status: '승인됨' },
    { id: 2, title: '오징어 게임', status: '검토중' },
    { id: 3, title: '왕좌의 게임', status: '반려됨' },
  ];

  const [requests, setRequests] = useState([]);

  useEffect(() => {
    setRequests(dummyRequests);
  }, []);

  return (
    <MainLayout>
      <div className="draft-container-wide">
        <div className="draft-header">
          <h2 className="draft-title">출간 확인</h2>
          <button
            className="btn back-btn small-btn"
            onClick={() => navigate(-1)}
          >
            ← 뒤로
          </button>
        </div>

        {requests.length === 0 ? (
          <p>출간 요청 내역이 없습니다.</p>
        ) : (
          <ul className="draft-list">
            {requests.map(req => (
              <li key={req.id} className="draft-item">
                <span className="item-title">{req.title}</span>
                <span
                  className="item-date"
                  style={{
                    color:
                      req.status === '승인됨'
                        ? 'green'
                        : req.status === '반려됨'
                        ? 'red'
                        : '#666',
                  }}
                >
                  {req.status}
                </span>
              </li>
            ))}
          </ul>
        )}
      </div>
    </MainLayout>
  );
}


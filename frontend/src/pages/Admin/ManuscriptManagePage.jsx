import React, { useEffect, useState } from 'react';
import axiosInstance from '../../api/axiosInstance';
import './ManuscriptManagePage.css';

const ManuscriptManagePage = () => {
  const [manuscripts, setManuscripts] = useState([]);
  const ADMIN_API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE';

  useEffect(() => {
    fetchManuscripts();
  }, []);

  const fetchManuscripts = async () => {
    try {
      const res = await axiosInstance.get('/manuscripts', {
        headers: {
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      const allManuscripts = res.data._embedded?.manuscripts || res.data || [];
      const filteredManuscripts = allManuscripts.filter(m => m.status !== 'TEMP');
      setManuscripts(filteredManuscripts);
    } catch (err) {
      console.error('❌ 원고 목록 불러오기 실패', err);
      alert('원고 목록을 불러오는데 실패했습니다.');
    }
  };

  const handleConfirmRequest = async (id) => {
    try {
      await axiosInstance.patch(`/manuscripts/${id}`, { status: 'PENDING' }, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      alert('출간 요청 확인 완료');
      setManuscripts((prev) =>
        prev.map((m) => (m.manuscriptId === id ? { ...m, status: 'PENDING' } : m))
      );
    } catch (err) {
      console.error('❌ 출간 요청 확인 실패', err);
      alert('출간 요청 확인 실패');
    }
  };

  const handleApprove = async (id) => {
    try {
      await axiosInstance.post(`/manuscripts/publish/${id}`, {}, {
        headers: {
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      alert('출간 승인 완료');
      setManuscripts((prev) =>
        prev.map((m) => (m.manuscriptId === id ? { ...m, status: 'APPROVED' } : m))
      );
    } catch (err) {
      console.error('❌ 출간 승인 실패', err);
      alert('승인 실패');
    }
  };

  // 삭제 버튼을 위한 핸들러 함수 추가
  const handleDelete = async (id) => {
    if (!window.confirm(`해당 원고를 정말로 삭제하시겠습니까?`)) {
        return;
    }
    try {
        await axiosInstance.delete(`/manuscripts/${id}`, {
            headers: {
                Authorization: `Bearer ${ADMIN_API_TOKEN}`,
            },
        });
        alert('원고가 성공적으로 삭제되었습니다.');
        // 삭제 후 목록에서 해당 원고를 제거
        setManuscripts(prev => prev.filter(m => m.manuscriptId !== id));
    } catch (err) {
        console.error('❌ 원고 삭제 실패', err);
        alert('원고 삭제에 실패했습니다.');
    }
  };

  return (
    <div className="manuscript-manage-page">
      <h2 className="manuscript-title">원고 목록</h2>
      <table className="manuscript-table">
        <thead>
          <tr>
            <th>제목</th>
            <th>작가</th>
            <th>등록일</th>
            <th>관리</th>
          </tr>
        </thead>
        <tbody>
          {manuscripts.map((m) => (
            <tr key={m.manuscriptId}>
              <td>{m.title}</td>
              <td>{m.authorName || '알 수 없음'}</td>
              <td>{m.createDate ? m.createDate.slice(0, 10) : '-'}</td>
              <td>
                {m.status === 'REQUESTED' && (
                  <button
                    className="action-btn confirm-btn"
                    onClick={() => handleConfirmRequest(m.manuscriptId)}
                  >
                    요청 확인
                  </button>
                )}
                {m.status === 'PENDING' && (
                  <button
                    className="action-btn approve-btn"
                    onClick={() => handleApprove(m.manuscriptId)}
                  >
                    출간 승인
                  </button>
                )}
                <button
                  className="action-btn delete-btn"
                  onClick={() => handleDelete(m.manuscriptId)}
                >
                  삭제
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ManuscriptManagePage;
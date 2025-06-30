import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ManuscriptManagePage.css';

const ManuscriptManagePage = () => {
  const [manuscripts, setManuscripts] = useState([]);

  useEffect(() => {
    fetchManuscripts();
  }, []);

  const fetchManuscripts = async () => {
    try {
      const res = await axios.get('/api/v1/manuscripts');
      console.log('✅ 원고 목록 응답:', res.data);
      setManuscripts(res.data);
    } catch (err) {
      console.error('❌ 원고 목록 불러오기 실패', err);
    }
  };

  const handleApprove = async (id) => {
    try {
      await axios.post(`/api/admin/manuscripts/${id}/approve`);
      alert('출간 승인 완료');
      setManuscripts((prev) =>
        prev.map((m) => (m.id === id ? { ...m, status: 'APPROVED' } : m))
      );
    } catch (err) {
      console.error('❌ 출간 승인 실패', err);
      alert('승인 실패');
    }
  };

  return (
    <div className="manuscript-manage-page">
      <h2 className="manuscript-title">원고 관리</h2>
      <table className="manuscript-table">
        <thead>
          <tr>
            <th>제목</th>
            <th>작가</th>
            <th>등록일</th>
            <th>출간 요청</th>
            <th>상태</th>
            <th>승인</th>
          </tr>
        </thead>
        <tbody>
          {manuscripts.map((m, index) => (
            <tr key={m.id || index}>
              <td>{m.title}</td>
              <td>{m.authorName || '알 수 없음'}</td>
              <td>{m.createdAt ? m.createdAt.slice(0, 10) : '-'}</td>
              <td>{m.requested ? '요청됨' : '미요청'}</td>
              <td>{m.status}</td>
              <td>
                {m.status === 'PENDING' && m.requested ? (
                  <button
                    className="approve-btn"
                    onClick={() => handleApprove(m.id)}
                  >
                    출간 승인
                  </button>
                ) : (
                  '-'
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ManuscriptManagePage;

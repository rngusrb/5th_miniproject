import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './ManuscriptManagePage.css';

const ManuscriptManagePage = () => {
  const [manuscripts, setManuscripts] = useState([]);
  // 관리자 API 토큰 (실제 환경에서는 환경 변수 등으로 관리)
  const ADMIN_API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE';

  useEffect(() => {
    fetchManuscripts();
  }, []);

  const fetchManuscripts = async () => {
    try {
      // GET /manuscripts 엔드포인트를 사용하여 모든 원고를 불러옴
      const res = await axios.get('http://localhost:8088/manuscripts', {
        headers: {
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      console.log('✅ 원고 목록 응답:', res.data);
      // TEMP 상태는 제외하고 필터링
      const filteredManuscripts = res.data.filter(m => m.status !== 'TEMP');
      setManuscripts(filteredManuscripts);
    } catch (err) {
      console.error('❌ 원고 목록 불러오기 실패', err);
      alert('원고 목록을 불러오는데 실패했습니다.');
    }
  };

  const handleConfirmRequest = async (id) => {
    try {
      // PATCH /manuscripts/{id} 엔드포인트를 사용하여 상태를 'PENDING'으로 변경
      await axios.patch(`http://localhost:8088/manuscripts/${id}`, { status: 'PENDING' }, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      alert('출간 요청 확인 완료');
      // UI 상태 업데이트
      setManuscripts((prev) =>
        prev.map((m) => (m.manuscriptId === id ? { ...m, status: 'PENDING' } : m)) // manuscriptId 사용
      );
    } catch (err) {
      console.error('❌ 출간 요청 확인 실패', err);
      alert('출간 요청 확인 실패');
    }
  };

  const handleApprove = async (id) => {
    try {
      // POST /manuscripts/publish/{ids}를 사용하여 최종 승인 (여기서는 단일 ID)
      // 또는 기존 /api/admin/manuscripts/${id}/approve 엔드포인트를 유지
      // 명세에 /manuscripts/publish/{ids}가 있으므로 이것을 활용하는 것이 더 적절할 수 있음.
      // 만약 단일 승인이라면 PATCH /manuscripts/{id} { status: 'APPROVED' }도 가능.
      // 여기서는 명세에 있는 POST /manuscripts/publish/{ids}를 사용한다고 가정하고
      // 단일 원고 승인이므로 ids 배열에 하나만 담아서 보냅니다.
      await axios.post(`http://localhost:8088/manuscripts/publish/${id}`, {}, { // {id} 자리에 직접 id를 넣어 보내는 형식 (예: /publish/123)
        headers: {
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      alert('출간 승인 완료');
      setManuscripts((prev) =>
        prev.map((m) => (m.manuscriptId === id ? { ...m, status: 'APPROVED' } : m)) // manuscriptId 사용
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
            <th>출간 요청 여부</th>
            <th>상태</th>
            <th>관리</th>
          </tr>
        </thead>
        <tbody>
          {manuscripts.map((m) => ( // key에 index 대신 m.manuscriptId 사용
            <tr key={m.manuscriptId}>
              <td>{m.title}</td>
              <td>{m.authorName || '알 수 없음'}</td> {/* authorName 필드가 없다면 백엔드에서 제공해야 함 */}
              <td>{m.createDate ? m.createDate.slice(0, 10) : '-'}</td> {/* createdAt 대신 createDate 사용 */}
              {/* 출간 요청 여부는 상태에 따라 판단 */}
              <td>
                {m.status === 'REQUESTED' || m.status === 'PENDING' || m.status === 'APPROVED' ? '요청됨' : '미요청'}
              </td>
              <td>{m.status}</td>
              <td>
                {m.status === 'REQUESTED' ? (
                  <button
                    className="action-btn confirm-btn"
                    onClick={() => handleConfirmRequest(m.manuscriptId)}
                  >
                    요청 확인
                  </button>
                ) : m.status === 'PENDING' ? (
                  <button
                    className="action-btn approve-btn"
                    onClick={() => handleApprove(m.manuscriptId)}
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
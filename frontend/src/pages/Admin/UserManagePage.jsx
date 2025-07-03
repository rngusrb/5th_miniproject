import React, { useEffect, useState } from 'react';
import axiosInstance from '../../api/axiosInstance'; // axios 대신 axiosInstance를 사용
import './UserManagePage.css';

// 이 페이지에서만 사용할 관리자 토큰 정의
const API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE';

const UserManagePage = () => {
  const [users, setUsers] = useState([]);
  const [selected, setSelected] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      // axiosInstance를 사용하고, 헤더를 직접 추가합니다.
      const res = await axiosInstance.get('/users', {
        headers: {
          Authorization: `Bearer ${API_TOKEN}`
        }
      });
      console.log(res);
      setUsers(res.data);
    } catch (err) {
      console.error('유저 불러오기 실패', err);
      alert('유저 정보를 불러오는데 실패했습니다. 백엔드 서버 상태 및 API 경로를 확인해주세요.');
    }
  };

  const handleCheckboxChange = (id) => {
    setSelected((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  const handleDelete = async () => {
    if (selected.length === 0) {
      alert('삭제할 유저를 선택하세요.');
      return;
    }

    try {
      for (const userId of selected) {
        // axiosInstance를 사용하고, 헤더를 직접 추가합니다.
        await axiosInstance.delete(`/users/${userId}`, {
          headers: {
            Authorization: `Bearer ${API_TOKEN}`
          }
        });
      }

      alert('유저 삭제 완료');
      setUsers((prev) => prev.filter((u) => !selected.includes(u.userId)));
      setSelected([]);
    } catch (err) {
      console.error('삭제 실패', err);
      alert('삭제 실패');
    }
  };

  return (
    <div className="user-manage-page">
      <h2 className="user-manage-title">유저 관리</h2>
      <table className="user-manage-table">
        <thead>
          <tr>
            <th>선택</th>
            <th>아이디</th>
            <th>비밀번호 (표시 안 함)</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.userId}>
              <td>
                <input
                  type="checkbox"
                  checked={selected.includes(user.userId)}
                  onChange={() => handleCheckboxChange(user.userId)}
                />
              </td>
              <td>{user.userId}</td>
              <td>********</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="user-manage-button" onClick={handleDelete}>
        선택한 회원 삭제
      </button>
    </div>
  );
};

export default UserManagePage;
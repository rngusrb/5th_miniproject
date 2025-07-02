import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './UserManagePage.css';

// API 토큰 정의
const API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE';

const UserManagePage = () => {
  const [users, setUsers] = useState([]);
  const [selected, setSelected] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      // URL을 백엔드 UserController.java의 @GetMapping에 정의된 '/users'로 수정합니다.
      // 기본 백엔드 URL이 'http://localhost:8088'이라고 가정하면, '/users'로만 호출합니다.
      const res = await axios.get('http://localhost:8088/users', {
        headers: {
          Authorization: `Bearer ${API_TOKEN}` // Bearer 토큰 형식 사용
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
      // 백엔드 UserController.java의 @DeleteMapping("/{id}")에 맞춰 수정
      // 각 선택된 userId에 대해 개별 DELETE 요청을 보냅니다.
      // 백엔드는 /users/{id} 형태의 단일 삭제를 지원합니다.
      // 여러 유저를 한 번에 삭제하려면 백엔드에 POST /users/delete-batch 와 같은 API가 필요합니다.
      // 현재는 선택된 각 유저에 대해 개별 요청을 보냅니다.
      for (const userId of selected) {
        await axios.delete(`http://localhost:8088/users/${userId}`, { // <-- 여기를 수정!
          headers: {
            Authorization: `Bearer ${API_TOKEN}`
          }
        });
      }

      alert('유저 삭제 완료');
      // 삭제된 유저를 목록에서 제거
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
            <th>아이디</th> {/* 이름 대신 아이디로 변경 */}
            <th>비밀번호 (표시 안 함)</th> {/* 이메일 대신 비밀번호 필드 */}
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
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './UserManagePage.css';

const UserManagePage = () => {
  const [users, setUsers] = useState([]);
  const [selected, setSelected] = useState([]);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const res = await axios.get('/api/admin/users');
      setUsers(res.data);
    } catch (err) {
      console.error('유저 불러오기 실패', err);
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
      await axios.post('/api/admin/delete-users', { userIds: selected });
      alert('유저 삭제 완료');
      setUsers((prev) => prev.filter((u) => !selected.includes(u.id)));
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
            <th>이름</th>
            <th>이메일</th>
            <th>가입일</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>
                <input
                  type="checkbox"
                  checked={selected.includes(user.id)}
                  onChange={() => handleCheckboxChange(user.id)}
                />
              </td>
              <td>{user.name}</td>
              <td>{user.email}</td>
              <td>{user.createdAt?.slice(0, 10)}</td>
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

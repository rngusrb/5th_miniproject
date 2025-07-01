import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AuthorManagePage.css';

const AuthorManagePage = () => {
  const [authors, setAuthors] = useState([]);
  const [selected, setSelected] = useState([]);

  useEffect(() => {
    fetchAuthors();
  }, []);

  const fetchAuthors = async () => {
    try {
      const res = await axios.get('/api/admin/authors');
      setAuthors(res.data);
    } catch (err) {
      console.error('작가 목록 불러오기 실패', err);
    }
  };

  const handleCheckboxChange = (id) => {
    setSelected((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  const handleDelete = async () => {
    if (selected.length === 0) {
      alert('삭제할 작가를 선택하세요.');
      return;
    }

    try {
      await axios.post('/api/admin/delete-authors', { authorIds: selected });
      alert('작가 삭제 완료');
      setAuthors((prev) => prev.filter((a) => !selected.includes(a.id)));
      setSelected([]);
    } catch (err) {
      alert('삭제 실패');
      console.error(err);
    }
  };

  return (
    <div className="author-manage-page">
      <h2 className="author-manage-title">작가 관리</h2>
      <table className="author-manage-table">
        <thead>
          <tr>
            <th>선택</th>
            <th>이름</th>
            <th>이메일</th>
            <th>포트폴리오</th>
          </tr>
        </thead>
        <tbody>
          {authors.map((author) => (
            <tr key={author.id}>
              <td>
                <input
                  type="checkbox"
                  checked={selected.includes(author.id)}
                  onChange={() => handleCheckboxChange(author.id)}
                />
              </td>
              <td>{author.name}</td>
              <td>{author.email}</td>
              <td>
                <a href={author.portfolioUrl} target="_blank" rel="noopener noreferrer">
                  보기
                </a>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="author-manage-button" onClick={handleDelete}>
        선택한 작가 삭제
      </button>
    </div>
  );
};

export default AuthorManagePage;

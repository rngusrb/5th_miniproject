import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './AuthorApprovalPage.css';

const AuthorApprovalPage = () => {
  const [authors, setAuthors] = useState([]);
  const [selected, setSelected] = useState([]);

  useEffect(() => {
    axios.get('/api/admin/pending-authors').then((res) => setAuthors(res.data));
  }, []);

  const handleCheckboxChange = (id) => {
    setSelected((prev) =>
      prev.includes(id) ? prev.filter((i) => i !== id) : [...prev, id]
    );
  };

  const handleApprove = async () => {
    if (selected.length === 0) {
      alert('승인할 작가를 선택하세요.');
      return;
    }

    try {
      await axios.post('/api/admin/approve-authors', { authorIds: selected });
      alert('작가 승인 완료');
      setAuthors((prev) => prev.filter((a) => !selected.includes(a.id)));
      setSelected([]);
    } catch (err) {
      alert('작가 승인 실패');
    }
  };

  return (
    <div className="approval-page">
      <h2 className="approval-title">작가 승인 요청</h2>
      <table className="approval-table">
        <thead>
          <tr>
            <th>선택</th>
            <th>이름</th>
            <th>소개</th>
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
              <td>{author.intro}</td>
              <td>
                <a href={author.portfolioUrl} target="_blank" rel="noopener noreferrer">
                  보기
                </a>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <button className="approval-button" onClick={handleApprove}>
        선택한 작가 승인
      </button>
    </div>
  );
};

export default AuthorApprovalPage;

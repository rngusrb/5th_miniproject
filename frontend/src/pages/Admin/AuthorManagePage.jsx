import React, { useEffect, useState } from 'react';
import axiosInstance from '../../api/axiosInstance';
import './AuthorManagePage.css';

// 이 페이지에서만 사용할 관리자 토큰 정의
const ADMIN_API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE';

const AuthorManagePage = () => {
  const [authors, setAuthors] = useState([]);
  const [selected, setSelected] = useState([]);

  useEffect(() => {
    fetchAuthors();
  }, []);

  const fetchAuthors = async () => {
    try {
      const res = await axiosInstance.get('/authors', {
        headers: {
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      // API 응답 구조에 따라 실제 데이터 경로를 설정합니다. (HATEOAS 또는 단순 배열)
      setAuthors(res.data._embedded?.authors || res.data || []);
    } catch (err) {
      console.error('작가 목록 불러오기 실패', err);
      alert('작가 목록을 불러오는데 실패했습니다.');
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

    if (!window.confirm(`선택된 작가 ${selected.length}명을 정말로 삭제하시겠습니까?`)) {
      return;
    }

    try {
      for (const authorId of selected) {
        await axiosInstance.delete(`/authors/${authorId}`, {
          headers: {
            Authorization: `Bearer ${ADMIN_API_TOKEN}`,
          },
        });
      }

      alert('작가 삭제 완료');
      setAuthors((prev) => prev.filter((a) => !selected.includes(a.authorId)));
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
            <th>로그인 아이디</th>
            <th>이름</th>
            <th>정보</th>
            <th>포트폴리오</th>
          </tr>
        </thead>
        <tbody>
          {authors.map((author) => (
            <tr key={author.authorId}>
              <td>
                <input
                  type="checkbox"
                  checked={selected.includes(author.authorId)}
                  onChange={() => handleCheckboxChange(author.authorId)}
                />
              </td>
              <td>{author.authorLoginId}</td>
              <td>{author.authorName}</td>
              <td>{author.authorInfo}</td>
              <td>
                {author.authorPortfolio && (
                  <a href={author.authorPortfolio} target="_blank" rel="noopener noreferrer">
                    보기
                  </a>
                )}
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
import React, { useEffect, useState } from 'react';
import axiosInstance from '../../api/axiosInstance';
import './AuthorApprovalPage.css';

const AuthorApprovalPage = () => {
  const [authors, setAuthors] = useState([]);
  const [selected, setSelected] = useState([]);

  // 포트폴리오 URL을 완전한 주소로 만들어주는 함수
  const formatPortfolioUrl = (url) => {
    if (!url) {
      return null;
    }
    // URL이 http:// 또는 https:// 로 시작하지 않으면, 앞에 // 를 붙여줍니다.
    if (!url.startsWith('http://') && !url.startsWith('https://')) {
      return `//${url}`;
    }
    return url;
  };

  const fetchPendingAuthors = async () => {
    try {
      const res = await axiosInstance.get('/authors');
      const pending = res.data.filter(author => author.isActive === false);
      setAuthors(pending);
    } catch (err) {
      console.error("승인 대기 작가 목록 조회 실패:", err);
      alert("작가 목록을 불러오는데 실패했습니다.");
    }
  };

  useEffect(() => {
    fetchPendingAuthors();
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
      const approvalPromises = selected.map(authorId => {
        return axiosInstance.put(`/authors/${authorId}`, { isActive: true });
      });

      await Promise.all(approvalPromises);
      alert('선택한 작가를 모두 승인했습니다.');
      fetchPendingAuthors();
      setSelected([]);
    } catch (err) {
      console.error("작가 승인 처리 실패:", err);
      alert('작가 승인 중 오류가 발생했습니다.');
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
            <tr key={author.authorId}>
              <td>
                <input
                  type="checkbox"
                  checked={selected.includes(author.authorId)}
                  onChange={() => handleCheckboxChange(author.authorId)}
                />
              </td>
              <td>{author.authorName}</td>
              <td>{author.authorInfo}</td>
              <td>
                {/* 🔽 a 태그의 href 부분을 수정합니다. */}
                <a href={formatPortfolioUrl(author.authorPortfolio)} target="_blank" rel="noopener noreferrer">
                  {author.authorPortfolio ? '보기' : '-'}
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
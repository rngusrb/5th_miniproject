import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../api/axiosInstance';
import './AuthorRegister.css';

const AuthorRegister = () => {
  const navigate = useNavigate();
  const authorId = localStorage.getItem('userId');

  const [form, setForm] = useState({
    authorName: '',
    authorInfo: '',
    authorPortfolio: '',
  });

  useEffect(() => {
    if (!authorId) {
      alert('로그인이 필요합니다.');
      navigate('/login/author');
      return;
    }

    const fetchAuthorData = async () => {
      try {
        const res = await axiosInstance.get(`/authors/${authorId}`);
        if (res.data) {
          setForm({
            authorName: res.data.authorName || '',
            authorInfo: res.data.authorInfo || '',
            authorPortfolio: res.data.authorPortfolio || '',
          });
        }
      } catch (err) {
        console.error("작가 정보 로딩 실패:", err);
      }
    };

    fetchAuthorData();
  }, [authorId, navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!authorId) {
      alert("세션이 만료되었습니다. 다시 로그인해주세요.");
      navigate('/login/author');
      return;
    }

    try {
      await axiosInstance.put(`/authors/${authorId}`, form);
      alert('작가 정보가 성공적으로 제출(수정)되었습니다.');
      navigate('/main/author');
    } catch (err) {
      console.error('작가 정보 제출 실패:', err);
      alert('정보 제출에 실패했습니다. 다시 시도해주세요.');
    }
  };

  return (
    <div className="author-register-container">
      <h2 className="author-register-title">작가 정보 등록/수정</h2>
      <p>작가 활동을 위해 프로필 정보를 입력해주세요. 관리자 승인 후 모든 기능을 이용할 수 있습니다.</p>
      <form className="author-register-form" onSubmit={handleSubmit}>
        <label className="author-register-label">이름</label>
        <input
          type="text"
          name="authorName"
          value={form.authorName}
          onChange={handleChange}
          className="author-register-input"
          required
        />

        {/* 자기소개 필드 */}
        {/* 오류 수정 */}
        <label className="author-register-label">자기소개</label>
        <textarea
          name="authorInfo"
          value={form.authorInfo}
          onChange={handleChange}
          className="author-register-textarea"
          rows="4"
          required
        />

        {/* 포트폴리오 필드 */}
        <label className="author-register-label">포트폴리오 (선택)</label>
        <input
          type="text"
          name="authorPortfolio"
          placeholder="개인 웹사이트, 블로그, SNS 주소 등"
          value={form.authorPortfolio}
          onChange={handleChange}
          className="author-register-input"
        />

        <button type="submit" className="author-register-button">
          등록 요청
        </button>
      </form>
    </div>
  );
};

export default AuthorRegister;
import React, { useState } from 'react';
import './AuthorRegister.css';

const AuthorRegister = () => {
  const [form, setForm] = useState({
    name: '',
    bio: '',
    email: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('작가 등록 정보:', form);
    alert('작가 등록 요청이 제출되었습니다.');
  };

  return (
    <div className="author-register-container">
      <h2 className="author-register-title">작가 등록</h2>
      <form className="author-register-form" onSubmit={handleSubmit}>
        <label className="author-register-label">이름</label>
        <input
          type="text"
          name="name"
          value={form.name}
          onChange={handleChange}
          className="author-register-input"
          required
        />

        <label className="author-register-label">이메일</label>
        <input
          type="email"
          name="email"
          value={form.email}
          onChange={handleChange}
          className="author-register-input"
          required
        />

        <label className="author-register-label">자기소개</label>
        <textarea
          name="bio"
          value={form.bio}
          onChange={handleChange}
          className="author-register-textarea"
          rows="4"
          required
        />

        <button type="submit" className="author-register-button">
          등록 요청
        </button>
      </form>
    </div>
  );
};

export default AuthorRegister;

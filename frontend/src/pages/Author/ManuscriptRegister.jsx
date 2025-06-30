import React, { useState } from 'react';
import './ManuscriptRegister.css';

const ManuscriptRegister = () => {
  const [form, setForm] = useState({
    title: '',
    description: '',
    genre: '',
    file: null,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    setForm((prev) => ({ ...prev, file: e.target.files[0] }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append('title', form.title);
    formData.append('genre', form.genre);
    formData.append('description', form.description);
    formData.append('file', form.file);

    // 서버에 전송하는 로직 (예: axios 사용)
    console.log('폼 제출:', form);
    alert('원고 등록이 완료되었습니다!');
  };

  return (
    <div className="manuscript-container">
      <h2 className="manuscript-title">원고 등록</h2>
      <form className="manuscript-form" onSubmit={handleSubmit}>
        <label>제목</label>
        <input
          type="text"
          name="title"
          value={form.title}
          onChange={handleChange}
          required
        />

        <label>장르</label>
        <input
          type="text"
          name="genre"
          value={form.genre}
          onChange={handleChange}
          required
        />

        <label>내용 요약</label>
        <textarea
          name="description"
          value={form.description}
          onChange={handleChange}
          rows="4"
          required
        />

        <label>원고 파일 첨부</label>
        <input
          type="file"
          name="file"
          accept=".pdf,.doc,.docx,.txt"
          onChange={handleFileChange}
          required
        />

        <button type="submit">등록 요청</button>
      </form>
    </div>
  );
};

export default ManuscriptRegister;

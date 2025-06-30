import React, { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './ManuscriptRegister.css';

function ManuscriptRegister() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    title: '',
    category: '',
    content: '',
    summary: '',
    bookCoverImage: '',
    status: 'TEMP',
  });

  const [tempLoaded, setTempLoaded] = useState(false);

  const handleChange = (field, value) => {
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  const handleTempSave = async () => {
    const manuscriptData = {
      ...form,
      authorId: 1, // TODO: Replace with logged-in author ID
      status: 'TEMP',
      createDate: new Date().toISOString(),
      modifyDate: new Date().toISOString(),
    };
    try {
      await axios.post('http://localhost:8080/api/v1/manuscripts', manuscriptData);
      alert('임시 저장 완료');
    } catch (e) {
      console.error(e);
      alert('임시 저장 실패');
    }
  };

  const handleFinalSave = async () => {
    const manuscriptData = {
      ...form,
      authorId: 1,
      status: 'COMPLETE',
      createDate: new Date().toISOString(),
      modifyDate: new Date().toISOString(),
    };
    try {
      await axios.post('http://localhost:8080/api/v1/manuscripts', manuscriptData);
      alert('최종 저장 완료');
      navigate('/main/author');
    } catch (e) {
      console.error(e);
      alert('최종 저장 실패');
    }
  };

  const loadTempData = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/v1/manuscripts/temp/1');
      setForm({
        title: res.data.title,
        category: res.data.category,
        content: res.data.content,
        summary: res.data.summary,
        bookCoverImage: res.data.bookCoverImage,
        status: res.data.status,
      });
      setTempLoaded(true);
    } catch (e) {
      console.error(e);
      alert('임시 저장된 원고가 없습니다');
    }
  };

  return (
    <div className="manuscript-register-container">
      <h2>원고 등록</h2>
      <input
        type="text"
        placeholder="책 제목"
        value={form.title}
        onChange={(e) => handleChange('title', e.target.value)}
      />
      <ReactQuill
        theme="snow"
        value={form.content}
        onChange={(value) => handleChange('content', value)}
      />
      <div className="manuscript-button-group">
        <button className="manuscript-button temp" onClick={handleTempSave}>임시 저장</button>
        <button className="manuscript-button load" onClick={loadTempData}>임시 저장 불러오기</button>
        <button className="manuscript-button final" onClick={handleFinalSave}>최종 저장</button>
      </div>
    </div>
  );
}

export default ManuscriptRegister;

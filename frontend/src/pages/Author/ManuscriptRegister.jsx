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
  const BASE_URL = 'http://localhost:8088/manuscripts';

  const handleChange = (field, value) => {
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  const handleTempSave = async () => {
    const manuscriptData = {
      ...form,
      authorId: 1,
      status: 'TEMP',
      createDate: new Date().toISOString(),
      modifyDate: new Date().toISOString(),
    };
    try {

      // const token = localStorage.getItem('accessToken');
      const token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE'
      await axios.post(BASE_URL, manuscriptData, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        }
      });
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
      // const token = localStorage.getItem('accessToken');
      const token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE'
      await axios.post(BASE_URL, manuscriptData, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        }
      });
      alert('최종 저장 완료');
      navigate('/main/author');
    } catch (e) {
      console.error(e);
      alert('최종 저장 실패');
    }
  };


  const loadTempData = async () => {
    try {
      // const token = localStorage.getItem('accessToken');
      const token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE'
      const res = await axios.get(`${BASE_URL}/1`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const temp = res.data.find((item) => item.status === 'TEMP');
      if (!temp) throw new Error('TEMP 데이터 없음');

      setForm({
        title: temp.title,
        category: temp.category,
        content: temp.content,
        summary: temp.summary,
        bookCoverImage: temp.bookCoverImage,
        status: temp.status,
      });
      setTempLoaded(true);
    } catch (e) {
      console.error(e);
      alert('임시 저장된 원고가 없습니다');
    }
  };


  return (
    <div className="manuscript-register-container">
      <h2 style={{ fontWeight: 'bold', fontSize: '24px', marginBottom: '20px' }}>원고 등록</h2>

      <div style={{ textAlign: 'left', marginBottom: '10px' }}>
        <label htmlFor="title" style={{ fontWeight: 'bold', fontSize: '16px' }}>책 제목</label>
        <input
          id="title"
          type="text"
          className="manuscript-title-input"
          placeholder="책 제목을 입력하세요"
          value={form.title}
          onChange={(e) => handleChange('title', e.target.value)}
        />
      </div>

      <div style={{ marginBottom: '30px' }}>
        <ReactQuill
          theme="snow"
          value={form.content}
          onChange={(value) => handleChange('content', value)}
        />
      </div>

      <div className="manuscript-button-group">
        <button className="manuscript-button temp" onClick={handleTempSave}>임시 저장</button>
        <button className="manuscript-button load" onClick={loadTempData}>임시 저장 불러오기</button>
        <button className="manuscript-button final" onClick={handleFinalSave}>최종 저장</button>
      </div>
    </div>
  );
}

export default ManuscriptRegister;

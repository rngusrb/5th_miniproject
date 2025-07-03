import React, { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../api/axiosInstance'; // axiosInstance 사용
import './ManuscriptRegister.css';

function ManuscriptRegister() {
  const navigate = useNavigate();
  // localStorage에서 사용자 ID 가져오기
  const authorId = localStorage.getItem('userId');

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
      authorId: Number(authorId), // authorId 사용
      status: 'TEMP',
      createDate: new Date().toISOString(),
      modifyDate: new Date().toISOString(),
    };
    try {
      // axiosInstance 사용, 상대 경로 및 헤더 자동 처리
      await axiosInstance.post('/manuscripts', manuscriptData);
      alert('임시 저장 완료');
    } catch (e) {
      console.error(e);
      alert('임시 저장 실패');
    }
  };

  const handleFinalSave = async () => {
    const manuscriptData = {
      ...form,
      authorId: Number(authorId), // authorId 사용
      status: 'COMPLETE',
      createDate: new Date().toISOString(),
      modifyDate: new Date().toISOString(),
    };
    try {
      // axiosInstance 사용, 상대 경로 및 헤더 자동 처리
      await axiosInstance.post('/manuscripts', manuscriptData);
      alert('최종 저장 완료');
      navigate('/main/author');
    } catch (e) {
      console.error(e);
      alert('최종 저장 실패');
    }
  };


  const loadTempData = async () => {
    if (!authorId) {
      alert('로그인이 필요합니다.');
      return;
    }
    try {
      // axiosInstance 사용
      const res = await axiosInstance.get(`/manuscripts/${authorId}`);

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
import { useNavigate } from 'react-router-dom';
import React from 'react';
import './SelectLoginPage.css';

export default function SelectLoginPage() {
  const navigate = useNavigate();

  return (
    <div className="select-container">
      <div className="select-box">
        <button onClick={() => navigate('/login/author')}>작가 로그인</button>
        <button onClick={() => navigate('/login/user')}>일반 로그인</button>
        <button onClick={() => navigate('/signup/author')}>작가 회원가입</button>
        <button onClick={() => navigate('/signup/user')}>일반 회원가입</button>
      </div>
    </div>
  );
}
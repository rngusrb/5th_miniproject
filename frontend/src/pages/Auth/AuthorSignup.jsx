import React from 'react';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
// import axios from 'axios'; // 🔄 실제 백엔드 연동 시 사용
import './AuthForm.css';

export default function AuthorSignup() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { signup } = useAuth();
  const navigate = useNavigate();

  const handleSignup = async () => {
    const success = signup(username, password);
    if (success) {
      alert('회원가입 성공!');
      navigate('/login/author');
    } else {
      alert('이미 존재하는 아이디입니다.');
    }

    /*
    🔄 백엔드 연동 시:
    try {
      await axios.post('/api/signup/author', { username, password });
      alert('회원가입 성공!');
      navigate('/login/author');
    } catch (err) {
      alert('회원가입 실패');
    }
    */
  };

  return (
    <div className="auth-container">
      <h2>작가 회원가입</h2>
      <input
        placeholder="아이디"
        value={username}
        onChange={e => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="비밀번호"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />
      <button onClick={handleSignup}>회원가입</button>
      <p className="link-text">이미 계정이 있으신가요? 로그인</p>
    </div>
  );
}

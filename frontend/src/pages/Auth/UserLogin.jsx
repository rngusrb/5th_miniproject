import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import axios from 'axios';
import './AuthForm.css';

export default function UserLogin() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth(); // 선택적으로 사용
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (!username || isNaN(username) || !password || isNaN(password)) {
      alert('아이디와 비밀번호는 숫자로 입력해주세요.');
      return;
    }

    try {
      const res = await axios.post('/users/login', {
        userId: Number(username),
        userPw: Number(password),
      });

      const token = res.data.token;
      localStorage.setItem('token', token); // ✅ JWT 저장
      // 필요 시 userId도 저장 가능
      localStorage.setItem('userId', res.data.userId);

      alert('로그인 성공!');
      navigate('/main/user');
    } catch (err) {
      if (err.response?.status === 401) {
        alert('아이디 또는 비밀번호가 틀렸습니다.');
      } else if (err.response?.status === 404) {
        alert('존재하지 않는 사용자입니다.');
      } else {
        alert('로그인 실패');
      }
    }
  };

  return (
    <div className="auth-container">
      <h2>로그인</h2>
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
      <button onClick={handleLogin}>로그인</button>
      <p className="link-text">비밀번호를 잊으셨나요?</p>
    </div>
  );
}

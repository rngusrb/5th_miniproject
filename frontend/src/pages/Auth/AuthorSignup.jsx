import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../api/axiosInstance'; // API 호출을 위한 인스턴스
import './AuthForm.css';

export default function AuthorSignup() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSignup = async () => {
    if (!username || !password) {
      alert('아이디와 비밀번호를 모두 입력해주세요.');
      return;
    }

    try {
      const response = await axiosInstance.post('/authors/requestRegistration', {
        authorLoginId: username,
        authorPw: password,
      });

      // 회원가입 성공 시의 로직은 그대로 유지합니다.
      alert('회원가입에 성공하였습니다.');
      navigate('/login/author');

    } catch (err) {
      // 에러 처리 로직을 수정
      console.error("Signup failed:", err);

      if (err.response?.status === 409) {
        alert('이미 사용 중인 아이디입니다.');
      } else {
        alert('회원가입에 실패했습니다. 잠시 후 다시 시도해주세요.');
      }
    }
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
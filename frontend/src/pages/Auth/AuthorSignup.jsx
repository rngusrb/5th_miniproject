import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// import { useAuth } from '../../contexts/AuthContext'; // 백엔드 직접 연동으로 변경
import axiosInstance from '../../api/axiosInstance'; // API 호출을 위한 인스턴스
import './AuthForm.css';

export default function AuthorSignup() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSignup = async () => {
    // 입력 값 검증
    if (!username || !password) {
      alert('아이디와 비밀번호를 모두 입력해주세요.');
      return;
    }

    try {
      // 🔄 백엔드 회원가입 API 호출
      // AuthorController의 requestRegistration 메서드에 맞춰 요청 경로와 데이터를 수정합니다.
      const response = await axiosInstance.post('/authors/requestRegistration', {
        authorLoginId: username, // 'username' -> 'authorLoginId'
        authorPw: password,      // 'password' -> 'authorPw'
      });

      // 회원가입 성공 시 서버로부터 받은 작가 정보를 활용할 수 있습니다.
      console.log('회원가입 성공:', response.data);
      alert('회원가입 성공!');

      // 성공 후 작가 로그인 페이지로 이동
      navigate('/login/author');

    } catch (err) {
      // API 호출 실패 시 에러 처리
      console.error("Signup failed:", err);
      alert('회원가입에 실패했습니다. 다른 아이디를 사용해주세요.');
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
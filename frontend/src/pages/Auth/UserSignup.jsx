import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './AuthForm.css';

export default function UserSignup() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isKtMember, setIsKtMember] = useState(false);
  const navigate = useNavigate();

  const handleSignup = async () => {
    try {
      const res = await axios.post('/users', {
        userId: Number(username),     // 또는 그냥 username
        userPw: Number(password),     // 또는 그냥 password
        isKtMember: isKtMember        // ✅ KT 회원 여부 전달
      });
      console.log('✅ 회원가입 응답:', res.data); // 🔥 응답 로그 확인

      alert(
        isKtMember
          ? 'KT 회원으로 가입 완료! (추가 포인트 지급됨)'
          : '회원가입 완료!'
      );

      navigate('/login/user');
    } catch (err) {
      alert('회원가입 실패');
    }
  };

  return (
    <div className="auth-container">
      <h2>회원가입</h2>
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
      <div className="checkbox_label">
        <label>
          <input
            type="checkbox"
            checked={isKtMember}
            onChange={e => setIsKtMember(e.target.checked)}
            className="checkbox_input"
          />
          <span className="checkbox_icon"></span>
          <span className="kt-text">KT 회원이신가요?</span>
        </label>
      </div>
      <button onClick={handleSignup}>회원가입</button>
      <p className="link-text">이미 계정이 있으신가요? 로그인</p>
    </div>
  );
}

// src/components/layout/HeaderBar.jsx
import { React, useState, useEffect } from 'react';
import { Route, useLocation, useNavigate } from 'react-router-dom';
import './HeaderBar.css';

export default function HeaderBar() {
  const { pathname } = useLocation();
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogout = () => {
    console.log('logout')
    if(confirm('로그아웃 하시겠습니까?')) {
      const token = localStorage.getItem('token');
      if(token) localStorage.removeItem('token');
      navigate('/'); // ✅ 홈으로 이동
    } 
  }

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsLoggedIn(!!token); // 토큰이 있으면 true
  }, []);

  return (
    <header className="header-bar">
      <div className="header-left">걷다가 서재</div>
      {isLoggedIn && (
        <button className="logout-btn" onClick={handleLogout}>로그아웃</button>
      )}
    </header>
  );
}

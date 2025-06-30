// src/components/layout/HeaderBar.jsx
import React from 'react';
import { useLocation } from 'react-router-dom';
import './HeaderBar.css';

export default function HeaderBar() {
  const { pathname } = useLocation();


  return (
    <header className="header-bar">
      걷다가 서재
    </header>
  );
}

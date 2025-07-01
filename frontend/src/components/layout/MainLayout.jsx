// src/components/layout/MainLayout.jsx
import React from 'react';
import HeaderBar from './HeaderBar';
import './MainLayout.css';

export default function MainLayout({ children }) {
  return (
    <div className="main-layout">
      <HeaderBar />
      <main className="main-content">{children}</main>
    </div>
  );
}

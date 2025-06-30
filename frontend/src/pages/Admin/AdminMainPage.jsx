import React from 'react';
import MainLayout from '../../components/layout/MainLayout';
import './AdminMainPage.css';

const AdminMainPage = () => {
  return (
    <MainLayout>
      <div className="admin-page">
        <div className="admin-grid">
          <div className="admin-section">
            <button className="admin-button">작가 승인</button>
            <button className="admin-button">작가 관리</button>
            <button className="admin-button">원고 관리</button>
          </div>
          <div className="admin-section">
            <button className="admin-button">유저 관리</button>
            <button className="admin-button">도서 관리</button>
            <button className="admin-button">AI 관리</button>
          </div>
        </div>
      </div>
    </MainLayout>
  );
};

export default AdminMainPage;

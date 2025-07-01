import React from 'react';
import { useNavigate } from 'react-router-dom';
import MainLayout from '../../components/layout/MainLayout';
import './AdminMainPage.css';

const AdminMainPage = () => {
  const navigate = useNavigate();

  return (
    <MainLayout>
      <div className="admin-page">
        <div className="admin-grid">
          <div className="admin-section">
            <button className="admin-button" onClick={() => navigate('/admin/author-approval')}>작가 승인</button>
            <button className="admin-button" onClick={() => navigate('/admin/author-manage')}>작가 관리</button>
            <button className="admin-button" onClick={() => navigate('/admin/manuscript-manage')}>원고 관리</button>
          </div>
          <div className="admin-section">
            <button className="admin-button" onClick={() => navigate('/admin/user-manage')}>유저 관리</button>
            <button className="admin-button" onClick={() => navigate('/admin/book-manage')}>도서 관리</button>
            <button className="admin-button" onClick={() => navigate('/admin/ai-manage')}>AI 관리</button>
          </div>
        </div>
      </div>
    </MainLayout>
  );
};

export default AdminMainPage;

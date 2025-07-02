import { Routes, Route } from 'react-router-dom';
import React from 'react';

// Auth
import SelectLoginPage  from '../pages/Auth/SelectLoginPage';
import UserLogin from '../pages/Auth/UserLogin';
import AuthorLogin from '../pages/Auth/AuthorLogin';
import UserSignup from '../pages/Auth/UserSignup';
import AuthorSignup from '../pages/Auth/AuthorSignup';

// User
import UserMainPage from '../pages/User/UserMainPage';
import UserMyPage from '../pages/User/UserMyPage';
import BookDetailPage from '../pages/User/BookDetailPage';
import SubscriptionBookPage from '../pages/User/SubscriptionBookPage';

// Author
import AuthorMainPage from '../pages/Author/AuthorMainPage';
import AuthorRegister from '../pages/Author/AuthorRegister';
import ManuscriptRegister from '../pages/Author/ManuscriptRegister';
import DraftListPage from '../pages/Author/DraftListPage';
import PublishRequest from '../pages/Author/PublishRequest';
import PublishConfirm from '../pages/Author/PublishConfirm';
import BookRead       from '../pages/Author/BookRead';
// Admin
import AdminMainPage from '../pages/Admin/AdminMainPage';
import AuthorApprovalPage from "../pages/Admin/AuthorApprovalPage";
import UserManagePage from '../pages/Admin/UserManagePage';
import AuthorManagePage from '../pages/Admin/AuthorManagePage';
import BookManagePage from '../pages/Admin/BookManagePage';
import ManuscriptManagePage from '../pages/Admin/ManuscriptManagePage';

export default function AppRoutes() {
  return (
    <Routes>
      {/* 로그인 선택 */}
      <Route path="/" element={<SelectLoginPage />} />

      {/* 로그인 / 회원가입 */}
      <Route path="/login">
        <Route path="user" element={<UserLogin />} />
        <Route path="author" element={<AuthorLogin />} />
      </Route>
      <Route path="/signup">
        <Route path="user" element={<UserSignup />} />
        <Route path="author" element={<AuthorSignup />} />
      </Route>

      {/* 사용자 페이지 */}
      <Route path="/main/user" element={<UserMainPage />} />
      <Route path="/mypage/user" element={<UserMyPage />} />
      <Route path="/book/:id" element={<BookDetailPage />} />
      <Route path="/mypage/user/subscribed-books" element={<SubscriptionBookPage />} />


      {/* 작가 페이지 */}
      <Route path="/main/author" element={<AuthorMainPage />} />
      <Route path="/author/register" element={<AuthorRegister />} />
      <Route path="/author/manuscript" element={<ManuscriptRegister />} />
      <Route path="/author/draftlist" element={<DraftListPage />} />
      <Route path="/author/publishrequest" element={<PublishRequest />} />
      <Route path="/author/publishconfirm" element={<PublishConfirm />} />
      <Route path="/author/book/:id" element={<BookRead />} />
      {/* 관리자 페이지 */}
      <Route path="/admin" element={<AdminMainPage />} />
      <Route path="/admin/author-approval" element={<AuthorApprovalPage />} />

      <Route path="/admin/author-manage" element={<AuthorManagePage />}  />
      <Route path="/admin/manuscript-manage" element={<ManuscriptManagePage />}  />
      <Route path="/admin/user-manage" element={<UserManagePage />}  />
      <Route path="/admin/book-manage" element={<BookManagePage />}  />
      <Route path="/admin/ai-manage" element={<div>AI 관리 페이지</div>} />
    </Routes>
  );
}
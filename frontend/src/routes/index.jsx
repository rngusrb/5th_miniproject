import { Routes, Route } from 'react-router-dom';

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

// Author
import AuthorMainPage from '../pages/Author/AuthorMainPage';
import AuthorRegister from '../pages/Author/AuthorRegister';
import ManuscriptRegister from '../pages/Author/ManuscriptRegister';

// Admin
import AdminMainPage from '../pages/Admin/AdminMainPage';
import AuthorApprovalPage from "../pages/Admin/AuthorApprovalPage";
import UserManagePage from '../pages/Admin/UserManagePage';
import AuthorManagePage from '../pages/Admin/AuthorManagePage';
import BookManagePage from '../pages/Admin/BookManagePage';

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

      {/* 작가 페이지 */}
      <Route path="/main/author" element={<AuthorMainPage />} />
      <Route path="/author/register" element={<AuthorRegister />} />
      <Route path="/author/manuscript" element={<ManuscriptRegister />} />

      {/* 관리자 페이지 */}
      <Route path="/admin" element={<AdminMainPage />} />
      <Route path="/admin/author-approval" element={<AuthorApprovalPage />} />

      <Route path="/admin/author-manage" element={<AuthorManagePage />}  />
      <Route path="/admin/manuscript-manage" element={<div>원고 관리 페이지</div>} />
      <Route path="/admin/user-manage" element={<UserManagePage />}  />
      <Route path="/admin/book-manage" element={<BookManagePage />}  />
      <Route path="/admin/ai-manage" element={<div>AI 관리 페이지</div>} />
    </Routes>
  );
}
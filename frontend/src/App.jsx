import React from 'react';
import { Routes, Route } from 'react-router-dom';
import DefaultLayout from './layouts/DefaultLayout';
import ChoicePage from './routes/ChoicePage';
import GenLogin from './routes/GenLogin';
import WriterLogin from './routes/WriterLogin';
import GenSignup from './routes/GenSignup';
import WriterSignup from './routes/WriterSignup';
import GenMain from './routes/GenMain';
import GenMyPage from './routes/GenMyPage';
import WriterMain from './routes/WriterMain';
import AdminMain from './routes/AdminMain';
import BookDetail from './routes/BookDetail';

function App() {
  return (
    <Routes>
      <Route element={<DefaultLayout />}>
        <Route path="/" element={<ChoicePage />} />
        <Route path="login/general" element={<GenLogin />} />
        <Route path="login/writer" element={<WriterLogin />} />
        <Route path="signup/general" element={<GenSignup />} />
        <Route path="signup/writer" element={<WriterSignup />} />
        <Route path="app" element={<GenMain />} />
        <Route path="app/mypage" element={<GenMyPage />} />
        <Route path="app/book/:id" element={<BookDetail />} />
        <Route path="writer" element={<WriterMain />} />
        <Route path="admin" element={<AdminMain />} />
      </Route>
    </Routes>
  );
}

export default App;

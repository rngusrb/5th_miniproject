import React, { useEffect, useState } from 'react';
import axiosInstance from '../../api/axiosInstance';
import './BookManagePage.css';

const BookManagePage = () => {
  const [books, setBooks] = useState([]);
  const [authors, setAuthors] = useState([]);

  const ADMIN_API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE';

  useEffect(() => {
    fetchBooks();
    fetchAuthors();
  }, []);

  const fetchBooks = async () => {
    try {
      const res = await axiosInstance.get('/books', {
        headers: {
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      setBooks(res.data._embedded.books);
    } catch (err) {
      console.error('도서 목록 불러오기 실패', err.response ? err.response.data : err.message);
      alert('도서 목록을 불러오는데 실패했습니다.');
    }
  };

  const fetchAuthors = async () => {
    try {
      const res = await axiosInstance.get('/authors', {
        headers: {
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      setAuthors(res.data);
    } catch (err) {
      console.error('작가 목록 불러오기 실패', err.response ? err.response.data : err.message);
      alert('작가 목록을 불러오는데 실패했습니다.');
    }
  };

  const handleDelete = async (bookId) => {
    if (!window.confirm('정말 삭제하시겠습니까?')) return;

    try {
      await axiosInstance.delete(`/books/${bookId}`, {
        headers: {
          Authorization: `Bearer ${ADMIN_API_TOKEN}`,
        },
      });
      alert('도서 삭제 완료');
      setBooks((prev) => prev.filter((book) => book.bookId !== bookId));
    } catch (err) {
      console.error('도서 삭제 실패', err.response ? err.response.data : err.message);
      alert('삭제 실패');
    }
  };

  const handleEdit = (bookId) => {
    alert(`수정 기능 준비 중: 도서 ID ${bookId}`);
  };

  return (
    <div className="book-manage-page">
      <h2 className="book-manage-title">도서 관리</h2>
      <table className="book-manage-table">
        <thead>
          <tr>
            <th>제목</th>
            <th>작가</th>
            <th>조회수</th>
            <th>좋아요</th>
            <th>관심등록</th>
            <th>관리</th>
          </tr>
        </thead>
        <tbody>
          {books.map((book) => {
            const author = authors.find((a) => a.authorId === book.authorId);

            return (
              <tr key={book.bookId}>
                <td>{book.bookTitle}</td>
                <td>{author?.authorLoginId || '알 수 없음'}</td>
                <td>{book.viewCount}</td>
                <td>{book.likeCount}</td>
                <td>{book.favoriteCount || 0}</td>
                <td>
                  <button className="book-edit-btn" onClick={() => handleEdit(book.bookId)}>수정</button>
                  <button className="book-delete-btn" onClick={() => handleDelete(book.bookId)}>삭제</button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default BookManagePage;
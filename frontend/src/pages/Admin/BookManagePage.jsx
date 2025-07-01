import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './BookManagePage.css';

const BookManagePage = () => {
  const [books, setBooks] = useState([]);

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const res = await axios.get('/api/admin/books');
      setBooks(res.data);
    } catch (err) {
      console.error('도서 목록 불러오기 실패', err);
    }
  };

  const handleDelete = async (bookId) => {
    if (!window.confirm('정말 삭제하시겠습니까?')) return;

    try {
      await axios.delete(`/api/admin/books/${bookId}`);
      alert('도서 삭제 완료');
      setBooks((prev) => prev.filter((book) => book.id !== bookId));
    } catch (err) {
      alert('삭제 실패');
    }
  };

  const handleEdit = (bookId) => {
    // 수정 페이지로 이동하거나 모달 띄우기
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
          {books.map((book) => (
            <tr key={book.id}>
              <td>{book.title}</td>
              <td>{book.authorName}</td>
              <td>{book.viewCount}</td>
              <td>{book.likeCount}</td>
              <td>{book.favoriteCount}</td>
              <td>
                <button className="book-edit-btn" onClick={() => handleEdit(book.id)}>수정</button>
                <button className="book-delete-btn" onClick={() => handleDelete(book.id)}>삭제</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BookManagePage;

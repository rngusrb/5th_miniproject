import React, { useEffect, useState } from 'react';
import axiosInstance from "../../api/axiosInstance";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../../components/card/BookCard.css';

export default function SubscribedBookCard({ book }) {
  const [likeCount, setLikeCount] = useState(book.likeCount || 0);
  const [viewCount, setViewCount] = useState(book.viewCount || 0);
  const bookId = book.bookId;
  const navigate = useNavigate();

  useEffect(() => {
    const fetchLatestBookInfo = async () => {
      try {
        const res = await axiosInstance.get(`/books/${bookId}`);
        const latest = res.data;
        setLikeCount(latest.likeCount);
        setViewCount(latest.viewCount);
      } catch (err) {
        console.error(`도서 ${bookId} 정보 조회 실패:`, err);
      }
    };

    if (bookId) fetchLatestBookInfo();
  }, [bookId]);

  const handleLikeClick = async () => {
    try {
      const res = await axiosInstance.patch(`/books/${bookId}/likebook`);
      setLikeCount(res.data.likeCount);
    } catch (err) {
      console.error("좋아요 요청 실패:", err);
    }
  };

  const handleReadClick = async () => {
    alert(`${book.bookTitle} 열람 페이지로 이동합니다.`);

    try {
      const token = localStorage.getItem('token');

      await axios.patch(
        `/books/${bookId}/viewbook`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      navigate(`/books/${bookId}`);
    } catch (err) {
      console.error('📕 열람(viewbook) 요청 실패:', err);
      alert('열람 기록에 실패했습니다.');
      navigate(`/books/${bookId}`);
    }
  };

  return (
    <div className="book-card">
      <div className="book-thumbnail">
        <img
          className="book-cover-thumbnail"
          src={book.bookCoverImage}
          alt="cover"
        />
      </div>
      <div className="book-title-thumbnail" title={book.bookTitle}>
        {book.bookTitle}
      </div>
      <div className="book-meta">
        <span className="like-btn" onClick={handleLikeClick}>❤️ {likeCount}</span> ☆ {viewCount}
      </div>
      <div className="book-actions">
        <button onClick={handleReadClick}>열람</button>
      </div>
    </div>
  );
}

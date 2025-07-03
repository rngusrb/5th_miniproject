import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../../components/card/BookCard.css';
import axios from 'axios';


export default function SubscribedBookCard({ book }) {
  const navigate = useNavigate();
  const [likeCount, setLikeCount] = useState(book.likeCount ?? 0);


  const handleReadClick = async () => {
    alert(`${book.bookTitle} 열람 페이지로 이동합니다.`);

    try {
      const token = localStorage.getItem('token');

      await axios.patch(
        `http://localhost:8088/books/${book.bookId}/viewbook`,
        {}, // PATCH는 body 없어도 되므로 빈 객체
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      // 이후 상세 페이지로 이동
      navigate(`/books/${book.bookId}`);
    } catch (err) {
      console.error('📕 열람(viewbook) 요청 실패:', err);
      alert('열람 기록에 실패했습니다.');
      navigate(`/books/${book.bookId}`); // 그래도 이동은 시킴
    }
  };
  const handleLikeClick = async () => {
    try {
      const token = localStorage.getItem('token');

      const res = await axios.patch(
        `http://localhost:8088/books/${book.bookId}/likebook`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setLikeCount(res.data.likeCount); // 서버 응답값으로 갱신
    } catch (err) {
      console.error('❤️ 좋아요 요청 실패:', err);
      alert('좋아요에 실패했습니다.');
    }
  };



  return (
    <div className="book-card">
      <div className="book-thumbnail">
        <img
          src={`/images/${book.bookCoverImage}`}
          alt="표지"
          className="book-cover-thumbnail"
        />
      </div>

      <div className="book-title-thumbnail">{book.bookTitle}</div>

      <div className="book-meta">
        <span className="like-btn" onClick={() => handleLikeClick(book)}>❤️ {likeCount}</span> ☆ {book.viewCount}
      </div>

      <div className="book-actions">
        <button onClick={handleReadClick}>열람</button>
      </div>
    </div>
  );
}

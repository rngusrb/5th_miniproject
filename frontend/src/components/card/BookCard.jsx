import './BookCard.css';
import React, { useState } from 'react';
import axiosInstance from "../../api/axiosInstance";

const extractBookId = (book) => {
  const selfLink = book._links?.self?.href;
  if (!selfLink) return null;
  const parts = selfLink.split('/');
  return parts[parts.length - 1]; // 마지막 요소가 ID
};

export default function BookCard({ book, showSubscribe = true }) {
  const [likeCount, setLikeCount] = useState(book.likeCount);

  const handleSubscribeClick = async () => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (!userId || !token) {
      alert("로그인이 필요합니다.");
      return;
    }

    const confirm = window.confirm("💸 1000포인트를 사용하여 이 책을 구독하시겠습니까?");
    if (!confirm) return;

    try {
      // 1. 포인트 차감
      const res = await axiosInstance.put(`/points/${userId}/pluspoints`, {
        points: -1000,
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (res.status === 200) {
        // 2. 구독 상태 업데이트 (pass=true)
        await axiosInstance.put(`/users/${userId}/requestsubscription`, {}, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        alert("✅ 구독이 완료되었습니다. (1000포인트 차감됨)");
      }
    } catch (err) {
      if (err.response?.status === 500 || err.response?.status === 400) {
        alert("❌ 포인트가 부족하거나 오류가 발생했습니다.");
      } else {
        alert("에러: " + err.message);
      }
      console.error("구독 실패:", err);
    }
  };

  const handleLikeClick = async (book) => {
    try {
      const res = await axiosInstance.patch(`/books/${extractBookId(book)}/likebook`);
      setLikeCount(res.data.likeCount);
    } catch (err) {
      console.error("좋아요 요청 실패", err);
    }
  };

  return (
    <div className="book-card">
      <div className="book-thumbnail">
        <img className="book-cover-thumbnail" src={book.bookCoverImage} />
      </div>
      <div className="book-title-thumbnail" title={book.bookTitle}>
        {book.bookTitle}
      </div>
      <div className="book-actions">
        {showSubscribe && (
          <button onClick={handleSubscribeClick}>구독</button>
        )}
      </div>
      <div className="book-meta">
        <span onClick={() => handleLikeClick(book)}>❤️ {likeCount}</span> ☆ {book.viewCount}
      </div>
    </div>
  );
}

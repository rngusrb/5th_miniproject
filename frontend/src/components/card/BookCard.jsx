import './BookCard.css';
import React, { useState, useEffect } from 'react';
import axiosInstance from "../../api/axiosInstance";

const extractBookId = (book) => {
  const selfLink = book._links?.self?.href;
  if (!selfLink) return null;
  const parts = selfLink.split('/');
  return parts[parts.length - 1];
};

export default function BookCard({ book, showSubscribe = true }) {
  const [likeCount, setLikeCount] = useState(book.likeCount);
  const [isSubscribed, setIsSubscribed] = useState(false);
  const [loading, setLoading] = useState(false);

  const bookId = extractBookId(book);

  // ✅ 렌더링 시 구독 여부 확인
  useEffect(() => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (!userId || !token || !bookId) return;

    const checkSubscription = async () => {
      try {
        const res = await axiosInstance.get(`/subscriptions/${userId}/${bookId}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (res.data === true) {
          setIsSubscribed(true);
        }
      } catch (err) {
        console.error("초기 구독 확인 실패:", err);
      }
    };

    checkSubscription();
  }, [bookId]);

  const handleSubscribeClick = async () => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (!userId || !token || !bookId) {
      alert("로그인이 필요하거나 책 정보가 없습니다.");
      return;
    }

    if (isSubscribed) {
      alert("✅ 이미 구독한 책입니다.");
      return;
    }

    try {
      setLoading(true);

      // 먼저 구독권 접근 시도
      const response = await axiosInstance.post(`/users/${userId}/access`, {
        bookId: parseInt(bookId),
      }, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
      });

      const access = response.data?.access;

      if (access === 'GRANTED') {
        setIsSubscribed(true);
        alert("✅ 구독권으로 구독 완료");
      } else {
        alert("❌ 구독권이 없으므로 포인트 결제를 시도합니다...");

        // 포인트 결제 후 구독 여부 확인
        try {
          const res = await axiosInstance.get(`/subscriptions/${userId}/${bookId}`, {
            headers: { Authorization: `Bearer ${token}` },
          });

          if (res.data === true) {
            setIsSubscribed(true);
            alert("✅ 포인트 결제 후 구독 성공!");
          } else {
            setIsSubscribed(false);
            alert("❌ 포인트가 부족하여 구독에 실패했습니다.");
          }
        } catch (err) {
          setIsSubscribed(false);
          alert("❌ 구독 상태 확인 실패: " + (err?.response?.data?.message || err.message));
          console.error("구독 상태 조회 에러:", err);
        }
      }

    } catch (err) {
      alert("❌ 요청 실패: " + (err?.response?.data?.message || err.message));
      console.error("접근 요청 실패:", err);
    } finally {
      setLoading(false);
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
        <img className="book-cover-thumbnail" src={book.bookCoverImage} alt="cover" />
      </div>
      <div className="book-title-thumbnail" title={book.bookTitle}>
        {book.bookTitle}
      </div>
      <div className="book-actions">
        {showSubscribe && (
          <button
            onClick={handleSubscribeClick}
            disabled={isSubscribed || loading}
          >
            {isSubscribed ? "✅ 이미 구독함" : "📘 구독하기"}
          </button>
        )}
        {/* <button className="btn btn-primary" onClick={onRead}>
          열람
        </button> */}
      </div>
      <div className="book-meta">
        <span className="like-btn" onClick={() => handleLikeClick(book)}>❤️ {likeCount}</span> ☆ {book.viewCount}
      </div>
    </div>
  );
}

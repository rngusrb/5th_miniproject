import './BookCard.css';
import React, { useEffect, useState } from 'react';
import axiosInstance from "../../api/axiosInstance";

const extractBookId = (book) => {
  const selfLink = book._links?.self?.href;
  if (!selfLink) return null;
  const parts = selfLink.split('/');
  return parts[parts.length - 1];
};

export default function BookCard({ book, showSubscribe = true }) {
  const [likeCount, setLikeCount] = useState(book.likeCount);
  const [isSubscribed, setIsSubscribed] = useState(false); // ✅ 구독 상태
  const [loading, setLoading] = useState(false);            // ✅ 요청 중 표시

  useEffect(() => {
    checkSubscriptionStatus();
  }, []);

  const checkSubscriptionStatus = async () => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');
    if (!userId || !token) return;

    try {
      const res = await axiosInstance.get(`/users/${userId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      if (res.data?.pass === true) {
        setIsSubscribed(true); // ✅ 프리미엄이면 항상 구독 상태
      }
    } catch (err) {
      console.error("구독 상태 확인 실패:", err);
    }
  };

  const handleSubscribeClick = async () => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (!userId || !token) {
      alert("로그인이 필요합니다.");
      return;
    }

    try {
      setLoading(true);

      // 1. 사용자 정보로 프리미엄 여부 확인
      const userRes = await axiosInstance.get(`/users/${userId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      const isPremium = userRes.data?.pass === true;

      if (isPremium) {
        // 프리미엄은 바로 구독 처리
        await axiosInstance.put(`/users/${userId}/requestsubscription`, {}, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setIsSubscribed(true);
        alert("✅ 프리미엄 구독자입니다. 바로 열람이 가능합니다.");
        return;
      }

      // 일반 유저 - 포인트 차감
      const confirm = window.confirm("💸 1000포인트를 사용하여 이 책을 구독하시겠습니까?");
      if (!confirm) return;

      const res = await axiosInstance.put(`/points/${userId}/pluspoints`, {
        points: -1000,
      }, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (res.status === 200) {
        await axiosInstance.put(`/users/${userId}/requestsubscription`, {}, {
          headers: { Authorization: `Bearer ${token}` },
        });
        setIsSubscribed(true);
        alert("✅ 구독이 완료되었습니다. (1000포인트 차감됨)");
      }

    } catch (err) {
      alert("❌ 구독 실패: " + (err?.response?.data?.message || err.message));
      console.error("구독 실패:", err);
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
            className={isSubscribed ? 'subscribed-btn' : ''}
          >
            {isSubscribed ? '📘 구독 중' : '구독'}
          </button>
        )}
        {/* <button className="btn btn-primary" onClick={onRead}>
          열람
        </button> */}
      </div>

      <div className="book-meta"><span className="like-btn" onClick={() => handleLikeClick(book)}>❤️ {likeCount}</span> ☆ {book.viewCount}</div>
    </div>
  );
}

import React, { useEffect, useState } from 'react';
import './SubscribePanel.css';

export default function SubscribePanel({ onClose, onSubscribed }) {
  const [isSubscribed, setIsSubscribed] = useState(false);
  const [loading, setLoading] = useState(false);

  const userId = localStorage.getItem('userId');
  const token = localStorage.getItem('token');

  // ✅ mount 시 구독 상태 확인
  useEffect(() => {
    const checkSubscription = async () => {
      if (!userId || !token) return;
      try {
        const res = await fetch(`/users/${userId}`, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        const data = await res.json();
        setIsSubscribed(data?.pass === true);
      } catch (err) {
        console.error('구독 상태 확인 실패:', err);
      }
    };

    checkSubscription();
  }, []);

  // ✅ 구독 or 구독 취소 요청
  const handleToggleSubscription = async () => {
    if (!userId || !token) {
      alert('로그인이 필요합니다.');
      return;
    }

    setLoading(true);

    try {
      if (isSubscribed) {
        // 🔴 구독 취소 요청
        const res = await fetch(`/users/${userId}/cancelsubscription`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        });

        if (!res.ok) throw new Error('서버 응답 오류');

        alert('정기 구독이 취소되었습니다.');
        setIsSubscribed(false);
        onSubscribed?.(); // 부모에서 상태 업데이트 용도
      } else {
        // 🟢 정기 구독 요청
        const res = await fetch(`/users/${userId}/requestsubscription`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        });

        if (!res.ok) throw new Error('서버 응답 오류');

        alert('정기 구독이 완료되었습니다!');
        setIsSubscribed(true);
        onSubscribed?.(); // 부모에서 상태 업데이트 용도
      }

      onClose();
    } catch (err) {
      console.error('구독 요청 실패:', err);
      alert('처리 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="subscribe-panel">
      <h2>정기 구독권</h2>
      <p>
        한 달 이용권: <strong>9,900원</strong>
      </p>
      <div className="subscribe-buttons">
        <button onClick={handleToggleSubscription} disabled={loading}>
          {isSubscribed ? '구독 취소' : '구독하기'}
        </button>
        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
}

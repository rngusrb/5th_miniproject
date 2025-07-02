import React from 'react';
import './SubscribePanel.css'; // 👈 CSS 따로 정의

export default function SubscribePanel({ onClose, onSubscribed }) {
  const handleSubscribe = async () => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (!userId || !token) {
      alert('로그인이 필요합니다.');
      return;
    }

    try {
      // 실제 API 구현 시 이곳 변경
      await fetch('/subscriptions/subscribe', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ userId: Number(userId) }),
      });

      alert('정기 구독권 결제가 완료되었습니다!');
      onSubscribed?.();
      onClose();
    } catch (err) {
      console.error('정기 구독 결제 실패:', err);
      alert('결제 중 오류가 발생했습니다.');
    }
  };

  return (
    <div className="subscribe-panel">
      <h2>정기 구독권 결제</h2>
      <p>한 달 이용권: <strong>9,900원</strong></p>
      <div className="subscribe-buttons">
        <button onClick={handleSubscribe}>결제</button>
        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
}

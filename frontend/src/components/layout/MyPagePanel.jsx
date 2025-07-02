import './MyPagePanel.css';
import React from 'react';

export default function MyPagePanel({ onClose, onChargeClick }) {
  return (
    <div className="mypage-panel">
      <h2>My Page</h2>
      <ul>
        <li onClick={onChargeClick}>포인트 충전</li>
        <li>정기 구독권 결제</li>
        <li>구독중인 도서</li>
        <li>열람한 도서</li>
        <li>로그아웃</li>
        <li onClick={onClose}>닫기</li>
      </ul>
    </div>
  );
}

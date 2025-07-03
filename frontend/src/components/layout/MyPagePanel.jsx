import './MyPagePanel.css';
import React from 'react';

export default function MyPagePanel({ onClose, onChargeClick, onSubscribeClick, onMySubscribeClick, onLogout }) {
  return (
    <div className="mypage-panel">
      <h2>My Page</h2>
      <ul>
        <li onClick={onChargeClick}>포인트 충전</li>
        <li onClick={onSubscribeClick}>정기 구독권 관리</li> {/* ✅ 클릭 가능하게 수정 */}
        <li onClick={onMySubscribeClick}>구독중인 도서</li> 
        <li onClick={onLogout}>로그아웃</li>
        <li onClick={onClose}>닫기</li>
      </ul>
    </div>
  );
}

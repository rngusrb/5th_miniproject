// src/components/modal/RecommendPopup.jsx
import React from 'react';
import './RecommendPopup.css';

export default function RecommendPopup({ onClose }) {
  return (
    <div className="popup-overlay">
      <div className="popup-box">
        <h2>포인트가 모두 소진되었습니다!</h2>
        <p>
          🎁 지금 KT로 이동하고 <strong>월 9,900원 요금제</strong>로 프리미엄 혜택을 누리세요!
        </p>
        <p>
          📞 고객센터: 080-000-0000<br />
          🌐 <a href="https://kt.com" target="_blank" rel="noopener noreferrer">kt.com</a>
        </p>
        <button onClick={onClose}>확인</button>
      </div>
    </div>
  );
}

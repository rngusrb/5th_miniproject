import React, { useState } from 'react';
import axios from 'axios';// 선택: 스타일링 파일
import './PointChargePanel.css';

export default function PointChargePanel({ onClose, onCharged }) {
  const [amount, setAmount] = useState('');

  const handleCharge = async () => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (!userId || !token) {
      alert('로그인이 필요합니다.');
      return;
    }

    if (!amount || Number(amount) <= 0) {
      alert('충전 금액을 올바르게 입력하세요.');
      return;
    }

    try {
      await axios.post('/points/charge', {
        userId: Number(userId),
        amount: Number(amount),
      }, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      alert('포인트 충전이 완료되었습니다!');
      onCharged();   // 포인트 재조회
      onClose();     // 창 닫기
    } catch (err) {
      console.error('포인트 충전 실패:', err);
      alert('충전 중 오류가 발생했습니다.');
    }
  };

  return (
    <div className="point-charge-panel">
      <h2>포인트 충전</h2>
      <input
        type="number"
        value={amount}
        onChange={e => setAmount(e.target.value)}
        placeholder="충전할 포인트 입력"
        min="1"
      />
      <div className="charge-buttons">
        <button onClick={handleCharge}>충전하기</button>
        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
}

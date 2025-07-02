
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import SubscribedBookCard from './SubscribedBookCard';
import '../../components/card/BookCard.css';

export default function SubscriptionBookPage() {
  const [books, setBooks] = useState([]);
  
  const userId = 1; // 실제 사용자 ID
  const token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUxNDQxMDkxLCJleHAiOjE3NTE1Mjc0OTF9._LbIIVbNhMjGKgNSzGTAoxP5df72p366MgRucvM73vI"

  useEffect(() => {
    axios
      .get(`http://localhost:8088/subscriptions/list/${userId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setBooks(res.data);
      })
      .catch((err) => {
        console.error('구독 도서 불러오기 실패:', err);
      });
  }, []);
return (
    <div style={{ padding: '20px' }}>
      <h2>📚 {userId} 님의 구독 중인 도서 목록입니다.</h2>

      {books.length === 0 ? (
        <p>현재 구독 중인 도서가 없습니다.</p>
      ) : (
        <div className="book-row-scrollable">
          {books.map((book, idx) => (
            <SubscribedBookCard key={idx} book={book} />
          ))}
        </div>
      )}
    </div>
  );
}
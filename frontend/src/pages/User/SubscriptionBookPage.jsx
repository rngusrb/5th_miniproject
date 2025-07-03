import React, { useEffect, useState } from 'react';
import axios from 'axios';
import SubscribedBookCard from './SubscribedBookCard';
import '../../components/card/BookCard.css';
import axiosInstance from '../../api/axiosInstance';

export default function SubscriptionBookPage() {
  const [books, setBooks] = useState([]);
  const [likeCounts, setLikeCounts] = useState({});
  const [viewCounts, setViewCounts] = useState({});

  const token = localStorage.getItem('token');
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    axios
      .get(`http://localhost:8088/subscriptions/list/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setBooks(res.data);

        // 📌 추가로 각 책의 최신 like/view 정보 받아오기
        res.data.forEach(book => {
          const bookId = book.bookId;
          axiosInstance.get(`/books/${bookId}`)
            .then((res) => {
              const { likeCount, viewCount } = res.data;
              setLikeCounts(prev => ({ ...prev, [bookId]: likeCount }));
              setViewCounts(prev => ({ ...prev, [bookId]: viewCount }));
            })
            .catch(err => console.error("like/view 불러오기 실패:", err));
        });
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
            <SubscribedBookCard
              key={idx}
              book={{
                ...book,
                likeCount: likeCounts[book.bookId] ?? book.likeCount,
                viewCount: viewCounts[book.bookId] ?? book.viewCount
              }}
            />
          ))}
        </div>
      )}
    </div>
  );
}

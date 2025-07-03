import React, { useEffect, useState } from 'react';
import axios from 'axios';
import axiosInstance from '../../api/axiosInstance';
import SubscribedBookCard from './SubscribedBookCard';
import MainLayout from '../../components/layout/MainLayout';
import { useNavigate } from 'react-router-dom';
import './UserMainPage.css';

export default function SubscriptionBookPage() {
  const [books, setBooks] = useState([]);
  const [likeCounts, setLikeCounts] = useState({});
  const [viewCounts, setViewCounts] = useState({});

  const token = localStorage.getItem('token');
  const userId = localStorage.getItem('userId');
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`http://localhost:8088/subscriptions/list/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setBooks(res.data);
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
    <MainLayout>
      <div className="user-main-container">
        <div className="main-left">
          <div className="user-header-panel" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <h1>📚 구독 중인 도서</h1>
            <button
              className="small-button"
              onClick={() => navigate('/main/user')}
              style={{
                padding: '6px 12px',
                backgroundColor: '#4CAF50',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer',
                fontSize: '0.9rem'
              }}
            >
              홈으로
            </button>
          </div>

          {/* 👇 설명 문구만 따로 아래에 위치 */}
          <p style={{ fontSize: '1.1rem', marginBottom: '20px' }}>
            '<strong style={{ color: '#333', fontWeight: '600' }}>{userId}</strong>' 님의 구독 목록입니다.
          </p>

          {books.length === 0 ? (
            <p style={{ marginTop: '30px' }}>현재 구독 중인 도서가 없습니다.</p>
          ) : (
            <div className="category-list-vertical">
              <div className="category-row">
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
              </div>
            </div>
          )}
        </div>
      </div>
    </MainLayout>
  );
}

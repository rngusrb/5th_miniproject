import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import MainLayout from '../../components/layout/MainLayout';
import './BookRead.css';  // 원하시면 별도 스타일 작성

export default function BookReadPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);

  useEffect(() => {
    // TODO: 실제 API 연동 전 더미 데이터
    const dummy = {
      id,
      title: id === '1' ? '해리포터' : '왕좌의 게임',
      coverUrl: 'https://via.placeholder.com/200x300?text=Cover',
      views: 1234,
      content: `이곳에 도서(${id})의 본문 내용이 표시됩니다.\n\n`
    };
    setBook(dummy);
  }, [id]);

  if (!book) return null;

  return (
    <MainLayout>
      <div className="book-read-container">
        <div className="read-header">
          <h2>{book.title}</h2>
          <button className="btn back-btn small-btn" onClick={() => navigate(-1)}>
            ← 뒤로
          </button>
        </div>

        <div className="read-meta">
          <img src={book.coverUrl} alt="book cover" className="read-cover"/>
          <div className="read-stats">
            <p>조회수: <strong>{book.views}</strong></p>
          </div>
        </div>

        <div className="read-content">
          {book.content.split('\n').map((line, i) => (
            <p key={i}>{line}</p>
          ))}
        </div>
      </div>
    </MainLayout>
  );
}

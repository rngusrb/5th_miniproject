import './BookCard.css';
import React from 'react';

export default function BookCard({ book, showSubscribe = true,onRead }) {
  return (
    <div className="book-card">
      <div className="book-thumbnail">{book.title}</div>
      <div className="book-actions">
        {showSubscribe && <button>구독</button>}
        <button className="btn btn-primary" onClick={onRead}>
        열람
      </button>
      </div>
      <div className="book-meta">❤️ {book.likes} ☆ {book.subscribes}</div>
    </div>
  );
}
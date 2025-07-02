import React from 'react';
import '../../components/card/BookCard.css';

export default function SubscribedBookCard({ book }) {
  return (
    <div className="book-card">
      <div className="book-thumbnail">
        <img
          src={`/images/${book.bookCoverImage}`}
          alt="표지"
          className="book-cover-thumbnail"
        />
      </div>

      <div className="book-title-thumbnail">{book.bookTitle}</div>

      <div className="book-meta">
        ❤️ {book.likeCount ?? 0} ☆ {book.viewCount ?? 0}
      </div>

      <div className="book-actions">
        <button onClick={() => alert(`${book.bookTitle} 열람 페이지로 이동합니다.`)}>
          열람
        </button>
      </div>
    </div>
  );
}

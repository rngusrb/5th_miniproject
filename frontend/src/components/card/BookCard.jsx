import './BookCard.css';
import React, { useState } from 'react';
import axiosInstance from "../../api/axiosInstance";

const extractBookId = (book) => {
  const selfLink = book._links?.self?.href;
  if (!selfLink) return null;
  const parts = selfLink.split('/');
  return parts[parts.length - 1]; // 마지막 요소가 ID
};

export default function BookCard({ book, showSubscribe = true,onRead }) {
  const [likeCount, setLikeCount] = useState(book.likeCount);

  const handleLikeClick = async (book) => {
  try {
    const res = await axiosInstance.patch(`/books/${extractBookId(book)}/likebook`);
    setLikeCount(res.data.likeCount);
    // 서버 반영 후 상태 업데이트

  } catch (err) {
    console.error("좋아요 요청 실패", err);
  }

};
  return (
    <div className="book-card">
      <div className="book-thumbnail">
        <img className="book-cover-thumbnail" src={book.bookCoverImage}/>
      </div>
      <div className="book-title-thumbnail" title={book.bookTitle}>{book.bookTitle}</div>
      <div className="book-actions">
        {showSubscribe && <button>구독</button>}
        <button className="btn btn-primary" onClick={onRead}>
        열람
      </button>
      </div>
      <div className="book-meta"><span onClick={() => handleLikeClick(book)}>❤️ {likeCount}</span> ☆ {book.viewCount}</div>
    </div>
  );
}
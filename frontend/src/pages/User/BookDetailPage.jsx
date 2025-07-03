import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

export default function BookDetailPage() {
  const { id } = useParams(); // URL에서 bookId
  const token = localStorage.getItem('token');
  const userId = localStorage.getItem('userId');

  const [book, setBook] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8088/subscriptions/list/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        const bookList = res.data;
        const matchedBook = bookList.find((b) => b.bookId === parseInt(id));
        if (matchedBook) {
          setBook(matchedBook);
        } else {
          console.error("📕 해당 bookId를 가진 구독 도서를 찾을 수 없습니다.");
        }
      })
      .catch((err) => {
        console.error("📕 도서 정보를 불러오는 데 실패했습니다.", err);
      });
  }, [id, userId, token]);

  if (!book) return <p>도서 정보를 불러오는 중입니다...</p>;

  return (
    <div style={{ padding: '30px', maxWidth: '700px', margin: 'auto' }}>
      <div style={{ display: 'flex', gap: '20px' }}>
        <img
          src={book.bookCoverImage}
          alt="표지"
          style={{ width: '160px', height: '240px', objectFit: 'cover', borderRadius: '8px' }}
        />
        <div>
          <h2>{book.bookTitle}</h2>
          <p>👤 저자: {book.authorId ?? '정보 없음'}</p>
          <p>📁 카테고리: {book.category}</p>
          <p>📅 구독일: {new Date(book.subscribedDate).toLocaleDateString()}</p>
        </div>
      </div>

      <div style={{ marginTop: '20px' }}>
        <h3>📄 요약</h3>
        <p>{book.bookSummary}</p>
      </div>

      <div style={{ marginTop: '20px' }}>
        <h3>📘 내용 미리보기</h3>
        <p>{book.bookContent ?? '내용이 없습니다.'}</p>
      </div>
    </div>
  );
}

import React, { useEffect, useState } from "react";
import MainLayout from '../../components/layout/MainLayout.jsx'; // 확장자 명시
import BookCard from "../../components/card/BookCard.jsx";     // 확장자 명시
import { useNavigate } from "react-router-dom";
import axiosInstance from "../../api/axiosInstance.js"; // axiosInstance 확장자 명시

export default function AuthorMainPage() {
  const navigate = useNavigate();
  const AUTHOR_ID = 1; // 현재 로그인한 작가의 ID를 가정. 실제로는 AuthContext 등에서 가져와야 함.
  const API_TOKEN = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6InRlc3QiLCJwYXNzIjoxMjM0fQ.sBcWSbn_ZRJX6S_C-qF4m45zPNaQwVdKE20wuRroQbE'; // 실제 토큰으로 대체 필요

  const [publishedBooks, setPublishedBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // 출판된 도서 목록을 불러오는 함수
  const fetchPublishedBooks = async () => {
    setLoading(true);
    setError(null);
    try {
      // 모든 책을 불러오는 API 호출
      // 백엔드에 작가별 책 조회 API (예: /books/author/{authorId})가 있다면 더 효율적입니다.
      // 현재는 모든 책을 불러와서 프론트에서 필터링합니다.
      const res = await axiosInstance.get("/books", {
        headers: {
          Authorization: `Bearer ${API_TOKEN}`,
        },
      });

      // Spring Data REST 응답 형식에서 _embedded.book 배열에 접근
      const allBooks = res.data._embedded?.book || [];

      // 현재 작가(AUTHOR_ID)가 출판한 책만 필터링
      const filteredBooks = allBooks.filter(book => book.authorId === AUTHOR_ID);
      setPublishedBooks(filteredBooks);

    } catch (err) {
      console.error("출판된 도서 목록 불러오기 실패:", err);
      setError('출판된 도서 목록을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPublishedBooks();
  }, [AUTHOR_ID, API_TOKEN]); // AUTHOR_ID 또는 API_TOKEN 변경 시 다시 불러오기

  return (
    <MainLayout>
      <h2>내가 출판한 도서</h2>
      <div style={{ display: "flex", gap: "1rem", flexWrap: "wrap", marginBottom: "2rem" }}>
        {loading ? (
          <p>도서 목록을 불러오는 중...</p>
        ) : error ? (
          <p style={{ color: 'red' }}>{error}</p>
        ) : publishedBooks.length === 0 ? (
          <p>출판된 도서가 없습니다.</p>
        ) : (
          publishedBooks.map((book) => (
            <BookCard
              key={book.bookId} // book.id 대신 book.bookId 사용 (API 응답 필드에 맞춤)
              book={book}
              showSubscribe={false} // 작가 페이지에서는 구독 버튼을 숨김
              onRead={() => navigate(`/author/book/${book.bookId}`)} // book.id 대신 book.bookId 사용
            />
          ))
        )}
      </div>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(2, 1fr)", gap: "1rem", maxWidth: "400px" }}>
        <button onClick={() => navigate("/author/register")}>작가 등록</button>
        <button onClick={() => navigate("/author/manuscript")}>원고 등록</button>
        <button onClick={() => navigate("/author/draftlist")}>원고 목록 & AI 활용</button>
        <button onClick={() => navigate("/author/publishrequest")}>출간 요청</button>
        <button onClick={() => alert("출간 확인 기능 준비중")}>출간 확인</button> {/* 출간 확인은 관리자 기능이므로 임시로 alert */}
      </div>
    </MainLayout>
  );
}

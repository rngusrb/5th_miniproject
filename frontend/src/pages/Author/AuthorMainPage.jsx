import React from "react";
import MainLayout from '../../components/layout/MainLayout';
import BookCard from "../../components/card/BookCard";
import { useNavigate } from "react-router-dom";

const dummyBooks = [
  {
    id: 1,
    title: "해리포터",
    likes: 30,
    subscribes: 22,
    imageUrl: "https://via.placeholder.com/150",
  },
  {
    id: 2,
    title: "왕좌의 게임",
    likes: 12,
    subscribes: 6,
    imageUrl: "https://via.placeholder.com/150",
  },
];

export default function AuthorMainPage() {
  const navigate = useNavigate();

  return (
    <MainLayout>
      <h2>내가 출판한 도서</h2>
      <div style={{ display: "flex", gap: "1rem", flexWrap: "wrap", marginBottom: "2rem" }}>
        {dummyBooks.map((book) => (
          <BookCard
            key={book.id}
            book={book}
            showSubscribe={false}
            onRead={() => navigate(`/author/book/${book.id}`)}
          />
        ))}

        <button onClick={() => alert("더보기 기능 준비중")}>
          더보기
        </button>
      </div>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(2, 1fr)", gap: "1rem", maxWidth: "400px" }}>
        <button onClick={() => navigate("/author/register")}>작가 등록</button>
        <button onClick={() => navigate("/author/manuscript")}>원고 등록</button>
        <button onClick={() => navigate("/author/draftlist")}>원고 목록 & AI 활용</button>
        <button onClick={() => navigate("/author/publishrequest")}>출간 요청</button>
        <button onClick={() => navigate("/author/publishconfirm")}>출간 확인</button>
        
      </div>
    </MainLayout>
  );
}

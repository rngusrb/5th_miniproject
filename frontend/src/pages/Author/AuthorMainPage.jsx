import React from "react";
import AuthorLayout from "../../components/layout/AuthorLayout";
import BookCard from "../../components/card/BookCard";
import { useNavigate } from "react-router-dom";

const dummyBooks = [
  {
    id: 1,
    title: "작가의 첫 번째 책",
    likes: 30,
    saves: 22,
    imageUrl: "https://via.placeholder.com/150",
  },
  {
    id: 2,
    title: "후속작: 깊은 이야기",
    likes: 12,
    saves: 6,
    imageUrl: "https://via.placeholder.com/150",
  },
];

export default function AuthorMainPage() {
  const navigate = useNavigate();

  return (
    <AuthorLayout>
      <h2>내가 출판한 도서</h2>
      <div style={{ display: "flex", gap: "1rem", flexWrap: "wrap", marginBottom: "2rem" }}>
        {dummyBooks.map((book) => (
          <BookCard key={book.id} book={book} showSubscribe={false}/>
        ))}
        <button onClick={() => alert("더보기 기능 준비중")}>더보기</button>
      </div>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(2, 1fr)", gap: "1rem", maxWidth: "400px" }}>
        <button onClick={() => navigate("/author/register")}>작가 등록</button>
        <button onClick={() => navigate("/author/manuscript")}>원고 등록</button>
        <button onClick={() => navigate("/author/draft")}>임시 저장 목록</button>
        <button onClick={() => navigate("/author/request")}>출간 요청</button>
        <button onClick={() => navigate("/author/confirm")}>출간 확인</button>
      </div>
    </AuthorLayout>
  );
}

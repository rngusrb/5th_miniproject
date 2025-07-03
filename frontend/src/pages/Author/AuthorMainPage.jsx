import React, { useEffect, useState } from "react";
import MainLayout from '../../components/layout/MainLayout.jsx';
import BookCard from "../../components/card/BookCard.jsx";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../../api/axiosInstance.js";

export default function AuthorMainPage() {
  const navigate = useNavigate();
  const authorId = localStorage.getItem('userId');

  const [authorInfo, setAuthorInfo] = useState(null);
  const [publishedBooks, setPublishedBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchAuthorInfo = async () => {
    if (!authorId) return;
    try {
      const res = await axiosInstance.get(`/authors/${authorId}`);
      setAuthorInfo(res.data);
      if (!res.data.isActive) {
        setLoading(false);
      }
    } catch (err) {
      console.error("작가 정보 불러오기 실패:", err);
      setError("작가 정보를 불러오는데 실패했습니다.");
      setLoading(false);
    }
  };


const fetchPublishedBooks = async () => {
    if (!authorId) return;
    setLoading(true);
    try {
      const res = await axiosInstance.get("/books");

      const allBooks = res.data._embedded?.books || [];

      const filteredBooks = allBooks.filter(book => book.authorId === Number(authorId));
      setPublishedBooks(filteredBooks);

    } catch (err) {
      console.error("출판된 도서 목록 불러오기 실패:", err);
      setError('출판된 도서 목록을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (!authorId) {
      alert('로그인이 필요합니다.');
      navigate('/login/author');
    } else {
      fetchAuthorInfo();
    }
  }, [authorId, navigate]);

  useEffect(() => {
    // 작가 정보가 있고, 활성 상태일 때만 책 목록을 불러옵니다.
    if (authorInfo && authorInfo.isActive) {
      fetchPublishedBooks();
    }
  }, [authorInfo]);

  if (loading) {
    return <MainLayout><p>정보를 불러오는 중...</p></MainLayout>;
  }
  if (error) {
    return <MainLayout><p style={{ color: 'red' }}>{error}</p></MainLayout>;
  }

  return (
    <MainLayout>
      {authorInfo && !authorInfo.isActive ? (
        <div style={{ textAlign: 'center', marginTop: '5rem' }}>
          <h2>작가 승인 대기 중</h2>
          <p>관리자의 승인 후 작가 전용 기능을 사용하실 수 있습니다.</p>
          <button onClick={() => navigate("/author/register")}>
            작가 정보 등록/수정 페이지로 이동
          </button>
        </div>
      ) : (
        <>
          <h2>내가 출판한 도서</h2>
          <div style={{ display: "flex", gap: "1rem", flexWrap: "wrap", marginBottom: "2rem" }}>
            {publishedBooks.length === 0 ? (
              <p>출판된 도서가 없습니다.</p>
            ) : (
              publishedBooks.map((book) => (
                <BookCard
                  key={book.bookId}
                  book={book}
                  showSubscribe={false}
                />
              ))
            )}
          </div>
          <div style={{ display: "grid", gridTemplateColumns: "repeat(2, 1fr)", gap: "1rem", maxWidth: "400px" }}>
            <button onClick={() => navigate("/author/register")}>작가 정보 수정</button>
            <button onClick={() => navigate("/author/manuscript")}>원고 등록</button>
            <button onClick={() => navigate("/author/draftlist")}>원고 목록 & AI 활용</button>
            <button onClick={() => navigate("/author/publishrequest")}>출간 요청</button>
            <button onClick={() => alert("출간 확인 기능 준비중")}>출간 확인</button>
            <button onClick={() => {
              if(confirm('로그아웃 하시겠습니까?')) {
                const token = localStorage.getItem('token');
                if(token) localStorage.removeItem('token');
                navigate('/');
              }
            }}>로그아웃</button>
          </div>
        </>
      )}
    </MainLayout>
  );
}
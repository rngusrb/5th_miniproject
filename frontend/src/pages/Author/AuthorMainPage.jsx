import React, { useEffect, useState } from "react";
import MainLayout from '../../components/layout/MainLayout.jsx';
import BookCard from "../../components/card/BookCard.jsx";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../../api/axiosInstance.js";

export default function AuthorMainPage() {
  const navigate = useNavigate();

  // localStorage에서 로그인된 사용자의 ID를 가져옴
  const authorId = localStorage.getItem('userId');

  const [authorInfo, setAuthorInfo] = useState(null);
  const [publishedBooks, setPublishedBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // 작가 정보를 불러오는 함수
  const fetchAuthorInfo = async () => {
    // authorId가 있을 때만 API를 호출합니다.
    if (!authorId) return;

    try {
      // 하드코딩된 ID 대신 localStorage에서 가져온 authorId를 사용합
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

  // 출판된 도서 목록을 불러오는 함수
  const fetchPublishedBooks = async () => {
    if (!authorId) return;
    setLoading(true);
    try {
      const res = await axiosInstance.get("/books");
      const allBooks = res.data._embedded?.book || [];
      // 하드코딩된 ID 대신 localStorage에서 가져온 authorId를 사용
      const filteredBooks = allBooks.filter(book => book.authorId === Number(authorId));
      setPublishedBooks(filteredBooks);
    } catch (err) {
      console.error("출판된 도서 목록 불러오기 실패:", err);
      setError('출판된 도서 목록을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  // useEffect 로직 수정
  useEffect(() => {
    //  로그인 상태를 확인하고, 비로그인 시 로그인 페이지로 보냄
    if (!authorId) {
      alert('로그인이 필요합니다.');
      navigate('/login/author');
    } else {
      // 로그인 상태이면 작가 정보를 조회합니다.
      fetchAuthorInfo();
    }
  }, [authorId, navigate]); // 의존성 배열에 authorId와 navigate 추가

  useEffect(() => {
    if (authorInfo && authorInfo.isActive) {
      fetchPublishedBooks();
    }
  }, [authorInfo]);


  // 로딩 중일 때의 화면
  if (loading) {
    return <MainLayout><p>정보를 불러오는 중...</p></MainLayout>;
  }

  // 에러 발생 시의 화면
  if (error) {
    return <MainLayout><p style={{ color: 'red' }}>{error}</p></MainLayout>;
  }


  return (
    <MainLayout>
      {authorInfo && !authorInfo.isActive ? (
        // 작가가 비활성 상태일 경우
        <div style={{ textAlign: 'center', marginTop: '5rem' }}>
          <h2>작가 승인 대기 중</h2>
          <p>관리자의 승인 후 작가 전용 기능을 사용하실 수 있습니다.</p>
          <p>작가 프로필(자기소개, 포트폴리오 등)을 등록하여 승인을 요청하세요.</p>
          <button
            style={{ marginTop: '1rem', padding: '10px 20px', cursor: 'pointer' }}
            onClick={() => navigate("/author/register")}
          >
            작가 정보 등록/수정 페이지로 이동
          </button>
        </div>
      ) : (
        // 작가가 활성 상태일 경우
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
                  onRead={() => navigate(`/author/book/${book.bookId}`)}
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
          </div>
        </>
      )}
    </MainLayout>
  );
}
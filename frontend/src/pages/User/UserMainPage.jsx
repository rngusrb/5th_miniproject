import React, { useEffect, useState } from 'react';
import axios from 'axios';
import BookCard from '../../components/card/BookCard';
import MyPagePanel from '../../components/layout/MyPagePanel';
import PointChargePanel from '../../components/layout/PointChargePanel';
import SubscribePanel from '../../components/layout/SubscribePanel';
import MainLayout from '../../components/layout/MainLayout';
import './UserMainPage.css';
import axiosInstance from "../../api/axiosInstance";

export default function UserMainPage() {
  const [showMyPage, setShowMyPage] = useState(false);
  const [showChargePanel, setShowChargePanel] = useState(false);
  const [showSubscribePanel, setShowSubscribePanel] = useState(false);
  const [point, setPoint] = useState(0);
  const [isPremium, setIsPremium] = useState(false); // ✅ 구독 여부 상태
  const [bestsellers, setBestsellers] = useState([]);
  const [bookList, setBooklist] = useState([]);
  const [categories, setCategories] = useState({});

  const getBestsellers = async () => {
    try {
      const res = await axiosInstance.get("/books");

      setBooklist(res.data._embedded.books)

      const sorted = res.data._embedded.books
        .sort((a, b) => b.likeCount - a.likeCount)
        .slice(0, 3);

      setBestsellers(sorted); 

    } catch (err) {
      console.error("오류: ", err.response?.data);
    }
  };

  // const categories = {
  //   "소설": [{ id: 4, title: "소설책", likes: 370, subscribes: 82 }],
  //   "판타지": [{ id: 5, title: "판타지책", likes: 370, subscribes: 82 }],
  //   "경제": [{ id: 6, title: "경제책", likes: 370, subscribes: 82 }]
  // };

  function getBookIdFromHref(href) {
    return parseInt(href?.split("/").pop(), 10);
  }
  // 카테고리별 책 정리 함수
  const getCategoryBooks = () => {
    const grouped = {};

    bookList.forEach(book => {
      const category = book.category || "기타";
      const bookId = getBookIdFromHref(book._links?.self?.href);

      if (!grouped[category]) grouped[category] = [];

      grouped[category].push({
        bookId,
        bookTitle: book.bookTitle,
        likeCount: book.likeCount,
        viewCount: book.viewCount,
        bookCoverImage: book.bookCoverImage, // 👉 BookCard에 전달할 전체 book 데이터 포함
      });
    });

    setCategories(grouped);
  };

  const fetchPoint = async () => {
    try {
      const userId = localStorage.getItem('userId');
      const token = localStorage.getItem('token');
      if (!userId || !token) return;

      const res = await axios.get(`/points/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      console.log("📦 point 응답 데이터:", res.data);

      if (res.data?.pointSum !== undefined) {
        setPoint(res.data.pointSum);
      } else {
        setPoint(0);
      }
    } catch (err) {
      console.error('포인트 조회 실패:', err);
      setPoint(0);
    }
  };

  const fetchUserPass = async () => {
    try {
      const userId = localStorage.getItem('userId');
      const token = localStorage.getItem('token');
      if (!userId || !token) return;

      const res = await axios.get(`/users/${userId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (res.data?.pass === true) {
        setIsPremium(true);
      } else {
        setIsPremium(false);
      }
    } catch (err) {
      console.error("구독 상태 조회 실패:", err);
      setIsPremium(false);
    }
  };

  useEffect(() => {
    fetchPoint();
    fetchUserPass(); // ✅ 함께 실행
  }, []);

  useEffect(() => {
    getBestsellers();
  }, []);
  
  useEffect(() => {
    if (bookList.length > 0) {
      getCategoryBooks();
    }
  }, [bookList]);

  return (
    <MainLayout>
      <div className="user-main-container">
        <div className="main-left">
          <div className="user-header-panel">
            <h1>걷다가 서재</h1>
            {
              isPremium
                ? <span className="premium-badge">🌟 Premium Pass</span>
                : <span>포인트: {point.toLocaleString()}P</span>
            }
            <button onClick={() => setShowMyPage(v => !v)}>
              My Page
            </button>
          </div>

          <h2>이달의 베스트셀러</h2>
          <div className="bestseller-grid">
            {bestsellers.map(book => (
              <BookCard key={book.bookId} book={book} />
            ))}
          </div>

          <h2>카테고리별</h2>
          <div className="category-list-vertical">
            {Object.entries(categories).map(([catName, books]) => (
              <div key={catName} className="category-row">
                <div className="category-label">{catName}</div>
                <div className="book-row-scrollable">
                  {books.map(book => (
                    <BookCard key={book.id} book={book} />
                  ))}
                </div>
              </div>
            ))}
          </div>
        </div>

        {showMyPage && (
          <div className="main-right">
            <MyPagePanel
              onClose={() => setShowMyPage(false)}
              onChargeClick={() => {
                setShowMyPage(false);
                setShowChargePanel(true);
              }}
              onSubscribeClick={() => {
                setShowMyPage(false);
                setShowSubscribePanel(true);
              }}
            />
          </div>
        )}

        {showChargePanel && (
          <div className="main-right">
            <PointChargePanel
              onClose={() => setShowChargePanel(false)}
              onCharged={fetchPoint}
            />
          </div>
        )}

        {showSubscribePanel && (
          <div className="main-right">
            <SubscribePanel
              onClose={() => setShowSubscribePanel(false)}
              onSubscribed={() => {
                alert("구독이 완료되었습니다!");
                fetchUserPass(); // ✅ 구독 후 상태 갱신
              }}
            />
          </div>
        )}
      </div>
    </MainLayout>
  );
}

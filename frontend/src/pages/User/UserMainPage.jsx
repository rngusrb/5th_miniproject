import React, { useEffect, useState } from 'react';
import axios from 'axios';
import BookCard from '../../components/card/BookCard';
import MyPagePanel from '../../components/layout/MyPagePanel';
import PointChargePanel from '../../components/layout/PointChargePanel';
import SubscribePanel from '../../components/layout/SubscribePanel';
import MainLayout from '../../components/layout/MainLayout';
import './UserMainPage.css';
import axiosInstance from "../../api/axiosInstance";
import { useNavigate } from 'react-router-dom';

const getBookIdFromHref = (selfLink) => {
  if (!selfLink) return null;
  const parts = selfLink.split('/');
  return parts[parts.length - 1];
};

export default function UserMainPage() {
  const [showMyPage, setShowMyPage] = useState(false);
  const [showChargePanel, setShowChargePanel] = useState(false);
  const [showSubscribePanel, setShowSubscribePanel] = useState(false);
  const [point, setPoint] = useState(0);
  const [isPremium, setIsPremium] = useState(false); // ✅ 구독 여부 상태
  const [bestsellers, setBestsellers] = useState([]);
  const [bookList, setBooklist] = useState([]);
  const [categories, setCategories] = useState({});
  const [refreshFlag, setRefreshFlag] = useState(false);
  const navigate = useNavigate();

  const fetchBooksAndSeparate = async () => {
    try {
      const res = await axiosInstance.get("/books");
      const books = res.data._embedded.books;

      setBooklist(books); // 전체 원본 저장

      // 📌 1. bestseller 따로 추출
      const bestsellers = books
        .sort((a, b) => b.likeCount - a.likeCount)
        .slice(0, 3)
        .map(book => {
          const bookId = getBookIdFromHref(book._links?.self?.href);
          return {
            bookId,
            bookTitle: book.bookTitle,
            likeCount: book.likeCount,
            viewCount: book.viewCount,
            bookCoverImage: book.bookCoverImage,
          };
        });

      setBestsellers(bestsellers);

      // 📌 2. 카테고리별 분류
      const grouped = {};

      books.forEach(book => {
        const category = book.category || "기타";
        const bookId = getBookIdFromHref(book._links?.self?.href);

        if (!grouped[category]) grouped[category] = [];

        grouped[category].push({
          bookId,
          bookTitle: book.bookTitle,
          likeCount: book.likeCount,
          viewCount: book.viewCount,
          bookCoverImage: book.bookCoverImage,
        });
      });

      setCategories(grouped);

    } catch (err) {
      console.error("오류: ", err.response?.data);
    }
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
    fetchBooksAndSeparate();
  }, [refreshFlag]);
  
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
              <BookCard key={book.bookId} book={book} onPointChanged={fetchPoint} onLike={() => setRefreshFlag(prev => !prev)}  />
            ))}
          </div>

          <h2>카테고리별</h2>
          <div className="category-list-vertical">
            {Object.entries(categories).map(([catName, books]) => (
              <div key={catName} className="category-row">
                <div className="category-label">{catName}</div>
                <div className="book-row-scrollable">
                  {books.map(book => (
                    <BookCard key={book.bookId} book={book} onPointChanged={fetchPoint} onLike={() => setRefreshFlag(prev => !prev)} />
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
              onMySubscribeClick={() => {
                setShowMyPage(false);
                navigate('/mypage/user/subscribed-books');
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

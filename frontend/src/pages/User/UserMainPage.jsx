import React, { useEffect } from 'react';
import { useState } from 'react';
import BookCard from '../../components/card/BookCard';
import MyPagePanel from '../../components/layout/MyPagePanel';
import MainLayout from '../../components/layout/MainLayout';
import './UserMainPage.css';
import axiosInstance from "../../api/axiosInstance";

export default function UserMainPage() {
  const [showMyPage, setShowMyPage] = useState(false);
  const [bestsellers, setBestsellers] = useState([]);

  // const bestsellers = [
  //   { bookId: 1, bookTitle: "책 1", likeCount: 370, viewCount: 82, bookCoverImage: "" },
  //   { bookId: 2, bookTitle: "책 2", likeCount: 350, viewCount: 70, bookCoverImage: "" },
  //   { bookId: 3, bookTitle: "책 3", likeCount: 320, viewCount: 60, bookCoverImage: "" }
  // ];

  const getBestsellers = async () => {
    try {
      const res = await axiosInstance.get("/books");
      console.log(res.data._embedded.books);
      setBestsellers(res.data._embedded.books); // ✅ 상태에 반영

      return res.data._embedded.books
    } catch (err) {
      console.error("오류: ", err.response?.data);
    }
  };

  const categories = {
    "소설": [{ id: 4, title: "소설책", likes: 370, subscribes: 82 }],
    "판타지": [{ id: 5, title: "판타지책", likes: 370, subscribes: 82 }],
    "경제": [{ id: 6, title: "경제책", likes: 370, subscribes: 82 }]
  };

  useEffect(() => {
    getBestsellers();
  }, []);

  return (
    <MainLayout>
      <div className="user-main-container">       {/* ② 최상위 container */}
        <div className="main-left">
          {/* 헤더 */}
          <div className="user-header-panel">
            <h1>걷다가 서재</h1>
            <span>포인트: 8000</span>
            <button onClick={() => setShowMyPage(v => !v)}>
              My Page
            </button>
          </div>

          {/* 이달의 베스트셀러 */}
          <h2>이달의 베스트셀러</h2>
          <div className="bestseller-grid">
            {bestsellers.map(book => (
              <BookCard key={book.bookId} book={book} />
            ))}
          </div>

          {/* 카테고리별 */}
          <h2>카테고리별</h2>
          <div className="category-grid">
            {Object.entries(categories).map(([catName, books]) => (
              <div key={catName} className="category-item">
                <div className="category-label">{catName}</div>
                {books.map(book => (
                  <BookCard key={book.id} book={book} />
                ))}
              </div>
            ))}
          </div>
        </div>

        {/* My Page 패널 */}
        {showMyPage && (
          <div className="main-right">
            <MyPagePanel onClose={() => setShowMyPage(false)} />
          </div>
        )}
      </div>
    </MainLayout>
  );
}

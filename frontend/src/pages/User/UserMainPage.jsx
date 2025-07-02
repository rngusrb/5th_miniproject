import React, { useEffect, useState } from 'react';
import axios from 'axios';
import React, { useEffect } from 'react';
import { useState } from 'react';
import BookCard from '../../components/card/BookCard';
import MyPagePanel from '../../components/layout/MyPagePanel';
import PointChargePanel from '../../components/layout/PointChargePanel'; // 👈 새로 추가
import MainLayout from '../../components/layout/MainLayout';
import './UserMainPage.css';
import axiosInstance from "../../api/axiosInstance";

export default function UserMainPage() {
  const [showMyPage, setShowMyPage] = useState(false);
  const [showChargePanel, setShowChargePanel] = useState(false); // 👈 포인트 충전 패널 상태
  const [point, setPoint] = useState(0);
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

  useEffect(() => {
    fetchPoint();

  useEffect(() => {
    getBestsellers();

  }, []);

  return (
    <MainLayout>
      <div className="user-main-container">
        <div className="main-left">
          <div className="user-header-panel">
            <h1>걷다가 서재</h1>
            <span>포인트: {point.toLocaleString()}P</span>
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

        {/* 마이페이지 패널 */}
        {showMyPage && (
          <div className="main-right">
            <MyPagePanel
              onClose={() => setShowMyPage(false)}
              onChargeClick={() => {
                setShowMyPage(false);         // 마이페이지 닫고
                setShowChargePanel(true);     // 충전창 열기
              }}
            />
          </div>
        )}

        {/* 포인트 충전 패널 */}
        {showChargePanel && (
          <div className="main-right">
            <PointChargePanel
              onClose={() => setShowChargePanel(false)}
              onCharged={fetchPoint}
            />
          </div>
        )}
      </div>
    </MainLayout>
  );
}

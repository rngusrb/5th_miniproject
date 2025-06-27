import React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';

function GenMyPage() {
  return (
    <List>
      <ListItem>포인트 충전</ListItem>
      <ListItem>정기 구독권 결제</ListItem>
      <ListItem>구독중인 도서</ListItem>
      <ListItem>열람한 도서</ListItem>
      <ListItem>로그아웃</ListItem>
      <ListItem>탈퇴</ListItem>
    </List>
  );
}

export default GenMyPage;

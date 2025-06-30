import React from 'react';
import Typography from '@mui/material/Typography';
import { useParams } from 'react-router-dom';

function BookDetail() {
  const { id } = useParams();
  return <Typography>도서 상세 페이지 (ID: {id}) 스켈레톤</Typography>;
}

export default BookDetail;

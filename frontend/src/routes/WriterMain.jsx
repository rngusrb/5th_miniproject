import React from 'react';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';

function WriterMain() {
  return (
    <Stack spacing={2} sx={{ mt: 4 }}>
      <Button variant="contained">작가 등록</Button>
      <Button variant="contained">원고 등록</Button>
      <Button variant="contained">임시 저장 목록</Button>
      <Button variant="contained">출간 요청</Button>
      <Button variant="contained">출간 확인</Button>
    </Stack>
  );
}

export default WriterMain;

import React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

function WriterSignup() {
  return (
    <Box component="form" sx={{ mt: 4, maxWidth: 360, mx: 'auto' }}>
      <TextField label="이메일" fullWidth sx={{ mb: 2 }} />
      <TextField label="아이디" fullWidth sx={{ mb: 2 }} />
      <TextField label="비밀번호" type="password" fullWidth sx={{ mb: 2 }} />
      <TextField label="비밀번호 확인" type="password" fullWidth sx={{ mb: 2 }} />
      <Button variant="contained" fullWidth>회원가입</Button>
    </Box>
  );
}

export default WriterSignup;

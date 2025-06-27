import React from 'react';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';

function ChoicePage() {
  return (
    <Grid container spacing={2} justifyContent="center" sx={{ mt: 4 }}>
      <Grid item>
        <Button variant="contained" href="/login/writer">작가 로그인</Button>
      </Grid>
      <Grid item>
        <Button variant="contained" href="/login/general">일반 로그인</Button>
      </Grid>
      <Grid item>
        <Button variant="contained" href="/signup/writer">작가 회원가입</Button>
      </Grid>
      <Grid item>
        <Button variant="contained" href="/signup/general">일반 회원가입</Button>
      </Grid>
    </Grid>
  );
}

export default ChoicePage;

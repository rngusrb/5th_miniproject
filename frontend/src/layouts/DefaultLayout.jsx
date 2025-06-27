import React from 'react';
import HeaderBar from '../components/HeaderBar';
import Container from '@mui/material/Container';
import { Outlet } from 'react-router-dom';

function DefaultLayout() {
  return (
    <>
      <HeaderBar />
      <Container sx={{ mt: 2 }}>
        <Outlet />
      </Container>
    </>
  );
}

export default DefaultLayout;

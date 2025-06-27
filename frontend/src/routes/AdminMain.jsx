import React from 'react';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import Box from '@mui/material/Box';
import { Outlet } from 'react-router-dom';

function AdminMain() {
  return (
    <Box sx={{ display: 'flex' }}>
      <Drawer variant="permanent" open>
        <List>
          <ListItem>작가 승인</ListItem>
          <ListItem>작가 관리</ListItem>
          <ListItem>원고 관리</ListItem>
          <ListItem>유저 관리</ListItem>
          <ListItem>도서 관리</ListItem>
          <ListItem>AI 관리</ListItem>
        </List>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Outlet />
      </Box>
    </Box>
  );
}

export default AdminMain;

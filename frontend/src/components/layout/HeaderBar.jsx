import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';

function HeaderBar() {
  const user = null; // TODO: replace with actual auth logic
  const points = 0;
  return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6">걷다가 서재</Typography>
        <Box sx={{ flexGrow: 1 }} />
        {user && (
          <>
            <Typography sx={{ mr: 2 }}>포인트: {points}</Typography>
            <Button color="inherit" href="/app/mypage">My Page</Button>
          </>
        )}
      </Toolbar>
    </AppBar>
  );
}

export default HeaderBar;

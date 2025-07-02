import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // 모든 요청은 Gateway로 전달
      '/users': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/userLists': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/books': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/booksForSubscribers': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/booksByAuthors': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/allBooks': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/admins': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/authors': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/authorLists': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/platforms': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/manuscripts': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/manuscriptLists': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/subscriptions': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/subscriptionLists': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/points': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
      '/pointUsageHistories': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
    },
  },
});

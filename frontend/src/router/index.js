import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../components/pages/Index.vue'),
    },
    {
      path: '/users',
      component: () => import('../components/ui/UserGrid.vue'),
    },
    {
      path: '/userLists',
      component: () => import('../components/UserListView.vue'),
    },
    {
      path: '/books',
      component: () => import('../components/ui/BookGrid.vue'),
    },
    {
      path: '/booksForSubscribers',
      component: () => import('../components/BooksForSubscribersView.vue'),
    },
    {
      path: '/booksByAuthors',
      component: () => import('../components/BooksByAuthorView.vue'),
    },
    {
      path: '/allBooks',
      component: () => import('../components/AllBooksView.vue'),
    },
    {
      path: '/admins',
      component: () => import('../components/ui/AdminGrid.vue'),
    },
    {
      path: '/authors',
      component: () => import('../components/ui/AuthorGrid.vue'),
    },
    {
      path: '/authorLists',
      component: () => import('../components/AuthorListView.vue'),
    },
    {
      path: '/platforms',
      component: () => import('../components/ui/PlatformGrid.vue'),
    },
    {
      path: '/manuscripts',
      component: () => import('../components/ui/ManuscriptGrid.vue'),
    },
    {
      path: '/manuscriptLists',
      component: () => import('../components/ManuscriptListView.vue'),
    },
    {
      path: '/subscriptions',
      component: () => import('../components/ui/SubscriptionGrid.vue'),
    },
    {
      path: '/subscriptionLists',
      component: () => import('../components/SubscriptionListView.vue'),
    },
    {
      path: '/points',
      component: () => import('../components/ui/PointGrid.vue'),
    },
    {
      path: '/pointUsageHistories',
      component: () => import('../components/PointUsageHistoryView.vue'),
    },
  ],
})

export default router;

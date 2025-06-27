<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: '유저 관리' }" />
        <VerticalNavLink
            :item="{
                title: '유저관리',
                to: '/users',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '유저 목록',
                to: '/userLists',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '도서관리' }" />
        <VerticalNavLink
            :item="{
                title: '도서 관리',
                to: '/books',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '구독자 도서 목록',
                to: '/booksForSubscribers',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '작가 도서 목록',
                to: '/booksByAuthors',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '전체 도서 목록',
                to: '/allBooks',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'Admin' }" />
        <VerticalNavLink
            :item="{
                title: 'Admin',
                to: '/admins',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '작가 관리' }" />
        <VerticalNavLink
            :item="{
                title: '작가관리',
                to: '/authors',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '작가 목록',
                to: '/authorLists',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '플랫폼 관리' }" />
        <VerticalNavLink
            :item="{
                title: '플랫폼 관리',
                to: '/platforms',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '원고 관리' }" />
        <VerticalNavLink
            :item="{
                title: '원고 관리',
                to: '/manuscripts',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '원고 목록',
                to: '/manuscriptLists',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '구독 관리' }" />
        <VerticalNavLink
            :item="{
                title: '구독 관리',
                to: '/subscriptions',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '구독권/구독 목록',
                to: '/subscriptionLists',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '포인트 관리' }" />
        <VerticalNavLink
            :item="{
                title: '포인트 관리',
                to: '/points',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '포인트 사용 내역',
                to: '/pointUsageHistories',
            }"
        />
    </ul>
</template>

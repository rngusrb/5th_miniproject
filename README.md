# AI 기반 자동 출판 및 구독 플랫폼

이 프로젝트는 MSA 기반 클라우드 네이티브 아키텍처를 활용해 자동 출판, 표지 생성, 구독 기반 전자책 서비스를 제공합니다.

---

## 개요
- 프로젝트명: AI 기반 자동 출판 및 구독 플랫폼
- 개발 기간: 2025년 6월 25일 ~ 7월 3일
- 팀원: 김지현, 김동영, 박기웅, 구현규, 신현승, 이유민, 양준모, 옥정우
- 목적: 클라우드 네이티브 앱 개발 (MSA 구조, 배포 자동화 포함)

---

## 주요 기술 스택 및 환경

- **MSA 기반 서비스 분리**
  - user / subscription / point / book / author / admin / manuscript / gateway / platform / frontend
- **백엔드**: Spring Boot, JPA, H2
- **프론트엔드**: React, Axios, Tailwind
- **AI 기능**: OpenAI(DALL·E 3) 기반 표지 자동 생성
- **인증/보안**: JWT 기반 인증 시스템 구현
- **DevOps**: Docker, Kubernetes, Azure, Istio, Kafka
- **CI/CD**: Github Actions, shell script 자동화

---

## 프로젝트 주요 기능 요약

- 유저 로그인/로그아웃
- 전자책 업로드 및 출판 요청
- AI 기반 책 표지 생성 (OpenAI API 활용)
- 도서 구독 및 구독권 생성/취소 기능
- 포인트 충전 및 사용 내역 관리
- 도서 열람/좋아요 기록 통계화
- 마이페이지 기반 UX 설계

---

## API 문서 (Swagger 기반)

![API 명세 1](https://github.com/user-attachments/assets/ec42a72e-476c-442c-9191-927e91d39267)
![API 명세 2](https://github.com/user-attachments/assets/58631a2d-9ff0-4202-9fb7-f4d0b4beb6df)
![API 명세 3](https://github.com/user-attachments/assets/22c23fe8-a16a-42c0-968f-a7b3f5ff2e28)
![API 명세 4](https://github.com/user-attachments/assets/3bfec8f6-4bfe-4675-acd9-6c944f546733)
![API 명세 5](https://github.com/user-attachments/assets/a13b9739-9f07-4880-8f83-7fe9d802d331)

---

## 설계 구조 (MSA EZ 기반)

![MSA 설계 다이어그램](https://github.com/user-attachments/assets/f8f919ab-2874-4cd6-87fe-197552fa95e4)

> 전체 서비스는 MSA-EZ를 통해 헥사고날 아키텍처 기반으로 설계되었으며, 이벤트 기반 Kafka 구조와 Gateway API 라우팅이 적용됨

---

## 개발 프로세스 정리

### 프론트엔드
[Notion - 프론트엔드 작업 정리](https://www.notion.so/2267bde02a668082a79dfc0a3d605113?pvs=21)

### 백엔드
[Notion - 백엔드 작업 정리](https://www.notion.so/2267bde02a6680d3afa8c7802e0f0b9d?pvs=21)

### DevOps
[Notion - DevOps 작업 정리](https://www.notion.so/2267bde02a6680bcba74ebfbec8fcd96?pvs=21)

---

## 깃허브 주소
[GitHub - leeyumin-dev/5th_miniproject](https://github.com/leeyumin-dev/5th_miniproject)

---

## 회의 및 일정 요약

| 날짜 | 내용 요약 |
|------|-----------|
| 1일차 | 역할 분담, MSA EZ 구성, 헥사고날 다이어그램 작성 |
| 2일차 | ERD 확정, gateway 포함 구조 검토 |
| 3일차 | 로그인, 표지 생성, 구독권 UI/기능 구성 |
| 4일차 | MSA 구조 반영한 설계 수정, API 연결 테스트 |
| 5일차 | JWT 인증 연동, 구독권 취소 기능, 포인트 로직 적용 |
| 6일차 | 메인 연동, 배포 준비 (Azure, Docker, Istio 등) |
| 7일차 | 통합 테스트 및 배포 완료 |

---

## 결론
- 클라우드 네이티브 환경에서의 MSA 분산 시스템 개발 실습 경험 확보
- Kafka, Istio, JWT, Azure를 포함한 실전 기술 스택 적용
- OpenAI API와의 연동 및 사용자 중심 서비스 기획/설계 경험


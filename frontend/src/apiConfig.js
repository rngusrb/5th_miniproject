// 1. Vite가 모드에 맞게 주입한 기본 URL을 가져옴
let baseURL = import.meta.env.VITE_API_BASE_URL;

// 2. 현재 페이지의 호스트 이름을 확인하여 Codespaces 환경인지 감지
const hostname = window.location.hostname;

if (hostname.endsWith('.github.dev')) {
  // 3. Codespaces 환경일 경우, URL을 동적으로 생성
  // 예: 프론트엔드 포트가 '5173'이고 백엔드가 '8088'이라면,
  // '5173' 부분을 '8088'로 교체하여 백엔드 주소를 만듬
  baseURL = `https://${hostname.replace('5173', '8088')}`;
}

// 4. 최종적으로 결정된 baseURL을 export
export default baseURL;
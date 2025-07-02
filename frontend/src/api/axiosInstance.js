import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://localhost:8088", // 백엔드 API 주소
});

// 요청 인터셉터로 토큰 추가
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default axiosInstance;
import axios from "axios";
// 하드코딩된 주소 대신, 동적 설정 로직을 import 합니다.
import baseURL from "../apiConfig";

// const axiosInstance = axios.create({
//   // 동적으로 결정된 baseURL을 사용
//   baseURL: baseURL,
// });
const axiosInstance = axios.create(); // baseURL 없이 생성

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
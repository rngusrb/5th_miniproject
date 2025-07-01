import axios from "axios";

const BASE_URL = "http://localhost:8088"; // ← Swagger 기준으로 확인된 포트 번호

export const generateAISummary = async (manuscriptId) => {
  return axios.post(`http://localhost:8088/manuscripts/summary/${manuscriptId}`);
};

export const generateAICategory = async (id) => {
  const res = await axios.post(`${BASE_URL}/manuscripts/category/${id}`);
  return res.data;
};

export const generateAICover = async (id) => {
  const res = await axios.post(`${BASE_URL}/manuscripts/coverimage/${id}`);
  return res.data;
};

export const postManuscript = async (data) => {
  return await axios.post(`${BASE_URL}/manuscripts`, data);
};

export const getTempManuscript = async (authorId) => {
  return await axios.get(`${BASE_URL}/manuscripts/temp/${authorId}`);
};

export const getRegisteredManuscripts = async () => {
  const res = await axios.get(`${BASE_URL}/manuscripts`);
  // "REGISTERED" 상태만 필터링
  return res.data.filter((m) => m.status === "REGISTERED");
};

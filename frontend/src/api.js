import axios from 'axios';

export const generateCoverImage = async (draftId) => {
  const res = await axios.post(`/api/ai/cover`, { id: draftId });
  return res.data.imageUrl; // 백엔드가 반환하는 커버 이미지 주소
};

export const generateSummary = async (draftId) => {
  const res = await axios.post(`/api/ai/summary`, { id: draftId });
  return res.data; // { pdfUrl, categories }
};

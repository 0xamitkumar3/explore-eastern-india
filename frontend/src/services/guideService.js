
import api from "./api";

export const getAllGuides = async () => {
  const response = await api.get("/guides");
  return response.data;
};

export const getGuideById = async (id) => {
  const response = await api.get(`/guides/${id}`);
  return response.data;
};

export const getGuidesByDistrict = async (district) => {
  const response = await api.get(
    `/guides/district/${district}`
  );
  return response.data;
};

export const getGuidesByLanguage = async (language) => {
  const response = await api.get(
    `/guides/language/${language}`
  );
  return response.data;
};

export const getVerifiedGuides = async () => {
  const response = await api.get("/guides/verified");
  return response.data;
};

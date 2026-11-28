
import api from "./api";

export const getAllServices = async () => {
  const response = await api.get("/tourism-services");
  return response.data;
};

export const getServiceById = async (id) => {
  const response = await api.get(`/tourism-services/${id}`);
  return response.data;
};

export const getServicesByCategory = async (category) => {
  const response = await api.get(
    `/tourism-services/category/${category}`
  );
  return response.data;
};

export const getServicesByDistrict = async (district) => {
  const response = await api.get(
    `/tourism-services/district/${district}`
  );
  return response.data;
};

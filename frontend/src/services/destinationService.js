import api from "./api";

// Get all destinations
export const getAllDestinations = async () => {
  const response = await api.get("/destinations");
  return response.data;
};

// Get destination by ID
export const getDestinationById = async (id) => {
  const response = await api.get(`/destinations/${id}`);
  return response.data;
};

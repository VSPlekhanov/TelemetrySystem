import axios, { type AxiosError } from "axios";

const BASE_URL = `${window.location.origin}/api/admin`;

const withoutToken = axios.create({
  baseURL: BASE_URL,
});

const withToken = axios.create({
  baseURL: BASE_URL,
});

withToken.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken");
    config.headers = { ...config.headers, Authorization: `Bearer ${token}` };

    return config;
  },
  (err) => Promise.reject(err)
);

withToken.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem("accessToken");
    }

    return Promise.reject(error);
  }
);

export { withToken, withoutToken };

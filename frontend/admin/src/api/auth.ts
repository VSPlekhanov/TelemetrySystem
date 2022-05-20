import { withoutToken, withToken } from "@/axiosConfig";

async function login(username: string, password: string) {
  const data = await withoutToken
    .post("/auth/login", {
      username,
      password,
    })
    .then((r) => r.data);

  localStorage.setItem("accessToken", data.token);
}

async function logout() {
  await withToken.post("/auth/logout");
  localStorage.removeItem("accessToken");
}

export const authApi = {
  login,
  logout,
};

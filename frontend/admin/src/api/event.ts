import { withToken } from "@/axiosConfig";

async function list(): Promise<any[]> {
  return await withToken.get("/event/list").then((r) => r.data);
}

export const eventApi = {
  list,
};

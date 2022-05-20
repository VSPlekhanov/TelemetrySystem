import { withToken } from "@/axiosConfig";
import type { Event } from "@/types";
import type { Event as BackendEvent } from "./types";

async function list(): Promise<Event[]> {
  const rawData = await withToken
    .get<BackendEvent[]>("/event/list")
    .then((r) => r.data);

  return rawData.map((e) => ({ ...e, createdAt: new Date(e.createdAt) }));
}

export const eventApi = {
  list,
};

import type { Event as CommonEvent } from "@/types";

export interface Event extends Omit<CommonEvent, "createdAt"> {
  createdAt: string;
}

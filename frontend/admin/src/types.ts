export interface User {
  name: string;
  id: number;
}

export interface Event {
  type: "STARTUP" | "SHUTDOWN";
  createdAt: Date;
  user: User;
  additional: string;
}

export interface User {
  username: string;
  email: string;
  id: string;
  authorities: { name: string; id: string }[];
  create_dt: string;
}

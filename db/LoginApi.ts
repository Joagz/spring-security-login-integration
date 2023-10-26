import axios from "axios";

export const LoginApi = axios.create({
  baseURL: "http://192.168.100.50:8080",
});

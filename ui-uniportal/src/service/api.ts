import axios from "axios";

const api = axios.create({
    baseURL: "http://3.27.194.230:8080/api",
});

export default api;

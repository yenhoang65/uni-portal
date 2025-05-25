import axios from "axios";

const api = axios.create({
    baseURL: "http://3.107.199.183:8080/api",
});

export default api;

import axios from "axios";

const api = axios.create({
    baseURL: "http://13.238.128.239:8080/api/",
});

export default api;

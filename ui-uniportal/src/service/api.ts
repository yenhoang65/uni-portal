import axios from "axios";

const api = axios.create({
    baseURL: "http://54.79.236.44:8080/api",
});

export default api;

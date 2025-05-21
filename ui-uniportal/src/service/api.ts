import axios from "axios";

const api = axios.create({
    baseURL: "http://16.176.18.251:8080/api",
});

export default api;

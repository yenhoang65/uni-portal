import axios from "axios";

const api = axios.create({
    baseURL: "http://uniportal.site:8080/api",
});

export default api;

import axios from "axios";

const api = axios.create({
    baseURL: "http://13.54.162.105:8080/api/",
    // baseURL: "https://uniportal.site/api/api/",
});

export default api;

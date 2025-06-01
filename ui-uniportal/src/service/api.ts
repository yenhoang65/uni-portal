import axios from "axios";

const api = axios.create({
    baseURL: "http://3.107.23.172:8080/api/",
    // baseURL: "https://uniportal.site/api/api/",
});

export default api;

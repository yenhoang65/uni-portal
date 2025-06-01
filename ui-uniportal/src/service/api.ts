import axios from "axios";

const api = axios.create({
    baseURL: "http://3.27.172.109:8080/api/",
    // baseURL: "https://uniportal.site/api/api/",
});

export default api;

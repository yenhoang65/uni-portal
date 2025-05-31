import axios from "axios";

const api = axios.create({
    baseURL: "http://3.107.204.3:8080/api/",
});

export default api;

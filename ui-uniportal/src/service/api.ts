import axios from "axios";

const api = axios.create({
    baseURL: "http://13.239.227.158:8080/api",
});

export default api;

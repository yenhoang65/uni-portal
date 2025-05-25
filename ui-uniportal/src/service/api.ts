import axios from "axios";

const api = axios.create({
    baseURL: "http://3.25.54.30:8080/api",
});

export default api;

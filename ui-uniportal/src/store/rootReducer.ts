import authReducer from "./reducer/authReducer";
import facultyReducer from "./reducer/facultyReducer";

const rootReducer = {
    auth: authReducer,
    faculty: facultyReducer,
};

export default rootReducer;

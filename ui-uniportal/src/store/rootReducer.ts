import authReducer from "./reducer/authReducer";
import facultyReducer from "./reducer/facultyReducer";
import majorReducer from "./reducer/majorReducer";
import specializationReducer from "./reducer/specializationReducer";

const rootReducer = {
    auth: authReducer,
    faculty: facultyReducer,
    major: majorReducer,
    specialization: specializationReducer,
};

export default rootReducer;

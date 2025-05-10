import authReducer from "./reducer/authReducer";
import classroomReducer from "./reducer/classroomReducer";
import facultyReducer from "./reducer/facultyReducer";
import majorReducer from "./reducer/majorReducer";
import specializationReducer from "./reducer/specializationReducer";
import subjectReducer from "./reducer/subjectReducer";

const rootReducer = {
    auth: authReducer,
    faculty: facultyReducer,
    major: majorReducer,
    specialization: specializationReducer,
    subject: subjectReducer,
    classroom: classroomReducer,
};

export default rootReducer;

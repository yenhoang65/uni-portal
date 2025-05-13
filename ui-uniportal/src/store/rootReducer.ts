import authReducer from "./reducer/authReducer";
import classroomReducer from "./reducer/classroomReducer";
import facultyReducer from "./reducer/facultyReducer";
import lecturerReducer from "./reducer/lecturerReducer";
import majorReducer from "./reducer/majorReducer";
import specializationReducer from "./reducer/specializationReducer";
import subjectReducer from "./reducer/subjectReducer";
import activateTimeReducer from "./reducer/activateTimeReducer";
import trainingProgramReducer from "./reducer/trainingProgramReducer";
import classReducer from "./reducer/classReducer";
import studentReducer from "./reducer/studentReducer";

const rootReducer = {
    auth: authReducer,
    faculty: facultyReducer,
    major: majorReducer,
    specialization: specializationReducer,
    subject: subjectReducer,
    classroom: classroomReducer,
    lecturer: lecturerReducer,
    student: studentReducer,
    activateTime: activateTimeReducer,
    trainingProgram: trainingProgramReducer,
    class: classReducer,
};

export default rootReducer;

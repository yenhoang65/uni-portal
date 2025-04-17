import ProtectedRoute from "@/hooks/ProtectedRoute";
import MainLayout from "@/layout/main";
import CreateEditStudent from "./student_management/create-edit";
import SubjectManagement from "./subject";
import SubjectDetail from "./subject/view";
import CreateEditSubject from "./subject/create-edit";
import Classroom from "./classroom";
import ClassroomDetail from "./classroom/view";
import CreateEditClassroom from "./classroom/create-edit";
import RegistrationTimeActivation from "./enable-credit-registration";

export default function Home() {
    return (
        <MainLayout>
            <RegistrationTimeActivation />
        </MainLayout>
    );
}

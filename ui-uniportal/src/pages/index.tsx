import ProtectedRoute from "@/hooks/ProtectedRoute";
import MainLayout from "@/layout/main";
import Major from "./major";
import MajorDetail from "./major/view";
import CreateEditMajor from "./major/create-edit";
import Specialization from "./specialization";
import SpecializationDetail from "./specialization/view";
import CreateEditSpecialization from "./specialization/create-edit";
import LecturerManagement from "./lecturer_management";
import LecturerDetail from "./lecturer_management/view";
import CreateEditLecturer from "./lecturer_management/create-edit";

export default function Home() {
    return (
        <MainLayout>
            <LecturerManagement />
        </MainLayout>
    );
}

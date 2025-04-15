import ProtectedRoute from "@/hooks/ProtectedRoute";
import MainLayout from "@/layout/main";
import Major from "./major";
import MajorDetail from "./major/view";
import CreateEditMajor from "./major/create-edit";
import Specialization from "./specialization";
import SpecializationDetail from "./specialization/view";
import CreateEditSpecialization from "./specialization/create-edit";

export default function Home() {
    return (
        <MainLayout>
            <CreateEditSpecialization />
        </MainLayout>
    );
}

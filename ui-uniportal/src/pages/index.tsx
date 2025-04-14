import ProtectedRoute from "@/hooks/ProtectedRoute";
import MainLayout from "@/layout/main";
import FacultyDetailPage from "./faculty/view";

export default function Home() {
    return (
        <MainLayout>
            <FacultyDetailPage />
        </MainLayout>
    );
}

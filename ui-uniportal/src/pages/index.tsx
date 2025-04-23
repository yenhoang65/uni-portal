import ProtectedRoute from "@/hooks/ProtectedRoute";
import ActivateTeachingScheduleRegistration from "./activate-teaching-schedule-registration";
import TrainingProgram from "./training-program";
import Faculty from "./faculty";
import ViewTrainingProgram from "./training-program/view";
import StudentManagement from "./student_management";
import MainLayout from "@/components/layout/main";
import { privateRoutes } from "@/router/routes/privateRoutes";
import ProtectRoute from "@/router/routes/ProtectRoute";
import { useEffect, useState } from "react";
import publicRoutes from "@/router/routes/publicRoutes";
import { getRoutes } from "@/router/routes";
import RegistrationTimeActivation from "./enable-credit-registration";

export default function Home() {
    return (
        <MainLayout>
            <RegistrationTimeActivation />
        </MainLayout>
    );
}

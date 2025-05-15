import AuthGuard from "@/components/AuthGuard";
import React from "react";

const DashboardStudent = () => {
    return (
        <AuthGuard allowedRoles={["student"]}>
            <div>admin</div>
        </AuthGuard>
    );
};

export default DashboardStudent;

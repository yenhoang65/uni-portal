import Classroom from "@/pages/classroom";
import Profile from "@/pages/profile/view";

export const employeeRoutes = [
    //classroom
    {
        path: "/classroom",
        role: "employee",
        element: <Classroom />,
    },
    // {
    //     path: "/classroom/view",
    //     role: "employee",
    //     component: () => import("@/pages/classroom/view"),
    // },
    // {
    //     path: "/classroom/create-edit",
    //     role: "employee",
    //     component: () => import("@/pages/classroom/create-edit"),
    // },
    // //profile
    // {
    //     path: "/profile/view",
    //     role: "employee",
    //     component: () => import("@/pages/profile/view"),
    // },
    // {
    //     path: "/profile/edit",
    //     role: "employee",
    //     component: () => import("@/pages/profile/edit"),
    // },
];

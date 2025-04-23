import Profile from "@/pages/profile/view";

export const studentRoutes = [
    //profile
    {
        path: "/profile/view",
        role: "student",
        element: <Profile />,
    },
    // {
    //     path: "/profile/edit",
    //     role: "student",
    //     component: () => import("@/pages/profile/edit"),
    // },
];

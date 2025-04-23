import Profile from "@/pages/profile/view";

export const lecturerRoutes = [
    //profile
    {
        path: "/profile/view",
        role: "lecturer",
        element: <Profile />,
    },
    // {
    //     path: "/profile/edit",
    //     role: "lecturer",
    //     component: () => import("@/pages/profile/edit"),
    // },
];

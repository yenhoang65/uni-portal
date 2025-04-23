import ActivateTeachingScheduleRegistration from "@/pages/activate-teaching-schedule-registration";
import Classroom from "@/pages/classroom";
import CreateEditClassroom from "@/pages/classroom/create-edit";
import ClassroomDetail from "@/pages/classroom/view";
import RegistrationTimeActivation from "@/pages/enable-credit-registration";
import Faculty from "@/pages/faculty";

export const adminRoutes = [
    {
        path: "/activate-teaching-schedule-registration",
        role: "admin",
        element: <ActivateTeachingScheduleRegistration />,
    },
    // classroom
    // {
    //     path: "/classroom",
    //     role: "admin",
    //     element: <Classroom />,
    // },
    // {
    //     path: "/classroom/view",
    //     role: "admin",
    //     element: <ClassroomDetail />,
    // },
    // {
    //     path: "/classroom/create-edit",
    //     role: "admin",
    //     element: <ClassroomDetail />,
    // },
    // {
    //     path: "/enable-credit-registration",
    //     role: "admin",
    //     component: () => import("@/pages/enable-credit-registration"),
    // },
    // //faculty
    // {
    //     path: "/faculty",
    //     role: "admin",
    //     component: () => import("@/pages/faculty"),
    // },
    // {
    //     path: "/faculty/view",
    //     role: "admin",
    //     component: () => import("@/pages/faculty/view"),
    // },
    // {
    //     path: "/faculty/create-edit",
    //     role: "admin",
    //     component: () => import("@/pages/faculty/create-edit"),
    // },
    // //lecturer
    // {
    //     path: "/lecturer_management",
    //     role: "admin",
    //     component: () => import("@/pages/lecturer_management"),
    // },
    // {
    //     path: "/lecturer_management/view",
    //     role: "admin",
    //     component: () => import("@/pages/lecturer_management/view"),
    // },
    // {
    //     path: "/lecturer_management/create-edit",
    //     role: "admin",
    //     component: () => import("@/pages/lecturer_management/create-edit"),
    // },
    // //major
    // {
    //     path: "/major",
    //     role: "admin",
    //     component: () => import("@/pages/major"),
    // },
    // {
    //     path: "/major/view",
    //     role: "admin",
    //     component: () => import("@/pages/major/view"),
    // },
    // {
    //     path: "/major/create-edit",
    //     role: "admin",
    //     component: () => import("@/pages/major/create-edit"),
    // },
    // //profile
    // {
    //     path: "/profile/view",
    //     role: "admin",
    //     component: () => import("@/pages/profile/view"),
    // },
    // {
    //     path: "/profile/edit",
    //     role: "admin",
    //     component: () => import("@/pages/profile/edit"),
    // },
    // //specialization
    // {
    //     path: "/specialization",
    //     role: "admin",
    //     component: () => import("@/pages/specialization"),
    // },
    // {
    //     path: "/specialization/view",
    //     role: "admin",
    //     component: () => import("@/pages/specialization/view"),
    // },
    // {
    //     path: "/specialization/create-edit",
    //     role: "admin",
    //     component: () => import("@/pages/specialization/create-edit"),
    // },
    // //student_management
    // {
    //     path: "/student_management",
    //     role: "admin",
    //     component: () => import("@/pages/student_management"),
    // },
    // {
    //     path: "/student_management/view",
    //     role: "admin",
    //     component: () => import("@/pages/student_management/view"),
    // },
    // {
    //     path: "/student_management/create-edit",
    //     role: "admin",
    //     component: () => import("@/pages/student_management/create-edit"),
    // },
    // //subject
    // {
    //     path: "/subject",
    //     role: "admin",
    //     component: () => import("@/pages/subject"),
    // },
    // {
    //     path: "/subject/view",
    //     role: "admin",
    //     component: () => import("@/pages/subject/view"),
    // },
    // {
    //     path: "/subject/create-edit",
    //     role: "admin",
    //     component: () => import("@/pages/subject/create-edit"),
    // },
    // //training-program
    // {
    //     path: "/training-program",
    //     role: "admin",
    //     component: () => import("@/pages/training-program"),
    // },
    // {
    //     path: "/training-program/view",
    //     role: "admin",
    //     component: () => import("@/pages/training-program/view"),
    // },
    // {
    //     path: "/training-program/create-edit",
    //     role: "admin",
    //     component: () => import("@/pages/training-program/creadit-edit"),
    // },
];

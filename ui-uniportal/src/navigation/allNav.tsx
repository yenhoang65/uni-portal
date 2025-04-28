import {
    AiOutlineTeam,
    AiOutlineBook,
    AiOutlineFile,
    AiOutlineCheckCircle,
    AiOutlineUser,
    AiOutlineHome,
    AiOutlineCalendar,
} from "react-icons/ai";
import { FaUserGraduate, FaUserTie } from "react-icons/fa";

export const allNav = [
    {
        group: "QUẢN LÝ TRƯỜNG HỌC",
        items: [
            {
                id: 1,
                title: "Quản lý khoa",
                icon: <AiOutlineHome />,
                role: ["admin"],
                path: "/faculty",
            },
            {
                id: 2,
                title: "Quản lý ngành học",
                icon: <AiOutlineBook />,
                role: ["admin"],
                path: "/major",
            },
            {
                id: 3,
                title: "Quản lý chuyên ngành học",
                icon: <AiOutlineTeam />,
                role: ["admin"],
                path: "/specialization",
            },
        ],
    },
    {
        group: "QUẢN LÝ ĐÀO TẠO",
        items: [
            {
                id: 4,
                title: "Quản lý học phần",
                icon: <AiOutlineBook />,
                role: ["admin"],
                path: "/subject",
            },
            {
                id: 5,
                title: "Quản lý chương trình đào tạo",
                icon: <AiOutlineFile />,
                role: ["admin"],
                path: "/training-program",
            },
            {
                id: 15,
                title: "Chương trình khung",
                icon: <AiOutlineFile />,
                role: ["student"],
                path: "/training-program/view",
            },
        ],
    },
    {
        group: "QUẢN LÝ LỊCH VÀ ĐĂNG KÝ",
        items: [
            {
                id: 6,
                title: "Kích hoạt đăng ký lịch dạy",
                icon: <AiOutlineCheckCircle />,
                role: ["admin"],
                path: "/activate-teaching-schedule-registration",
            },
            {
                id: 7,
                title: "Kích hoạt đăng ký tín chỉ",
                icon: <AiOutlineCheckCircle />,
                role: ["admin"],
                path: "/enable-credit-registration",
            },
            {
                id: 13,
                title: "Phân công lịch giảng dạy",
                icon: <AiOutlineCalendar />,
                role: ["admin"],
                path: "/teaching-assignment",
            },
            {
                id: 14,
                title: "Đăng ký lịch giảng dạy",
                icon: <AiOutlineCalendar />,
                role: ["admin"],
                path: "/teaching-schedule-request",
            },
            {
                id: 16,
                title: "Đăng ký tín chỉ",
                icon: <AiOutlineCalendar />,
                role: ["student"],
                path: "/credit-registration",
            },
            {
                id: 17,
                title: "Thời khóa biểu sinh viên",
                icon: <AiOutlineCalendar />,
                role: ["student", "admin"],
                path: "/student-timetable",
            },
        ],
    },
    {
        group: "QUẢN LÝ NHÂN SỰ",
        items: [
            {
                id: 8,
                title: "Quản lý giảng viên",
                icon: <FaUserTie />,
                role: ["admin"],
                path: "/lecturer_management",
            },
            {
                id: 9,
                title: "Quản lý sinh viên",
                icon: <FaUserGraduate />,
                role: ["admin"],
                path: "/student_management",
            },
        ],
    },
    {
        group: "QUẢN LÝ LỚP HỌC",
        items: [
            {
                id: 10,
                title: "Quản lý phòng học",
                icon: <AiOutlineHome />,
                role: ["admin", "employee"],
                path: "/classroom",
            },
            {
                id: 12,
                title: "Quản lý lớp học phần",
                icon: <AiOutlineBook />,
                role: ["admin", "employee"],
                path: "/class-term-subject",
            },
        ],
    },
    {
        group: "QUẢN LÝ THÔNG TIN CÁ NHÂN",
        items: [
            {
                id: 11,
                title: "Quản lý thông tin cá nhân",
                icon: <AiOutlineUser />,
                role: ["admin", "employee", "student", "lecturer"],
                path: "/profile/view",
            },
        ],
    },
];

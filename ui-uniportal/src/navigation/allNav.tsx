import {
    AiOutlineDashboard,
    AiOutlineTeam,
    AiOutlineBarChart,
    AiOutlineWallet,
} from "react-icons/ai";
import {
    FaTasks,
    FaChartLine,
    FaArchive,
    FaShoppingCart,
} from "react-icons/fa";

export const allNav = [
    {
        group: "QUẢN LÝ TRƯỜNG HỌC",
        items: [
            {
                id: 1,
                title: "Quản lý khoa",
                icon: <AiOutlineDashboard />,
                role: ["admin"],
                path: "/faculty",
            },
            {
                id: 2,
                title: "Quản lý ngành học",
                icon: <FaTasks />,
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
                icon: <AiOutlineBarChart />,
                role: ["admin"],
                path: "/subject",
            },
            {
                id: 5,
                title: "Quản lý chương trình đào tạo",
                icon: <FaArchive />,
                role: ["admin"],
                path: "/training-program",
            },
        ],
    },
    {
        group: "QUẢN LÝ LỊCH VÀ ĐĂNG KÝ",
        items: [
            {
                id: 6,
                title: "Kích hoạt đăng ký lịch dạy",
                icon: <FaChartLine />,
                role: ["admin"],
                path: "/activate-teaching-schedule-registration",
            },
            {
                id: 7,
                title: "Kích hoạt đăng ký tín chỉ",
                icon: <FaShoppingCart />,
                role: ["admin"],
                path: "/enable-credit-registration",
            },
            {
                id: 7,
                title: "Phân công lịch giảng dạy",
                icon: <FaShoppingCart />,
                role: ["admin"],
                path: "/teaching-assignment",
            },
        ],
    },
    {
        group: "QUẢN LÝ NHÂN SỰ",
        items: [
            {
                id: 8,
                title: "Quản lý giảng viên",
                icon: <AiOutlineTeam />,
                role: ["admin"],
                path: "/lecturer_management",
            },
            {
                id: 9,
                title: "Quản lý sinh viên",
                icon: <AiOutlineTeam />,
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
                icon: <AiOutlineDashboard />,
                role: ["admin", "employee"],
                path: "/classroom",
            },
            {
                id: 12,
                title: "Quản lý lớp học phần",
                icon: <AiOutlineDashboard />,
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
                icon: <AiOutlineWallet />,
                role: ["admin", "employee", "student", "lecturer"],
                path: "/profile/view",
            },
        ],
    },
];

import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useState } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import clsx from "clsx";
import AuthGuard from "@/components/AuthGuard";
import { FcViewDetails } from "react-icons/fc";
import Link from "next/link";

type ClassSubjectType = {
    class_name: string;
    subject_name: string;
    lecturer_name: string;
    lesson_time: string;
    start_date: string;
    week_time: string;
    classroom_name: string;
    tc: number;
};

const classSubjects: ClassSubjectType[] = [
    {
        class_name: "MKT1002",
        subject_name: "SEO 2",
        lecturer_name: "Nguyễn Văn An",
        lesson_time: "Tiết 1-3",
        start_date: "28/4/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng B101",
        tc: 2,
    },
    {
        class_name: "CNTT103",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Đào Anh Hiển",
        lesson_time: "Tiết 4-6",
        start_date: "30/4/2025",
        week_time: "Tuần 5-15",
        classroom_name: "Phòng C202",
        tc: 3,
    },
    {
        class_name: "MKT1005",
        subject_name: "SEO",
        lecturer_name: "Nguyễn Văn An",
        lesson_time: "Tiết 1-5",
        start_date: "3/5/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng B101",
        tc: 1,
    },
    {
        class_name: "CNTT109",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Đào Anh Hiển",
        lesson_time: "Tiết 4-6",
        start_date: "28/4/2025",
        week_time: "Tuần 5-15",
        classroom_name: "Phòng C202",
        tc: 2,
    },
    {
        class_name: "ENG101",
        subject_name: "English",
        lecturer_name: "Trần Thị Bình",
        lesson_time: "Tiết 7-9",
        start_date: "29/4/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng A303",
        tc: 3,
    },
    {
        class_name: "MATH202",
        subject_name: "Mathematics",
        lecturer_name: "Lê Văn Cường",
        lesson_time: "Tiết 1-3",
        start_date: "30/4/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng D404",
        tc: 5,
    },
    {
        class_name: "MATH202",
        subject_name: "Mathematics",
        lecturer_name: "Lê Văn Cường",
        lesson_time: "Tiết 9-11",
        start_date: "1/5/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng D404",
        tc: 4,
    },
];

const ClassRegistration = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);

    const filteredClasses = classSubjects.filter((cls) =>
        Object.values(cls).some((value) =>
            String(value).toLowerCase().includes(searchValue.toLowerCase())
        )
    );

    return (
        <AuthGuard allowedRoles={["student"]}>
            <BorderBox title="Đăng ký tín chỉ - Các lớp đang mở">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th>STT</th>
                                    <th>Môn học</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {classSubjects.map((cls, index) => (
                                    <tr key={index}>
                                        <td className={styles.tableCell}>
                                            {index + 1}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {cls.subject_name}
                                        </td>

                                        <td
                                            className={clsx(
                                                styles.tableCell,
                                                styles.action
                                            )}
                                        >
                                            <Link
                                                href={`/credit-registration/${cls.class_name}`}
                                            >
                                                <FcViewDetails />
                                            </Link>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    <div className={styles.paginationWrapper}>
                        <Pagination
                            pageNumber={currentPage}
                            setPageNumber={setCurrentPage}
                            totalItem={classSubjects.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ClassRegistration;

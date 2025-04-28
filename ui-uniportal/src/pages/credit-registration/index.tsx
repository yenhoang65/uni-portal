"use client";

import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useState } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import clsx from "clsx";
import AuthGuard from "@/components/AuthGuard";
import { Button } from "@/components/Button";

type ClassSubjectType = {
    class_name: string;
    subject_name: string;
    lecturer_name: string;
    lesson_time: string;
    week_time: string;
    classroom_name: string;
};

const classSubjects: ClassSubjectType[] = [
    {
        class_name: "MKT1002",
        subject_name: "SEO",
        lecturer_name: "Nguyễn Văn An",
        lesson_time: "Tiết 1-3",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng B101",
    },
    {
        class_name: "CNTT103",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Đào Anh Hiển",
        lesson_time: "Tiết 4-6",
        week_time: "Tuần 5-15",
        classroom_name: "Phòng C202",
    },
    {
        class_name: "MKT1005",
        subject_name: "SEO",
        lecturer_name: "Nguyễn Văn An",
        lesson_time: "Tiết 1-3",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng B101",
    },
    {
        class_name: "CNTT109",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Đào Anh Hiển",
        lesson_time: "Tiết 4-6",
        week_time: "Tuần 5-15",
        classroom_name: "Phòng C202",
    },
];

const ClassRegistration = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [selectedClasses, setSelectedClasses] = useState<string[]>([]);

    const filteredClasses = classSubjects.filter((cls) =>
        Object.values(cls).some((value) =>
            String(value).toLowerCase().includes(searchValue.toLowerCase())
        )
    );

    const startIndex = (currentPage - 1) * parPage;
    const endIndex = startIndex + parPage;
    const currentClasses = filteredClasses.slice(startIndex, endIndex);

    const handleCheckboxChange = (className: string) => {
        setSelectedClasses((prevSelected) =>
            prevSelected.includes(className)
                ? prevSelected.filter((name) => name !== className)
                : [...prevSelected, className]
        );
    };

    const handleRegisterSelected = () => {
        if (selectedClasses.length === 0) {
            alert("Bạn chưa chọn lớp nào để đăng ký.");
            return;
        }
        alert(`Bạn đã đăng ký các lớp: ${selectedClasses.join(", ")}`);
        setSelectedClasses([]);
    };

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
                                    <th></th>
                                    <th>Tên lớp</th>
                                    <th>Môn học</th>
                                    <th>Giảng viên</th>
                                    <th>Tiết học</th>
                                    <th>Tuần học</th>
                                    <th>Phòng học</th>
                                </tr>
                            </thead>
                            <tbody>
                                {currentClasses.map((cls, index) => (
                                    <tr key={index}>
                                        <td className={styles.tableCell}>
                                            <input
                                                type="checkbox"
                                                checked={selectedClasses.includes(
                                                    cls.class_name
                                                )}
                                                onChange={() =>
                                                    handleCheckboxChange(
                                                        cls.class_name
                                                    )
                                                }
                                            />
                                        </td>
                                        <td className={styles.tableCell}>
                                            {cls.class_name}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {cls.subject_name}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {cls.lecturer_name}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {cls.lesson_time}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {cls.week_time}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {cls.classroom_name}
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
                            totalItem={filteredClasses.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>

                    <div className={styles.registerWrapper}>
                        <Button
                            className={clsx(styles.buttonAdd)}
                            onClick={handleRegisterSelected}
                        >
                            Đăng ký các lớp đã chọn
                        </Button>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ClassRegistration;

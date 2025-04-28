"use client";

import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useState } from "react";
import clsx from "clsx";
import AuthGuard from "@/components/AuthGuard";
import { Button } from "@/components/Button";
import SelectWithLabel from "@/components/SelectWithLabel";

type ScheduleClassType = {
    weekday: number; // Thứ trong tuần: 2-7
    lesson: string; // "1-4", "5-6"
    class_name: string; // Mã lớp học phần
    subject_name: string; // Tên môn học
    class_section_name: string; // Tên lớp học phần chi tiết
    classroom_name: string; // Phòng học
    lecturer_name: string; // Giảng viên
};

const scheduleClasses: ScheduleClassType[] = [
    {
        weekday: 2,
        lesson: "1-4",
        class_name: "215687",
        subject_name: "Tiếng anh cho CNTT 2",
        class_section_name: "215687_125213_125217 1",
        classroom_name: "ĐH413",
        lecturer_name: "VŨ KHÁNH QUÝ 0945528686",
    },
    {
        weekday: 2,
        lesson: "7-10",
        class_name: "211897",
        subject_name: "Trí tuệ nhân tạo",
        class_section_name: "125217_125213",
        classroom_name: "ĐH415",
        lecturer_name: "NGUYỄN HOÀNG ĐIỆP 0987862348",
    },
    {
        weekday: 3,
        lesson: "7-10",
        class_name: "231129",
        subject_name: "Tiếng anh cho CNTT 3",
        class_section_name: "231129_125213_125217 1",
        classroom_name: "ĐH412",
        lecturer_name: "VŨ KHÁNH QUÝ 0945528686",
    },
];

const weekDays = ["Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"];

const ScheduleComponent = () => {
    const [year] = useState("2024-2025");
    const [week] = useState("Tuần 6 [23/09/2024 - 29/09/2024]");

    return (
        <AuthGuard allowedRoles={["student", "admin"]}>
            <BorderBox title="Thời khóa biểu">
                <div className={styles.box}>
                    <div className={styles.filterBar}>
                        <SelectWithLabel
                            name="year"
                            value={year}
                            onChange={(e) => {}}
                            options={[
                                { value: "2024-2025", label: "2024-2025" },
                                { value: "2025-2026", label: "2025-2026" },
                            ]}
                        />
                        <SelectWithLabel
                            name="year"
                            value={year}
                            onChange={(e) => {}}
                            options={[
                                {
                                    value: "2024-2025",
                                    label: "Tuần 6 [Từ 23/09/2024 đến 29/09/2024]",
                                },
                            ]}
                        />
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead>
                                <tr>
                                    <th>Thứ</th>
                                    <th>Tiết</th>
                                    <th>Ký hiệu</th>
                                    <th>Tên môn</th>
                                    <th>Tên lớp học phần</th>
                                    <th>Phòng học</th>
                                    <th>Giảng viên</th>
                                </tr>
                            </thead>
                            <tbody>
                                {scheduleClasses.map((cls, index) => (
                                    <tr key={index}>
                                        <td>{cls.weekday}</td>
                                        <td>{cls.lesson}</td>
                                        <td>{cls.class_name}</td>
                                        <td>{cls.subject_name}</td>
                                        <td>{cls.class_section_name}</td>
                                        <td>{cls.classroom_name}</td>
                                        <td>{cls.lecturer_name}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ScheduleComponent;

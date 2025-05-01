import React, { useState } from "react";
import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { Button } from "@/components/Button";
import AuthGuard from "@/components/AuthGuard";
import clsx from "clsx";

interface Student {
    id: string;
    name: string;
    studentCode: string;
}

interface AttendanceRecord {
    studentId: string;
    sessionId: string; // Sử dụng scheduled_date làm sessionId
    status: "present" | "absent" | "excused";
}

interface AttendanceSession {
    session_id: string;
    class_subject_id: string;
    scheduled_date: string;
}

const mockStudents: Student[] = [
    { id: "STU001", name: "Nguyễn Văn A", studentCode: "SV001" },
    { id: "STU002", name: "Trần Thị B", studentCode: "SV002" },
    { id: "STU003", name: "Lê Công C", studentCode: "SV003" },
    { id: "STU004", name: "Phạm Văn D", studentCode: "SV004" },
    { id: "STU005", name: "Hoàng Thị E", studentCode: "SV005" },
    { id: "STU006", name: "Lý Văn F", studentCode: "SV006" },
    { id: "STU007", name: "Đinh Thị G", studentCode: "SV007" },
    { id: "STU001", name: "Nguyễn Văn A", studentCode: "SV001" },
    { id: "STU002", name: "Trần Thị B", studentCode: "SV002" },
    { id: "STU003", name: "Lê Công C", studentCode: "SV003" },
    { id: "STU004", name: "Phạm Văn D", studentCode: "SV004" },
    { id: "STU005", name: "Hoàng Thị E", studentCode: "SV005" },
    { id: "STU006", name: "Lý Văn F", studentCode: "SV006" },
    { id: "STU007", name: "Đinh Thị G", studentCode: "SV007" },
];

const mockSessions: AttendanceSession[] = [
    {
        session_id: "1",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-01",
    },
    {
        session_id: "2",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-02",
    },
    {
        session_id: "3",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-03",
    },
    {
        session_id: "4",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-04",
    },
    {
        session_id: "5",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-05",
    },
    {
        session_id: "6",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-06",
    },
    {
        session_id: "7",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-07",
    },
    {
        session_id: "8",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-08",
    },
    {
        session_id: "9",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-09",
    },
    {
        session_id: "10",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-10",
    },
    {
        session_id: "11",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-11",
    },
    {
        session_id: "12",
        class_subject_id: "CS001",
        scheduled_date: "2025-05-12",
    },
];

const Attendance = () => {
    const classSubjectId = "CS001"; // Giả định classSubjectId
    const [students] = useState<Student[]>(mockStudents);
    const [attendanceRecords, setAttendanceRecords] = useState<
        AttendanceRecord[]
    >([]);

    // Lọc session dựa trên classSubjectId trực tiếp từ mockSessions
    const filteredSessions = mockSessions.filter(
        (session) => session.class_subject_id === classSubjectId
    );
    // Lấy danh sách các ngày trực tiếp từ filteredSessions
    const sessionDates = filteredSessions.map(
        (session) => session.scheduled_date
    );

    const handleAttendanceChange = (
        studentId: string,
        sessionId: string,
        status: "present" | "absent" | "excused"
    ) => {
        const updatedRecords = attendanceRecords.filter(
            (record) =>
                !(
                    record.studentId === studentId &&
                    record.sessionId === sessionId
                )
        );
        setAttendanceRecords([
            ...updatedRecords,
            { studentId, sessionId, status },
        ]);
    };

    const handleSubmitAttendance = () => {
        console.log("Dữ liệu điểm danh đã lưu (giả lập):", attendanceRecords);
        alert("Điểm danh đã được lưu!");
        // Gọi API thật của bạn để lưu dữ liệu điểm danh
    };

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox title={`Điểm danh - Lớp ${classSubjectId}`}>
                <div className={styles.attendanceContainer}>
                    <table className={styles.attendanceTable}>
                        <thead>
                            <tr>
                                <th className={styles.stickyColumn}>#</th>
                                <th
                                    className={clsx(
                                        styles.stickyColumn,
                                        styles.stickySecondColumn
                                    )}
                                >
                                    Mã SV
                                </th>
                                <th
                                    className={clsx(
                                        styles.stickyColumn,
                                        styles.stickyThirdColumn
                                    )}
                                >
                                    Tên Sinh Viên
                                </th>
                                {sessionDates.map((date) => (
                                    <th key={date}>
                                        {new Date(date).toLocaleDateString(
                                            "vi-VN"
                                        )}
                                    </th>
                                ))}
                            </tr>
                        </thead>
                        <tbody>
                            {students.map((student, index) => (
                                <tr key={student.id}>
                                    <td className={styles.stickyColumn}>
                                        {index + 1}
                                    </td>
                                    <td
                                        className={clsx(
                                            styles.stickyColumn,
                                            styles.stickySecondColumn
                                        )}
                                    >
                                        {student.studentCode}
                                    </td>
                                    <td
                                        className={clsx(
                                            styles.stickyColumn,
                                            styles.stickyThirdColumn
                                        )}
                                    >
                                        {student.name}
                                    </td>
                                    {sessionDates.map((date) => {
                                        const record = attendanceRecords.find(
                                            (r) =>
                                                r.studentId === student.id &&
                                                r.sessionId === date
                                        );
                                        const status = record
                                            ? record.status
                                            : "absent";

                                        return (
                                            <td
                                                key={`${student.id}-${date}`}
                                                className={
                                                    styles.attendanceCell
                                                }
                                            >
                                                <select
                                                    value={status}
                                                    onChange={(e) =>
                                                        handleAttendanceChange(
                                                            student.id,
                                                            date,
                                                            e.target.value as
                                                                | "present"
                                                                | "absent"
                                                                | "excused"
                                                        )
                                                    }
                                                    className={
                                                        styles.attendanceSelect
                                                    }
                                                >
                                                    <option value="present">
                                                        Có mặt
                                                    </option>
                                                    <option value="absent">
                                                        Vắng
                                                    </option>
                                                    <option value="excused">
                                                        Có phép
                                                    </option>
                                                </select>
                                            </td>
                                        );
                                    })}
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                <div className={styles.attendanceActions}>
                    <Button onClick={handleSubmitAttendance}>
                        Lưu Điểm Danh
                    </Button>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default Attendance;

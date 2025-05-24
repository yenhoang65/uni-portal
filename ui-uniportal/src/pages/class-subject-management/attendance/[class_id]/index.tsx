import React, { useState } from "react";
import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { Button } from "@/components/Button";
import AuthGuard from "@/components/AuthGuard";
import SelectWithLabel from "@/components/SelectWithLabel";
import clsx from "clsx";
import InputWithLabel from "@/components/InputWithLabel";
import { useTranslation } from "react-i18next";

interface Student {
    id: string;
    name: string;
    studentCode: string;
}

interface AttendanceRecord {
    studentId: string;
    sessionId: string;
    status: "present" | "absent" | "excused";
    note?: string;
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
];

const Attendance = () => {
    const { t } = useTranslation();
    const classSubjectId = "CS001";
    const [students] = useState<Student[]>(mockStudents);

    const sessions = mockSessions.filter(
        (session) => session.class_subject_id === classSubjectId
    );
    const [selectedSessionId, setSelectedSessionId] = useState<string>(
        sessions[0]?.session_id || ""
    );
    const [attendanceRecords, setAttendanceRecords] = useState<
        AttendanceRecord[]
    >(
        students.map((stu) => ({
            studentId: stu.id,
            sessionId: sessions[0]?.session_id || "",
            status: "absent",
            note: "",
        }))
    );

    const handleSessionChange = (sessionId: string) => {
        setSelectedSessionId(sessionId);
        setAttendanceRecords((prev) => {
            const filtered = prev.filter((r) => r.sessionId === sessionId);
            if (filtered.length === students.length) return filtered;
            const recordsMap = new Map(filtered.map((r) => [r.studentId, r]));
            return students.map(
                (stu) =>
                    recordsMap.get(stu.id) || {
                        studentId: stu.id,
                        sessionId,
                        status: "absent",
                        note: "",
                    }
            );
        });
    };

    const handleAttendanceChange = (
        studentId: string,
        status: "present" | "absent" | "excused"
    ) => {
        setAttendanceRecords((prev) =>
            prev.map((rec) =>
                rec.studentId === studentId ? { ...rec, status } : rec
            )
        );
    };

    const handleNoteChange = (studentId: string, note: string) => {
        setAttendanceRecords((prev) =>
            prev.map((rec) =>
                rec.studentId === studentId ? { ...rec, note } : rec
            )
        );
    };

    const handleSubmitAttendance = () => {
        console.log("Dữ liệu điểm danh đã lưu (giả lập):", attendanceRecords);
        alert("Điểm danh đã được lưu!");
    };

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox title={`Điểm danh - Lớp ${classSubjectId}`}>
                <div
                    className={styles.attendanceHeader}
                    style={{
                        display: "flex",
                        gap: 16,
                        alignItems: "center",
                        marginBottom: 24,
                    }}
                >
                    <SelectWithLabel
                        label="Ngày điểm danh"
                        name="session"
                        value={selectedSessionId}
                        onChange={(e) => handleSessionChange(e.target.value)}
                        options={sessions.map((session) => ({
                            value: session.session_id,
                            label: new Date(
                                session.scheduled_date
                            ).toLocaleDateString("vi-VN"),
                        }))}
                        className={styles.dateSelect}
                    />
                </div>
                <div className={styles.attendanceContainer}>
                    <table className={styles.attendanceTable}>
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Mã SV</th>
                                <th>Tên Sinh Viên</th>
                                <th>Trạng thái</th>
                                <th>Ghi chú</th>
                            </tr>
                        </thead>
                        <tbody>
                            {students.map((student, index) => {
                                const record = attendanceRecords.find(
                                    (r) =>
                                        r.studentId === student.id &&
                                        r.sessionId === selectedSessionId
                                );
                                return (
                                    <tr key={student.id}>
                                        <td>{index + 1}</td>
                                        <td>{student.studentCode}</td>
                                        <td>{student.name}</td>
                                        <td>
                                            <SelectWithLabel
                                                label={undefined}
                                                name={`status-${student.id}`}
                                                value={
                                                    record?.status || "absent"
                                                }
                                                onChange={(e) =>
                                                    handleAttendanceChange(
                                                        student.id,
                                                        e.target.value as
                                                            | "present"
                                                            | "absent"
                                                            | "excused"
                                                    )
                                                }
                                                options={[
                                                    {
                                                        value: "present",
                                                        label: "Có mặt",
                                                    },
                                                    {
                                                        value: "absent",
                                                        label: "Vắng",
                                                    },
                                                    {
                                                        value: "excused",
                                                        label: "Có phép",
                                                    },
                                                ]}
                                                className={clsx(
                                                    record?.status === "present"
                                                        ? styles[
                                                              "selectPresent"
                                                          ]
                                                        : record?.status ===
                                                          "excused"
                                                        ? styles[
                                                              "selectExcused"
                                                          ]
                                                        : styles["selectAbsent"]
                                                )}
                                            />
                                        </td>

                                        <td>
                                            <InputWithLabel
                                                type="text"
                                                value={record?.note || ""}
                                                onChange={(e) =>
                                                    handleNoteChange(
                                                        student.id,
                                                        e.target.value
                                                    )
                                                }
                                                placeholder="Nhập ghi chú"
                                            />
                                        </td>
                                    </tr>
                                );
                            })}
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

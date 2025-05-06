import React, { useState, useEffect } from "react";
import styles from "./styles.module.css";
import AuthGuard from "@/components/AuthGuard";
import BorderBox from "@/components/BorderBox";

interface Submission {
    id: string;
    student_id: string;
    student_name: string;
    file_url: string;
    submission_time: string;
    is_late: boolean;
}

interface Assignment {
    id: string;
    title: string;
    deadline: string;
}

const mockSubmissions: Submission[] = [
    {
        id: "1",
        student_id: "SV001",
        student_name: "Nguyễn Văn A",
        file_url: "https://example.com/bai-tap-1-sv001.pdf",
        submission_time: "2024-03-10T23:50:00",
        is_late: false,
    },
    {
        id: "2",
        student_id: "SV002",
        student_name: "Trần Thị B",
        file_url: "https://example.com/bai-tap-1-sv002.docx",
        submission_time: "2024-03-11T00:05:00",
        is_late: true,
    },
    {
        id: "3",
        student_id: "SV003",
        student_name: "Lê Công C",
        file_url: "https://example.com/bai-tap-1-sv003.zip",
        submission_time: "2024-03-10T22:00:00",
        is_late: false,
    },
    {
        id: "4",
        student_id: "SV004",
        student_name: "Phạm Thu D",
        file_url: "https://example.com/bai-tap-1-sv004.pdf",
        submission_time: "2024-03-11T01:20:00",
        is_late: true,
    },
    {
        id: "5",
        student_id: "SV005",
        student_name: "Hoàng Minh E",
        file_url: "https://example.com/bai-tap-1-sv005.docx",
        submission_time: "2024-03-10T23:30:00",
        is_late: false,
    },
    {
        id: "6",
        student_id: "SV006",
        student_name: "Vũ Ngọc F",
        file_url: "https://example.com/bai-tap-1-sv006.pdf",
        submission_time: "2024-03-11T00:01:00",
        is_late: true,
    },
];

const ViewSubmissions = ({ assignment }: { assignment: Assignment }) => {
    const [submissions, setSubmissions] = useState<Submission[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox title="Danh sách sinh viên đã nộp bài tập 1 - Lớp 125213">
                <div className={styles.viewSubmissionsContainer}>
                    <h1 className={styles.viewSubmissionsTitle}>
                        Bài nộp: "assignment.title"
                    </h1>
                    <p className={styles.assignmentDeadline}>
                        {/* Hạn nộp: {new Date(assignment.deadline).toLocaleString()} */}
                        Hạn nộp: 10/5/2025
                    </p>

                    <div className={styles.submissionsScrollArea}>
                        <div className={styles.submissionsList}>
                            {mockSubmissions.length === 0 ? (
                                <div className={styles.noSubmissions}>
                                    Chưa có sinh viên nào nộp bài.
                                </div>
                            ) : (
                                <table className={styles.submissionsTable}>
                                    <thead className={styles.submissionsThead}>
                                        <tr>
                                            <th className={styles.tableHeader}>
                                                STT
                                            </th>
                                            <th className={styles.tableHeader}>
                                                Mã SV
                                            </th>
                                            <th className={styles.tableHeader}>
                                                Tên SV
                                            </th>
                                            <th className={styles.tableHeader}>
                                                File
                                            </th>
                                            <th className={styles.tableHeader}>
                                                Thời gian nộp
                                            </th>
                                            <th className={styles.tableHeader}>
                                                Trạng thái
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody className={styles.submissionsTbody}>
                                        {mockSubmissions.map(
                                            (submission, index) => (
                                                <tr
                                                    key={submission.id}
                                                    className={
                                                        styles.submissionRow
                                                    }
                                                >
                                                    <td
                                                        className={
                                                            styles.tableCell
                                                        }
                                                    >
                                                        {index + 1}
                                                    </td>
                                                    <td
                                                        className={
                                                            styles.tableCell
                                                        }
                                                    >
                                                        {submission.student_id}
                                                    </td>
                                                    <td
                                                        className={
                                                            styles.tableCell
                                                        }
                                                    >
                                                        {
                                                            submission.student_name
                                                        }
                                                    </td>
                                                    <td
                                                        className={
                                                            styles.tableCell
                                                        }
                                                    >
                                                        <a
                                                            href={
                                                                submission.file_url
                                                            }
                                                            target="_blank"
                                                            rel="noopener noreferrer"
                                                            className={
                                                                styles.fileLink
                                                            }
                                                        >
                                                            {submission.file_url
                                                                .split("/")
                                                                .pop()}
                                                        </a>
                                                    </td>
                                                    <td
                                                        className={
                                                            styles.tableCell
                                                        }
                                                    >
                                                        {new Date(
                                                            submission.submission_time
                                                        ).toLocaleString()}
                                                    </td>
                                                    <td
                                                        className={
                                                            styles.tableCell
                                                        }
                                                    >
                                                        Nộp muộn
                                                    </td>
                                                </tr>
                                            )
                                        )}
                                    </tbody>
                                </table>
                            )}
                        </div>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ViewSubmissions;

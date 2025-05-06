//xem list bài tập theo lớp của giảng viên
import React, { useState } from "react";
import Link from "next/link";
import { IoMdAddCircle } from "react-icons/io";
import styles from "./styles.module.css";
import { useRouter } from "next/router";
import AuthGuard from "@/components/AuthGuard";
import BorderBox from "@/components/BorderBox";

type Assignment = {
    deadline: string;
    title: string;
    description: string;
    file_url: string;
    created_at: string;
    exercise_id: number;
    class_subject_id: number;
};

const AssignmentItem = ({
    assignment,
    submissionsCount,
}: {
    assignment: Assignment;
    submissionsCount: number;
}) => {
    return (
        <div className={styles.assignmentItem}>
            <h3 className={styles.title}>{assignment.title}</h3>
            <p className={styles.description}>{assignment.description}</p>
            <div className={styles.infoRow}>
                <strong>Hạn nộp:</strong>
                <span className={styles.deadline}>{assignment.deadline}</span>
            </div>
            <div className={styles.infoRow}>
                <strong>Đã nộp:</strong>
                <span className={styles.submissionsCount}>
                    {submissionsCount} / 10
                </span>
                sinh viên
            </div>
            {assignment.file_url && (
                <div className={styles.infoRow}>
                    <strong>File:</strong>{" "}
                    <a
                        href={assignment.file_url}
                        target="_blank"
                        rel="noopener noreferrer"
                        className={styles.fileLink}
                    >
                        Xem file
                    </a>
                </div>
            )}
            <div className={styles.infoRow}>
                <strong>Ngày tạo:</strong>{" "}
                <span className={styles.createdDate}>
                    {new Date(assignment.created_at).toLocaleDateString()}
                </span>
            </div>
            <div className={styles.actions}>
                <Link
                    href={`/assignment/${assignment.class_subject_id}/view/?id=${assignment.exercise_id}`}
                    className={styles.buttonDetail}
                >
                    Xem chi tiết
                </Link>
                <Link
                    href={`/assignment/${assignment.class_subject_id}/create-edit?id=${assignment.exercise_id}&mode=edit`}
                    className={styles.buttonUpdate}
                >
                    Chỉnh sửa
                </Link>
                <Link
                    href={`/assignment/${assignment.class_subject_id}/view_submissions/?id=${assignment.exercise_id}`}
                    className={styles.viewSubmissions}
                >
                    Xem bài nộp
                </Link>
            </div>
        </div>
    );
};

const AssignmentList = () => {
    const [assignments, setAssignments] = useState<Assignment[]>([
        {
            exercise_id: 1,
            class_subject_id: 101,
            title: "Bài tập về nhà số 1",
            description: "Giải các bài toán trang 10 đến 15.",
            file_url: "https://example.com/baitap1.pdf",
            deadline: "2025-05-10",
            created_at: "2025-05-03T10:00:00Z",
        },
        {
            exercise_id: 2,
            class_subject_id: 101,
            title: "Báo cáo thực hành",
            description: "Viết báo cáo về buổi thực hành ngày 2 tháng 5.",
            file_url: "https://example.com/baocao.docx",
            deadline: "2025-05-15",
            created_at: "2025-05-01T14:30:00Z",
        },
        {
            exercise_id: 3,
            class_subject_id: 102,
            title: "Bài tập nhóm",
            description: "Hoàn thành bài tập nhóm số 1",
            file_url: "https://example.com/baitapnhom1.pdf",
            deadline: "2025-05-20",
            created_at: "2025-05-05T10:00:00Z",
        },
        {
            exercise_id: 4,
            class_subject_id: 102,
            title: "Thuyết trình",
            description: "Chuẩn bị bài thuyết trình",
            file_url: "https://example.com/thuyettrinh.pptx",
            deadline: "2025-05-22",
            created_at: "2025-05-07T14:00:00Z",
        },
    ]);
    const [submissionsCounts, setSubmissionsCounts] = useState<{
        [key: number]: number;
    }>({});

    const router = useRouter();
    const { class_id } = router.query;

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox title="Danh sách bài tập lớp 125213">
                <div className={styles.assignmentList}>
                    <Link
                        href={`/assignment/${class_id}/create-edit`}
                        className={styles.addAssignmentButton}
                    >
                        <IoMdAddCircle /> Thêm mới
                    </Link>
                    <div className={styles.assignmentGrid}>
                        {assignments.map((assignment) => (
                            <AssignmentItem
                                key={assignment.exercise_id}
                                assignment={assignment}
                                submissionsCount={
                                    submissionsCounts[assignment.exercise_id] ||
                                    0
                                }
                            />
                        ))}
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default AssignmentList;

import React, { useState } from "react";
import { useRouter } from "next/router";
import BorderBox from "@/components/BorderBox";
import { IoIosArrowBack } from "react-icons/io";
import AuthGuard from "@/components/AuthGuard";
import { Button } from "@/components/Button";
import styles from "./styles.module.css";

type Assignment = {
    exercise_id: number;
    title: string;
    description: string;
    deadline: string;
    file_url: string;
    created_at: string;
    class_subject_id: number;
};

const mockAssignment: Assignment = {
    exercise_id: 123,
    title: "Bài tập về nhà số 1",
    description: "Giải các bài toán từ trang 20 đến 25 trong sách giáo trình.",
    deadline: "2025-05-15T23:59:00Z",
    file_url: "https://example.com/assignment1.pdf",
    created_at: "2025-05-01T10:00:00Z",
    class_subject_id: 101,
};

const AssignmentDetail = () => {
    const router = useRouter();
    const assignment = mockAssignment;

    const [submitLink, setSubmitLink] = useState<string>("");
    const [submitted, setSubmitted] = useState<boolean>(false);

    const handleSubmit = () => {
        if (!submitLink.trim()) return;
        setSubmitted(true);
        alert(`Đã nộp bài tập với link: ${submitLink}`);
    };

    const deadlineDate = new Date(assignment.deadline);
    const createdDate = new Date(assignment.created_at);

    return (
        <AuthGuard allowedRoles={["admin", "lecturer", "student"]}>
            <BorderBox title={`Chi tiết bài tập: ${assignment.title}`}>
                <div className={styles.gridContainer}>
                    {/* LEFT COLUMN */}
                    <div className={styles.leftColumn}>
                        <div className={styles.detailItem}>
                            <span className={styles.detailLabel}>Tiêu đề:</span>
                            <span>{assignment.title}</span>
                        </div>
                        <div className={styles.detailItem}>
                            <span className={styles.detailLabel}>Mô tả:</span>
                            <span>{assignment.description}</span>
                        </div>
                        <div className={styles.detailItem}>
                            <span className={styles.detailLabel}>Hạn nộp:</span>
                            <span>{deadlineDate.toLocaleString()}</span>
                        </div>
                        <div className={styles.detailItem}>
                            <span className={styles.detailLabel}>File:</span>
                            {assignment.file_url ? (
                                <a
                                    href={assignment.file_url}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className={styles.fileLink}
                                >
                                    Tải xuống
                                </a>
                            ) : (
                                <span>Không có file</span>
                            )}
                        </div>
                        <div className={styles.detailItem}>
                            <span className={styles.detailLabel}>
                                Ngày tạo:
                            </span>
                            <span>{createdDate.toLocaleString()}</span>
                        </div>
                        <div className={styles.detailItem}>
                            <span className={styles.detailLabel}>
                                Mã lớp học:
                            </span>
                            <span>{assignment.class_subject_id}</span>
                        </div>
                        <div className={styles.buttonGroup}>
                            <div
                                className={styles.backButton}
                                onClick={() => router.back()}
                            >
                                <IoIosArrowBack className={styles.backIcon} />
                                <span>Quay lại</span>
                            </div>
                        </div>
                    </div>

                    {/* RIGHT COLUMN - SUBMISSION */}
                    <div className={styles.rightColumn}>
                        <h3>Nộp bài tập</h3>
                        <input
                            type="text"
                            placeholder="Dán link bài tập của bạn"
                            className={styles.inputBox}
                            value={submitLink}
                            onChange={(e) => setSubmitLink(e.target.value)}
                            disabled={submitted}
                        />
                        <Button
                            onClick={handleSubmit}
                            className={styles.submitButton}
                            disabled={submitted}
                        >
                            {submitted ? "Đã nộp" : "Nộp bài"}
                        </Button>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default AssignmentDetail;

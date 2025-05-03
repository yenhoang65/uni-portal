import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import BorderBox from "@/components/BorderBox";
import { TypographyBody } from "@/components/TypographyBody";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";
import AuthGuard from "@/components/AuthGuard";
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
    const { query } = router;
    const { id } = query; // Lấy ID bài tập từ query

    // State để lưu trữ dữ liệu bài tập (sử dụng mock data cho đơn giản)
    const [assignment, setAssignment] = useState<Assignment | null>(null);

    useEffect(() => {
        // Trong thực tế, bạn sẽ fetch dữ liệu bài tập từ API dựa trên ID

        if (id) {
            setAssignment(mockAssignment); // Đặt mock data vào state
        }
    }, [id]);

    if (!assignment) {
        return <div>Loading...</div>;
    }

    const deadlineDate = new Date(assignment.deadline);
    const createdDate = new Date(assignment.created_at);

    return (
        <AuthGuard allowedRoles={["admin", "lecturer", "student"]}>
            <BorderBox title={`Chi tiết bài tập: ${assignment.title}`}>
                <div className={styles.detailContainer}>
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Tiêu đề:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {assignment.title}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mô tả:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {assignment.description}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Hạn nộp:
                        </TypographyBody>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.deadline}
                        >
                            {deadlineDate.toLocaleString()}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            File:
                        </TypographyBody>
                        {assignment.file_url && (
                            <a
                                href={assignment.file_url}
                                target="_blank"
                                rel="noopener noreferrer"
                                className={styles.fileLink}
                            >
                                Tải xuống
                            </a>
                        )}
                        {!assignment.file_url && (
                            <TypographyBody tag="span" theme="md">
                                Không có file
                            </TypographyBody>
                        )}
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Ngày tạo:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {createdDate.toLocaleString()}
                        </TypographyBody>
                    </div>
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mã Lớp Học:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {assignment.class_subject_id}
                        </TypographyBody>
                    </div>

                    <div className={styles.buttonGroup}>
                        <div
                            className={styles.backButton}
                            onClick={() => router.back()}
                        >
                            <IoIosArrowBack className={styles.backIcon} />
                            <span className={styles.backText}>Back</span>
                        </div>
                        <button
                            className={styles.editButton}
                            onClick={() =>
                                router.push(
                                    `/assignment/${assignment.class_subject_id}/create-edit?id=${assignment.exercise_id}&mode=edit`
                                )
                            }
                        >
                            <AiFillEdit /> Edit
                        </button>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default AssignmentDetail;

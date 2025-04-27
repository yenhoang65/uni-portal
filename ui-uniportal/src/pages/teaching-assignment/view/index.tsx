import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";
import AuthGuard from "@/components/AuthGuard";

type TeachingAssignment = {
    assignment_id: string;
    lecturer_id: string;
    subject_id: string;
    term_class_id: string;
    lecturer_name?: string; // Tên giảng viên (lấy từ bảng lecturer)
    subject_name?: string; // Tên môn học (lấy từ bảng subject)
    class_name?: string; // Tên lớp (lấy từ bảng term_class)
};

const TeachingAssignmentDetail = () => {
    const router = useRouter();
    const { query } = router;
    const { id } = query;
    const [teachingAssignment, setTeachingAssignment] =
        useState<TeachingAssignment | null>(null);

    // Dữ liệu giả (dummy data) để ánh xạ thông tin
    const dummyTeachingAssignment: TeachingAssignment = {
        assignment_id: "ASS001",
        lecturer_id: "LEC001",
        subject_id: "SUB001",
        term_class_id: "TERM001",
        lecturer_name: "Nguyễn Văn A", // Ánh xạ từ lecturer_id
        subject_name: "Nhập môn Lập trình", // Ánh xạ từ subject_id
        class_name: "Lớp 101", // Ánh xạ từ term_class_id
    };

    useEffect(() => {
        if (id) {
            // Trong thực tế, bạn sẽ gọi API để lấy dữ liệu dựa trên id
            // Ví dụ: fetch(`/api/teaching-assignments/${id}`)
            // Dữ liệu trả về cần bao gồm lecturer_name, subject_name, class_name
            setTeachingAssignment(dummyTeachingAssignment);
        }
    }, [id]);

    if (!teachingAssignment) {
        return <div>Đang tải...</div>;
    }

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox
                title={`Chi tiết Phân công Giảng dạy: ${teachingAssignment.assignment_id}`}
            >
                <div className={styles.detailContainer}>
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mã phân công:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingAssignment.assignment_id}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mã giảng viên:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingAssignment.lecturer_id}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Tên giảng viên:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingAssignment.lecturer_name ||
                                "Không có thông tin"}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mã môn học:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingAssignment.subject_id}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Tên môn học:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingAssignment.subject_name ||
                                "Không có thông tin"}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mã học kỳ - lớp:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingAssignment.term_class_id}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Tên lớp:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingAssignment.class_name ||
                                "Không có thông tin"}
                        </TypographyBody>
                    </div>

                    <div className={styles.buttonGroup}>
                        <div
                            className={styles.backButton}
                            onClick={() => router.back()}
                        >
                            <IoIosArrowBack className={styles.backIcon} />
                            <span className={styles.backText}>Quay lại</span>
                        </div>
                        <button
                            className={styles.editButton}
                            onClick={() =>
                                router.push(
                                    `/teaching-assignment/create-edit?id=${teachingAssignment.assignment_id}&mode=edit`
                                )
                            }
                        >
                            <AiFillEdit /> Chỉnh sửa
                        </button>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default TeachingAssignmentDetail;

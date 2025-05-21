import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import BorderBox from "@/components/BorderBox";
import { TypographyBody } from "@/components/TypographyBody";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";
import AuthGuard from "@/components/AuthGuard";
import styles from "./styles.module.css";

// Mock data for grade_event
type GradeEvent = {
    grade_event_id: string;
    class_subject_id: string;
    grade_type_id: string;
    title: string;
    event_date: string;
    max_score: number;
    coeff_override: number | null;
    description: string;
    created_at: string;
};

const mockGradeEvent: GradeEvent = {
    grade_event_id: "GE001",
    class_subject_id: "CS001",
    grade_type_id: "GT002",
    title: "Thi cuối kỳ Công nghệ phần mềm",
    event_date: "2025-06-10T08:00:00Z",
    max_score: 10,
    coeff_override: 1.5,
    description: "Thi cuối kỳ, thời gian làm bài 120 phút.",
    created_at: "2025-05-01T10:00:00Z",
};

const GradeEventDetail = () => {
    const router = useRouter();
    const { id } = router.query;

    const [gradeEvent, setGradeEvent] = useState<GradeEvent | null>(null);

    useEffect(() => {
        if (id) {
            setGradeEvent(mockGradeEvent); // Thay thế bằng fetch thực tế nếu cần
        }
    }, [id]);

    if (!gradeEvent) {
        return <div>Loading...</div>;
    }

    const eventDate = new Date(gradeEvent.event_date);
    const createdDate = new Date(gradeEvent.created_at);

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox title={`Chi tiết Sự kiện điểm`}>
                <div className={styles.detailHeader}>
                    <TypographyBody
                        tag="span"
                        theme="lg"
                        className={styles.assignmentTitle}
                    >
                        {gradeEvent.title}
                    </TypographyBody>
                    <button
                        className={styles.editButton}
                        onClick={() =>
                            router.push(
                                `/assignment/${gradeEvent.class_subject_id}/create-edit?id=${gradeEvent.grade_event_id}&mode=edit`
                            )
                        }
                    >
                        <AiFillEdit /> Chỉnh sửa
                    </button>
                </div>
                <div className={styles.detailGrid}>
                    <div>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mã sự kiện điểm:
                        </TypographyBody>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailValue}
                        >
                            {gradeEvent.grade_event_id}
                        </TypographyBody>
                    </div>
                    <div>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Lớp học phần:
                        </TypographyBody>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailValue}
                        >
                            {gradeEvent.class_subject_id}
                        </TypographyBody>
                    </div>
                    <div>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Loại điểm:
                        </TypographyBody>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailValue}
                        >
                            {gradeEvent.grade_type_id}
                        </TypographyBody>
                    </div>
                    <div>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Ngày diễn ra:
                        </TypographyBody>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailValue}
                        >
                            {eventDate.toLocaleString()}
                        </TypographyBody>
                    </div>
                    <div>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Điểm tối đa:
                        </TypographyBody>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailValue}
                        >
                            {gradeEvent.max_score}
                        </TypographyBody>
                    </div>
                    <div>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Hệ số (override):
                        </TypographyBody>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailValue}
                        >
                            {gradeEvent.coeff_override !== null
                                ? gradeEvent.coeff_override
                                : "Không có"}
                        </TypographyBody>
                    </div>
                    <div style={{ gridColumn: "1 / -1" }}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mô tả:
                        </TypographyBody>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailValue}
                        >
                            {gradeEvent.description}
                        </TypographyBody>
                    </div>
                    <div>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Ngày tạo:
                        </TypographyBody>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailValue}
                        >
                            {createdDate.toLocaleString()}
                        </TypographyBody>
                    </div>
                </div>
                <div className={styles.buttonGroup}>
                    <button
                        className={styles.backButton}
                        onClick={() => router.back()}
                    >
                        <IoIosArrowBack className={styles.backIcon} />
                        Quay lại
                    </button>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default GradeEventDetail;

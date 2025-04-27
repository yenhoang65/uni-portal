import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";
import AuthGuard from "@/components/AuthGuard";

type TeachingScheduleRequest = {
    schedule_id: string;
    classroom_id: string;
    lesson: number;
    date_time: string;
    status: number;
    created_at: string;
    assignment_id: string;
    class_type: number;
    // Assuming you want to display related information:
    classroom_name?: string; // Tên phòng học (lấy từ bảng classroom)
    lecturer_name?: string; // Tên giảng viên (lấy từ bảng lecturer thông qua assignment)
    subject_name?: string; // Tên môn học (lấy từ bảng subject thông qua assignment)
};

const TeachingScheduleRequestDetail = () => {
    const router = useRouter();
    const { query } = router;
    const { id } = query;
    const [teachingScheduleRequest, setTeachingScheduleRequest] =
        useState<TeachingScheduleRequest | null>(null);

    // Dữ liệu giả (dummy data) để ánh xạ thông tin
    const dummyTeachingScheduleRequest: TeachingScheduleRequest = {
        schedule_id: "SCH001",
        classroom_id: "CRM001",
        lesson: 1,
        date_time: "2025-05-05T08:00:00Z",
        status: 1,
        created_at: "2025-05-01T10:00:00Z",
        assignment_id: "ASS001",
        class_type: 0,
        classroom_name: "Phòng A101", // Ánh xạ từ classroom_id
        lecturer_name: "Nguyễn Văn A", // Ánh xạ từ assignment_id -> lecturer
        subject_name: "Nhập môn Lập trình", // Ánh xạ từ assignment_id -> subject
    };

    useEffect(() => {
        if (id) {
            // Trong thực tế, bạn sẽ gọi API để lấy dữ liệu dựa trên id
            // Ví dụ: fetch(`/api/teaching-schedule-requests/${id}`)
            // Dữ liệu trả về cần bao gồm các trường của TeachingScheduleRequest
            // và có thể đã join sẵn thông tin về classroom, lecturer, subject
            setTeachingScheduleRequest(dummyTeachingScheduleRequest);
        }
    }, [id]);

    if (!teachingScheduleRequest) {
        return <div>Đang tải...</div>;
    }

    const statusLabel =
        teachingScheduleRequest.status === 0 ? "Chưa duyệt" : "Đã duyệt";
    const classTypeLabel =
        teachingScheduleRequest.class_type === 0 ? "Lý thuyết" : "Thực hành";

    return (
        <AuthGuard allowedRoles={["admin", "employee"]}>
            <BorderBox
                title={`Chi tiết Yêu cầu Lịch giảng: ${teachingScheduleRequest.schedule_id}`}
            >
                <div className={styles.detailContainerHorizontal}>
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mã lịch:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingScheduleRequest.schedule_id}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mã phòng học:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingScheduleRequest.classroom_id}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Tên phòng học:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingScheduleRequest.classroom_name ||
                                "Không có thông tin"}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Tiết học:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingScheduleRequest.lesson}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Thời gian:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingScheduleRequest.date_time}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Trạng thái:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {statusLabel}
                        </TypographyBody>
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
                            {teachingScheduleRequest.created_at}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Mã phân công:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {teachingScheduleRequest.assignment_id}
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
                            {teachingScheduleRequest.lecturer_name ||
                                "Không có thông tin"}
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
                            {teachingScheduleRequest.subject_name ||
                                "Không có thông tin"}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            theme="md"
                            className={styles.detailLabel}
                        >
                            Loại lớp:
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md">
                            {classTypeLabel}
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
                                    `/teaching-schedule-request/edit?id=${teachingScheduleRequest.schedule_id}&mode=edit`
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

export default TeachingScheduleRequestDetail;

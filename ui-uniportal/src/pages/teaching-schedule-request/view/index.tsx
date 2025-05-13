import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";
import AuthGuard from "@/components/AuthGuard";
import { FaCheck } from "react-icons/fa";
import { IoWarning } from "react-icons/io5";

type TeachingScheduleRequest = {
    schedule_id: string;
    classroom_id: string;
    lesson: number;
    date_time: string;
    status: 0 | 1 | 2; // 0: Pending, 1: Accepted, 2: Rejected
    created_at: string;
    assignment_id: string;
    class_type: number;
    classroom_name?: string;
    lecturer_name?: string;
    subject_name?: string;
};

const TeachingScheduleRequestDetail = () => {
    const router = useRouter();
    const { query } = router;
    const { id } = query;
    const [teachingScheduleRequest, setTeachingScheduleRequest] =
        useState<TeachingScheduleRequest | null>(null);

    const [status, setStatus] = useState<0 | 1 | 2>(0); // Default to "Pending" (0)
    const [isStatusChanged, setIsStatusChanged] = useState(false);

    const dummyTeachingScheduleRequest: TeachingScheduleRequest = {
        schedule_id: "SCH001",
        classroom_id: "CRM001",
        lesson: 1,
        date_time: "2025-05-05T08:00:00Z",
        status: 0, // Pending by default
        created_at: "2025-05-01T10:00:00Z",
        assignment_id: "ASS001",
        class_type: 0,
        classroom_name: "Phòng A101", // Mocked data
        lecturer_name: "Nguyễn Văn A", // Mocked data
        subject_name: "Nhập môn Lập trình", // Mocked data
    };

    useEffect(() => {
        if (id) {
            // Simulate fetching data from an API
            setTeachingScheduleRequest(dummyTeachingScheduleRequest);
            setStatus(dummyTeachingScheduleRequest.status); // Set the status from the data
        }
    }, [id]);

    const handleAccept = () => {
        setStatus(1); // Set status to "Accepted"
        setIsStatusChanged(true);
    };

    const handleReject = () => {
        setStatus(2); // Set status to "Rejected"
        setIsStatusChanged(true);
    };

    const statusLabel =
        status === 0 ? "Chưa duyệt" : status === 1 ? "Đã duyệt" : "Bị từ chối";
    const classTypeLabel =
        teachingScheduleRequest?.class_type === 0 ? "Lý thuyết" : "Thực hành";

    if (!teachingScheduleRequest) {
        return <div>Đang tải...</div>;
    }

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
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

                    <div className={styles.buttonGroup}>
                        <div
                            className={styles.backButton}
                            onClick={() => router.back()}
                        >
                            <IoIosArrowBack className={styles.backIcon} />
                            <span className={styles.backText}>Quay lại</span>
                        </div>

                        {status !== 1 && (
                            <>
                                <Button
                                    // onClick={handleAccept}
                                    className={styles.acceptButton}
                                    onClick={() =>
                                        router.push(
                                            `/teaching-schedule-request/edit?id=${teachingScheduleRequest.schedule_id}&mode=edit`
                                        )
                                    }
                                >
                                    <FaCheck /> Đăng ký lịch dạy
                                </Button>
                                <Button
                                    onClick={handleReject}
                                    className={styles.rejectButton}
                                >
                                    <IoWarning /> Từ chối
                                </Button>
                            </>
                        )}

                        {status === 1 && (
                            <Button
                                className={styles.editButton}
                                onClick={() =>
                                    router.push(
                                        `/teaching-schedule-request/edit?id=${teachingScheduleRequest.schedule_id}&mode=edit`
                                    )
                                }
                            >
                                <AiFillEdit /> Chỉnh sửa
                            </Button>
                        )}
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default TeachingScheduleRequestDetail;

import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { Button } from "@/components/Button";
import SelectWithLabel from "@/components/SelectWithLabel";
import AuthGuard from "@/components/AuthGuard";
import Link from "next/link";
import { IoIosArrowBack } from "react-icons/io";

type TeachingScheduleRequestState = {
    schedule_id: string;
    classroom_id: string;
    lesson: string;
    date_time: string;
    status: string;
    assignment_id: string;
    class_type: string;
};

// Dữ liệu giả định cho các dropdown (trong thực tế bạn sẽ fetch từ API)
const classroomOptions = [
    { value: "CRM001", label: "Phòng A101" },
    { value: "CRM002", label: "Phòng B205" },
    // Thêm các phòng học khác
];

const assignmentOptions = [
    { value: "ASS001", label: "GV: Nguyễn Văn A - MH: Nhập môn LT - Lớp: 101" },
    { value: "ASS002", label: "GV: Trần Thị B - MH: Giải tích 1 - Lớp: 102" },
    // Thêm các phân công giảng dạy khác
];

const lessonOptions = [
    { value: "1", label: "Tiết 1" },
    { value: "2", label: "Tiết 2" },
    { value: "3", label: "Tiết 3" },
    { value: "4", label: "Tiết 4" },
    { value: "5", label: "Tiết 5" },
    // Thêm các tiết học khác
];

const statusOptions = [
    { value: "0", label: "Chưa duyệt" },
    { value: "1", label: "Đã duyệt" },
];

const classTypeOptions = [
    { value: "0", label: "Lý thuyết" },
    { value: "1", label: "Thực hành" },
    { value: "2", label: "Online" }, // Added "Online" option
];

const CreateEditTeachingScheduleRequest = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [state, setState] = useState<TeachingScheduleRequestState>({
        schedule_id: "",
        classroom_id: classroomOptions[0]?.value || "",
        lesson: lessonOptions[0]?.value || "",
        date_time: "",
        status: statusOptions[0]?.value || "",
        assignment_id: assignmentOptions[0]?.value || "",
        class_type: classTypeOptions[0]?.value || "",
    });

    // State để lưu thông tin từ Teaching Assignment (sẽ bị disable)
    const [teachingAssignmentInfo, setTeachingAssignmentInfo] = useState<{
        lecturer_id?: string;
        subject_name?: string;
        term_class_id?: string;
    }>({});

    const inputHandle = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
    ) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };

    useEffect(() => {
        if (query.mode === "edit" && query.id) {
            setMode("edit");
            // Gọi API để lấy thông tin Teaching Schedule Request theo ID
            // Dữ liệu trả về cần map vào state
            fetchTeachingScheduleRequest(query.id as string);
        } else {
            setMode("create");
            resetState();
        }
    }, [query.mode, query.id]);

    useEffect(() => {
        // Khi assignment_id thay đổi, fetch thông tin Teaching Assignment
        if (state.assignment_id) {
            fetchTeachingAssignmentInfo(state.assignment_id);
        } else {
            setTeachingAssignmentInfo({});
        }
    }, [state.assignment_id]);

    const fetchTeachingScheduleRequest = async (id: string) => {
        // Thay thế bằng API call thực tế
        console.log(`Fetching Teaching Schedule Request with ID: ${id}`);
        // const response = await fetch(`/api/teaching-schedule-requests/${id}`);
        // const data = await response.json();
        const dummyData: TeachingScheduleRequestState = {
            schedule_id: "SCH001",
            classroom_id: "CRM001",
            lesson: "2",
            date_time: "2025-05-06T10:00",
            status: "0",
            assignment_id: "ASS001",
            class_type: "0",
        };
        setState(dummyData);
        fetchTeachingAssignmentInfo(dummyData.assignment_id);
    };

    const fetchTeachingAssignmentInfo = async (assignmentId: string) => {
        // Thay thế bằng API call thực tế để lấy thông tin Teaching Assignment
        console.log(
            `Fetching Teaching Assignment info for ID: ${assignmentId}`
        );
        // const response = await fetch(`/api/teaching-assignments/${assignmentId}`);
        // const data = await response.json();
        const dummyAssignmentInfo = {
            lecturer_id: "LEC001",
            subject_name: "Nhập môn lập trình",
            term_class_id: "TERM001",
        };
        setTeachingAssignmentInfo(dummyAssignmentInfo);
    };

    const resetState = () => {
        setState({
            schedule_id: "",
            classroom_id: classroomOptions[0]?.value || "",
            lesson: lessonOptions[0]?.value || "",
            date_time: "",
            status: statusOptions[0]?.value || "",
            assignment_id: assignmentOptions[0]?.value || "",
            class_type: classTypeOptions[0]?.value || "",
        });
        setTeachingAssignmentInfo({});
    };

    const handleSubmit = () => {
        if (mode === "create") {
            // Gọi API để tạo Teaching Schedule Request với state
            console.log("Creating Teaching Schedule Request:", state);
        } else {
            // Gọi API để cập nhật Teaching Schedule Request với state
            console.log("Updating Teaching Schedule Request:", state);
        }
        // Sau khi submit thành công, có thể điều hướng
        router.push("/teaching-schedule-request");
    };

    const handleGoBack = () => {
        router.push("/teaching-schedule-request");
    };

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox
                title={
                    mode === "create"
                        ? "Tạo Yêu cầu Lịch giảng"
                        : "Chỉnh sửa Yêu cầu Lịch giảng"
                }
            >
                <section className={styles.container}>
                    {mode === "edit" && (
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Mã lịch"
                                name="schedule_id"
                                value={state.schedule_id}
                                onChange={inputHandle}
                                type="text"
                                required
                                disabled // Thường disable ở chế độ edit
                            />
                        </div>
                    )}
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Phòng học"
                            name="classroom_id"
                            value={state.classroom_id}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={classroomOptions}
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Tiết học"
                            name="lesson"
                            value={state.lesson}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={lessonOptions}
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Thời gian"
                            name="date_time"
                            value={state.date_time}
                            onChange={inputHandle}
                            type="datetime-local"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Trạng thái"
                            name="status"
                            value={state.status}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={statusOptions}
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Phân công giảng dạy"
                            name="assignment_id"
                            value={state.assignment_id}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={assignmentOptions}
                            required
                        />
                    </div>
                    {teachingAssignmentInfo.lecturer_id && (
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Mã giảng viên"
                                value={teachingAssignmentInfo.lecturer_id}
                                type="text"
                                disabled
                            />
                        </div>
                    )}
                    {teachingAssignmentInfo.subject_name && (
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Tên môn học"
                                value={teachingAssignmentInfo.subject_name}
                                type="text"
                                disabled
                            />
                        </div>
                    )}
                    {teachingAssignmentInfo.term_class_id && (
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Mã học kỳ - lớp"
                                value={teachingAssignmentInfo.term_class_id}
                                type="text"
                                disabled
                            />
                        </div>
                    )}
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Loại lớp"
                            name="class_type"
                            value={state.class_type}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={classTypeOptions}
                            required
                        />
                    </div>
                </section>

                <div className={styles.buttonContainer}>
                    <Link
                        href="/teaching-schedule-request"
                        className={styles.backButton}
                    >
                        <IoIosArrowBack /> Quay lại
                    </Link>
                    <Button
                        className={styles.buttonAction}
                        onClick={handleSubmit}
                    >
                        {mode === "create" ? "Lưu" : "Cập nhật"}
                    </Button>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default CreateEditTeachingScheduleRequest;

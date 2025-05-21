import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import SelectWithLabel from "@/components/SelectWithLabel";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import AuthGuard from "@/components/AuthGuard";

// Mock data cho select options
const mockClassSubjects = [
    { value: "CS001", label: "Công nghệ phần mềm (CS001)" },
    { value: "CS002", label: "Cơ sở dữ liệu (CS002)" },
    { value: "CS003", label: "Lập trình Web (CS003)" },
];
const mockGradeEvents = [
    { value: "GE001", label: "Giữa kỳ (GE001)" },
    { value: "GE002", label: "Cuối kỳ (GE002)" },
    { value: "GE003", label: "Kiểm tra 15p (GE003)" },
];
const mockClassrooms = [
    { value: "A101", label: "A101" },
    { value: "B202", label: "B202" },
    { value: "C303", label: "C303" },
];
const examForms = [
    { value: "Tự luận", label: "Tự luận" },
    { value: "Trắc nghiệm", label: "Trắc nghiệm" },
    { value: "Vấn đáp", label: "Vấn đáp" },
];

type State = {
    id: string;
    class_subject_id: string;
    grade_event_id: string;
    classroom_id: string;
    exam_date: string;
    start_time: string;
    end_time: string;
    exam_form: string;
};

const defaultState: State = {
    id: "",
    class_subject_id: "",
    grade_event_id: "",
    classroom_id: "",
    exam_date: "",
    start_time: "",
    end_time: "",
    exam_form: "",
};

const CreateEditExamSchedule = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [state, setState] = useState<State>(defaultState);

    // Nếu có logic lấy dữ liệu cũ, đưa vào đây
    useEffect(() => {
        if (query.id) {
            setMode("edit");
            // Lấy dữ liệu detail bằng id, mock thử:
            // setState({ ...dataFromAPI });
        } else {
            setMode("create");
            setState(defaultState);
        }
    }, [query.id]);

    const inputHandle = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
    ) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = () => {
        // Submit dữ liệu, tùy theo mode
        // Gọi API tạo/sửa ở đây
        alert(`${mode === "create" ? "Tạo" : "Cập nhật"} thành công!`);
        router.push("/exam-schedule");
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox
                title={
                    mode === "create" ? "Thêm lịch thi" : "Chỉnh sửa lịch thi"
                }
            >
                <section className={styles.container}>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Mã lịch thi"
                            name="id"
                            value={state.id}
                            onChange={inputHandle}
                            type="text"
                            required
                            disabled={mode === "edit"}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Lớp học phần"
                            name="class_subject_id"
                            value={state.class_subject_id}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={mockClassSubjects}
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Sự kiện điểm"
                            name="grade_event_id"
                            value={state.grade_event_id}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={mockGradeEvents}
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Phòng thi"
                            name="classroom_id"
                            value={state.classroom_id}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={mockClassrooms}
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Ngày thi"
                            name="exam_date"
                            value={state.exam_date}
                            onChange={inputHandle}
                            type="date"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Giờ bắt đầu"
                            name="start_time"
                            value={state.start_time}
                            onChange={inputHandle}
                            type="time"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Giờ kết thúc"
                            name="end_time"
                            value={state.end_time}
                            onChange={inputHandle}
                            type="time"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Hình thức thi"
                            name="exam_form"
                            value={state.exam_form}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={examForms}
                            required
                        />
                    </div>
                </section>

                <div className={styles.button}>
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

export default CreateEditExamSchedule;

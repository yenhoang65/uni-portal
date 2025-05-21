import React, { useState, useEffect, useRef } from "react";
import { useRouter } from "next/router";
import { Button } from "@/components/Button";
import InputWithLabel from "@/components/InputWithLabel";
import SelectWithLabel from "@/components/SelectWithLabel";
import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import AuthGuard from "@/components/AuthGuard";
import { TypographyBody } from "@/components/TypographyBody";
import dynamic from "next/dynamic";

// JoditEditor for description
const JoditEditor = dynamic(() => import("jodit-react"), { ssr: false });

// Mock data for select options
const mockClassSubjects = [
    { value: "CS001", label: "Công nghệ phần mềm (CS001)" },
    { value: "CS002", label: "Cơ sở dữ liệu (CS002)" },
    { value: "CS003", label: "Lập trình Web (CS003)" },
];
const mockGradeTypes = [
    { value: "GT001", label: "Giữa kỳ" },
    { value: "GT002", label: "Cuối kỳ" },
    { value: "GT003", label: "15 phút" },
];

type State = {
    grade_event_id: string;
    class_subject_id: string;
    grade_type_id: string;
    title: string;
    event_date: string;
    max_score: string;
    coeff_override: string;
    description: string;
    created_at: string;
};

const defaultState: State = {
    grade_event_id: "",
    class_subject_id: "",
    grade_type_id: "",
    title: "",
    event_date: "",
    max_score: "",
    coeff_override: "",
    description: "",
    created_at: "",
};

const CreateEditGradeEvent = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [state, setState] = useState<State>(defaultState);
    const [loading, setLoading] = useState(false);
    const editor = useRef<any>(null);

    useEffect(() => {
        if (query.id) {
            setMode("edit");
            setLoading(true);
            // Mock fetch for edit mode
            setTimeout(() => {
                setState({
                    grade_event_id: "GE001",
                    class_subject_id: "CS001",
                    grade_type_id: "GT001",
                    title: "Giữa kỳ Công nghệ phần mềm",
                    event_date: "2025-12-15",
                    max_score: "10",
                    coeff_override: "1.5",
                    description: "Kiểm tra giữa kỳ môn Công nghệ phần mềm.",
                    created_at: "2025-05-20",
                });
                setLoading(false);
            }, 600);
        } else {
            setMode("create");
            setState({
                ...defaultState,
                class_subject_id: mockClassSubjects[0]?.value || "",
                grade_type_id: mockGradeTypes[0]?.value || "",
            });
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

    const handleSubmit = async () => {
        setLoading(true);
        try {
            if (mode === "create") {
                // Call API create here
                console.log("Creating grade event:", state);
            } else {
                // Call API update here
                console.log("Updating grade event:", state);
            }
            router.push("/grade-event");
        } catch (error) {
            console.error("Failed to submit grade event:", error);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox
                title={
                    mode === "create"
                        ? "Giao bài tập"
                        : "Chỉnh sửa bài tập đã giao"
                }
            >
                <div className={styles.formContainer}>
                    <div className={styles.formGrid}>
                        <div className={styles.formItem}>
                            <InputWithLabel
                                label="Mã bài tập"
                                name="grade_event_id"
                                value={state.grade_event_id}
                                onChange={inputHandle}
                                type="text"
                                required
                                disabled={mode === "edit"}
                            />
                        </div>
                        <div className={styles.formItem}>
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
                        <div className={styles.formItem}>
                            <SelectWithLabel
                                label="Loại điểm"
                                name="grade_type_id"
                                value={state.grade_type_id}
                                onChange={
                                    inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                                }
                                options={mockGradeTypes}
                                required
                            />
                        </div>
                        <div className={styles.formItem}>
                            <InputWithLabel
                                label="Tiêu đề"
                                name="title"
                                value={state.title}
                                onChange={inputHandle}
                                type="text"
                                required
                            />
                        </div>
                        <div className={styles.formItem}>
                            <InputWithLabel
                                label="Ngày hết hạn"
                                name="event_date"
                                value={state.event_date}
                                onChange={inputHandle}
                                type="date"
                                required
                            />
                        </div>
                        <div className={styles.formItem}>
                            <InputWithLabel
                                label="Điểm tối đa"
                                name="max_score"
                                value={state.max_score}
                                onChange={inputHandle}
                                type="number"
                                required
                            />
                        </div>
                        <div className={styles.formItem}>
                            <InputWithLabel
                                label="Hệ số (nếu có)"
                                name="coeff_override"
                                value={state.coeff_override}
                                onChange={inputHandle}
                                type="number"
                            />
                        </div>
                        <div className={styles.formItemFull}>
                            <label
                                htmlFor="description"
                                className={styles.label}
                            >
                                Mô tả
                            </label>
                            <JoditEditor
                                ref={editor}
                                value={state.description}
                                onChange={(newContent) =>
                                    setState({
                                        ...state,
                                        description: newContent,
                                    })
                                }
                                className={styles.inputDesc}
                            />
                        </div>
                    </div>
                    <div className={styles.button}>
                        <Button
                            className={styles.buttonAction}
                            onClick={handleSubmit}
                            disabled={loading}
                        >
                            {mode === "create" ? "Tạo" : "Cập nhật"}
                        </Button>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default CreateEditGradeEvent;

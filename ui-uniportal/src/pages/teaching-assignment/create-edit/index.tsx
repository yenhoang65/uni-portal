import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { Button } from "@/components/Button";
import SelectWithLabel from "@/components/SelectWithLabel";
import AuthGuard from "@/components/AuthGuard";

type TeachingAssignmentState = {
    assignment_id: string; // Assuming bigint can be treated as string for input
    lecturer_id: string;
    subject_id: string;
    term_class_id: string;
};

// Dummy data for dropdown options - replace with your actual API calls
const lecturerOptions = [
    { value: "LEC001", label: "Giảng viên A" },
    { value: "LEC002", label: "Giảng viên B" },
    // ... more lecturers
];

const subjectOptions = [
    { value: "SUB001", label: "Môn học X" },
    { value: "SUB002", label: "Môn học Y" },
    // ... more subjects
];

const termClassOptions = [
    { value: "TC001", label: "Lớp 1 - Học kỳ 1" },
    { value: "TC002", label: "Lớp 2 - Học kỳ 2" },
    // ... more term classes
];

const CreateEditTeachingAssignment = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [state, setState] = useState<TeachingAssignmentState>({
        assignment_id: "",
        lecturer_id: lecturerOptions[0]?.value || "",
        subject_id: subjectOptions[0]?.value || "",
        term_class_id: termClassOptions[0]?.value || "",
    });

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
            // Call API to get teaching assignment data by ID
            // Replace this with your actual API call
            const fetchAssignmentData = async (id: string) => {
                // Example:
                // try {
                //   const response = await fetch(`/api/teaching-assignments/${id}`);
                //   const data = await response.json();
                //   setState({
                //     assignment_id: data.assignment_id.toString(),
                //     lecturer_id: data.lecturer_id.toString(),
                //     subject_id: data.subject_id.toString(),
                //     term_class_id: data.term_class_id.toString(),
                //   });
                // } catch (error) {
                //   console.error("Error fetching teaching assignment:", error);
                // }
                // Dummy data for edit mode
                setState({
                    assignment_id: query.id as string,
                    lecturer_id: "LEC001",
                    subject_id: "SUB002",
                    term_class_id: "TC002",
                });
            };

            fetchAssignmentData(query.id as string);
        } else {
            setMode("create");
            setState({
                assignment_id: "",
                lecturer_id: lecturerOptions[0]?.value || "",
                subject_id: subjectOptions[0]?.value || "",
                term_class_id: termClassOptions[0]?.value || "",
            });
        }
    }, [query.mode, query.id]);

    const handleSubmit = async () => {
        if (mode === "create") {
            // Call API to create teaching assignment with state
            console.log("Creating teaching assignment:", state);
            // Example API call:
            // try {
            //   const response = await fetch("/api/teaching-assignments", {
            //     method: "POST",
            //     headers: {
            //       "Content-Type": "application/json",
            //     },
            //     body: JSON.stringify(state),
            //   });
            //   if (response.ok) {
            //     router.push("/teaching-assignment");
            //   } else {
            //     console.error("Failed to create teaching assignment");
            //   }
            // } catch (error) {
            //   console.error("Error creating teaching assignment:", error);
            // }
            router.push("/teaching-assignment");
        } else {
            // Call API to update teaching assignment with state
            console.log("Updating teaching assignment:", state);
            // Example API call:
            // try {
            //   const response = await fetch(`/api/teaching-assignments/${query.id}`, {
            //     method: "PUT",
            //     headers: {
            //       "Content-Type": "application/json",
            //     },
            //     body: JSON.stringify(state),
            //   });
            //   if (response.ok) {
            //     router.push("/teaching-assignment");
            //   } else {
            //     console.error("Failed to update teaching assignment");
            //   }
            // } catch (error) {
            //   console.error("Error updating teaching assignment:", error);
            // }
            router.push("/teaching-assignment");
        }
        // After successful submission, you might want to redirect
    };

    return (
        <AuthGuard allowedRoles={["admin", "employee"]}>
            <BorderBox
                title={
                    mode === "create"
                        ? "Thêm phân công giảng dạy"
                        : "Chỉnh sửa phân công giảng dạy"
                }
            >
                <section className={styles.container}>
                    {mode === "create" && (
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Mã phân công"
                                name="assignment_id"
                                value={state.assignment_id}
                                onChange={inputHandle}
                                type="text"
                                required
                            />
                        </div>
                    )}
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Giảng viên"
                            name="lecturer_id"
                            value={state.lecturer_id}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={lecturerOptions}
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Môn học"
                            name="subject_id"
                            value={state.subject_id}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={subjectOptions}
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Lớp - Học kỳ"
                            name="term_class_id"
                            value={state.term_class_id}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={termClassOptions}
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

export default CreateEditTeachingAssignment;

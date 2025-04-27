import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useTranslation } from "react-i18next";
import InputWithLabel from "@/components/InputWithLabel";
import { useEffect, useState } from "react";
import { Button } from "@/components/Button";
import { useRouter } from "next/router";
import AuthGuard from "@/components/AuthGuard";

type ClassTermSubjectState = {
    term_class_id: string;
    class_name: string;
    progress: string;
    semester: string;
    school_year: string;
};

const CreateEditClassTermSubject = () => {
    const { t } = useTranslation();
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [state, setState] = useState<ClassTermSubjectState>({
        term_class_id: "",
        class_name: "",
        progress: "",
        semester: "",
        school_year: "",
    });

    useEffect(() => {
        if (query.mode === "edit" && query.id) {
            setMode("edit");
            // Gọi API để lấy dữ liệu class_term_subject dựa trên query.id
            setState({
                term_class_id: "", // Lấy từ API
                class_name: "",
                progress: "",
                semester: "",
                school_year: "",
            });
        } else {
            setMode("create");
            setState({
                term_class_id: "",
                class_name: "",
                progress: "",
                semester: "",
                school_year: "",
            });
        }
    }, [query.mode, query.id]);

    const inputHandle = (e: React.ChangeEvent<HTMLInputElement>) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async () => {
        if (mode === "create") {
            // Gọi API để tạo mới class_term_subject
            console.log("Create:", state);
        } else if (mode === "edit") {
            // Gọi API để cập nhật class_term_subject với ID từ query
            console.log("Update:", state);
        }
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox
                title={
                    mode === "create"
                        ? t("common.add-class-term-subject")
                        : t("common.edit-class-term-subject")
                }
            >
                <section className={styles.container}>
                    {mode === "edit" && (
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Mã học kỳ - lớp"
                                name="term_class_id"
                                value={state.term_class_id}
                                type="text"
                                required
                                onChange={inputHandle}
                                disabled={mode === "edit"}
                            />
                        </div>
                    )}
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Tên lớp"
                            name="class_name"
                            value={state.class_name}
                            type="text"
                            required
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Tiến độ (%)"
                            name="progress"
                            value={state.progress}
                            type="number"
                            required
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Học kỳ"
                            name="semester"
                            value={state.semester}
                            type="number"
                            required
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Năm học"
                            name="school_year"
                            value={state.school_year}
                            type="number"
                            required
                            onChange={inputHandle}
                        />
                    </div>
                </section>
                <div className={styles.button}>
                    <Button
                        className={styles.buttonAction}
                        onClick={handleSubmit}
                    >
                        {mode === "create"
                            ? t("common.save-button")
                            : t("common.update-button")}
                    </Button>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default CreateEditClassTermSubject;

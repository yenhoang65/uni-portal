import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { useEffect, useRef, useState } from "react";
import SelectWithLabel from "@/components/SelectWithLabel";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import dynamic from "next/dynamic";

const JoditEditor = dynamic(() => import("jodit-react"), { ssr: false });

type State = {
    specializationId: string;
    name: string;
    description: string;
    establishDate: string;
    majorId: string; // Thêm trường majorId
};

// Dữ liệu mẫu cho danh sách ngành (thay thế bằng API call thực tế)
const majorOptions = [
    { value: "MAJ001", label: "Công nghệ phần mềm" },
    { value: "MAJ002", label: "Kỹ thuật cơ khí" },
    { value: "MAJ003", label: "Quản trị kinh doanh" },
];

const CreateEditSpecialization = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const editor = useRef(null);
    const [state, setState] = useState<State>({
        specializationId: "",
        name: "",
        description: "",
        establishDate: "",
        majorId: "", // Khởi tạo majorId
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
            // Call API to get specialization data by ID
            // Replace this with your actual API call
            setState({
                specializationId: "SPE001",
                name: "Phát triển ứng dụng Web",
                description: "<p>Chuyên sâu về phát triển web</p>",
                establishDate: "2015-05-10",
                majorId: "MAJ001", // Example default value for edit mode
            });
        } else {
            setMode("create");
            setState({
                specializationId: "",
                name: "",
                description: "",
                establishDate: "",
                majorId: majorOptions[0]?.value || "", // Set default to the first option if available
            });
        }
    }, [query.mode, query.id]);

    const handleSubmit = () => {
        if (mode === "create") {
            // Call API to create specialization with state including majorId
            console.log("Creating specialization:", state);
        } else {
            // Call API to update specialization with state including majorId
            console.log("Updating specialization:", state);
        }
    };

    return (
        <BorderBox
            title={
                mode === "create"
                    ? "Thêm chuyên ngành"
                    : "Chỉnh sửa chuyên ngành"
            }
        >
            <section className={styles.container}>
                <div className={styles.gridItem}>
                    <InputWithLabel
                        label="Mã chuyên ngành"
                        name="specializationId"
                        value={state.specializationId}
                        onChange={inputHandle}
                        type="text"
                        required
                        disabled={mode === "edit"}
                    />
                </div>
                <div className={styles.gridItem}>
                    <SelectWithLabel
                        label="Chọn ngành"
                        name="majorId"
                        value={state.majorId}
                        onChange={
                            inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                        }
                        options={majorOptions}
                        required
                    />
                </div>
                <div className={styles.gridItem}>
                    <InputWithLabel
                        label="Tên chuyên ngành"
                        name="name"
                        value={state.name}
                        onChange={inputHandle}
                        type="text"
                        required
                    />
                </div>
                <div className={styles.gridItem}>
                    <InputWithLabel
                        label="Ngày thành lập"
                        name="establishDate"
                        value={state.establishDate}
                        onChange={inputHandle}
                        type="date"
                    />
                </div>
            </section>

            <div className={styles.description}>
                <TypographyBody tag="span" theme="md" className={styles.label}>
                    Mô tả
                </TypographyBody>
                <JoditEditor
                    ref={editor}
                    value={state.description}
                    onChange={(newContent) =>
                        setState({ ...state, description: newContent })
                    }
                    className={styles.inputDesc}
                />
            </div>

            <div className={styles.button}>
                <Button className={styles.buttonAction} onClick={handleSubmit}>
                    {mode === "create" ? "Lưu" : "Cập nhật"}
                </Button>
            </div>
        </BorderBox>
    );
};

export default CreateEditSpecialization;

import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import dynamic from "next/dynamic";
import { useEffect, useRef, useState } from "react";
import SelectWithLabel from "@/components/SelectWithLabel";

const JoditEditor = dynamic(() => import("jodit-react"), { ssr: false });

type State = {
    majorId: string;
    name: string;
    description: string;
    establishDate: string;
    facultyId: string; // Thêm trường facultyId
};

// Dữ liệu mẫu cho danh sách khoa (thay thế bằng API call thực tế)
const facultyOptions = [
    { value: "FIT", label: "Khoa Công nghệ Thông tin" },
    { value: "FME", label: "Khoa Cơ khí" },
    { value: "FEB", label: "Khoa Kinh tế" },
    { value: "FAA", label: "Khoa Kiến trúc & Mỹ thuật" },
];

const CreateEditMajor = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");

    const editor = useRef(null);
    const [state, setState] = useState<State>({
        majorId: "",
        name: "",
        description: "",
        establishDate: "",
        facultyId: "", // Khởi tạo facultyId
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
            // Call API to get major data by ID
            // Replace this with your actual API call
            setState({
                majorId: "CNTT01",
                name: "Công nghệ thông tin",
                description: "<p>Ngành học về máy tính và phần mềm</p>",
                establishDate: "2010-08-20",
                facultyId: "FIT", // Example default value for edit mode
            });
        } else {
            setMode("create");
            setState({
                majorId: "",
                name: "",
                description: "",
                establishDate: "",
                facultyId: facultyOptions[0]?.value || "", // Set default to the first option if available
            });
        }
    }, [query.mode, query.id]);

    const handleSubmit = () => {
        if (mode === "create") {
            // Call API to create major with state including facultyId
            console.log("Creating major:", state);
        } else {
            // Call API to update major with state including facultyId
            console.log("Updating major:", state);
        }
    };

    return (
        <BorderBox
            title={mode === "create" ? "Thêm ngành học" : "Chỉnh sửa ngành học"}
        >
            <section className={styles.container}>
                <div className={styles.gridItem}>
                    <InputWithLabel
                        label="Mã ngành"
                        name="majorId"
                        value={state.majorId}
                        onChange={inputHandle}
                        type="text"
                        required
                        disabled={mode === "edit"}
                    />
                </div>
                <div className={styles.gridItem}>
                    <SelectWithLabel
                        label="Chọn khoa"
                        name="facultyId"
                        value={state.facultyId}
                        onChange={
                            inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                        }
                        options={facultyOptions}
                        required
                    />
                </div>
                <div className={styles.gridItem}>
                    <InputWithLabel
                        label="Tên ngành"
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

export default CreateEditMajor;

import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { useEffect, useState, useRef } from "react";
import SelectWithLabel from "@/components/SelectWithLabel";
import { Button } from "@/components/Button";
import Image from "next/image";
import { TypographyBody } from "@/components/TypographyBody";

type SubjectState = {
    subject_id: string;
    subject_name: string;
    it_credits: string;
    th_credits: string;
    subject_description: string;
    subject_type: string;
    subject_he_so: string;
};

const subjectTypeOptions = [
    { value: "Bắt buộc", label: "Bắt buộc" },
    { value: "Tự chọn", label: "Tự chọn" },
];

const CreateEditSubject = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [showImportFile, setShowImportFile] = useState(false);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [fileName, setFileName] = useState<string | null>(null);
    const fileInputRef = useRef<HTMLInputElement | null>(null);
    const [state, setState] = useState<SubjectState>({
        subject_id: "",
        subject_name: "",
        it_credits: "",
        th_credits: "",
        subject_description: "",
        subject_type: subjectTypeOptions[0]?.value || "",
        subject_he_so: "",
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
            setShowImportFile(false); // Hide import section in edit mode
            // Call API to get subject data by ID
            // Replace this with your actual API call
            setState({
                subject_id: "SUB001",
                subject_name: "Nhập môn Lập trình",
                it_credits: "3",
                th_credits: "2",
                subject_description: "Môn học cơ bản về lập trình.",
                subject_type: "Bắt buộc",
                subject_he_so: "1",
            });
        } else {
            setMode("create");
            setShowImportFile(false);
            setState({
                subject_id: "",
                subject_name: "",
                it_credits: "",
                th_credits: "",
                subject_description: "",
                subject_type: subjectTypeOptions[0]?.value || "",
                subject_he_so: "",
            });
            setSelectedFile(null);
            setFileName(null);
        }
    }, [query.mode, query.id]);

    const handleSubmit = () => {
        if (mode === "create") {
            // Call API to create subject with state
            console.log("Creating subject:", state);
        } else {
            // Call API to update subject with state
            console.log("Updating subject:", state);
        }
        // After successful submission, you might want to redirect
        router.push("/subject_management");
    };

    const handleImportButtonClick = () => {
        setShowImportFile(true);
    };

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const target = event.target as HTMLInputElement;
        if (target.files && target.files.length > 0) {
            const file = target.files[0];
            setSelectedFile(file);
            setFileName(file.name);
        }
    };

    const handleCancelImport = () => {
        setShowImportFile(false);
        setSelectedFile(null);
        setFileName(null);
        if (fileInputRef.current) {
            fileInputRef.current.value = ""; // Reset the file input
        }
    };

    const handleDownloadClick = () => {
        if (selectedFile) {
            const url = URL.createObjectURL(selectedFile);
            const a = document.createElement("a");
            a.href = url;
            a.download = selectedFile.name;
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(url);
        }
    };

    const handleProcessImport = () => {
        if (selectedFile) {
            console.log("Processing file:", selectedFile);
            // Implement your file processing logic here for subjects
            // After processing, you might want to redirect or update the UI
        } else {
            alert("Vui lòng chọn một file để import.");
        }
    };

    return (
        <BorderBox
            title={mode === "create" ? "Thêm mới môn học" : "Chỉnh sửa môn học"}
        >
            <div className={styles.headerActions}>
                {mode === "create" && !showImportFile && (
                    <Button
                        onClick={handleImportButtonClick}
                        className={styles.importButton}
                    >
                        Import từ Excel
                    </Button>
                )}
            </div>

            {mode === "create" && showImportFile && (
                <>
                    <div className={styles.template}>
                        <TypographyBody tag="span" theme="md-bold">
                            Template:
                        </TypographyBody>
                        <Image
                            src={require("./assets/template.png")}
                            alt="Subject Import Template"
                            className={styles.imageTemplate}
                            width={300}
                            height={200}
                        />
                    </div>
                    <div className={styles.excelImportSection}>
                        <div className={styles.fileUpload}>
                            <label
                                htmlFor="fileInput"
                                className={styles.fileLabel}
                            >
                                Chọn file Excel
                            </label>
                            <input
                                id="fileInput"
                                type="file"
                                accept=".xlsx, .csv"
                                className={styles.fileInput}
                                onChange={handleFileChange}
                                ref={fileInputRef}
                            />
                            {fileName && (
                                <TypographyBody
                                    tag="span"
                                    className={styles.fileName}
                                    onClick={handleDownloadClick}
                                    style={{
                                        cursor: "pointer",
                                        fontWeight: "bold",
                                        color: "blue",
                                    }}
                                >
                                    Đã chọn: {fileName}
                                </TypographyBody>
                            )}
                        </div>
                        <div className={styles.importActions}>
                            <Button
                                onClick={handleCancelImport}
                                className={styles.buttonAction}
                            >
                                Hủy
                            </Button>
                            {fileName && (
                                <Button
                                    onClick={handleProcessImport}
                                    className={styles.buttonAction}
                                >
                                    Xử lý Import
                                </Button>
                            )}
                        </div>
                    </div>
                </>
            )}

            {(mode === "edit" || (mode === "create" && !showImportFile)) && (
                <section className={styles.container}>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Mã môn học"
                            name="subject_id"
                            value={state.subject_id}
                            onChange={inputHandle}
                            type="text"
                            required
                            disabled={mode === "edit"}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Tên môn học"
                            name="subject_name"
                            value={state.subject_name}
                            onChange={inputHandle}
                            type="text"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Số tín chỉ lý thuyết"
                            name="it_credits"
                            value={state.it_credits}
                            onChange={inputHandle}
                            type="number"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Số tín chỉ thực hành"
                            name="th_credits"
                            value={state.th_credits}
                            onChange={inputHandle}
                            type="number"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Loại môn học"
                            name="subject_type"
                            value={state.subject_type}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={subjectTypeOptions}
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Hệ số môn học"
                            name="subject_he_so"
                            value={state.subject_he_so}
                            onChange={inputHandle}
                            type="number"
                        />
                    </div>
                    <div className={styles.gridItemFull}>
                        <InputWithLabel
                            label="Mô tả môn học"
                            name="subject_description"
                            value={state.subject_description}
                            onChange={inputHandle}
                            type="textarea"
                        />
                    </div>
                </section>
            )}

            {(mode === "edit" || (mode === "create" && !showImportFile)) && (
                <div className={styles.button}>
                    <Button
                        className={styles.buttonAction}
                        onClick={handleSubmit}
                    >
                        {mode === "create" ? "Lưu" : "Cập nhật"}
                    </Button>
                </div>
            )}
        </BorderBox>
    );
};

export default CreateEditSubject;

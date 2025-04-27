import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { useEffect, useState, useRef } from "react";
import SelectWithLabel from "@/components/SelectWithLabel";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import Image from "next/image";
import AuthGuard from "@/components/AuthGuard";

type LecturerState = {
    user_id: string;
    user_name: string;
    admission_date: string;
    gender: string;
    phone_number: string;
    address: string;
    ethnic_group: string;
    date_of_birth: string;
    religion: string;
    id_number: string;
    email: string;
    place_of_birth: string;
    permanent_resident: string;
    bank: string;
    bank_account_owner: string;
    bank_account_number: string;
    major_id: string;
    academic_degree: string;
    graduated_from: string;
    position: string;
};

const majorOptions = [
    { value: "MAJ001", label: "Công nghệ phần mềm" },
    { value: "MAJ002", label: "Kỹ thuật cơ khí" },
    { value: "MAJ003", label: "Quản trị kinh doanh" },
];

const genderOptions = [
    { value: "Nam", label: "Nam" },
    { value: "Nữ", label: "Nữ" },
    { value: "Khác", label: "Khác" },
];

const CreateEditLecturer = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [showImportFile, setShowImportFile] = useState(false);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [fileName, setFileName] = useState<string | null>(null);
    const fileInputRef = useRef<HTMLInputElement | null>(null);
    const [state, setState] = useState<LecturerState>({
        user_id: "",
        user_name: "",
        admission_date: "",
        gender: genderOptions[0]?.value || "",
        phone_number: "",
        address: "",
        ethnic_group: "",
        date_of_birth: "",
        religion: "",
        id_number: "",
        email: "",
        place_of_birth: "",
        permanent_resident: "",
        bank: "",
        bank_account_owner: "",
        bank_account_number: "",
        major_id: majorOptions[0]?.value || "",
        academic_degree: "",
        graduated_from: "",
        position: "",
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
            // Call API to get lecturer data by ID
            // Replace this with your actual API call
            setState({
                user_id: "LEC001",
                user_name: "Nguyễn Văn A",
                admission_date: "2023-08-01",
                gender: "Nam",
                phone_number: "0901234567",
                address: "123 Đường ABC, Hà Nội",
                ethnic_group: "Kinh",
                date_of_birth: "1990-05-15",
                religion: "Không",
                id_number: "0123456789",
                email: "nguyenvana@example.com",
                place_of_birth: "Hà Nội",
                permanent_resident: "Hà Nội",
                bank: "Vietcombank",
                bank_account_owner: "Nguyễn Văn A",
                bank_account_number: "1234567890",
                major_id: "MAJ001",
                academic_degree: "Thạc sĩ",
                graduated_from: "Đại học Bách khoa Hà Nội",
                position: "Giảng viên",
            });
        } else {
            setMode("create");
            setShowImportFile(false);
            setState({
                user_id: "",
                user_name: "",
                admission_date: "",
                gender: genderOptions[0]?.value || "",
                phone_number: "",
                address: "",
                ethnic_group: "",
                date_of_birth: "",
                religion: "",
                id_number: "",
                email: "",
                place_of_birth: "",
                permanent_resident: "",
                bank: "",
                bank_account_owner: "",
                bank_account_number: "",
                major_id: majorOptions[0]?.value || "",
                academic_degree: "",
                graduated_from: "",
                position: "",
            });
            setSelectedFile(null);
            setFileName(null);
        }
    }, [query.mode, query.id]);

    const handleSubmit = () => {
        if (mode === "create") {
            // Call API to create lecturer with state
            console.log("Creating lecturer:", state);
        } else {
            // Call API to update lecturer with state
            console.log("Updating lecturer:", state);
        }
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
            // Implement your file processing logic here
            // After processing, you might want to redirect or update the UI
        } else {
            alert("Vui lòng chọn một file để import.");
        }
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox
                title={
                    mode === "create"
                        ? "Thêm giảng viên"
                        : "Chỉnh sửa giảng viên"
                }
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
                                alt="Picture of the author"
                                className={styles.imageTemplate}
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

                {(mode === "edit" ||
                    (mode === "create" && !showImportFile)) && (
                    <section className={styles.container}>
                        {/* Các input form giữ nguyên */}
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Mã giảng viên"
                                name="user_id"
                                value={state.user_id}
                                onChange={inputHandle}
                                type="text"
                                required
                                disabled={mode === "edit"}
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Tên giảng viên"
                                name="user_name"
                                value={state.user_name}
                                onChange={inputHandle}
                                type="text"
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <SelectWithLabel
                                label="Giới tính"
                                name="gender"
                                value={state.gender}
                                onChange={
                                    inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                                }
                                options={genderOptions}
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Ngày sinh"
                                name="date_of_birth"
                                value={state.date_of_birth}
                                onChange={inputHandle}
                                type="date"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Số điện thoại"
                                name="phone_number"
                                value={state.phone_number}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Email"
                                name="email"
                                value={state.email}
                                onChange={inputHandle}
                                type="email"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Số CMND/CCCD"
                                name="id_number"
                                value={state.id_number}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Ngày vào trường"
                                name="admission_date"
                                value={state.admission_date}
                                onChange={inputHandle}
                                type="date"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Dân tộc"
                                name="ethnic_group"
                                value={state.ethnic_group}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Tôn giáo"
                                name="religion"
                                value={state.religion}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Nơi sinh"
                                name="place_of_birth"
                                value={state.place_of_birth}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Thường trú"
                                name="permanent_resident"
                                value={state.permanent_resident}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Ngân hàng"
                                name="bank"
                                value={state.bank}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Chủ tài khoản"
                                name="bank_account_owner"
                                value={state.bank_account_owner}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Số tài khoản"
                                name="bank_account_number"
                                value={state.bank_account_number}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <SelectWithLabel
                                label="Ngành"
                                name="major_id"
                                value={state.major_id}
                                onChange={
                                    inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                                }
                                options={majorOptions}
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Học vị"
                                name="academic_degree"
                                value={state.academic_degree}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Tốt nghiệp từ"
                                name="graduated_from"
                                value={state.graduated_from}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label="Vị trí"
                                name="position"
                                value={state.position}
                                onChange={inputHandle}
                                type="text"
                            />
                        </div>
                    </section>
                )}

                {(mode === "edit" ||
                    (mode === "create" && !showImportFile)) && (
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
        </AuthGuard>
    );
};

export default CreateEditLecturer;

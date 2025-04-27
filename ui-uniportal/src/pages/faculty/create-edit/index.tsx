import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useTranslation } from "react-i18next";
import InputWithLabel from "@/components/InputWithLabel";
import { useEffect, useRef, useState } from "react";
import { FaImage } from "react-icons/fa";
import { Button } from "@/components/Button";
import { TypographyBody } from "@/components/TypographyBody";
import { useRouter } from "next/router";
import dynamic from "next/dynamic";
import AuthGuard from "@/components/AuthGuard";

const JoditEditor = dynamic(() => import("jodit-react"), {
    ssr: false,
});

type state = {
    image: string | File;
    name: string;
    description: string;
    facultyId: string;
    establishDate: string;
    website: string;
    email: string;
    phone: string;
    address: string;
};

const CreateEditFaculty = () => {
    const { t } = useTranslation();
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const editor = useRef(null);
    const [state, setState] = useState<state>({
        image: "",
        name: "",
        description: "",
        facultyId: "",
        establishDate: "",
        website: "",
        email: "",
        phone: "",
        address: "",
    });

    const [imageShow, setImageShow] = useState("");

    const inputHandle = (e: React.ChangeEvent<HTMLInputElement>) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };
    const imageHandle = (e: React.ChangeEvent<HTMLInputElement>) => {
        const files = e.target.files;

        if (files && files.length > 0) {
            const file = files[0];
            setImageShow(URL.createObjectURL(file));
            setState((prev) => ({
                ...prev,
                image: file,
            }));
        }
    };

    useEffect(() => {
        if (query.mode === "edit" && query.id) {
            setMode("edit");
            // Gọi API để lấy dữ liệu khoa dựa trên query.id
            setState({
                image: "", //api
                name: "",
                description: "",
                facultyId: "",
                establishDate: "",
                website: "",
                email: "",
                phone: "",
                address: "",
            });
        } else {
            // Trường hợp không khớp (có thể điều hướng sai)
            setMode("create");
            //set ô input rỗng
            setState({
                image: "",
                name: "",
                description: "",
                facultyId: "",
                establishDate: "",
                website: "",
                email: "",
                phone: "",
                address: "",
            });
        }
    }, [query.mode, query.id]);

    const handleSubmit = async (formData: any) => {
        if (mode === "create") {
            // Gọi API để tạo khoa mới
        } else if (mode === "edit") {
            // Gọi API để cập nhật khoa với ID từ query
        }
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox
                title={
                    mode === "create"
                        ? t("common.add-faculty")
                        : t("common.edit-faculty")
                }
            >
                <section className={styles.container}>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Mã khoa"
                            name="facultyId"
                            value={state.facultyId}
                            type="text"
                            required
                            onChange={inputHandle}
                            disabled={mode === "edit"}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Tên khoa"
                            name="name"
                            value={state.name}
                            type="text"
                            required
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Ngày thành lập"
                            name="establishDate"
                            value={state.establishDate}
                            type="date"
                            required
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Website"
                            name="website"
                            value={state.website}
                            type="url"
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Email"
                            name="email"
                            value={state.email}
                            type="email"
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Điện thoại"
                            name="phone"
                            value={state.phone}
                            type="text"
                            onChange={inputHandle}
                        />
                    </div>
                </section>
                <div className={styles.address}>
                    <InputWithLabel
                        label="Địa chỉ"
                        name="address"
                        value={state.address}
                        type="text"
                        onChange={inputHandle}
                    />
                </div>
                <div className={styles.description}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.label}
                    >
                        Mô tả
                    </TypographyBody>

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
                <div className={styles.logo}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.titleLogo}
                    >
                        {t("common.logo")} {/* Đã thêm translation */}
                    </TypographyBody>
                    <label htmlFor="image" className={styles.imageWrapper}>
                        {imageShow ? (
                            <img
                                src={imageShow}
                                alt="Preview"
                                className={styles.previewImage}
                            />
                        ) : (
                            <>
                                <span className={styles.icon}>
                                    <FaImage />
                                </span>
                                <span>Select Image</span>
                            </>
                        )}
                    </label>
                    <input
                        onChange={imageHandle}
                        type="file"
                        name="image"
                        id="image"
                        className={styles.hiddenInput}
                    />
                </div>
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

export default CreateEditFaculty;

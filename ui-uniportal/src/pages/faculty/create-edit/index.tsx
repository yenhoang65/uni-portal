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
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import { getFacultyDetail } from "@/store/reducer/facultyReducer";

const JoditEditor = dynamic(() => import("jodit-react"), {
    ssr: false,
});

type state = {
    facultyId: number | null;
    facultyName: string | null;
    facultyDateOfEstablishment: string | null;
    facultyEmail: string | null;
    facultyPhoneNumber: string | null;
    facultyAddress: string | null;
    facultyDescription: string | null;
    facultyLogo: string | null;
    facultyStatus: string | null;
};

const CreateEditFaculty = () => {
    const { t } = useTranslation();

    const dispatch = useDispatch<AppDispatch>();
    const { faculty } = useSelector((state: RootState) => state.faculty);

    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const editor = useRef(null);
    const [state, setState] = useState<state>({
        facultyId: null,
        facultyName: "",
        facultyDateOfEstablishment: "",
        facultyEmail: "",
        facultyPhoneNumber: "",
        facultyAddress: "",
        facultyDescription: "",
        facultyLogo: "",
        facultyStatus: "",
    });

    const [imageShow, setImageShow] = useState<string>("");

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
        if (query.id && typeof query.id === "string") {
            dispatch(getFacultyDetail(query.id));
        }
    }, [query.id]);

    useEffect(() => {
        if (faculty) {
            setMode("edit");
            setState({
                facultyId: faculty.facultyId,
                facultyName: faculty.facultyName,
                facultyDateOfEstablishment: faculty.facultyDateOfEstablishment,
                facultyEmail: faculty.facultyEmail,
                facultyPhoneNumber: faculty.facultyPhoneNumber,
                facultyAddress: faculty.facultyAddress,
                facultyDescription: faculty.facultyDescription,
                facultyLogo: faculty.facultyLogo,
                facultyStatus: faculty.facultyStatus,
            });

            if (faculty.facultyLogo) {
                setImageShow(faculty.facultyLogo);
            }
        } else {
            setMode("create");
            setState({
                facultyId: null,
                facultyName: "",
                facultyDateOfEstablishment: "",
                facultyEmail: "",
                facultyPhoneNumber: "",
                facultyAddress: "",
                facultyDescription: "",
                facultyLogo: "",
                facultyStatus: "",
            });
        }
    }, [faculty]);

    const handleSubmit = async (formData: any) => {
        if (mode === "create") {
            // API call to create a new faculty
        } else if (mode === "edit") {
            // API call to update the faculty with the ID from query
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
                            value={String(state.facultyId) || ""}
                            type="text"
                            required
                            onChange={inputHandle}
                            disabled={mode === "edit"}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Tên khoa"
                            name="facultyName"
                            value={state.facultyName || ""}
                            type="text"
                            required
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Ngày thành lập"
                            name="facultyDateOfEstablishment"
                            value={state.facultyDateOfEstablishment || ""}
                            type="date"
                            required
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Email"
                            name="facultyEmail"
                            value={state.facultyEmail || ""}
                            type="email"
                            onChange={inputHandle}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Điện thoại"
                            name="facultyPhoneNumber"
                            value={state.facultyPhoneNumber || ""}
                            type="text"
                            onChange={inputHandle}
                        />
                    </div>
                </section>
                <div className={styles.address}>
                    <InputWithLabel
                        label="Địa chỉ"
                        name="facultyAddress"
                        value={state.facultyAddress || ""}
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
                        value={state.facultyDescription || ""}
                        onChange={(newContent) =>
                            setState({
                                ...state,
                                facultyDescription: newContent,
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
                        {t("common.logo")}
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

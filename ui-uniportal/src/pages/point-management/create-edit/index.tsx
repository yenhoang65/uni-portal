import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import AuthGuard from "@/components/AuthGuard";
import toast from "react-hot-toast";

type State = {
    grade_type_id: string;
    code: string;
    name: string;
    coefficient: string;
};

const MOCK_GRADE_TYPES = [
    {
        grade_type_id: "1",
        code: "MID",
        name: "Giữa kỳ",
        coefficient: "0.4",
    },
    {
        grade_type_id: "2",
        code: "FINAL",
        name: "Cuối kỳ",
        coefficient: "0.6",
    },
];

const CreateEditPoint = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [gradeTypes, setGradeTypes] = useState(MOCK_GRADE_TYPES);
    const [state, setState] = useState<State>({
        grade_type_id: "",
        code: "",
        name: "",
        coefficient: "",
    });

    useEffect(() => {
        if (query.id) {
            const found = gradeTypes.find((g) => g.grade_type_id === query.id);
            if (found) {
                setMode("edit");
                setState({ ...found });
            }
        } else {
            setMode("create");
            setState({
                grade_type_id: "",
                code: "",
                name: "",
                coefficient: "",
            });
        }
        // eslint-disable-next-line
    }, [query.id]);

    const inputHandle = (e: React.ChangeEvent<HTMLInputElement>) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = () => {
        if (!state.code || !state.name || !state.coefficient) {
            toast.error("Vui lòng nhập đầy đủ thông tin bắt buộc!");
            return;
        }
        if (mode === "create") {
            setGradeTypes([
                ...gradeTypes,
                {
                    ...state,
                    grade_type_id: (
                        Math.max(
                            0,
                            ...gradeTypes.map((g) => +g.grade_type_id)
                        ) + 1
                    ).toString(),
                },
            ]);
            toast.success("Thêm loại điểm thành công!");
            router.push("/point_management");
        } else {
            setGradeTypes(
                gradeTypes.map((g) =>
                    g.grade_type_id === state.grade_type_id ? state : g
                )
            );
            toast.success("Cập nhật loại điểm thành công!");
            router.push("/point_management");
        }
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox
                title={
                    mode === "create" ? "Thêm loại điểm" : "Chỉnh sửa loại điểm"
                }
            >
                <section className={styles.container}>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Mã loại điểm"
                            name="code"
                            value={state.code}
                            onChange={inputHandle}
                            type="text"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Tên loại điểm"
                            name="name"
                            value={state.name}
                            onChange={inputHandle}
                            type="text"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Hệ số"
                            name="coefficient"
                            value={state.coefficient}
                            onChange={inputHandle}
                            type="number"
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

export default CreateEditPoint;

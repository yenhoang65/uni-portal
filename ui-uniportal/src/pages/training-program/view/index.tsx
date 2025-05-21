import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { Button } from "@/components/Button";
import { TypographyHeading } from "@/components/TypographyHeading";
import { FaMinusCircle } from "react-icons/fa";
import { IoMdAddCircle } from "react-icons/io";
import Modal from "@/components/Modal";
import SelectWithLabel from "@/components/SelectWithLabel";
import { TypographyBody } from "@/components/TypographyBody";
import AuthGuard from "@/components/AuthGuard";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";

type TrainingProgram = {
    training_program_id: string;
    specialization_id: string;
    train_code: string;
    specialization_name?: string;
};

type Subject = {
    subject_id: string;
    subject_name: string;
    subject_type: string;
    prerequisite_for: string | null;
};

type Intermediary = {
    training_program_id: string;
    subject_id: string;
};

const ViewTrainingProgram = () => {
    const dispatch = useDispatch<AppDispatch>();
    const { role } = useSelector((state: RootState) => state.auth);

    const router = useRouter();
    const { id } = router.query;

    const [intermediaryData, setIntermediaryData] = useState<Intermediary[]>(
        []
    );

    const [selectedSubjectsToAdd, setSelectedSubjectsToAdd] = useState<
        string[]
    >([]);

    const [isOpenModal, setIsOpenModal] = useState(false);

    useEffect(() => {
        if (id) {
            // Mô phỏng gọi API để lấy Training Program
        }
    }, [id]);

    const handleRemoveSubject = async (subjectIdToRemove: string) => {
        if (!id) return;

        // Mô phỏng gọi API xóa khỏi intermediary
    };

    const trainingProgram = {
        training_program_id: "TP2024-IT",
        specialization_id: "SP001",
        specialization_name: "Công nghệ thông tin",
        train_code: "2024",
    };

    const subjects = [
        {
            subject_id: "SUB001",
            subject_name: "Lập trình căn bản",
            subject_type: "Bắt buộc",
            prerequisite_for: "",
        },
        {
            subject_id: "SUB002",
            subject_name: "Cấu trúc dữ liệu và giải thuật",
            subject_type: "Bắt buộc",
            prerequisite_for: "SUB001",
        },
        {
            subject_id: "SUB003",
            subject_name: "Toán rời rạc",
            subject_type: "Bắt buộc",
            prerequisite_for: "",
        },
        {
            subject_id: "SUB004",
            subject_name: "Thiết kế Web",
            subject_type: "Tự chọn",
            prerequisite_for: "SUB001",
        },
    ];

    const allSubjects = [
        { subject_id: "SUB001", subject_name: "Lập trình căn bản" },
        {
            subject_id: "SUB002",
            subject_name: "Cấu trúc dữ liệu và giải thuật",
        },
        { subject_id: "SUB003", subject_name: "Toán rời rạc" },
        { subject_id: "SUB004", subject_name: "Thiết kế Web" },
        { subject_id: "SUB005", subject_name: "Cơ sở dữ liệu" },
        { subject_id: "SUB006", subject_name: "Mạng máy tính" },
    ];

    const availableSubjectsToAdd = allSubjects.filter(
        (subject) => !subjects.some((s) => s.subject_id === subject.subject_id)
    );

    const handleAddSelectedSubjects = () => {
        if (id && selectedSubjectsToAdd.length > 0) {
            console.log(
                "Thêm các môn học:",
                selectedSubjectsToAdd,
                "vào CTĐT:",
                id
            );
            // Gọi API để thêm các môn học đã chọn
        }
        setIsOpenModal(false);
    };

    const handleCheckboxChange = (subjectId: string, isChecked: boolean) => {
        if (isChecked) {
            setSelectedSubjectsToAdd([...selectedSubjectsToAdd, subjectId]);
        } else {
            setSelectedSubjectsToAdd(
                selectedSubjectsToAdd.filter((id) => id !== subjectId)
            );
        }
    };

    return (
        <AuthGuard allowedRoles={["admin", "student"]}>
            <BorderBox
                title={`Chương trình Đào tạo: ${trainingProgram.train_code} - ${
                    trainingProgram.specialization_name ||
                    trainingProgram.specialization_id
                }`}
            >
                <div className={styles.groupTitle}>
                    <div className={styles.group}>
                        <div>
                            <TypographyHeading tag="span" theme="lg">
                                Mã CTĐT:
                            </TypographyHeading>{" "}
                            {trainingProgram.training_program_id}
                        </div>

                        <div>
                            <TypographyHeading tag="span" theme="lg">
                                Chuyên ngành:
                            </TypographyHeading>{" "}
                            {trainingProgram.specialization_name ||
                                trainingProgram.specialization_id}
                        </div>

                        <div>
                            <TypographyHeading tag="span" theme="lg">
                                Mã khóa học:
                            </TypographyHeading>{" "}
                            {trainingProgram.train_code}
                        </div>
                    </div>

                    {role !== "student" && (
                        <div
                            className={styles.buttonAdd}
                            onClick={() => {
                                setIsOpenModal(true);
                            }}
                        >
                            <IoMdAddCircle /> Chỉnh sửa
                        </div>
                    )}
                </div>

                <div className={styles.action}>
                    <TypographyHeading
                        tag="h5"
                        theme="xl"
                        color="var(--secondary-blue)"
                        className={styles.headingSubject}
                    >
                        Các môn học trong chương trình
                    </TypographyHeading>
                    {role !== "student" && (
                        <div
                            className={styles.buttonAddSubject}
                            onClick={() => {
                                setIsOpenModal(true);
                            }}
                        >
                            Thêm môn học
                        </div>
                    )}
                </div>
                {subjects.length > 0 ? (
                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th>Mã học phần</th>
                                    <th>Tên học phần</th>
                                    <th>Loại môn học</th>
                                    <th>Môn học phụ thuộc</th>
                                    {role !== "student" && <th>Hành động</th>}
                                </tr>
                            </thead>
                            <tbody>
                                {subjects.map((subject) => (
                                    <tr key={subject.subject_id}>
                                        <td>{subject.subject_id}</td>
                                        <td>{subject.subject_name}</td>
                                        <td>{subject.subject_type}</td>
                                        <td>
                                            {subject.prerequisite_for || "-"}
                                        </td>
                                        {role !== "student" && (
                                            <td>
                                                <Button
                                                    className={
                                                        styles.actionButton
                                                    }
                                                    onClick={() => {
                                                        handleRemoveSubject(
                                                            subject.subject_id
                                                        );
                                                    }}
                                                >
                                                    <FaMinusCircle />
                                                </Button>
                                            </td>
                                        )}
                                        {/* <td>
                                            <Button
                                                className={styles.actionButton}
                                                onClick={() => {
                                                    handleRemoveSubject(
                                                        subject.subject_id
                                                    );
                                                }}
                                            >
                                                <FaMinusCircle />
                                            </Button>
                                        </td> */}
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                ) : (
                    <p className={styles.noSubjects}>
                        Chưa có môn học nào trong chương trình này.
                    </p>
                )}

                <Modal
                    isOpen={isOpenModal}
                    onClose={() => setIsOpenModal(false)}
                    title={`Thêm môn học vào CTĐT: ${trainingProgram.specialization_name}`}
                >
                    <div className={styles.checkboxTableWrapper}>
                        <TypographyBody
                            tag="span"
                            className={styles.titleModal}
                        >
                            Các môn học chưa tồn tại trong CTĐT: Công nghệ thông
                            tin
                        </TypographyBody>
                        <table className={styles.checkboxTable}>
                            <thead>
                                <tr>
                                    <th>Chọn</th>
                                    <th>Mã học phần</th>
                                    <th>Tên học phần</th>
                                </tr>
                            </thead>
                            <tbody>
                                {availableSubjectsToAdd.map((subject) => (
                                    <tr key={subject.subject_id}>
                                        <td>
                                            <input
                                                type="checkbox"
                                                value={subject.subject_id}
                                                checked={selectedSubjectsToAdd.includes(
                                                    subject.subject_id
                                                )}
                                                onChange={(e) =>
                                                    handleCheckboxChange(
                                                        subject.subject_id,
                                                        e.target.checked
                                                    )
                                                }
                                            />
                                        </td>
                                        <td>{subject.subject_id}</td>
                                        <td>{subject.subject_name}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <div className={styles.modalActions}>
                        <Button
                            onClick={handleAddSelectedSubjects}
                            disabled={selectedSubjectsToAdd.length === 0}
                        >
                            Thêm
                        </Button>
                        <Button onClick={() => setIsOpenModal(false)}>
                            Hủy
                        </Button>
                    </div>
                </Modal>
            </BorderBox>
        </AuthGuard>
    );
};

export default ViewTrainingProgram;

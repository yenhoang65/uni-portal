import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { Button } from "@/components/Button";
import { TypographyHeading } from "@/components/TypographyHeading";
import { FaMinusCircle } from "react-icons/fa";
import { IoMdAddCircle } from "react-icons/io";
import Modal from "@/components/Modal";
import ModalConfirm from "@/components/ModalConfirm";
import { TypographyBody } from "@/components/TypographyBody";
import AuthGuard from "@/components/AuthGuard";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import {
    deleteSubjectFollowTrainingProgram,
    getSubjectFollowTrainingProgram,
    messageClear,
} from "@/store/reducer/trainingProgramReducer";
import toast from "react-hot-toast";

const ViewTrainingProgram = () => {
    const dispatch = useDispatch<AppDispatch>();
    const { role } = useSelector((state: RootState) => state.auth);
    const { subjectFollowTrainingProgram, successMessage, errorMessage } =
        useSelector((state: RootState) => state.trainingProgram);

    const router = useRouter();
    const { id } = router.query;

    const [selectedSubjectsToAdd, setSelectedSubjectsToAdd] = useState<
        string[]
    >([]);
    const [isOpenModal, setIsOpenModal] = useState(false);

    // Confirm delete modal state
    const [showConfirmModal, setShowConfirmModal] = useState(false);
    const [subjectIdPendingRemove, setSubjectIdPendingRemove] = useState<
        string | null
    >(null);

    useEffect(() => {
        if (id) {
            dispatch(getSubjectFollowTrainingProgram(id));
        }
    }, [id, dispatch]);

    // Xác nhận xóa môn học
    const handleRemoveSubject = (subjectIdToRemove: string) => {
        setSubjectIdPendingRemove(subjectIdToRemove);
        setShowConfirmModal(true);
    };
    const handleConfirmRemove = () => {
        if (!id || !subjectIdPendingRemove) return;
        dispatch(
            deleteSubjectFollowTrainingProgram({
                trainingProgramId: id,
                subjectId: subjectIdPendingRemove,
            })
        );
        setShowConfirmModal(false);
        setSubjectIdPendingRemove(null);
    };
    const handleCancelRemove = () => {
        setShowConfirmModal(false);
        setSubjectIdPendingRemove(null);
    };

    // Danh sách tất cả môn học, nên lấy qua API ở thực tế
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
    // Lọc những môn chưa có trong CTĐT
    const availableSubjectsToAdd =
        subjectFollowTrainingProgram?.subjects &&
        Array.isArray(subjectFollowTrainingProgram.subjects)
            ? allSubjects.filter(
                  (subject) =>
                      !subjectFollowTrainingProgram.subjects.some(
                          (s: any) => s.subjectId === subject.subject_id
                      )
              )
            : allSubjects;

    const handleAddSelectedSubjects = () => {
        // Logic thêm môn học vào CTĐT
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

    useEffect(() => {
        if (successMessage) {
            toast.success(successMessage);
            dispatch(messageClear());
            if (id) dispatch(getSubjectFollowTrainingProgram(id));
        }
        if (errorMessage) {
            toast.error(errorMessage);
            dispatch(messageClear());
        }
    }, [successMessage, errorMessage, id, dispatch]);

    return (
        <AuthGuard allowedRoles={["admin", "student"]}>
            <BorderBox
                title={`Chương trình Đào tạo: ${
                    subjectFollowTrainingProgram?.trainingProgramId || ""
                } - ${subjectFollowTrainingProgram?.trainingProgramName || ""}`}
            >
                <div className={styles.groupTitle}>
                    <div className={styles.group}>
                        <div>
                            <TypographyHeading tag="span" theme="lg">
                                Mã CTĐT:
                            </TypographyHeading>{" "}
                            {subjectFollowTrainingProgram?.trainingProgramId}
                        </div>
                        <div>
                            <TypographyHeading tag="span" theme="lg">
                                Tên CTĐT:
                            </TypographyHeading>{" "}
                            {subjectFollowTrainingProgram?.trainingProgramName}
                        </div>
                        <div>
                            <TypographyHeading tag="span" theme="lg">
                                Năm học:
                            </TypographyHeading>{" "}
                            {subjectFollowTrainingProgram?.schoolYear}
                        </div>
                    </div>
                    {role !== "student" && (
                        <div
                            className={styles.buttonAdd}
                            onClick={() => setIsOpenModal(true)}
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
                            onClick={() => setIsOpenModal(true)}
                        >
                            Thêm môn học
                        </div>
                    )}
                </div>
                {subjectFollowTrainingProgram?.subjects?.length > 0 ? (
                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ textAlign: "center" }}>
                                        Mã học phần
                                    </th>
                                    <th>Tên học phần</th>
                                    <th style={{ textAlign: "center" }}>
                                        Môn học phụ thuộc
                                    </th>
                                    {role !== "student" && (
                                        <th style={{ textAlign: "center" }}>
                                            Hành động
                                        </th>
                                    )}
                                </tr>
                            </thead>
                            <tbody>
                                {subjectFollowTrainingProgram.subjects.map(
                                    (subject: any) => (
                                        <tr key={subject.subjectId}>
                                            <td style={{ textAlign: "center" }}>
                                                {subject.subjectId}
                                            </td>
                                            <td>{subject.subjectName}</td>
                                            <td style={{ textAlign: "center" }}>
                                                {subject.prerequisiteFor
                                                    ?.length > 0
                                                    ? subject.prerequisiteFor.map(
                                                          (
                                                              pre: any,
                                                              idx: number
                                                          ) => (
                                                              <span
                                                                  key={
                                                                      pre.subjectId
                                                                  }
                                                              >
                                                                  {
                                                                      pre.subjectName
                                                                  }
                                                                  {idx <
                                                                  subject
                                                                      .prerequisiteFor
                                                                      .length -
                                                                      1
                                                                      ? ", "
                                                                      : ""}
                                                              </span>
                                                          )
                                                      )
                                                    : "—"}
                                            </td>
                                            {role !== "student" && (
                                                <td
                                                    style={{
                                                        textAlign: "center",
                                                    }}
                                                >
                                                    <Button
                                                        className={
                                                            styles.actionButton
                                                        }
                                                        onClick={() =>
                                                            handleRemoveSubject(
                                                                subject.subjectId
                                                            )
                                                        }
                                                    >
                                                        <FaMinusCircle />
                                                    </Button>
                                                </td>
                                            )}
                                        </tr>
                                    )
                                )}
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
                    title={`Thêm môn học vào CTĐT: ${
                        subjectFollowTrainingProgram?.trainingProgramId || ""
                    } - ${
                        subjectFollowTrainingProgram?.trainingProgramName || ""
                    }`}
                >
                    <div className={styles.checkboxTableWrapper}>
                        <TypographyBody
                            tag="span"
                            className={styles.titleModal}
                        >
                            Các môn học chưa tồn tại trong CTĐT:{" "}
                            {subjectFollowTrainingProgram?.trainingProgramId} -{" "}
                            {subjectFollowTrainingProgram?.trainingProgramName}
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
                {showConfirmModal && (
                    <ModalConfirm
                        message="Bạn sẽ xóa môn học này khỏi chương trình đào tạo. Hành động này không thể hoàn tác!"
                        confirmText="xóa môn học khỏi CTĐT"
                        buttonText="Xóa"
                        onConfirm={handleConfirmRemove}
                        onCancel={handleCancelRemove}
                    />
                )}
            </BorderBox>
        </AuthGuard>
    );
};

export default ViewTrainingProgram;

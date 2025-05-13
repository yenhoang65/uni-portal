import React, { useState, useEffect } from "react";
import BorderBox from "@/components/BorderBox";
import InputWithLabel from "@/components/InputWithLabel";
import { Button } from "@/components/Button";
import styles from "./styles.module.css";
import { TypographyBody } from "@/components/TypographyBody";
import AuthGuard from "@/components/AuthGuard";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import {
    createActiveTimeRegisStu,
    deleteActiveTimeRegisStu,
    getListActiveTimeStudent,
} from "@/store/reducer/activateTimeReducer";
import SelectWithLabel from "@/components/SelectWithLabel";
import toast from "react-hot-toast";
import { messageClear } from "@/store/reducer/activateTimeReducer";
import clsx from "clsx";
import Link from "next/link";
import { MdDeleteForever } from "react-icons/md";
import ModalConfirm from "@/components/ModalConfirm";

type ActivateTime = {
    schoolYear: number;
    semester: number;
    startDate: Date;
    endDate: Date;
    // status: string | null;
};
const RegistrationTimeActivation = () => {
    const dispatch = useDispatch<AppDispatch>();

    const {
        activeTimeStudents,
        activeTimeStudent,
        successMessage,
        errorMessage,
    } = useSelector((state: RootState) => state.activateTime);

    const [state, setState] = useState<ActivateTime>({
        schoolYear: new Date(Date.now()).getFullYear(),
        semester: 0,
        startDate: new Date(new Date().setDate(new Date().getDate() + 3)),
        endDate: new Date(new Date().setDate(new Date().getDate() + 7)),
        // status: null,
    });

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteActiveTimeId, setDeleteActiveTimeId] = useState<number | null>(
        null
    );

    const inputHandle = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
    ) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };

    useEffect(() => {
        dispatch(getListActiveTimeStudent());
    }, []);

    const handleActivateTime = () => {
        const obj = {
            schoolYear: state.schoolYear,
            semester: state.semester,
            startDate: state.startDate,
            endDate: state.endDate,
        };
        dispatch(createActiveTimeRegisStu({ dto: obj }));
    };

    const handleDelete = () => {
        if (deleteActiveTimeId) {
            dispatch(deleteActiveTimeRegisStu(deleteActiveTimeId));
            setIsModalOpen(false);
            setDeleteActiveTimeId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteActiveTimeId(null);
    };

    useEffect(() => {
        if (successMessage) {
            toast.success(successMessage);
            dispatch(messageClear());

            dispatch(getListActiveTimeStudent());
        }
        if (errorMessage) {
            toast.error(errorMessage);
            dispatch(messageClear());
        }
    }, [successMessage, errorMessage]);

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title="Kích hoạt thời gian đăng ký tín chỉ">
                <div className={styles.activationForm}>
                    <InputWithLabel
                        label="Ngày giờ Bắt đầu"
                        type="datetime-local"
                        name="startDate"
                        value={state.startDate.toISOString().slice(0, 16)}
                        onChange={inputHandle}
                        required
                    />
                    <InputWithLabel
                        label="Ngày giờ Kết thúc"
                        type="datetime-local"
                        name="endDate"
                        value={state.endDate.toISOString().slice(0, 16)}
                        onChange={inputHandle}
                        required
                    />
                    <InputWithLabel
                        label="Kỳ học"
                        type="number"
                        name="semester"
                        value={String(state.semester)}
                        onChange={inputHandle}
                        required
                    />
                    <InputWithLabel
                        label="Năm học"
                        type="number"
                        name="schoolYear"
                        value={String(state.schoolYear)}
                        onChange={inputHandle}
                        required
                    />

                    <Button
                        onClick={handleActivateTime}
                        className={styles.activateButton}
                    >
                        Kích hoạt
                    </Button>
                </div>

                <div className={styles.activatedList}>
                    <TypographyBody
                        tag="span"
                        theme="md-bold"
                        className={styles.listTitle}
                    >
                        Danh sách Thời gian đã kích hoạt
                    </TypographyBody>
                    {activeTimeStudents.length > 0 ? (
                        <table className={styles.activatedTable}>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Thời gian Bắt đầu</th>
                                    <th>Thời gian Kết thúc</th>
                                    <th>Kỳ học - Năm học</th>
                                    <th>Trạng thái</th>
                                    <th style={{ minWidth: "70px" }}>
                                        Hành động
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {activeTimeStudents.map((time) => (
                                    <tr key={time.id}>
                                        <td>{time.id}</td>
                                        <td>
                                            {new Date(
                                                time.startDate
                                            ).toLocaleString()}
                                        </td>
                                        <td>
                                            {new Date(
                                                time.endDate
                                            ).toLocaleString()}
                                        </td>

                                        <td>
                                            {`${time.semester} - ${time.schoolYear}`}
                                        </td>
                                        <td>{time.status}</td>
                                        <td
                                            className={clsx(
                                                styles.buttonAction,
                                                styles.tableCell
                                            )}
                                        >
                                            <Link
                                                onClick={(e) => {
                                                    e.preventDefault();
                                                    setIsModalOpen(true);
                                                    setDeleteActiveTimeId(
                                                        time.id
                                                    );
                                                }}
                                                href="#"
                                                className={clsx(
                                                    styles.viewButton,
                                                    styles.viewButtonDelete
                                                )}
                                            >
                                                <MdDeleteForever />
                                            </Link>

                                            {isModalOpen &&
                                                deleteActiveTimeId ===
                                                    time.id && (
                                                    <ModalConfirm
                                                        message="Bạn có chắc chắn muốn xóa khoảng thời gian kích hoạt này?"
                                                        onConfirm={handleDelete}
                                                        onCancel={handleCancel}
                                                    />
                                                )}
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    ) : (
                        <TypographyBody
                            tag="span"
                            theme="sm"
                            className={styles.noData}
                        >
                            Chưa có thời gian nào được kích hoạt.
                        </TypographyBody>
                    )}
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default RegistrationTimeActivation;

import React from "react";
import styles from "./styles.module.css";
import { FaTimesCircle, FaCheckCircle } from "react-icons/fa";
import { BsCheckCircleFill } from "react-icons/bs";

type ModalConfirmProps = {
    message?: React.ReactNode;
    confirmText?: string;
    buttonText?: string;
    onConfirm: () => void;
    onCancel: () => void;
};

const ModalConfirm: React.FC<ModalConfirmProps> = ({
    message,
    onConfirm,
    onCancel,
    confirmText = "hủy lớp học phần",
    buttonText = "Đồng ý",
}) => {
    return (
        <div className={styles.overlay}>
            <div className={styles.modal} onClick={(e) => e.stopPropagation()}>
                <div className={styles.iconWrapper}>
                    <BsCheckCircleFill className={styles.icon} />
                </div>
                <h2 className={styles.title}>
                    Bạn chắc chắn muốn {confirmText}?
                </h2>
                <p className={styles.message}>
                    {message || "Chọn hủy để quay lại"}
                </p>
                <div className={styles.actions}>
                    <button className={styles.cancelButton} onClick={onCancel}>
                        <FaTimesCircle className={styles.buttonIcon} />
                        Hủy
                    </button>
                    <button
                        className={styles.confirmButton}
                        onClick={onConfirm}
                    >
                        <FaCheckCircle className={styles.buttonIcon} />
                        {buttonText}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ModalConfirm;

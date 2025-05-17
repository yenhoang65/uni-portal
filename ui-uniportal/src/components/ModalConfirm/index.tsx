import React from "react";
import styles from "./styles.module.css";
import { FaTrash } from "react-icons/fa";

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
    confirmText = "Delete",
    buttonText = "Delete",
}) => {
    return (
        <div className={styles.overlay}>
            <div
                className={styles.modal}
                onClick={(e) => e.stopPropagation()} // Ngăn sự kiện click overlay lọt vào modal
            >
                <div className={styles.iconWrapper}>
                    <FaTrash className={styles.icon} />
                </div>
                <h2 className={styles.title}>Confirm {confirmText}</h2>
                <p className={styles.message}>
                    {message ||
                        "Are you sure you want to delete this item? This action cannot be undone."}
                </p>
                <div className={styles.actions}>
                    <button className={styles.cancelButton} onClick={onCancel}>
                        Cancel
                    </button>
                    <button
                        className={styles.confirmButton}
                        onClick={onConfirm}
                    >
                        {buttonText}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ModalConfirm;

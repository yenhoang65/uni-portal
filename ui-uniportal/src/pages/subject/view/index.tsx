import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";
import AuthGuard from "@/components/AuthGuard";

type SubjectDetailType = {
    subject_id: string;
    subject_name: string;
    it_credits: number;
    th_credits: number;
    subject_description?: string;
    subject_type?: string;
    subject_he_so?: number;
};

const SubjectDetail = () => {
    const router = useRouter();
    const { query } = router;
    const { id } = query;
    const [subject, setSubject] = useState<SubjectDetailType | null>(null);

    const dummySubject: SubjectDetailType = {
        subject_id: "SUB001",
        subject_name: "Nhập môn Lập trình",
        it_credits: 3,
        th_credits: 2,
        subject_description: "Môn học cơ bản về lập trình.",
        subject_type: "Bắt buộc",
        subject_he_so: 1,
    };

    useEffect(() => {
        // Trong thực tế, bạn sẽ gọi API ở đây để lấy thông tin chi tiết của môn học dựa trên 'id'
        if (id) {
            setSubject(dummySubject);
        }
    }, [id]);

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title={`Chi tiết môn học: ${dummySubject.subject_name}`}>
                <div className={styles.detailGrid}>
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Mã môn học:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummySubject.subject_id}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItemFull}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Tên môn học:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummySubject.subject_name}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Số tín chỉ lý thuyết:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummySubject.it_credits}
                        </TypographyBody>
                    </div>

                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Số tín chỉ thực hành:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummySubject.th_credits}
                        </TypographyBody>
                    </div>

                    {dummySubject.subject_type && (
                        <div className={styles.detailItem}>
                            <TypographyBody
                                tag="span"
                                className={styles.detailLabel}
                            >
                                Loại môn học:
                            </TypographyBody>
                            <TypographyBody tag="span">
                                {dummySubject.subject_type}
                            </TypographyBody>
                        </div>
                    )}

                    {dummySubject.subject_he_so !== undefined && (
                        <div className={styles.detailItem}>
                            <TypographyBody
                                tag="span"
                                className={styles.detailLabel}
                            >
                                Hệ số môn học:
                            </TypographyBody>
                            <TypographyBody tag="span">
                                {dummySubject.subject_he_so}
                            </TypographyBody>
                        </div>
                    )}

                    {dummySubject.subject_description && (
                        <div className={styles.detailItemFull}>
                            <TypographyBody
                                tag="span"
                                className={styles.detailLabel}
                            >
                                Mô tả:
                            </TypographyBody>
                            <TypographyBody tag="span">
                                {dummySubject.subject_description}
                            </TypographyBody>
                        </div>
                    )}

                    <div className={styles.buttonGroup}>
                        <div
                            className={styles.backButton}
                            onClick={() => router.back()}
                        >
                            <IoIosArrowBack className={styles.backIcon} />
                            <span className={styles.backText}>Quay lại</span>
                        </div>
                        <button
                            className={styles.editButton}
                            onClick={() =>
                                router.push(
                                    `/subject/create-edit?id=${dummySubject.subject_id}&mode=edit`
                                )
                            }
                        >
                            <AiFillEdit /> Chỉnh sửa
                        </button>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default SubjectDetail;

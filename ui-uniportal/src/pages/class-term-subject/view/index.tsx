import Link from "next/link";
import styles from "./styles.module.css";
import BorderBox from "@/components/BorderBox";
import { TypographyHeading } from "@/components/TypographyHeading";
import AuthGuard from "@/components/AuthGuard";

const ClassTermSubjectDetailPage: React.FC = () => {
    const classTermSubject = {
        term_class_id: 456,
        class_name: 101,
        progress: 80,
        semester: 1,
        school_year: 2024,
    };

    const {
        term_class_id: id,
        class_name,
        progress,
        semester,
        school_year,
    } = classTermSubject;

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title={`Chi tiết Học kỳ - Môn học (ID: ${id})`}>
                <header className={styles.detailHeader}>
                    <div className={styles.titleInfo}>
                        <TypographyHeading
                            tag="h1"
                            theme="2xl"
                            className={styles.description}
                            color="var(--secondary-blue)"
                        >
                            Thông tin Học kỳ - Môn học
                        </TypographyHeading>
                        <p className={styles.detailId}>ID: {id}</p>
                    </div>
                </header>

                <section className={styles.detailOverview}>
                    <TypographyHeading
                        tag="h5"
                        theme="xl"
                        className={styles.sectionTitle}
                        color="var(--secondary-blue)"
                    >
                        Thông tin chung
                    </TypographyHeading>
                    <div className={styles.infoGrid}>
                        {class_name && (
                            <div className={styles.infoItem}>
                                <strong>Mã lớp:</strong> {class_name}
                            </div>
                        )}
                        {progress !== undefined && (
                            <div className={styles.infoItem}>
                                <strong>Tiến độ:</strong> {progress}%
                            </div>
                        )}
                        {semester !== undefined && (
                            <div className={styles.infoItem}>
                                <strong>Học kỳ:</strong> {semester}
                            </div>
                        )}
                        {school_year !== undefined && (
                            <div className={styles.infoItem}>
                                <strong>Năm học:</strong> {school_year}
                            </div>
                        )}
                    </div>
                </section>

                <div className={styles.actionButtons}>
                    <Link
                        href={`/class-term-subject/edit?id=${id}`}
                        className={styles.editButton}
                    >
                        Chỉnh sửa
                    </Link>
                    <Link
                        href="/class-term-subject"
                        className={styles.backButton}
                    >
                        Quay lại
                    </Link>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ClassTermSubjectDetailPage;

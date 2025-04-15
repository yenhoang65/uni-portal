import Image from "next/image";
import Link from "next/link";
import styles from "./styles.module.css"; // Import CSS Module
import BorderBox from "@/components/BorderBox";
import { TypographyHeading } from "@/components/TypographyHeading";

const FacultyDetailPage: React.FC = () => {
    // Dữ liệu khoa mẫu (thay thế bằng dữ liệu thực tế sau này)
    const faculty = {
        id_khoa: 123,
        ten_khoa: "Khoa Công nghệ Thông tin",
        ngay_thanh_lap: "20/05/2005",
        website: "https://fit.example.edu.vn",
        email: "fit@example.edu.vn",
        dien_thoai: "024 123 4567",
        dia_chi: "Tòa nhà A1, Trường Đại học XYZ, Hà Nội",
        mo_ta: "Khoa Công nghệ Thông tin là một trong những khoa hàng đầu của trường, chuyên đào tạo các chuyên gia trong lĩnh vực phát triển phần mềm, khoa học máy tính, an ninh mạng và trí tuệ nhân tạo. Khoa có đội ngũ giảng viên giàu kinh nghiệm và cơ sở vật chất hiện đại...",
        logo: "/images/fit_logo.png", // Đường dẫn tương đối đến thư mục public
    };

    const {
        id_khoa,
        ten_khoa,
        ngay_thanh_lap,
        website,
        email,
        dien_thoai,
        dia_chi,
        mo_ta,
        logo,
    } = faculty;

    return (
        <BorderBox title={`Chi tiết khoa ${ten_khoa}`}>
            <header className={styles.facultyHeader}>
                {logo && (
                    <div className={styles.logoContainer}>
                        <Image
                            src={require("./assets/logo.jpg")}
                            alt={`Logo của khoa ${ten_khoa}`}
                            width={200}
                            height={200}
                            objectFit="contain"
                        />
                    </div>
                )}
                <div className={styles.titleInfo}>
                    <TypographyHeading
                        tag="h1"
                        theme="2xl"
                        className={styles.description}
                        color="var(--secondary-blue)"
                    >
                        {ten_khoa}
                    </TypographyHeading>
                    <p className={styles.facultyId}>Mã khoa: {id_khoa}</p>
                </div>
            </header>

            <section className={styles.facultyOverview}>
                <TypographyHeading
                    tag="h5"
                    theme="2xl"
                    className={styles.description}
                    color="var(--secondary-blue)"
                >
                    Thông tin chung
                </TypographyHeading>
                <div className={styles.infoGrid}>
                    {ngay_thanh_lap && (
                        <div className={styles.infoItem}>
                            <strong>Ngày thành lập:</strong> {ngay_thanh_lap}
                        </div>
                    )}
                    {website && (
                        <div className={styles.infoItem}>
                            <strong>Website:</strong>{" "}
                            <Link
                                href={website}
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                {website}
                            </Link>
                        </div>
                    )}
                    {email && (
                        <div className={styles.infoItem}>
                            <strong>Email:</strong>{" "}
                            <a href={`mailto:${email}`}>{email}</a>
                        </div>
                    )}
                    {dien_thoai && (
                        <div className={styles.infoItem}>
                            <strong>Điện thoại:</strong>{" "}
                            <a href={`tel:${dien_thoai}`}>{dien_thoai}</a>
                        </div>
                    )}
                </div>
                {dia_chi && (
                    <div className={styles.infoItemAddress}>
                        <strong>Địa chỉ:</strong> {dia_chi}
                    </div>
                )}
            </section>

            {mo_ta && (
                <section className={styles.facultyDescription}>
                    <TypographyHeading
                        tag="h5"
                        theme="2xl"
                        className={styles.description}
                        color="var(--secondary-blue)"
                    >
                        Mô tả
                    </TypographyHeading>
                    <p className={styles.desc}>{mo_ta}</p>
                </section>
            )}
        </BorderBox>
    );
};

export default FacultyDetailPage;

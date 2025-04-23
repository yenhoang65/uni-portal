import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";

type LecturerDetailType = {
    user_id: string;
    user_name: string;
    password?: string; // Chỉ nên hiển thị nếu cần và có biện pháp bảo mật
    admission_date?: string;
    gender?: string;
    phone_number?: string;
    address?: string;
    ethnic_group?: string;
    date_of_birth?: string;
    religion?: string;
    id_number?: string;
    email?: string;
    place_of_birth?: string;
    permanent_resident?: string;
    bank?: string;
    bank_account_owner?: string;
    bank_account_number?: string;
    role?: string;
    major_id?: string;
    major_name?: string; // Tên ngành (nếu có liên kết)
    academic_degree?: string;
    graduated_from?: string;
    position?: string;
};

const LecturerDetail = () => {
    const router = useRouter();
    const { query } = router;
    const { id } = query;
    const [lecturer, setLecturer] = useState<LecturerDetailType | null>(null);

    // Giả định dữ liệu tĩnh cho mục đích demo
    // Trong thực tế, bạn sẽ fetch dữ liệu này dựa trên 'id' từ API
    const dummyLecturer: LecturerDetailType = {
        user_id: "LEC001",
        user_name: "Nguyễn Văn A",
        admission_date: "2023-08-01",
        gender: "Nam",
        phone_number: "0901234567",
        address: "123 Đường ABC, Hà Nội",
        ethnic_group: "Kinh",
        date_of_birth: "1990-05-15",
        religion: "Không",
        id_number: "0123456789",
        email: "nguyenvana@example.com",
        place_of_birth: "Hà Nội",
        permanent_resident: "Hà Nội",
        bank: "Vietcombank",
        bank_account_owner: "Nguyễn Văn A",
        bank_account_number: "1234567890",
        role: "Giảng viên",
        major_id: "MAJ001",
        major_name: "Công nghệ phần mềm",
        academic_degree: "Thạc sĩ",
        graduated_from: "Đại học Bách khoa Hà Nội",
        position: "Giảng viên",
    };

    useEffect(() => {
        // Trong thực tế, bạn sẽ gọi API ở đây để lấy thông tin chi tiết của giảng viên dựa trên 'id'
        if (id) {
            // Ví dụ:
            // fetch(`/api/lecturers/${id}`)
            //   .then(res => res.json())
            //   .then(data => setLecturer(data))
            //   .catch(error => console.error("Error fetching lecturer:", error));

            // Sử dụng dữ liệu giả định cho demo
            setLecturer(dummyLecturer);
        }
    }, [id]);

    return (
        <BorderBox title={`Chi tiết giảng viên: ${dummyLecturer.user_name}`}>
            <div className={styles.detailGrid}>
                <div className={styles.detailItem}>
                    <TypographyBody tag="span" className={styles.detailLabel}>
                        Mã giảng viên:
                    </TypographyBody>
                    <TypographyBody tag="span">
                        {dummyLecturer.user_id}
                    </TypographyBody>
                </div>

                <div className={styles.detailItem}>
                    <TypographyBody tag="span" className={styles.detailLabel}>
                        Tên giảng viên:
                    </TypographyBody>
                    <TypographyBody tag="span">
                        {dummyLecturer.user_name}
                    </TypographyBody>
                </div>

                {dummyLecturer.major_name && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Tên ngành:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.major_name}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.position && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Vị trí:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.position}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.phone_number && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Số điện thoại:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.phone_number}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.email && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Email:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.email}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.gender && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Giới tính:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.gender}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.date_of_birth && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Ngày sinh:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.date_of_birth}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.address && (
                    <div className={styles.detailItemFull}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Địa chỉ:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.address}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.admission_date && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Ngày vào trường:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.admission_date}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.ethnic_group && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Dân tộc:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.ethnic_group}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.religion && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Tôn giáo:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.religion}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.id_number && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Số CMND/CCCD:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.id_number}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.place_of_birth && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Nơi sinh:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.place_of_birth}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.permanent_resident && (
                    <div className={styles.detailItemFull}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Thường trú:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.permanent_resident}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.bank && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Ngân hàng:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.bank}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.bank_account_owner && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Chủ tài khoản:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.bank_account_owner}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.bank_account_number && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Số tài khoản:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.bank_account_number}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.academic_degree && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Học vị:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.academic_degree}
                        </TypographyBody>
                    </div>
                )}

                {dummyLecturer.graduated_from && (
                    <div className={styles.detailItemFull}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Tốt nghiệp từ:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyLecturer.graduated_from}
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
                                `/lecturer/create-edit?id=${dummyLecturer.user_id}&mode=edit`
                            )
                        }
                    >
                        <AiFillEdit /> Chỉnh sửa
                    </button>
                </div>
            </div>
        </BorderBox>
    );
};

export default LecturerDetail;

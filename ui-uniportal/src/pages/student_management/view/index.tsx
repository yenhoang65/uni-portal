import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";

type StudentDetailType = {
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
    education_level?: string;
    specialization_id?: string;
    specialization_name?: string; // Tên chuyên ngành (nếu có liên kết)
    type_of_training?: string;
    class_id?: string;
};

const StudentDetail = () => {
    const router = useRouter();
    const { query } = router;
    const { id } = query;
    const [student, setStudent] = useState<StudentDetailType | null>(null);

    // Giả định dữ liệu tĩnh cho mục đích demo
    // Trong thực tế, bạn sẽ fetch dữ liệu này dựa trên 'id' từ API
    const dummyStudent: StudentDetailType = {
        user_id: "STU001",
        user_name: "Nguyễn Thị A",
        admission_date: "2024-09-05",
        gender: "Nữ",
        phone_number: "0912345678",
        address: "456 Đường XYZ, Hà Nội",
        ethnic_group: "Kinh",
        date_of_birth: "2002-11-20",
        religion: "Không",
        id_number: "9876543210",
        email: "nguyentha@example.com",
        place_of_birth: "Hà Nam",
        permanent_resident: "Hà Nội",
        bank: "BIDV",
        bank_account_owner: "Nguyễn Thị A",
        bank_account_number: "0987654321",
        role: "Sinh viên",
        education_level: "Đại học",
        specialization_id: "SE",
        specialization_name: "Kỹ thuật phần mềm",
        type_of_training: "Chính quy",
        class_id: "DHCNTT1A",
    };

    useEffect(() => {
        // Trong thực tế, bạn sẽ gọi API ở đây để lấy thông tin chi tiết của sinh viên dựa trên 'id'
        if (id) {
            // Sử dụng dữ liệu giả định cho demo
            setStudent(dummyStudent);
        }
    }, [id]);

    return (
        <BorderBox title={`Chi tiết sinh viên: ${dummyStudent.user_name}`}>
            <div className={styles.detailGrid}>
                <div className={styles.detailItem}>
                    <TypographyBody tag="span" className={styles.detailLabel}>
                        Mã sinh viên:
                    </TypographyBody>
                    <TypographyBody tag="span">
                        {dummyStudent.user_id}
                    </TypographyBody>
                </div>

                <div className={styles.detailItem}>
                    <TypographyBody tag="span" className={styles.detailLabel}>
                        Tên sinh viên:
                    </TypographyBody>
                    <TypographyBody tag="span">
                        {dummyStudent.user_name}
                    </TypographyBody>
                </div>

                {dummyStudent.class_id && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Lớp:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.class_id}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.specialization_name && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Chuyên ngành:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.specialization_name}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.gender && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Giới tính:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.gender}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.date_of_birth && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Ngày sinh:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.date_of_birth}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.phone_number && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Số điện thoại:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.phone_number}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.email && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Email:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.email}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.address && (
                    <div className={styles.detailItemFull}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Địa chỉ:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.address}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.admission_date && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Ngày nhập học:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.admission_date}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.ethnic_group && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Dân tộc:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.ethnic_group}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.religion && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Tôn giáo:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.religion}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.id_number && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Số CMND/CCCD:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.id_number}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.place_of_birth && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Nơi sinh:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.place_of_birth}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.permanent_resident && (
                    <div className={styles.detailItemFull}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Thường trú:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.permanent_resident}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.bank && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Ngân hàng:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.bank}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.bank_account_owner && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Chủ tài khoản:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.bank_account_owner}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.bank_account_number && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Số tài khoản:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.bank_account_number}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.education_level && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Trình độ học vấn:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.education_level}
                        </TypographyBody>
                    </div>
                )}

                {dummyStudent.type_of_training && (
                    <div className={styles.detailItem}>
                        <TypographyBody
                            tag="span"
                            className={styles.detailLabel}
                        >
                            Hình thức đào tạo:
                        </TypographyBody>
                        <TypographyBody tag="span">
                            {dummyStudent.type_of_training}
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
                                `/student_management/create-edit?id=${dummyStudent.user_id}&mode=edit`
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

export default StudentDetail;

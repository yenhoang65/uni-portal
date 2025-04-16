import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useState } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { FaEye } from "react-icons/fa";
import { AiFillEdit } from "react-icons/ai";
import { MdDeleteForever } from "react-icons/md";
import clsx from "clsx";
import { IoMdAddCircle } from "react-icons/io";
import ModalConfirm from "@/components/ModalConfirm";

// Dữ liệu giảng viên mẫu (thay thế bằng API call thực tế)
const lecturersData = [
    {
        user_id: "LEC001",
        user_name: "Nguyễn Văn A",
        major_id: "MAJ001",
        major_name: "Công nghệ phần mềm",
        position: "Giảng viên",
        phone_number: "0901234567",
        // Các trường khác không hiển thị
    },
    {
        user_id: "LEC002",
        user_name: "Trần Thị B",
        major_id: "MAJ002",
        major_name: "Kỹ thuật cơ khí",
        position: "Phó Giáo sư",
        phone_number: "0987654321",
        // Các trường khác không hiển thị
    },
    {
        user_id: "LEC003",
        user_name: "Lê Văn C",
        major_id: "MAJ001",
        major_name: "Công nghệ phần mềm",
        position: "Trợ giảng",
        phone_number: "0911223344",
        // Các trường khác không hiển thị
    },
    // Thêm dữ liệu giảng viên khác
];

type Lecturer = {
    user_id: string;
    user_name: string;
    major_id: string;
    major_name: string;
    position: string;
    phone_number: string;
    // Thêm các trường khác nếu cần cho các chức năng khác
};

const LecturerManagement = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteLecturerId, setDeleteLecturerId] = useState<string | null>(
        null
    );
    const [lecturers, setLecturers] = useState<Lecturer[]>(lecturersData); // State để quản lý danh sách giảng viên (có thể lọc/phân trang)

    const handleDelete = () => {
        if (deleteLecturerId) {
            // Gọi API để xóa giảng viên dựa trên deleteLecturerId
            console.log("Deleting lecturer with ID:", deleteLecturerId);
            // Sau khi xóa thành công, cập nhật lại danh sách giảng viên (ví dụ: fetch lại data hoặc lọc state hiện tại)
            setIsModalOpen(false);
            setDeleteLecturerId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteLecturerId(null);
    };

    // Logic để lọc giảng viên dựa trên searchValue (nếu cần)
    const filteredLecturers = lecturers.filter(
        (lecturer) =>
            lecturer.user_name
                .toLowerCase()
                .includes(searchValue.toLowerCase()) ||
            lecturer.user_id
                .toLowerCase()
                .includes(searchValue.toLowerCase()) ||
            lecturer.major_name
                .toLowerCase()
                .includes(searchValue.toLowerCase()) ||
            lecturer.position
                .toLowerCase()
                .includes(searchValue.toLowerCase()) ||
            lecturer.phone_number
                .toLowerCase()
                .includes(searchValue.toLowerCase())
    );

    // Logic để phân trang
    const startIndex = (currentPage - 1) * parPage;
    const endIndex = startIndex + parPage;
    const currentLecturers = filteredLecturers.slice(startIndex, endIndex);

    return (
        <BorderBox title="Quản lý giảng viên">
            <div className={styles.box}>
                <div className={styles.add}>
                    <Search
                        setParPage={setParPage}
                        setSearchValue={setSearchValue}
                        searchValue={searchValue}
                    />

                    <Link
                        href="/lecturer/create-edit" // Đường dẫn đến trang thêm mới giảng viên
                        className={styles.buttonAdd}
                    >
                        <IoMdAddCircle /> Thêm mới
                    </Link>
                </div>

                <div className={styles.tableWrapper}>
                    <table className={styles.table}>
                        <thead className={styles.thead}>
                            <tr>
                                <th style={{ minWidth: "100px" }}>Mã GV</th>
                                <th style={{ minWidth: "200px" }}>Tên GV</th>
                                <th style={{ minWidth: "250px" }}>Tên ngành</th>
                                <th style={{ minWidth: "150px" }}>Vị trí</th>
                                <th style={{ minWidth: "150px" }}>
                                    Số điện thoại
                                </th>
                                <th style={{ minWidth: "120px" }}>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            {currentLecturers.map((lecturer) => (
                                <tr key={lecturer.user_id}>
                                    <td>{lecturer.user_id}</td>
                                    <td>{lecturer.user_name}</td>
                                    <td>{lecturer.major_name}</td>
                                    <td>{lecturer.position}</td>
                                    <td>{lecturer.phone_number}</td>
                                    <td className={styles.buttonAction}>
                                        <Link
                                            href={`/lecturer/view?id=${lecturer.user_id}`} // Đường dẫn xem chi tiết
                                            className={clsx(styles.viewButton)}
                                        >
                                            <FaEye />
                                        </Link>
                                        <Link
                                            href={`/lecturer/create-edit?id=${lecturer.user_id}&mode=edit`} // Đường dẫn chỉnh sửa
                                            className={clsx(
                                                styles.viewButton,
                                                styles.viewButtonUpdate
                                            )}
                                        >
                                            <AiFillEdit />
                                        </Link>
                                        <Link
                                            href="#"
                                            onClick={(e) => {
                                                e.preventDefault();
                                                setIsModalOpen(true);
                                                setDeleteLecturerId(
                                                    lecturer.user_id
                                                );
                                            }}
                                            className={clsx(
                                                styles.viewButton,
                                                styles.viewButtonDelete
                                            )}
                                        >
                                            <MdDeleteForever />
                                        </Link>

                                        {isModalOpen &&
                                            deleteLecturerId ===
                                                lecturer.user_id && (
                                                <ModalConfirm
                                                    message="Bạn có chắc chắn muốn xóa giảng viên này?"
                                                    onConfirm={handleDelete}
                                                    onCancel={handleCancel}
                                                />
                                            )}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

                <div className={styles.paginationWrapper}>
                    <Pagination
                        pageNumber={currentPage}
                        setPageNumber={setCurrentPage}
                        totalItem={filteredLecturers.length}
                        parPage={parPage}
                        showItem={3}
                    />
                </div>
            </div>
        </BorderBox>
    );
};

export default LecturerManagement;

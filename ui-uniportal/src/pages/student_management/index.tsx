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
import SelectWithLabel from "@/components/SelectWithLabel";
import AuthGuard from "@/components/AuthGuard";

const studentsData = [
    {
        user_id: "STU001",
        user_name: "Nguyễn Thị A",
        class_id: "DHCNTT1A",
        specialization_id: "SE",
        specialization_name: "Kỹ thuật phần mềm",
    },
    {
        user_id: "STU002",
        user_name: "Lê Văn B",
        class_id: "DHCB2B",
        specialization_id: "ME",
        specialization_name: "Cơ điện tử",
    },
    {
        user_id: "STU003",
        user_name: "Phạm Thu C",
        class_id: "DHQTKD3C",
        specialization_id: "BA",
        specialization_name: "Quản trị kinh doanh",
    },
];

const classOptionsData = [
    { value: "", label: "Tất cả các lớp" },
    { value: "DHCNTT1A", label: "DHCNTT1A" },
    { value: "DHCB2B", label: "DHCB2B" },
    { value: "DHQTKD3C", label: "DHQTKD3C" },
];

type Student = {
    user_id: string;
    user_name: string;
    class_id: string;
    specialization_id: string;
    specialization_name: string;
};

const StudentManagement = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteStudentId, setDeleteStudentId] = useState<string | null>(null);
    const [students, setStudents] = useState<Student[]>(studentsData); // State quản lý danh sách sinh viên
    const [filterClass, setFilterClass] = useState("");

    const handleDelete = () => {
        if (deleteStudentId) {
            // Gọi API để xóa sinh viên dựa trên deleteStudentId
            console.log("Deleting student with ID:", deleteStudentId);
            // Sau khi xóa thành công, cập nhật lại danh sách sinh viên
            setIsModalOpen(false);
            setDeleteStudentId(null);
            // Cập nhật state students (ví dụ: lọc bỏ sinh viên đã xóa)
            setStudents(
                students.filter(
                    (student) => student.user_id !== deleteStudentId
                )
            );
        }
    };

    const handleFilterClassChange = (
        e: React.ChangeEvent<HTMLSelectElement>
    ) => {
        setFilterClass(e.target.value);
        setCurrentPage(1); // Reset về trang đầu tiên khi thay đổi bộ lọc
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteStudentId(null);
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title="Quản lý sinh viên">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <div className={styles.filters}>
                            <Search
                                setParPage={setParPage}
                                setSearchValue={setSearchValue}
                                searchValue={searchValue}
                            />

                            <SelectWithLabel
                                name="gender"
                                value={filterClass}
                                onChange={handleFilterClassChange}
                                options={classOptionsData}
                            />
                        </div>

                        <Link
                            href="/student_management/create-edit"
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> Thêm mới
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ minWidth: "100px" }}>Mã SV</th>
                                    <th style={{ minWidth: "200px" }}>
                                        Tên SV
                                    </th>
                                    <th style={{ minWidth: "150px" }}>Lớp</th>
                                    <th style={{ minWidth: "250px" }}>
                                        Chuyên ngành
                                    </th>
                                    <th style={{ minWidth: "120px" }}>
                                        Thao tác
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {studentsData.map((student) => (
                                    <tr key={student.user_id}>
                                        <td>{student.user_id}</td>
                                        <td>{student.user_name}</td>
                                        <td>{student.class_id}</td>
                                        <td>{student.specialization_name}</td>
                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`/student_management/view?id=${student.user_id}`} // Đường dẫn xem chi tiết
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/student_management/create-edit?id=${student.user_id}&mode=edit`} // Đường dẫn chỉnh sửa
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
                                                    setDeleteStudentId(
                                                        student.user_id
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
                                                deleteStudentId ===
                                                    student.user_id && (
                                                    <ModalConfirm
                                                        message="Bạn có chắc chắn muốn xóa sinh viên này?"
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
                            totalItem={studentsData.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default StudentManagement;

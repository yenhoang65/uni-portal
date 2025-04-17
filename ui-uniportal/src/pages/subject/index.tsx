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

// Dữ liệu môn học mẫu (thay thế bằng API call thực tế)
const subjectsData = [
    {
        subject_id: "SUB001",
        subject_name: "Nhập môn Lập trình",
        it_credits: 3,
        th_credits: 2,
        subject_description: "Môn học cơ bản về lập trình.",
        subject_type: "Bắt buộc",
        subject_he_so: 1,
    },
    {
        subject_id: "SUB002",
        subject_name: "Cấu trúc dữ liệu và giải thuật",
        it_credits: 4,
        th_credits: 2,
        subject_description: "Các cấu trúc dữ liệu và giải thuật thông dụng.",
        subject_type: "Bắt buộc",
        subject_he_so: 1.2,
    },
    {
        subject_id: "SUB003",
        subject_name: "Cơ sở dữ liệu",
        it_credits: 3,
        th_credits: 2,
        subject_description: "Các khái niệm cơ bản về cơ sở dữ liệu.",
        subject_type: "Bắt buộc",
        subject_he_so: 1.1,
    },
    // Thêm dữ liệu môn học khác
];

type Subject = {
    subject_id: string;
    subject_name: string;
    it_credits: number;
    th_credits: number;
    subject_description?: string;
    subject_type?: string;
    subject_he_so?: number;
};

const SubjectManagement = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteSubjectId, setDeleteSubjectId] = useState<string | null>(null);

    const handleDelete = () => {
        if (deleteSubjectId) {
            // Gọi API để xóa môn học dựa trên deleteSubjectId
            console.log("Deleting subject with ID:", deleteSubjectId);
            // Sau khi xóa thành công, cập nhật lại danh sách môn học (ví dụ: fetch lại data hoặc lọc state hiện tại)
            setIsModalOpen(false);
            setDeleteSubjectId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteSubjectId(null);
    };

    return (
        <BorderBox title="Quản lý môn học">
            <div className={styles.box}>
                <div className={styles.add}>
                    <Search
                        setParPage={setParPage}
                        setSearchValue={setSearchValue}
                        searchValue={searchValue}
                    />

                    <Link
                        href="/subject/create-edit" // Đường dẫn đến trang thêm mới môn học
                        className={styles.buttonAdd}
                    >
                        <IoMdAddCircle /> Thêm mới
                    </Link>
                </div>

                <div className={styles.tableWrapper}>
                    <table className={styles.table}>
                        <thead className={styles.thead}>
                            <tr>
                                <th style={{ minWidth: "100px" }}>Mã MH</th>
                                <th style={{ minWidth: "350px" }}>
                                    Tên môn học
                                </th>
                                <th style={{ minWidth: "100px" }}>Tín chỉ</th>
                                <th style={{ minWidth: "120px" }}>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            {subjectsData.map((subject) => (
                                <tr key={subject.subject_id}>
                                    <td>{subject.subject_id}</td>
                                    <td>{subject.subject_name}</td>
                                    <td>
                                        {subject.it_credits} +
                                        {subject.th_credits}*
                                    </td>
                                    <td className={styles.buttonAction}>
                                        <Link
                                            href={`/subject/view?id=${subject.subject_id}`} // Đường dẫn xem chi tiết
                                            className={clsx(styles.viewButton)}
                                        >
                                            <FaEye />
                                        </Link>
                                        <Link
                                            href={`/subject/create-edit?id=${subject.subject_id}&mode=edit`} // Đường dẫn chỉnh sửa
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
                                                setDeleteSubjectId(
                                                    subject.subject_id
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
                                            deleteSubjectId ===
                                                subject.subject_id && (
                                                <ModalConfirm
                                                    message="Bạn có chắc chắn muốn xóa môn học này?"
                                                    onConfirm={handleDelete}
                                                    onCancel={handleCancel}
                                                />
                                            )}
                                    </td>
                                </tr>
                            ))}
                            {subjectsData.length === 0 && (
                                <tr>
                                    <td colSpan={5} className={styles.noData}>
                                        Không có dữ liệu
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>

                <div className={styles.paginationWrapper}>
                    <Pagination
                        pageNumber={currentPage}
                        setPageNumber={setCurrentPage}
                        totalItem={subjectsData.length}
                        parPage={parPage}
                        showItem={3}
                    />
                </div>
            </div>
        </BorderBox>
    );
};

export default SubjectManagement;

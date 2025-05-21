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
import AuthGuard from "@/components/AuthGuard";

// Mock data với tên môn học (subject_name)
const mockExamSchedules = [
    {
        id: "EXS001",
        class_subject_id: "CS001",
        subject_name: "Công nghệ phần mềm",
        classroom_id: "A101",
        exam_date: "2025-06-10",
        start_time: "08:00",
        end_time: "10:00",
        exam_form: "Trắc nghiệm",
    },
    {
        id: "EXS002",
        class_subject_id: "CS002",
        subject_name: "Cơ sở dữ liệu",
        classroom_id: "B202",
        exam_date: "2025-06-12",
        start_time: "13:30",
        end_time: "15:30",
        exam_form: "Tự luận",
    },
    {
        id: "EXS003",
        class_subject_id: "CS003",
        subject_name: "Lập trình Web",
        classroom_id: "C303",
        exam_date: "2025-06-15",
        start_time: "09:00",
        end_time: "11:00",
        exam_form: "Vấn đáp",
    },
];

const ExamScheduleManagement = () => {
    const [examSchedules, setExamSchedules] = useState(mockExamSchedules);
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteExamScheduleId, setDeleteExamScheduleId] = useState<
        string | null
    >(null);

    const handleDelete = () => {
        if (deleteExamScheduleId) {
            setExamSchedules((prev) =>
                prev.filter((item) => item.id !== deleteExamScheduleId)
            );
            setIsModalOpen(false);
            setDeleteExamScheduleId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteExamScheduleId(null);
    };

    return (
        <AuthGuard allowedRoles={["admin", "employee"]}>
            <BorderBox title="Quản lý lịch thi">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />
                        <Link
                            href="/exam-schedule-management/create-edit"
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> Thêm mới
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ width: "70px" }}>STT</th>
                                    <th>Mã lớp học phần</th>
                                    <th>Tên môn học</th>
                                    <th>Mã phòng</th>
                                    <th>Ngày thi</th>
                                    <th>Giờ bắt đầu</th>
                                    <th>Giờ kết thúc</th>
                                    <th>Hình thức thi</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                {mockExamSchedules.map((item, index) => (
                                    <tr key={item.id}>
                                        <td>
                                            {(currentPage - 1) * parPage +
                                                index +
                                                1}
                                        </td>
                                        <td>{item.class_subject_id}</td>
                                        <td>{item.subject_name}</td>
                                        <td>{item.classroom_id}</td>
                                        <td>{item.exam_date}</td>
                                        <td>{item.start_time}</td>
                                        <td>{item.end_time}</td>
                                        <td>{item.exam_form}</td>
                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`/exam-schedule-management/view?id=${item.id}`}
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/exam-schedule-management/create-edit?id=${item.id}&mode=edit`}
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
                                                    setDeleteExamScheduleId(
                                                        item.id
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
                                                deleteExamScheduleId ===
                                                    item.id && (
                                                    <ModalConfirm
                                                        message="Bạn chắc chắn muốn xoá lịch thi này?"
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
                            totalItem={mockExamSchedules.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ExamScheduleManagement;

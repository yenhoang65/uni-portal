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

type TeachingAssignmentType = {
    assignment_id: string;
    lecturer_id: string;
    subject_id: string;
    term_class_id: string;
};

const teachingAssignments: TeachingAssignmentType[] = [
    {
        assignment_id: "ASS001",
        lecturer_id: "LEC001",
        subject_id: "SUB001",
        term_class_id: "TERM001",
    },
    {
        assignment_id: "ASS002",
        lecturer_id: "LEC002",
        subject_id: "SUB002",
        term_class_id: "TERM002",
    },
    {
        assignment_id: "ASS003",
        lecturer_id: "LEC003",
        subject_id: "SUB003",
        term_class_id: "TERM003",
    },
    {
        assignment_id: "ASS004",
        lecturer_id: "LEC004",
        subject_id: "SUB004",
        term_class_id: "TERM004",
    },
    {
        assignment_id: "ASS005",
        lecturer_id: "LEC005",
        subject_id: "SUB005",
        term_class_id: "TERM005",
    },
];

const TeachingAssignment = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteAssignmentId, setDeleteAssignmentId] = useState<string | null>(
        null
    );

    const handleDelete = () => {
        if (deleteAssignmentId) {
            // dispatch(delete_teaching_assignment(deleteAssignmentId));
            setIsModalOpen(false);
            setDeleteAssignmentId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteAssignmentId(null);
    };

    const filteredAssignments = teachingAssignments.filter((assignment) =>
        Object.values(assignment).some((value) =>
            String(value).toLowerCase().includes(searchValue.toLowerCase())
        )
    );

    const startIndex = (currentPage - 1) * parPage;
    const endIndex = startIndex + parPage;
    const currentAssignments = filteredAssignments.slice(startIndex, endIndex);

    return (
        <AuthGuard allowedRoles={["admin", "employee"]}>
            <BorderBox title="Quản lý Phân công Giảng dạy">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />

                        <Link
                            href={"/teaching-assignment/create-edit"}
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> Thêm mới
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ minWidth: "80px" }}>No</th>
                                    <th style={{ minWidth: "150px" }}>
                                        Mã phân công
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        Mã giảng viên
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        Mã môn học
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        Mã học kỳ - lớp
                                    </th>
                                    <th style={{ minWidth: "70px" }}>
                                        Hành động
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {currentAssignments.map((assignment, index) => (
                                    <tr key={assignment.assignment_id}>
                                        <td className={styles.tableCell}>
                                            {(currentPage - 1) * parPage +
                                                index +
                                                1}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.assignment_id}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.lecturer_id}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.subject_id}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.term_class_id}
                                        </td>
                                        <td
                                            className={clsx(
                                                styles.buttonAction,
                                                styles.tableCell
                                            )}
                                        >
                                            <Link
                                                href={`/teaching-assignment/view?id=${assignment.assignment_id}`}
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/teaching-assignment/create-edit?id=${assignment.assignment_id}&mode=edit`}
                                                className={clsx(
                                                    styles.viewButton,
                                                    styles.viewButtonUpdate
                                                )}
                                            >
                                                <AiFillEdit />
                                            </Link>
                                            <Link
                                                onClick={(e) => {
                                                    e.preventDefault();
                                                    setIsModalOpen(true);
                                                    setDeleteAssignmentId(
                                                        assignment.assignment_id
                                                    );
                                                }}
                                                href="#"
                                                className={clsx(
                                                    styles.viewButton,
                                                    styles.viewButtonDelete
                                                )}
                                            >
                                                <MdDeleteForever />
                                            </Link>

                                            {isModalOpen &&
                                                deleteAssignmentId ===
                                                    assignment.assignment_id && (
                                                    <ModalConfirm
                                                        message="Bạn có chắc chắn muốn xóa phân công giảng dạy này?"
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
                            totalItem={filteredAssignments.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default TeachingAssignment;

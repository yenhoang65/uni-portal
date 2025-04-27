import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useState, useEffect } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { FaEye } from "react-icons/fa";
import { AiFillEdit } from "react-icons/ai";
import clsx from "clsx";
import { IoMdAddCircle } from "react-icons/io";
import AuthGuard from "@/components/AuthGuard";

type TeachingAssignmentWithDetailsType = {
    assignment_id: string;
    lecturer_id: string;
    subject_id: string;
    term_class_id: string;
    progress?: string;
    term?: string;
    status?: "pending" | "đã đăng ký" | "cancel";
};

const teachingAssignmentsWithDetails: TeachingAssignmentWithDetailsType[] = [
    {
        assignment_id: "ASS001",
        lecturer_id: "LEC001",
        subject_id: "SUB001",
        term_class_id: "TERM001",
        progress: "75%",
        term: "Học kỳ 2",
        status: "đã đăng ký",
    },
    {
        assignment_id: "ASS002",
        lecturer_id: "LEC002",
        subject_id: "SUB002",
        term_class_id: "TERM002",
        progress: "30%",
        term: "Học kỳ 1",
        status: "pending",
    },
    {
        assignment_id: "ASS003",
        lecturer_id: "LEC003",
        subject_id: "SUB003",
        term_class_id: "TERM003",
        progress: "90%",
        term: "Học kỳ hè",
        status: "cancel",
    },
    {
        assignment_id: "ASS004",
        lecturer_id: "LEC004",
        subject_id: "SUB004",
        term_class_id: "TERM004",
        progress: "60%",
        term: "Học kỳ 2",
        status: "pending",
    },
    {
        assignment_id: "ASS005",
        lecturer_id: "LEC005",
        subject_id: "SUB005",
        term_class_id: "TERM005",
        progress: "45%",
        term: "Học kỳ 1",
        status: "đã đăng ký",
    },
];

const TeachingAssignmentWithDetails = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [filteredAssignments, setFilteredAssignments] = useState(
        teachingAssignmentsWithDetails
    );

    useEffect(() => {
        const results = teachingAssignmentsWithDetails.filter((assignment) =>
            Object.values(assignment).some((value) =>
                String(value).toLowerCase().includes(searchValue.toLowerCase())
            )
        );
        setFilteredAssignments(results);
        setCurrentPage(1);
    }, [searchValue]);

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
                                    <th>No</th>
                                    <th>Mã giảng viên</th>
                                    <th>Môn học</th>
                                    <th>Mã học kỳ - lớp</th>
                                    <th>Tiến độ</th>
                                    <th>Kỳ</th>
                                    <th>Trạng thái</th>
                                    <th>Hành động</th>
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
                                            {assignment.lecturer_id}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.subject_id}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.term_class_id}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.progress}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.term}
                                        </td>
                                        <td className={styles.tableCell}>
                                            <span
                                                className={clsx(
                                                    styles.status,
                                                    assignment.status ===
                                                        "pending" &&
                                                        styles.pending,
                                                    assignment.status ===
                                                        "đã đăng ký" &&
                                                        styles.registered,
                                                    assignment.status ===
                                                        "cancel" &&
                                                        styles.cancel
                                                )}
                                            >
                                                {assignment.status}
                                            </span>
                                        </td>
                                        <td
                                            className={clsx(
                                                styles.buttonAction,
                                                styles.tableCell
                                            )}
                                        >
                                            <Link
                                                href={`/teaching-schedule-request/view?id=${assignment.assignment_id}`}
                                                className={styles.viewButton}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/teaching-schedule-request/create-edit?id=${assignment.assignment_id}&mode=edit`}
                                                className={clsx(
                                                    styles.viewButton,
                                                    styles.viewButtonUpdate
                                                )}
                                            >
                                                <AiFillEdit />
                                            </Link>
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

export default TeachingAssignmentWithDetails;

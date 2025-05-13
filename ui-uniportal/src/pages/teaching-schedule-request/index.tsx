import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useState, useEffect } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { FaCheck, FaEye } from "react-icons/fa";
import { AiFillEdit } from "react-icons/ai";
import clsx from "clsx";
import { IoMdAddCircle } from "react-icons/io";
import AuthGuard from "@/components/AuthGuard";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import { getListTeachingAssignmentByLecturerId } from "@/store/reducer/teachingAssignment";

type TeachingAssignmentWithDetailsType = {
    assignment_id: string;
    lecturer_id: string;
    subject_id: string;
    term_class_id: string;
    progress?: string;
    term?: string;
    status?: "pending" | "đã đăng ký" | "cancel";
};

const TeachingAssignmentWithDetails = () => {
    const dispatch = useDispatch<AppDispatch>();

    const { teachingAssignments } = useSelector(
        (state: RootState) => state.teachingAssignment
    );
    const { token, role } = useSelector((state: RootState) => state.auth);

    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);

    useEffect(() => {
        dispatch(getListTeachingAssignmentByLecturerId(token));
    }, []);

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
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
                                {teachingAssignments.map(
                                    (assignment, index) => (
                                        <tr key={assignment.assignmentId}>
                                            <td className={styles.tableCell}>
                                                {(currentPage - 1) * parPage +
                                                    index +
                                                    1}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.lecturerId}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.subjectId}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.termClassId}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.termClassId}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.termClassId}
                                            </td>
                                            <td className={styles.tableCell}>
                                                <span
                                                // className={clsx(
                                                //     styles.status,
                                                //     assignment.status ===
                                                //         "pending" &&
                                                //         styles.pending,
                                                //     assignment.status ===
                                                //         "đã đăng ký" &&
                                                //         styles.registered,
                                                //     assignment.status ===
                                                //         "cancel" &&
                                                //         styles.cancel
                                                // )}
                                                >
                                                    {assignment.termClassId}
                                                </span>
                                            </td>

                                            <td
                                                className={clsx(
                                                    styles.buttonAction,
                                                    styles.tableCell
                                                )}
                                            >
                                                <Link
                                                    href={`/teaching-schedule-request/view?id=${assignment.assignmentId}`}
                                                    className={
                                                        styles.viewButton
                                                    }
                                                >
                                                    <FaEye />
                                                </Link>
                                                <Link
                                                    href={`/teaching-schedule-request/create-edit?id=${assignment.assignmentId}&mode=edit`}
                                                    className={clsx(
                                                        styles.viewButton,
                                                        styles.viewButtonUpdate
                                                    )}
                                                >
                                                    <FaCheck />
                                                </Link>
                                            </td>
                                        </tr>
                                    )
                                )}
                            </tbody>
                        </table>
                    </div>

                    <div className={styles.paginationWrapper}>
                        <Pagination
                            pageNumber={currentPage}
                            setPageNumber={setCurrentPage}
                            totalItem={teachingAssignments.length}
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

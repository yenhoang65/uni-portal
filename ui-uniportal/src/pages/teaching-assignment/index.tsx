import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useEffect, useState } from "react";
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
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import {
    deleteTeachingAssignment,
    getListTeachingAssignmentByLecturerId,
    messageClear,
} from "@/store/reducer/teachingAssignment";
import toast from "react-hot-toast";
import SelectWithLabel from "@/components/SelectWithLabel";

const TeachingAssignment = () => {
    const dispatch = useDispatch<AppDispatch>();

    const { teachingAssignments, totalCounts, successMessage, errorMessage } =
        useSelector((state: RootState) => state.teachingAssignment);
    // const { token } = useSelector((state: RootState) => state.auth);

    const [currentPage, setCurrentPage] = useState(0);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteAssignmentId, setDeleteAssignmentId] = useState<number | null>(
        null
    );

    useEffect(() => {
        dispatch(
            getListTeachingAssignmentByLecturerId({
                currentPage: currentPage,
                parPage: parPage,
                searchValue: searchValue,
                token: window.localStorage.getItem("accessToken"),
            })
        );
    }, [currentPage, parPage, searchValue]);

    const handleDelete = () => {
        if (deleteAssignmentId) {
            dispatch(deleteTeachingAssignment(deleteAssignmentId));
            setIsModalOpen(false);
            setDeleteAssignmentId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteAssignmentId(null);
    };

    useEffect(() => {
        if (successMessage) {
            toast.success(successMessage);
            dispatch(messageClear());

            getListTeachingAssignmentByLecturerId({
                currentPage: currentPage,
                parPage: parPage,
                searchValue: searchValue,
                token: window.localStorage.getItem("accessToken"),
            });
        }
        if (errorMessage) {
            toast.error(errorMessage);
            dispatch(messageClear());
        }
    }, [successMessage, errorMessage]);

    return (
        <AuthGuard allowedRoles={["admin"]}>
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
                        {/* <SelectWithLabel
                            label=""
                            name=""
                            value={""}
                            onChange={() => {}}
                            options={}
                            required
                        /> */}
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ minWidth: "80px" }}>No</th>

                                    <th style={{ minWidth: "150px" }}>
                                        Mã giảng viên
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        Mã môn học
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        Mã lớp học phần
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        Kỳ học - Năm học
                                    </th>
                                    <th style={{ minWidth: "70px" }}>
                                        Hành động
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {teachingAssignments.map((assignment) => (
                                    <tr key={assignment.assignmentId}>
                                        <td className={styles.tableCell}>
                                            {assignment.assignmentId}
                                        </td>

                                        <td className={styles.tableCell}>
                                            {assignment.lecturerId} -{" "}
                                            {assignment.lecturerName}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.subjectId} -{" "}
                                            {assignment.subjectName}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.className}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {assignment.semester} -{" "}
                                            {assignment.schoolYears}
                                        </td>
                                        <td
                                            className={clsx(
                                                styles.buttonAction,
                                                styles.tableCell
                                            )}
                                        >
                                            <Link
                                                href={`/teaching-assignment/view?id=${assignment.assignmentId}`}
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/teaching-assignment/create-edit?id=${assignment.assignmentId}&mode=edit`}
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
                                                        assignment.assignmentId
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
                                                    assignment.assignmentId && (
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
                        {totalCounts <= parPage ? (
                            ""
                        ) : (
                            <Pagination
                                pageNumber={currentPage + 1}
                                setPageNumber={(page) =>
                                    setCurrentPage(page - 1)
                                }
                                totalItem={totalCounts}
                                parPage={parPage}
                                showItem={3}
                            />
                        )}
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default TeachingAssignment;

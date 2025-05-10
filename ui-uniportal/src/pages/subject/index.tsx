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
    deleteSubject,
    getListSubject,
    messageClear,
} from "@/store/reducer/subjectReducer";
import toast from "react-hot-toast";

const SubjectManagement = () => {
    const dispatch = useDispatch<AppDispatch>();
    const { subjects, successMessage, errorMessage } = useSelector(
        (state: RootState) => state.subject
    );

    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteSubjectId, setDeleteSubjectId] = useState<string | null>(null);

    const handleDelete = () => {
        if (deleteSubjectId) {
            dispatch(deleteSubject(deleteSubjectId));
            setIsModalOpen(false);
            setDeleteSubjectId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteSubjectId(null);
    };

    useEffect(() => {
        dispatch(getListSubject());
    }, []);

    useEffect(() => {
        if (successMessage) {
            toast.success(successMessage);
            dispatch(messageClear());

            dispatch(getListSubject());
            // const obj = {
            //     parPage: parseInt(parPage),
            //     currentPage: parseInt(currentPage),
            //     searchValue,
            //     typeCate,
            // };
            // dispatch(get_category(obj));

            // setState({
            //     name: "",
            //     image: "",
            //     type: "",
            // });
            // setImageShow("");
        }
        if (errorMessage) {
            toast.error(errorMessage);
            dispatch(messageClear());
        }
    }, [successMessage, errorMessage]);

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title="Quản lý môn học">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />

                        <Link
                            href="/subject/create-edit"
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
                                    <th style={{ minWidth: "100px" }}>
                                        Tín chỉ
                                    </th>
                                    <th style={{ minWidth: "120px" }}>
                                        Thao tác
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {subjects.map((subject) => (
                                    <tr key={subject.subjectId}>
                                        <td>{subject.subjectId}</td>
                                        <td>{subject.subjectName}</td>
                                        <td>
                                            {subject.ltCredits
                                                ? `${subject.ltCredits}*`
                                                : ""}
                                            {subject.thCredits
                                                ? ` ${
                                                      subject.ltCredits
                                                          ? "+"
                                                          : ""
                                                  }  ${subject.thCredits}*`
                                                : ""}
                                        </td>
                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`/subject/view?id=${subject.subjectId}`}
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/subject/create-edit?id=${subject.subjectId}&mode=edit`}
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
                                                        subject.subjectId
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
                                                    subject.subjectId && (
                                                    <ModalConfirm
                                                        message="Bạn có chắc chắn muốn xóa môn học này?"
                                                        onConfirm={handleDelete}
                                                        onCancel={handleCancel}
                                                    />
                                                )}
                                        </td>
                                    </tr>
                                ))}
                                {subjects.length === 0 && (
                                    <tr>
                                        <td
                                            colSpan={5}
                                            className={styles.noData}
                                        >
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
                            totalItem={subjects.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default SubjectManagement;

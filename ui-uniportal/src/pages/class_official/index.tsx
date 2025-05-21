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
    deleteClassOffical,
    getListClassOffical,
    messageClear,
} from "@/store/reducer/classReducer";
import toast from "react-hot-toast";

const ClassOffical = () => {
    const dispatch = useDispatch<AppDispatch>();

    const { classOfficals, successMessage, errorMessage } = useSelector(
        (state: RootState) => state.class
    );
    const { lecturers } = useSelector((state: RootState) => state.lecturer);
    const { trainingPrograms } = useSelector(
        (state: RootState) => state.trainingProgram
    );

    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteClassOfficalId, setDeleteClassOfficalId] = useState<
        number | null
    >(null);

    useEffect(() => {
        dispatch(getListClassOffical());
    }, []);

    const handleDelete = () => {
        if (deleteClassOfficalId) {
            dispatch(deleteClassOffical(deleteClassOfficalId));
            setIsModalOpen(false);
            setDeleteClassOfficalId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteClassOfficalId(null);
    };

    useEffect(() => {
        if (successMessage) {
            toast.success(successMessage);
            dispatch(messageClear());
            dispatch(getListClassOffical());
        }
        if (errorMessage) {
            toast.error(errorMessage);
            dispatch(messageClear());
        }
    }, [successMessage, errorMessage]);

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title="Class Offical">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />

                        <Link
                            href={"/class_official/create-edit"}
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> Add New
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ width: "80px" }}>No</th>
                                    <th style={{ width: "150px" }}>Class ID</th>
                                    <th style={{ minWidth: "120px" }}>GVCN</th>
                                    <th style={{ width: "420px" }}>
                                        Training Program
                                    </th>
                                    <th style={{ width: "150px" }}>
                                        School Year
                                    </th>
                                    <th style={{ minWidth: "70px" }}>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {classOfficals.map((term, index) => (
                                    <tr key={term.classId}>
                                        <td>{index + 1}</td>
                                        <td>{term.classId}</td>
                                        <td>{term.lecturerName}</td>
                                        <td>{term.trainingProgramName}</td>
                                        <td>{term.schoolYear}</td>
                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`/class_official/create-edit?id=${term.classId}&mode=edit`}
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
                                                    setDeleteClassOfficalId(
                                                        term.classId
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
                                                deleteClassOfficalId ===
                                                    term.classId && (
                                                    <ModalConfirm
                                                        message="Are you sure you want to delete?"
                                                        onConfirm={handleDelete}
                                                        onCancel={handleCancel}
                                                    />
                                                )}
                                        </td>
                                    </tr>
                                ))}
                                {classOfficals.length === 0 && (
                                    <tr>
                                        <td
                                            colSpan={6}
                                            className={styles.noData}
                                        >
                                            No data
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
                            totalItem={classOfficals.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ClassOffical;

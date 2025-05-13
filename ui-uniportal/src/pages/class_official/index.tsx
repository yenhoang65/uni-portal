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
import { getListClassOffical } from "@/store/reducer/classReducer";

const classTerms = [
    {
        id: "CT001",
        class_name: 101,
        progress: 75,
        semester: 1,
        school_year: 2024,
    },
    {
        id: "CT002",
        class_name: 102,
        progress: 90,
        semester: 2,
        school_year: 2025,
    },
    {
        id: "CT003",
        class_name: 201,
        progress: 60,
        semester: 1,
        school_year: 2023,
    },
    {
        id: "CT004",
        class_name: 202,
        progress: 80,
        semester: 2,
        school_year: 2024,
    },
    {
        id: "CT005",
        class_name: 101,
        progress: 85,
        semester: 1,
        school_year: 2022,
    },
];

const ClassOffical = () => {
    const dispatch = useDispatch<AppDispatch>();

    const { classOfficals } = useSelector((state: RootState) => state.class);
    const { lecturers } = useSelector((state: RootState) => state.lecturer);
    const { trainingPrograms } = useSelector(
        (state: RootState) => state.trainingProgram
    );

    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteClassTermId, setDeleteClassTermId] = useState<number | null>(
        null
    );

    useEffect(() => {
        dispatch(getListClassOffical());
    }, []);

    const handleDelete = () => {
        if (deleteClassTermId) {
            setIsModalOpen(false);
            setDeleteClassTermId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteClassTermId(null);
    };

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

                        <Link href={""} className={styles.buttonAdd}>
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
                                                href={`/class-term-subject/view?id=${term.classId}`}
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/class-term-subject/create-edit?id=${term.classId}&mode=edit`}
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
                                                    setDeleteClassTermId(
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
                                                deleteClassTermId ===
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
                                {classTerms.length === 0 && (
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
                            totalItem={classTerms.length}
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

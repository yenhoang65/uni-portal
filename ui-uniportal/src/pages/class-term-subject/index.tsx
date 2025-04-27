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

// Dummy data for Class Terms - replace with your actual data fetching
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

const ClassTermSubject = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteClassTermId, setDeleteClassTermId] = useState<string | null>(
        null
    );

    const handleDelete = () => {
        if (deleteClassTermId) {
            // In a real application, you would dispatch an action to delete the class term.
            console.log(`Deleting class term with ID: ${deleteClassTermId}`);
            setIsModalOpen(false);
            setDeleteClassTermId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteClassTermId(null);
    };

    // Basic filtering based on search value (you can enhance this)
    const filteredClassTerms = classTerms.filter((term) =>
        Object.values(term).some((value) =>
            String(value).toLowerCase().includes(searchValue.toLowerCase())
        )
    );

    // Pagination logic
    const startIndex = (currentPage - 1) * parPage;
    const endIndex = startIndex + parPage;
    const currentClassTerms = filteredClassTerms.slice(startIndex, endIndex);

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title="Class Term Management">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />

                        <Link
                            href={"/class-term/create-edit"} // Adjust the link as needed
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> Add New
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ minWidth: "80px" }}>No</th>
                                    <th style={{ minWidth: "150px" }}>
                                        Class Name
                                    </th>
                                    <th style={{ minWidth: "120px" }}>
                                        Progress
                                    </th>
                                    <th style={{ minWidth: "120px" }}>
                                        Semester
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        School Year
                                    </th>
                                    <th style={{ minWidth: "70px" }}>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {currentClassTerms.map((term, index) => (
                                    <tr key={term.id}>
                                        <td>{startIndex + index + 1}</td>
                                        <td>{term.class_name}</td>
                                        <td>{term.progress}</td>
                                        <td>{term.semester}</td>
                                        <td>{term.school_year}</td>
                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`/class-term-subject/view?id=${term.id}`}
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/class-term-subject/create-edit?id=${term.id}&mode=edit`}
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
                                                        term.id
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
                                                    term.id && (
                                                    <ModalConfirm
                                                        message="Are you sure you want to delete?"
                                                        onConfirm={handleDelete}
                                                        onCancel={handleCancel}
                                                    />
                                                )}
                                        </td>
                                    </tr>
                                ))}
                                {currentClassTerms.length === 0 && (
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
                            totalItem={filteredClassTerms.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ClassTermSubject;

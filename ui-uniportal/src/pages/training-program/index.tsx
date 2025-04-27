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
import SelectWithLabel from "@/components/SelectWithLabel";
import AuthGuard from "@/components/AuthGuard";

const trainingProgramsData = [
    {
        training_program_id: "TP001",
        specialization_id: "SP001",
        train_code: "K19",
        created_at: "2019-08-01",
    },
    {
        training_program_id: "TP002",
        specialization_id: "SP002",
        train_code: "K20",
        created_at: "2020-07-15",
    },
    {
        training_program_id: "TP003",
        specialization_id: "SP001",
        train_code: "K21",
        created_at: "2021-09-10",
    },
    {
        training_program_id: "TP004",
        specialization_id: "SP003",
        train_code: "K18",
        created_at: "2018-06-22",
    },
    {
        training_program_id: "TP005",
        specialization_id: "SP002",
        train_code: "K22",
        created_at: "2022-08-05",
    },
];

const specializationOptions = [
    { value: "SP001", label: "Công nghệ phần mềm" },
    { value: "SP002", label: "Mạng máy tính" },
    { value: "SP003", label: "Hệ thống thông tin" },
];

const TrainingProgram = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteProgramId, setDeleteProgramId] = useState<string | null>(null);
    const [filterSpecialization, setFilterSpecialization] = useState<
        string | number
    >(""); // State cho filter chuyên ngành

    const handleDelete = () => {
        if (deleteProgramId) {
            // Gọi API hoặc dispatch action để xóa chương trình đào tạo
            console.log("Deleting program with ID:", deleteProgramId);
            setIsModalOpen(false);
            setDeleteProgramId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteProgramId(null);
    };

    const handleSpecializationFilterChange = (
        e: React.ChangeEvent<HTMLSelectElement>
    ) => {
        setFilterSpecialization(e.target.value);
        setCurrentPage(1);
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title="Quản lý Chương trình Đào tạo">
                <div className={styles.box}>
                    <div className={styles.filterAndAdd}>
                        <div className={styles.searchAndFilter}>
                            <Search
                                setParPage={setParPage}
                                setSearchValue={setSearchValue}
                                searchValue={searchValue}
                            />
                            <div className={styles.filter}>
                                <SelectWithLabel
                                    name="filterSpecialization"
                                    value={filterSpecialization}
                                    onChange={handleSpecializationFilterChange}
                                    options={[
                                        { value: "", label: "Tất cả" },
                                        ...specializationOptions,
                                    ]}
                                />
                            </div>
                        </div>

                        <Link
                            href={"/training-program/create-edit"}
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> Thêm mới
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ minWidth: "80px" }}>STT</th>
                                    <th style={{ minWidth: "200px" }}>
                                        Mã CTĐT
                                    </th>
                                    <th style={{ minWidth: "250px" }}>
                                        Chuyên ngành
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        Mã khóa học
                                    </th>
                                    <th style={{ minWidth: "180px" }}>
                                        Ngày tạo
                                    </th>
                                    <th style={{ minWidth: "100px" }}>
                                        Hành động
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {trainingProgramsData.map((program, index) => (
                                    <tr key={program.training_program_id}>
                                        <td>1</td>
                                        <td>{program.training_program_id}</td>
                                        <td>
                                            {
                                                specializationOptions.find(
                                                    (sp) =>
                                                        sp.value ===
                                                        program.specialization_id
                                                )?.label
                                            }
                                        </td>
                                        <td>{program.train_code}</td>
                                        <td>{program.created_at}</td>
                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`/training-program/view?id=${program.training_program_id}`}
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/training-program/create-edit?id=${program.training_program_id}&mode=edit`}
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
                                                    setDeleteProgramId(
                                                        program.training_program_id
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
                                                deleteProgramId ===
                                                    program.training_program_id && (
                                                    <ModalConfirm
                                                        message="Are you sure you want to delete?"
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
                            totalItem={trainingProgramsData.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default TrainingProgram;

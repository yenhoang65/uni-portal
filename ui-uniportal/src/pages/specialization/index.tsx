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

const specializations = [
    {
        id: "SPE001",
        majorId: "MAJ001", // Mã ngành
        majorName: "Công nghệ phần mềm", // Tên ngành
        facultyName: "Khoa CNTT", // Tên khoa
        name: "Phát triển ứng dụng Web",
        description: "Chuyên sâu về phát triển các ứng dụng web hiện đại.",
        establishDate: "2012-05-20",
    },
    {
        id: "SPE002",
        majorId: "MAJ002",
        majorName: "Kỹ thuật cơ khí",
        facultyName: "Khoa Cơ khí",
        name: "Thiết kế CAD/CAM",
        description: "Đào tạo chuyên sâu về thiết kế và sản xuất cơ khí.",
        establishDate: "2010-11-10",
    },
    {
        id: "SPE003",
        majorId: "MAJ001",
        majorName: "Công nghệ phần mềm",
        facultyName: "Khoa CNTT",
        name: "Phát triển ứng dụng di động",
        description: "Chuyên về xây dựng ứng dụng cho nền tảng iOS và Android.",
        establishDate: "2015-03-01",
    },
];

const Specialization = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteSpecializationId, setDeleteSpecializationId] = useState<
        string | null
    >(null);

    const handleDelete = () => {
        if (deleteSpecializationId) {
            setIsModalOpen(false);
            setDeleteSpecializationId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteSpecializationId(null);
    };

    return (
        <BorderBox title="Quản lý chuyên ngành">
            <div className={styles.box}>
                <div className={styles.add}>
                    <Search
                        setParPage={setParPage}
                        setSearchValue={setSearchValue}
                        searchValue={searchValue}
                    />

                    <Link
                        href="/specialization/create-edit"
                        className={styles.buttonAdd}
                    >
                        <IoMdAddCircle /> Thêm mới
                    </Link>
                </div>

                <div className={styles.tableWrapper}>
                    <table className={styles.table}>
                        <thead className={styles.thead}>
                            <tr>
                                <th style={{ minWidth: "60px" }}>STT</th>
                                <th style={{ minWidth: "150px" }}>
                                    Mã chuyên ngành
                                </th>
                                <th style={{ minWidth: "180px" }}>Tên ngành</th>{" "}
                                {/* Tên ngành (từ major) */}
                                <th style={{ minWidth: "180px" }}>
                                    Tên khoa
                                </th>{" "}
                                {/* Tên khoa (từ major) */}
                                <th style={{ minWidth: "220px" }}>
                                    Tên chuyên ngành
                                </th>
                                <th style={{ minWidth: "320px" }}>Mô tả</th>
                                <th style={{ minWidth: "160px" }}>
                                    Ngày thành lập
                                </th>
                                <th style={{ minWidth: "80px" }}>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            {specializations.map((specialization, index) => (
                                <tr key={specialization.id}>
                                    <td>{index + 1}</td>
                                    <td>{specialization.id}</td>
                                    <td>{specialization.majorName}</td>{" "}
                                    {/* Hiển thị Tên ngành */}
                                    <td>{specialization.facultyName}</td>{" "}
                                    {/* Hiển thị Tên khoa */}
                                    <td>{specialization.name}</td>
                                    <td>{specialization.description}</td>
                                    <td>{specialization.establishDate}</td>
                                    <td className={styles.buttonAction}>
                                        <Link
                                            href={`/specialization/view?id=${specialization.id}`}
                                            className={clsx(styles.viewButton)}
                                        >
                                            <FaEye />
                                        </Link>
                                        <Link
                                            href={`/specialization/create-edit?id=${specialization.id}&mode=edit`}
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
                                                setDeleteSpecializationId(
                                                    specialization.id
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
                                            deleteSpecializationId ===
                                                specialization.id && (
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
                        totalItem={specializations.length}
                        parPage={parPage}
                        showItem={3}
                    />
                </div>
            </div>
        </BorderBox>
    );
};

export default Specialization;

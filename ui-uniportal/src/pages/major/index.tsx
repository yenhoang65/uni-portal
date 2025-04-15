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

const majors = [
    {
        id: "MAJ001",
        tenKhoa: "Khoa CNTT", // Thêm tên khoa
        name: "Công nghệ phần mềm",
        description: "Ngành chuyên đào tạo phát triển phần mềm.",
        establishDate: "2008-01-15",
    },
    {
        id: "MAJ002",
        tenKhoa: "Khoa Cơ khí", // Thêm tên khoa
        name: "Kỹ thuật cơ khí",
        description: "Đào tạo kỹ sư thiết kế và chế tạo máy móc.",
        establishDate: "2005-03-20",
    },
    {
        id: "MAJ003",
        tenKhoa: "Khoa Kinh tế", // Thêm tên khoa
        name: "Quản trị kinh doanh",
        description: "Quản lý doanh nghiệp, marketing, tài chính.",
        establishDate: "2010-10-01",
    },
];

const Major = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteMajorId, setDeleteMajorId] = useState<string | null>(null);

    const handleDelete = () => {
        if (deleteMajorId) {
            setIsModalOpen(false);
            setDeleteMajorId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteMajorId(null);
    };

    return (
        <BorderBox title="Quản lý ngành học">
            <div className={styles.box}>
                <div className={styles.add}>
                    <Search
                        setParPage={setParPage}
                        setSearchValue={setSearchValue}
                        searchValue={searchValue}
                    />

                    <Link
                        href="/major/create-edit"
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
                                <th style={{ minWidth: "180px" }}>
                                    Tên khoa
                                </th>{" "}
                                {/* Thêm cột Tên khoa */}
                                <th style={{ minWidth: "180px" }}>Mã ngành</th>
                                <th style={{ minWidth: "220px" }}>Tên ngành</th>
                                <th style={{ minWidth: "320px" }}>Mô tả</th>
                                <th style={{ minWidth: "160px" }}>
                                    Ngày thành lập
                                </th>
                                <th style={{ minWidth: "80px" }}>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            {majors.map((major, index) => (
                                <tr key={major.id}>
                                    <td>{index + 1}</td>
                                    <td>{major.tenKhoa}</td>{" "}
                                    {/* Hiển thị Tên khoa */}
                                    <td>{major.id}</td>
                                    <td>{major.name}</td>
                                    <td>{major.description}</td>
                                    <td>{major.establishDate}</td>
                                    <td className={styles.buttonAction}>
                                        <Link
                                            href={`/major/view?id=${major.id}`}
                                            className={clsx(styles.viewButton)}
                                        >
                                            <FaEye />
                                        </Link>
                                        <Link
                                            href={`/major/create-edit?id=${major.id}&mode=edit`}
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
                                                setDeleteMajorId(major.id);
                                            }}
                                            className={clsx(
                                                styles.viewButton,
                                                styles.viewButtonDelete
                                            )}
                                        >
                                            <MdDeleteForever />
                                        </Link>

                                        {isModalOpen &&
                                            deleteMajorId === major.id && (
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
                        totalItem={majors.length}
                        parPage={parPage}
                        showItem={3}
                    />
                </div>
            </div>
        </BorderBox>
    );
};

export default Major;

import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useEffect, useState } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { AiFillEdit } from "react-icons/ai";
import { MdDeleteForever } from "react-icons/md";
import clsx from "clsx";
import ModalConfirm from "@/components/ModalConfirm";
import AuthGuard from "@/components/AuthGuard";

// Mock data for grade_type
type GradeType = {
    grade_type_id: number;
    code: string;
    name: string;
    coefficient: number;
};

const MOCK_GRADE_TYPES: GradeType[] = [
    { grade_type_id: 1, code: "MID", name: "Giữa kỳ", coefficient: 0.4 },
    { grade_type_id: 2, code: "FINAL", name: "Cuối kỳ", coefficient: 0.6 },
    {
        grade_type_id: 3,
        code: "QUIZ",
        name: "Kiểm tra nhanh",
        coefficient: 0.2,
    },
];

const PointManagement = () => {
    const [gradeTypes, setGradeTypes] = useState<GradeType[]>(MOCK_GRADE_TYPES);
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteId, setDeleteId] = useState<number | null>(null);

    // Filter and paginate
    const filteredTypes = gradeTypes.filter(
        (g) =>
            g.code.toLowerCase().includes(searchValue.toLowerCase()) ||
            g.name.toLowerCase().includes(searchValue.toLowerCase())
    );
    const startIndex = (currentPage - 1) * parPage;
    const paginatedTypes = filteredTypes.slice(
        startIndex,
        startIndex + parPage
    );

    const handleDelete = () => {
        if (deleteId !== null) {
            setIsModalOpen(false);
            setDeleteId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteId(null);
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title="Quản lý loại điểm">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />
                        <Link
                            href="/point-management/create-edit"
                            className={styles.buttonAdd}
                        >
                            + Thêm mới
                        </Link>
                    </div>
                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ minWidth: "100px" }}>ID</th>
                                    <th style={{ minWidth: "150px" }}>
                                        Mã loại điểm
                                    </th>
                                    <th style={{ minWidth: "200px" }}>
                                        Tên loại điểm
                                    </th>
                                    <th style={{ minWidth: "120px" }}>Hệ số</th>
                                    <th style={{ minWidth: "120px" }}>
                                        Thao tác
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {paginatedTypes.length === 0 && (
                                    <tr>
                                        <td
                                            colSpan={5}
                                            style={{ textAlign: "center" }}
                                        >
                                            Không có loại điểm nào.
                                        </td>
                                    </tr>
                                )}
                                {paginatedTypes.map((type) => (
                                    <tr key={type.grade_type_id}>
                                        <td>{type.grade_type_id}</td>
                                        <td>{type.code}</td>
                                        <td>{type.name}</td>
                                        <td>{type.coefficient}</td>
                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`/point-management/create-edit?id=${type.grade_type_id}&mode=edit`}
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
                                                    setDeleteId(
                                                        type.grade_type_id
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
                                                deleteId ===
                                                    type.grade_type_id && (
                                                    <ModalConfirm
                                                        message="Bạn có chắc chắn muốn xóa loại điểm này?"
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
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default PointManagement;

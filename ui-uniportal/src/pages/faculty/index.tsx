import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useTranslation } from "react-i18next";
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

const faculties = [
    {
        id: "FAC001",
        name: "Khoa Công nghệ thông tin",
        logo: "https://media.istockphoto.com/id/1425103315/vi/anh/ng%C6%B0%E1%BB%9Di-ph%E1%BB%A5-n%E1%BB%AF-ch%C3%A2u-%C3%A1-m%E1%BA%B7c-v%C4%83n-h%C3%B3a-vi%E1%BB%87t-nam-truy%E1%BB%81n-th%E1%BB%91ng-t%E1%BA%A1i-tam-c%E1%BB%91c-vi%E1%BB%87t-nam.jpg?s=612x612&w=0&k=20&c=xZDKlDmMiYEv7r5z0KNgMYfEe19Ozr7s1JXc040TR0Y=",
        establishDate: "2005-09-01",
        website: "https://fit.utehy.edu.vn",
    },
    {
        id: "FAC002",
        name: "Khoa Cơ khí",
        logo: "https://media.istockphoto.com/id/1425103315/vi/anh/ng%C6%B0%E1%BB%9Di-ph%E1%BB%A5-n%E1%BB%AF-ch%C3%A2u-%C3%A1-m%E1%BA%B7c-v%C4%83n-h%C3%B3a-vi%E1%BB%87t-nam-truy%E1%BB%81n-th%E1%BB%91ng-t%E1%BA%A1i-tam-c%E1%BB%91c-vi%E1%BB%87t-nam.jpg?s=612x612&w=0&k=20&c=xZDKlDmMiYEv7r5z0KNgMYfEe19Ozr7s1JXc040TR0Y=",
        establishDate: "2003-03-15",
        website: "https://me.utehy.edu.vn",
    },
    {
        id: "FAC003",
        name: "Khoa Kinh tế",
        logo: "https://media.istockphoto.com/id/1425103315/vi/anh/ng%C6%B0%E1%BB%9Di-ph%E1%BB%A5-n%E1%BB%AF-ch%C3%A2u-%C3%A1-m%E1%BA%B7c-v%C4%83n-h%C3%B3a-vi%E1%BB%87t-nam-truy%E1%BB%81n-th%E1%BB%91ng-t%E1%BA%A1i-tam-c%E1%BB%91c-vi%E1%BB%87t-nam.jpg?s=612x612&w=0&k=20&c=xZDKlDmMiYEv7r5z0KNgMYfEe19Ozr7s1JXc040TR0Y=",
        establishDate: "2007-07-20",
        website: "https://kt.utehy.edu.vn",
    },
    {
        id: "FAC004",
        name: "Khoa Điện",
        logo: "https://media.istockphoto.com/id/1425103315/vi/anh/ng%C6%B0%E1%BB%9Di-ph%E1%BB%A5-n%E1%BB%AF-ch%C3%A2u-%C3%A1-m%E1%BA%B7c-v%C4%83n-h%C3%B3a-vi%E1%BB%87t-nam-truy%E1%BB%81n-th%E1%BB%91ng-t%E1%BA%A1i-tam-c%E1%BB%91c-vi%E1%BB%87t-nam.jpg?s=612x612&w=0&k=20&c=xZDKlDmMiYEv7r5z0KNgMYfEe19Ozr7s1JXc040TR0Y=",
        establishDate: "2001-01-10",
        website: "https://dien.utehy.edu.vn",
    },
    {
        id: "FAC005",
        name: "Khoa Ngoại ngữ",
        logo: "https://media.istockphoto.com/id/1425103315/vi/anh/ng%C6%B0%E1%BB%9Di-ph%E1%BB%A5-n%E1%BB%AF-ch%C3%A2u-%C3%A1-m%E1%BA%B7c-v%C4%83n-h%C3%B3a-vi%E1%BB%87t-nam-truy%E1%BB%81n-th%E1%BB%91ng-t%E1%BA%A1i-tam-c%E1%BB%91c-vi%E1%BB%87t-nam.jpg?s=612x612&w=0&k=20&c=xZDKlDmMiYEv7r5z0KNgMYfEe19Ozr7s1JXc040TR0Y=",
        establishDate: "2010-05-05",
        website: "https://nn.utehy.edu.vn",
    },
];

const Faculty = () => {
    const { t } = useTranslation();
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteFacultyId, setDeleteFacultyId] = useState<string | null>(null);

    const handleDelete = () => {
        if (deleteFacultyId) {
            // dispatch(delete_product(deleteFacultyId));
            setIsModalOpen(false);
            setDeleteFacultyId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteFacultyId(null);
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title={t("common.faculty-management")}>
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />

                        <Link
                            href={"/faculty/create-edit"}
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> Thêm mới
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ minWidth: "80px" }}>No</th>
                                    <th style={{ minWidth: "230px" }}>
                                        {t("common.name")}
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        {t("common.logo")}
                                    </th>
                                    <th style={{ minWidth: "180px" }}>
                                        {t("common.established-date")}
                                    </th>
                                    <th style={{ minWidth: "200px" }}>
                                        {t("common.website")}
                                    </th>
                                    <th style={{ minWidth: "70px" }}>
                                        {t("common.action")}
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {faculties.map((faculty, index) => (
                                    <tr key={faculty.id}>
                                        <td>{index + 1}</td>
                                        <td>{faculty.name}</td>
                                        <td>
                                            <img
                                                src={faculty.logo}
                                                alt="logo"
                                                width={80}
                                                height={40}
                                            />
                                        </td>
                                        <td>{faculty.establishDate}</td>
                                        <td>
                                            <a
                                                href={faculty.website}
                                                target="_blank"
                                                rel="noopener noreferrer"
                                            >
                                                {faculty.website}
                                            </a>
                                        </td>
                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`/faculty/view?id=${faculty.id}`}
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/faculty/create-edit?id=${faculty.id}&mode=edit`}
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
                                                    setDeleteFacultyId(
                                                        faculty.id
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
                                                deleteFacultyId ===
                                                    faculty.id && (
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
                            totalItem={50}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default Faculty;

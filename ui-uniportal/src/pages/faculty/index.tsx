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

const Faculty = () => {
    const { t } = useTranslation();
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);

    return (
        <BorderBox title={t("common.faculty-management")}>
            <div className={styles.box}>
                <Search
                    setParPage={setParPage}
                    setSearchValue={setSearchValue}
                    searchValue={searchValue}
                />

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
                            {[1, 2, 3, 4, 5].map((c) => (
                                <tr>
                                    <td>#id</td>
                                    <td>name</td>
                                    <td>email</td>
                                    <td>payment</td>
                                    <td>status</td>
                                    <td className={styles.buttonAction}>
                                        <Link
                                            href={"/faculty/view"}
                                            className={clsx(styles.viewButton)}
                                        >
                                            <FaEye />
                                        </Link>
                                        <Link
                                            href={"/faculty/edit"}
                                            className={clsx(
                                                styles.viewButton,
                                                styles.viewButtonUpdate
                                            )}
                                        >
                                            <AiFillEdit />
                                        </Link>
                                        <Link
                                            href={"/"}
                                            className={clsx(
                                                styles.viewButton,
                                                styles.viewButtonDelete
                                            )}
                                        >
                                            <MdDeleteForever />
                                        </Link>
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
    );
};

export default Faculty;

import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useEffect, useState } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { FaUserFriends } from "react-icons/fa";
import { AiOutlineCheckSquare, AiFillStar } from "react-icons/ai";
import { BiListCheck } from "react-icons/bi";
import clsx from "clsx";
import { IoMdAddCircle } from "react-icons/io";
import AuthGuard from "@/components/AuthGuard";
import { AppDispatch, RootState } from "@/store";
import { useDispatch, useSelector } from "react-redux";
import { getClassByStatusLecturer } from "@/store/reducer/classReducer";
import { useTranslation } from "react-i18next";

const ClassSubjectManagement = () => {
    const { t } = useTranslation();
    const dispatch = useDispatch<AppDispatch>();

    const { classByStatus, totalClassByStatus } = useSelector(
        (state: RootState) => state.class
    );

    const [currentPage, setCurrentPage] = useState(0);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(10);

    useEffect(() => {
        dispatch(
            getClassByStatusLecturer({
                statuses: "success",
                currentPage: currentPage,
                perPage: parPage,
                searchValue: searchValue,
            })
        );
    }, [currentPage, searchValue, parPage]);

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox title={t("classSubject.title")}>
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />

                        <Link
                            href={"/class-subject/create"}
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> {t("common.addNew")}
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ minWidth: "50px" }}>
                                        {t("common.index")}
                                    </th>
                                    <th style={{ minWidth: "200px" }}>
                                        {t("classSubject.className")}
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        {t("classSubject.subjectName")}
                                    </th>
                                    {/* <th style={{ minWidth: "100px" }}>
                                        Tổng Số Sinh Viên
                                    </th> */}
                                    <th
                                        style={{
                                            minWidth: "300px",
                                            textAlign: "center",
                                        }}
                                    >
                                        {t("common.action")}
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {classByStatus.map((classSubject, index) => (
                                    <tr
                                        key={
                                            classSubject.assignment.assignmentId
                                        }
                                    >
                                        <td>
                                            {(currentPage - 1) * parPage +
                                                index +
                                                1}
                                        </td>
                                        <td>
                                            {
                                                classSubject.assignment
                                                    .termClass.termName
                                            }
                                        </td>
                                        <td>
                                            {
                                                classSubject.assignment.subject
                                                    .subjectName
                                            }
                                        </td>
                                        {/* <td className={styles.totalStudent}>
                                                {classSubject.totalStudents}
                                            </td> */}
                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`class-subject-management/attendance/${classSubject.assignment.assignmentId}`}
                                                className={clsx(
                                                    styles.viewButton,
                                                    styles.attendanceButton
                                                )}
                                            >
                                                <AiOutlineCheckSquare />
                                                {t("classSubject.attendance")}
                                            </Link>
                                            <Link
                                                href={`/grading/${classSubject.assignment.assignmentId}`}
                                                className={clsx(
                                                    styles.viewButton,
                                                    styles.gradingButton
                                                )}
                                            >
                                                <AiFillStar />{" "}
                                                {t("classSubject.grading")}
                                            </Link>
                                            <Link
                                                href={`/student-list/${classSubject.assignment.assignmentId}`}
                                                className={clsx(
                                                    styles.viewButton,
                                                    styles.listButton
                                                )}
                                            >
                                                <BiListCheck />{" "}
                                                {t("classSubject.printList")}
                                            </Link>
                                            <Link
                                                href={`../assignment/${classSubject.assignment.assignmentId}`}
                                                className={clsx(
                                                    styles.viewButton,
                                                    styles.assignment
                                                )}
                                            >
                                                <BiListCheck />{" "}
                                                {t("classSubject.assignment")}
                                            </Link>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    {totalClassByStatus <= parPage && (
                        <div className={styles.paginationWrapper}>
                            <Pagination
                                pageNumber={currentPage}
                                setPageNumber={setCurrentPage}
                                totalItem={totalClassByStatus}
                                parPage={parPage}
                                showItem={3}
                            />
                        </div>
                    )}
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ClassSubjectManagement;

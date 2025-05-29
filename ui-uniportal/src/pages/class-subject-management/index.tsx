import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useEffect, useState, useMemo } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { AiOutlineCheckSquare } from "react-icons/ai";
import { BiListCheck } from "react-icons/bi";
import clsx from "clsx";
import AuthGuard from "@/components/AuthGuard";
import { AppDispatch, RootState } from "@/store";
import { useDispatch, useSelector } from "react-redux";
import { useTranslation } from "react-i18next";
import { TypographyBody } from "@/components/TypographyBody";
import { getClassSubjectFollowLecturer } from "@/store/reducer/classReducer";
import { getCurrentSemesterAndSchoolYear } from "../credit-registration/[subject_id]/constants";
import SelectWithLabel from "@/components/SelectWithLabel";

const ClassSubjectManagement = () => {
    const { t } = useTranslation();
    const dispatch = useDispatch<AppDispatch>();

    const { classSubjectFollowLecturer } = useSelector(
        (state: RootState) => state.class
    );

    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(10);

    const [semester, setSemester] = useState<number>(1);
    const [schoolyear, setSchoolyear] = useState<number>(2026);

    useEffect(() => {
        dispatch(
            getClassSubjectFollowLecturer({
                semester: semester || undefined,
                schoolyear: schoolyear || undefined,
            })
        );
    }, [dispatch, schoolyear, semester]);

    const paginatedData = useMemo(() => {
        const start = (currentPage - 1) * parPage;
        const end = start + parPage;
        return classSubjectFollowLecturer.slice(start, end);
    }, [classSubjectFollowLecturer, currentPage, parPage]);

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox title={t("classSubject.title")}>
                {classSubjectFollowLecturer.length > 0 ? (
                    <div className={styles.box}>
                        <div className={styles.add}>
                            <Search
                                setParPage={setParPage}
                                setSearchValue={setSearchValue}
                                searchValue={searchValue}
                            />

                            <div className={styles.filterRow}>
                                <SelectWithLabel
                                    label=""
                                    value={semester}
                                    onChange={(e) =>
                                        setSemester(Number(e.target.value))
                                    }
                                    options={[
                                        { value: "", label: "Tất cả" },
                                        { value: 1, label: "Kỳ 1" },
                                        { value: 2, label: "Kỳ 2" },
                                    ]}
                                    className={styles.selectFilter}
                                />
                                <SelectWithLabel
                                    label=""
                                    value={schoolyear}
                                    onChange={(e) =>
                                        setSchoolyear(Number(e.target.value))
                                    }
                                    options={[
                                        { value: "", label: "Tất cả" },
                                        ...Array.from({ length: 6 }, (_, i) => {
                                            const year =
                                                new Date().getFullYear() +
                                                1 -
                                                i;
                                            return {
                                                value: year,
                                                label: `${year} - ${year + 1}`,
                                            };
                                        }),
                                    ]}
                                    className={styles.selectFilter}
                                />
                            </div>
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
                                    {paginatedData.map(
                                        (classSubject: any, index: number) => (
                                            <tr
                                                key={
                                                    classSubject.classStudentId
                                                }
                                            >
                                                <td>
                                                    {(currentPage - 1) *
                                                        parPage +
                                                        index +
                                                        1}
                                                </td>
                                                <td>
                                                    {classSubject.className}
                                                </td>
                                                <td>
                                                    {classSubject.subjectName}
                                                </td>

                                                <td
                                                    className={
                                                        styles.buttonAction
                                                    }
                                                >
                                                    <Link
                                                        href={`class-subject-management/attendance/${classSubject.classStudentId}`}
                                                        className={clsx(
                                                            styles.viewButton,
                                                            styles.attendanceButton
                                                        )}
                                                    >
                                                        <AiOutlineCheckSquare />
                                                        {t(
                                                            "classSubject.attendance"
                                                        )}
                                                    </Link>
                                                    <Link
                                                        href={`/student-list/${classSubject.classStudentId}`}
                                                        className={clsx(
                                                            styles.viewButton,
                                                            styles.listButton
                                                        )}
                                                    >
                                                        <BiListCheck />{" "}
                                                        {t(
                                                            "classSubject.printList"
                                                        )}
                                                    </Link>
                                                    <Link
                                                        href={`../assignment/${classSubject.classStudentId}`}
                                                        className={clsx(
                                                            styles.viewButton,
                                                            styles.assignment
                                                        )}
                                                    >
                                                        <BiListCheck />{" "}
                                                        {t(
                                                            "classSubject.assignment"
                                                        )}
                                                    </Link>
                                                </td>
                                            </tr>
                                        )
                                    )}
                                </tbody>
                            </table>
                        </div>

                        <div className={styles.paginationWrapper}>
                            <Pagination
                                pageNumber={currentPage}
                                setPageNumber={setCurrentPage}
                                totalItem={classSubjectFollowLecturer.length}
                                parPage={parPage}
                                showItem={3}
                            />
                        </div>
                    </div>
                ) : (
                    <TypographyBody tag="span" theme="lg">
                        Không có bản ghi
                    </TypographyBody>
                )}
            </BorderBox>
        </AuthGuard>
    );
};

export default ClassSubjectManagement;

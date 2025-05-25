import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useEffect, useState } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import clsx from "clsx";
import AuthGuard from "@/components/AuthGuard";
import { FcViewDetails } from "react-icons/fc";
import Link from "next/link";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import { getSubjectsFollowUser } from "@/store/reducer/creditRegistrationReducer";

const ClassRegistration = () => {
    const dispatch = useDispatch<AppDispatch>();
    const { subjects, successMessage, errorMessage } = useSelector(
        (state: RootState) => state.creditRegistration
    );

    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);

    useEffect(() => {
        dispatch(getSubjectsFollowUser());
    }, []);

    return (
        <AuthGuard allowedRoles={["student"]}>
            <BorderBox title="Đăng ký tín chỉ - Các lớp đang mở">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th>STT</th>
                                    <th>Mã môn học</th>
                                    <th>Môn học</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {subjects.map((cls, index) => (
                                    <tr key={index}>
                                        <td className={styles.tableCell}>
                                            {index + 1}
                                        </td>
                                        <td className={styles.tableCell}>
                                            {cls.subject?.subjectId}
                                        </td>

                                        <td className={styles.tableCell}>
                                            {cls.subject?.subjectName} (
                                            {cls.subject?.ltCredits}
                                            {cls.subject?.thCredits > 0 &&
                                                ` + ${cls.subject.thCredits}*`}
                                            )
                                        </td>

                                        <td
                                            className={clsx(
                                                styles.tableCell,
                                                styles.action
                                            )}
                                        >
                                            <Link
                                                href={`/credit-registration/${cls.subject?.subjectId}`}
                                            >
                                                <FcViewDetails />
                                            </Link>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                    {/* <div className={styles.paginationWrapper}>
                        <Pagination
                            pageNumber={currentPage}
                            setPageNumber={setCurrentPage}
                            totalItem={classSubjects.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div> */}
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ClassRegistration;

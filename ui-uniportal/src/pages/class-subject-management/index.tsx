import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useState } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { FaUserFriends } from "react-icons/fa";
import { AiOutlineCheckSquare, AiFillStar } from "react-icons/ai";
import { BiListCheck } from "react-icons/bi";
import clsx from "clsx";
import { IoMdAddCircle } from "react-icons/io";
import AuthGuard from "@/components/AuthGuard";

type ClassSubjectType = {
    id: string;
    className: string;
    subjectName: string;
    totalStudents: number;
};

const classSubjects: ClassSubjectType[] = [
    {
        id: "CS001",
        className: "Lớp Toán Cao Cấp 1",
        subjectName: "Toán Cao Cấp",
        totalStudents: 45,
    },
    {
        id: "CS002",
        className: "Lớp Lập Trình Web",
        subjectName: "Lập Trình Web",
        totalStudents: 30,
    },
    {
        id: "CS003",
        className: "Lớp Giải Tích 2",
        subjectName: "Giải Tích",
        totalStudents: 50,
    },
    {
        id: "CS004",
        className: "Lớp Cơ Sở Dữ Liệu",
        subjectName: "Cơ Sở Dữ Liệu",
        totalStudents: 35,
    },
    {
        id: "CS005",
        className: "Lớp Mạng Máy Tính",
        subjectName: "Mạng Máy Tính",
        totalStudents: 40,
    },
    {
        id: "CS006",
        className: "Lớp Vật Lý Đại Cương A1",
        subjectName: "Vật Lý Đại Cương",
        totalStudents: 55,
    },
];

const ClassSubjectManagement = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);

    const filteredClassSubjects = classSubjects.filter((classSubject) =>
        Object.values(classSubject).some((value) =>
            String(value).toLowerCase().includes(searchValue.toLowerCase())
        )
    );

    const startIndex = (currentPage - 1) * parPage;
    const endIndex = startIndex + parPage;
    const currentClassSubjects = filteredClassSubjects.slice(
        startIndex,
        endIndex
    );

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox title="Quản lý Lớp Học">
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
                            <IoMdAddCircle /> Thêm mới
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ minWidth: "80px" }}>No</th>
                                    <th style={{ minWidth: "200px" }}>
                                        Tên Lớp Học
                                    </th>
                                    <th style={{ minWidth: "200px" }}>
                                        Tên Môn Học
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        Tổng Số Sinh Viên
                                    </th>
                                    <th
                                        style={{
                                            minWidth: "200px",
                                            textAlign: "center",
                                        }}
                                    >
                                        Hành động
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {currentClassSubjects.map(
                                    (classSubject, index) => (
                                        <tr key={classSubject.id}>
                                            <td>
                                                {(currentPage - 1) * parPage +
                                                    index +
                                                    1}
                                            </td>
                                            <td>{classSubject.id}</td>
                                            <td>{classSubject.subjectName}</td>
                                            <td className={styles.totalStudent}>
                                                {classSubject.totalStudents}
                                            </td>
                                            <td className={styles.buttonAction}>
                                                <Link
                                                    href={`class-subject-management/attendance/${classSubject.id}`}
                                                    className={clsx(
                                                        styles.viewButton,
                                                        styles.attendanceButton
                                                    )}
                                                >
                                                    <AiOutlineCheckSquare />
                                                    Điểm danh
                                                </Link>
                                                <Link
                                                    href={`/grading/${classSubject.id}`}
                                                    className={clsx(
                                                        styles.viewButton,
                                                        styles.gradingButton
                                                    )}
                                                >
                                                    <AiFillStar /> Chấm điểm
                                                </Link>
                                                <Link
                                                    href={`/student-list/${classSubject.id}`}
                                                    className={clsx(
                                                        styles.viewButton,
                                                        styles.listButton
                                                    )}
                                                >
                                                    <BiListCheck /> In danh sách
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
                            totalItem={filteredClassSubjects.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ClassSubjectManagement;

import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useState, useEffect, useRef } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { FaCheck, FaEye } from "react-icons/fa";
import { AiFillEdit, AiOutlineClose } from "react-icons/ai";
import clsx from "clsx";
import { IoMdAddCircle } from "react-icons/io";
import AuthGuard from "@/components/AuthGuard";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import {
    getListTeachingAssignmentByLecturerId,
    getTeachingScheduleWithAssignID,
} from "@/store/reducer/teachingAssignment";
type TeachingScheduleDetail = {
    classroomId: number;
    lesson: string;
    dateTime: string;
    endDate: string;
    classType: "LT" | "TH";
};

type TeachingSchedule = {
    scheduleId: number;
    classroomId: number;
    lesson: string;
    dateTime: string;
    endDate: string;
    status: string;
    classType: "LT" | "TH";
    createdAt: string;
    scheduleDetails: TeachingScheduleDetail[];
    materials: { lt: string; th: string }[];
};

type TeachingScheduleFollowAssignId = {
    assignment: {
        assignmentId: number;
        assignmentType: any;
        lecturerId: number;
        lecturerName: string;
        subjectId: number;
        subjectName: string;
        termClassId: number;
        termClassName: string;
    };
    schedules: TeachingSchedule[];
};

const TeachingAssignmentWithDetails = () => {
    const dispatch = useDispatch<AppDispatch>();

    const { teachingAssignments } = useSelector(
        (state: RootState) => state.teachingAssignment
    );

    const teachingScheduleFollowAssignId = useSelector(
        (state: RootState) =>
            state.teachingAssignment.teachingScheduleFollowAssignId
    ) as TeachingScheduleFollowAssignId;

    const fetchedAssignmentIds = useRef<Set<number>>(new Set());

    const [currentPage, setCurrentPage] = useState(0);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(10);

    useEffect(() => {
        dispatch(
            getListTeachingAssignmentByLecturerId({
                currentPage: currentPage,
                parPage: parPage,
                searchValue: searchValue,
                token: window.localStorage.getItem("accessToken"),
            })
        );
    }, [currentPage, parPage, searchValue]);

    useEffect(() => {
        teachingAssignments.forEach((assignment) => {
            if (!fetchedAssignmentIds.current.has(assignment.assignmentId)) {
                fetchedAssignmentIds.current.add(assignment.assignmentId);
                dispatch(
                    getTeachingScheduleWithAssignID(assignment.assignmentId)
                );
            }
        });
    }, [teachingAssignments]);

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox title="Quản lý phân công giảng dạy">
                <div className={styles.box}>
                    <div className={styles.add}>
                        <Search
                            setParPage={setParPage}
                            setSearchValue={setSearchValue}
                            searchValue={searchValue}
                        />
                        <Link
                            href={"/teaching-assignment/create-edit"}
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> Thêm mới
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th>No</th>
                                    <th>Mã giảng viên</th>
                                    <th>Môn học</th>
                                    <th>Mã lớp</th>
                                    <th>Tiến độ</th>
                                    <th>Kỳ</th>
                                    <th>Trạng thái</th>
                                    <th>Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                {teachingAssignments.map(
                                    (assignment, index) => (
                                        <tr key={assignment.assignmentId}>
                                            <td className={styles.tableCell}>
                                                {index + 1}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.lecturerId}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.subjectId} -{" "}
                                                {assignment.subjectName}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.className}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.progress}
                                            </td>
                                            <td className={styles.tableCell}>
                                                {assignment.semester} -{" "}
                                                {assignment.schoolYears}
                                            </td>
                                            <td className={styles.tableCell}>
                                                <span
                                                    className={clsx(
                                                        styles.status,
                                                        teachingScheduleFollowAssignId
                                                            .schedules?.[0]
                                                            ?.status ===
                                                            "pending" &&
                                                            styles.pending,
                                                        teachingScheduleFollowAssignId
                                                            .schedules?.[0]
                                                            ?.status ===
                                                            "success" &&
                                                            styles.registered,
                                                        teachingScheduleFollowAssignId
                                                            .schedules?.[0]
                                                            ?.status ===
                                                            "cancel" &&
                                                            styles.cancel
                                                    )}
                                                >
                                                    {
                                                        teachingScheduleFollowAssignId
                                                            .schedules?.[0]
                                                            ?.status
                                                    }
                                                </span>
                                            </td>

                                            <td
                                                className={clsx(
                                                    styles.buttonAction,
                                                    styles.tableCell
                                                )}
                                            >
                                                <Link
                                                    href={`/teaching-schedule-request/view?id=${assignment.assignmentId}`}
                                                    className={
                                                        styles.viewButton
                                                    }
                                                >
                                                    <AiOutlineClose color="red" />
                                                </Link>
                                                <Link
                                                    href={`/teaching-schedule-request/create-edit?id=${assignment.assignmentId}&mode=edit`}
                                                    className={clsx(
                                                        styles.viewButton,
                                                        styles.viewButtonUpdate
                                                    )}
                                                >
                                                    <FaCheck />
                                                </Link>
                                            </td>
                                        </tr>
                                    )
                                )}
                            </tbody>
                        </table>
                    </div>

                    {teachingAssignments.length > parPage && (
                        <div className={styles.paginationWrapper}>
                            <Pagination
                                pageNumber={currentPage}
                                setPageNumber={setCurrentPage}
                                totalItem={teachingAssignments.length}
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

export default TeachingAssignmentWithDetails;

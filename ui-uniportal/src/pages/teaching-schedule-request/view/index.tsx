import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import { getListScheduleRequestFollowStatus } from "@/store/reducer/teachingAssignment";
import { FaEye } from "react-icons/fa";

const ScheduleRequestFollowStatus = () => {
    const dispatch = useDispatch<AppDispatch>();
    const [currentPage, setCurrentPage] = useState(0);
    const [perPage] = useState(100);
    const [searchValue] = useState("");

    const { scheduleRequests } = useSelector(
        (state: RootState) => state.teachingAssignment
    );

    useEffect(() => {
        dispatch(
            getListScheduleRequestFollowStatus({
                currentPage: currentPage,
                parPage: perPage,
                searchValue,
                statuses: "success",
            })
        );
    }, [currentPage, perPage, searchValue]);

    return (
        <BorderBox title="Lịch giảng dạy theo trạng thái">
            <div className={styles.tableWrapper}>
                <table className={styles.table}>
                    <thead className={styles.thead}>
                        <tr>
                            <th>STT</th>
                            <th>Môn học</th>
                            <th>Lớp</th>
                            <th>GV</th>
                            <th>Thời gian</th>
                            <th>Chi tiết</th>
                        </tr>
                    </thead>
                    <tbody>
                        {/* {scheduleRequests?.data?.content?.map((item, index) => (
                            <tr key={item.scheduleId}>
                                <td>{index + 1}</td>
                                <td>{item.assignment.subject.subjectName}</td>
                                <td>{item.assignment.termClass.termName}</td>
                                <td>{item.assignment.lecturer.lecturerName}</td>
                                <td>
                                    {new Date(item.dateTime).toLocaleDateString(
                                        "vi-VN"
                                    )}{" "}
                                    →{" "}
                                    {new Date(item.endDate).toLocaleDateString(
                                        "vi-VN"
                                    )}
                                </td>
                                <td>
                                    <button
                                        className={styles.viewButton}
                                        onClick={() =>
                                            console.log("Chi tiết:", item)
                                        }
                                    >
                                        <FaEye />
                                    </button>
                                </td>
                            </tr>
                        ))} */}
                    </tbody>
                </table>
            </div>
        </BorderBox>
    );
};

export default ScheduleRequestFollowStatus;

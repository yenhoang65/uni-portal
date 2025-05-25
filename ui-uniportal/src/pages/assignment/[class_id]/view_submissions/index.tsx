// import React, { useEffect, useState } from "react";
// import styles from "./styles.module.css";
// import AuthGuard from "@/components/AuthGuard";
// import BorderBox from "@/components/BorderBox";
// import { TypographyHeading } from "@/components/TypographyHeading";
// import { useTranslation } from "react-i18next";
// import { useDispatch, useSelector } from "react-redux";
// import { AppDispatch, RootState } from "@/store";
// import { useRouter } from "next/router";
// import {
//     getViewSubmissions,
//     gradeSubmission,
// } from "@/store/reducer/pointReducer";

// const assignment: any = {
//     id: "BT01",
//     title: "Bài tập về nhà số 1",
//     deadline: "2024-03-11T00:00:00",
//     max_score: 100,
// };

// const ViewSubmissions = () => {
//     const { t } = useTranslation();

//     const dispatch = useDispatch<AppDispatch>();
//     const {
//         viewSubmissions = [],
//         successMessage,
//         errorMessage,
//     } = useSelector((state: RootState) => state.point);

//     const router = useRouter();
//     const { id, class_id } = router.query;
//     const [state, setState] = useState({
//         score: 0,
//         feedback: "",
//     });
//     useEffect(() => {
//         if (id && class_id) {
//             dispatch(
//                 getViewSubmissions({
//                     classStudentId: class_id,
//                     gradeEventId: id,
//                 })
//             );
//         }
//     }, [id, class_id, dispatch]);

//     const [selectedId, setSelectedId] = useState<string | null>(null);

//     const inputHandle = (e: React.ChangeEvent<HTMLInputElement>) => {
//         setState({
//             ...state,
//             [e.target.name]: e.target.value,
//         });
//     };

//     const submittedList = viewSubmissions.filter(
//         (s: any) =>
//             s.submissionStatus === "ON_TIME" || s.submissionStatus === "LATE"
//     );
//     const notSubmittedList = viewSubmissions.filter(
//         (s: any) => s.submissionStatus === "NOT_SUBMITTED"
//     );

//     return (
//         <AuthGuard allowedRoles={["admin", "lecturer"]}>
//             <BorderBox
//                 title={t("submission.title", { title: assignment.title })}
//             >
//                 <div className={styles.viewSubmissionsContainer}>
//                     <h1 className={styles.viewSubmissionsTitle}>
//                         {t("submission.heading", { title: assignment.title })}
//                     </h1>
//                     <p className={styles.assignmentDeadline}>
//                         {t("submission.deadline")}:{" "}
//                         {new Date(assignment.deadline).toLocaleString()}
//                     </p>

//                     {/* BẢNG ĐÃ NỘP */}
//                     <div className={styles.submissionsScrollArea}>
//                         <div className={styles.submissionsList}>
//                             {submittedList.length === 0 ? (
//                                 <div className={styles.noSubmissions}>
//                                     {t("submission.empty")}
//                                 </div>
//                             ) : (
//                                 <table className={styles.submissionsTable}>
//                                     <thead className={styles.submissionsThead}>
//                                         <tr>
//                                             <th>{t("submission.index")}</th>
//                                             <th>{t("submission.studentId")}</th>
//                                             <th>
//                                                 {t("submission.studentName")}
//                                             </th>
//                                             <th>{t("submission.file")}</th>
//                                             <th>
//                                                 {t("submission.submitTime")}
//                                             </th>
//                                             <th>{t("submission.status")}</th>
//                                         </tr>
//                                     </thead>
//                                     <tbody className={styles.submissionsTbody}>
//                                         {submittedList.map(
//                                             (
//                                                 submission: any,
//                                                 index: number
//                                             ) => (
//                                                 <React.Fragment
//                                                     key={
//                                                         submission.id ||
//                                                         submission.studentId
//                                                     }
//                                                 >
//                                                     <tr
//                                                         className={
//                                                             styles.submissionRow
//                                                         }
//                                                         style={{
//                                                             cursor: "pointer",
//                                                             background:
//                                                                 selectedId ===
//                                                                 (submission.id ||
//                                                                     submission.studentId)
//                                                                     ? "#f0f7ff"
//                                                                     : undefined,
//                                                         }}
//                                                         onClick={() =>
//                                                             setSelectedId(
//                                                                 selectedId ===
//                                                                     (submission.id ||
//                                                                         submission.studentId)
//                                                                     ? null
//                                                                     : submission.id ||
//                                                                           submission.studentId
//                                                             )
//                                                         }
//                                                     >
//                                                         <td>{index + 1}</td>
//                                                         <td>
//                                                             {
//                                                                 submission.studentId
//                                                             }
//                                                         </td>
//                                                         <td>
//                                                             {
//                                                                 submission.studentName
//                                                             }
//                                                         </td>
//                                                         <td>
//                                                             {submission.fileUrl ? (
//                                                                 <a
//                                                                     href={
//                                                                         submission.fileUrl
//                                                                     }
//                                                                     target="_blank"
//                                                                     rel="noopener noreferrer"
//                                                                     className={
//                                                                         styles.fileLink
//                                                                     }
//                                                                     onClick={(
//                                                                         e
//                                                                     ) =>
//                                                                         e.stopPropagation()
//                                                                     }
//                                                                 >
//                                                                     Xem bài nộp
//                                                                 </a>
//                                                             ) : (
//                                                                 "-"
//                                                             )}
//                                                         </td>
//                                                         <td>
//                                                             {submission.submittedAt
//                                                                 ? new Date(
//                                                                       submission.submittedAt
//                                                                   ).toLocaleString()
//                                                                 : "-"}
//                                                         </td>
//                                                         <td>
//                                                             {submission.submissionStatus ===
//                                                                 "ON_TIME" && (
//                                                                 <span
//                                                                     className={
//                                                                         styles.onTimeStatus
//                                                                     }
//                                                                 >
//                                                                     {t(
//                                                                         "submission.onTime"
//                                                                     )}
//                                                                 </span>
//                                                             )}
//                                                             {submission.submissionStatus ===
//                                                                 "LATE" && (
//                                                                 <span
//                                                                     className={
//                                                                         styles.lateStatus
//                                                                     }
//                                                                 >
//                                                                     {t(
//                                                                         "submission.late"
//                                                                     )}
//                                                                 </span>
//                                                             )}
//                                                         </td>
//                                                     </tr>
//                                                     {selectedId ===
//                                                         (submission.id ||
//                                                             submission.studentId) && (
//                                                         <tr>
//                                                             <td
//                                                                 colSpan={6}
//                                                                 className={
//                                                                     styles.gradeExpandCell
//                                                                 }
//                                                             >
//                                                                 <form
//                                                                     className={
//                                                                         styles.gradeExpandRowVertical
//                                                                     }
//                                                                     onClick={(
//                                                                         e
//                                                                     ) =>
//                                                                         e.stopPropagation()
//                                                                     }
//                                                                     onSubmit={(
//                                                                         e
//                                                                     ) => {
//                                                                         e.preventDefault();
//                                                                         dispatch(
//                                                                             gradeSubmission(
//                                                                                 {
//                                                                                     studentGradeId:
//                                                                                         submission.studentGradeId,
//                                                                                     request:
//                                                                                         {
//                                                                                             score: state.score,
//                                                                                             feedback:
//                                                                                                 state.feedback,
//                                                                                         },
//                                                                                 }
//                                                                             )
//                                                                         );
//                                                                     }}
//                                                                 >
//                                                                     <div
//                                                                         className={
//                                                                             styles.gradeExpandRow
//                                                                         }
//                                                                     >
//                                                                         <div
//                                                                             className={
//                                                                                 styles.gradeHorizontalItem
//                                                                             }
//                                                                         >
//                                                                             <label
//                                                                                 className={
//                                                                                     styles.gradeLabel
//                                                                                 }
//                                                                             >
//                                                                                 {t(
//                                                                                     "submission.score"
//                                                                                 )}
//                                                                             </label>
//                                                                             <input
//                                                                                 type="number"
//                                                                                 min={
//                                                                                     0
//                                                                                 }
//                                                                                 max={
//                                                                                     submission.maxScore
//                                                                                 }
//                                                                                 name="score"
//                                                                                 value={
//                                                                                     state.score
//                                                                                 }
//                                                                                 className={
//                                                                                     styles.inputScore
//                                                                                 }
//                                                                                 onChange={
//                                                                                     inputHandle
//                                                                                 }
//                                                                                 placeholder={t(
//                                                                                     "submission.scorePlaceholder"
//                                                                                 )}
//                                                                             />
//                                                                         </div>
//                                                                         <div
//                                                                             className={
//                                                                                 styles.gradeHorizontalItem
//                                                                             }
//                                                                         >
//                                                                             <label
//                                                                                 className={
//                                                                                     styles.gradeLabel
//                                                                                 }
//                                                                             >
//                                                                                 {t(
//                                                                                     "submission.feedback"
//                                                                                 )}
//                                                                             </label>
//                                                                             <input
//                                                                                 name="feedback"
//                                                                                 value={
//                                                                                     state.feedback
//                                                                                 }
//                                                                                 className={
//                                                                                     styles.inputFeedback
//                                                                                 }
//                                                                                 onChange={
//                                                                                     inputHandle
//                                                                                 }
//                                                                                 placeholder={t(
//                                                                                     "submission.feedback"
//                                                                                 )}
//                                                                             />
//                                                                         </div>
//                                                                     </div>
//                                                                     <button
//                                                                         type="submit"
//                                                                         className={
//                                                                             styles.gradeButtonFull
//                                                                         }
//                                                                     >
//                                                                         {t(
//                                                                             "submission.save"
//                                                                         )}
//                                                                     </button>
//                                                                 </form>
//                                                             </td>
//                                                         </tr>
//                                                     )}
//                                                 </React.Fragment>
//                                             )
//                                         )}
//                                     </tbody>
//                                 </table>
//                             )}
//                         </div>
//                     </div>

//                     {/* BẢNG SINH VIÊN CHƯA NỘP */}
//                     <div className={styles.notSubmittedContainer}>
//                         <TypographyHeading
//                             tag="span"
//                             theme="lg"
//                             className={styles.notSubmittedTitle}
//                         >
//                             {t("submission.notSubmittedTitle")}
//                         </TypographyHeading>

//                         <table className={styles.notSubmittedTable}>
//                             <thead>
//                                 <tr>
//                                     <th>STT</th>
//                                     <th>{t("submission.studentId")}</th>
//                                     <th>{t("submission.studentName")}</th>
//                                     <th>{t("submission.status")}</th>
//                                 </tr>
//                             </thead>
//                             <tbody>
//                                 {notSubmittedList.length === 0 ? (
//                                     <tr>
//                                         <td
//                                             colSpan={4}
//                                             style={{
//                                                 textAlign: "center",
//                                                 color: "#888",
//                                             }}
//                                         >
//                                             {t("submission.allSubmitted")}
//                                         </td>
//                                     </tr>
//                                 ) : (
//                                     notSubmittedList.map(
//                                         (stu: any, idx: number) => (
//                                             <tr key={stu.studentId}>
//                                                 <td>{idx + 1}</td>
//                                                 <td>{stu.studentId}</td>
//                                                 <td>{stu.studentName}</td>
//                                                 <td>
//                                                     <span
//                                                         className={
//                                                             styles.lateStatus
//                                                         }
//                                                     >
//                                                         {t(
//                                                             "submission.notSubmitted"
//                                                         )}
//                                                     </span>
//                                                 </td>
//                                             </tr>
//                                         )
//                                     )
//                                 )}
//                             </tbody>
//                         </table>
//                     </div>
//                 </div>
//             </BorderBox>
//         </AuthGuard>
//     );
// };

// export default ViewSubmissions;

import React, { useEffect, useState } from "react";
import styles from "./styles.module.css";
import AuthGuard from "@/components/AuthGuard";
import BorderBox from "@/components/BorderBox";
import { TypographyHeading } from "@/components/TypographyHeading";
import { useTranslation } from "react-i18next";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import { useRouter } from "next/router";
import {
    getViewSubmissions,
    gradeSubmission,
    messageClear,
} from "@/store/reducer/pointReducer";
import toast from "react-hot-toast";

const assignment: any = {
    id: "BT01",
    title: "Bài tập về nhà số 1",
    deadline: "2024-03-11T00:00:00",
    max_score: 100,
};

const ViewSubmissions = () => {
    const { t } = useTranslation();

    const dispatch = useDispatch<AppDispatch>();
    const {
        viewSubmissions = [],
        successMessage,
        errorMessage,
    } = useSelector((state: RootState) => state.point);

    const router = useRouter();
    const { id, class_id } = router.query;
    const [state, setState] = useState({
        score: "",
        feedback: "",
    });

    useEffect(() => {
        if (id && class_id) {
            dispatch(
                getViewSubmissions({
                    classStudentId: class_id,
                    gradeEventId: id,
                })
            );
        }
    }, [id, class_id, dispatch]);

    const [selectedId, setSelectedId] = useState<string | null>(null);

    // Khi click vào 1 bài nộp, nếu đã có điểm/nhận xét thì set vào state
    const handleSelectRow = (submission: any) => {
        const rowId = submission.id || submission.studentId;
        setSelectedId(selectedId === rowId ? null : rowId);

        // Nếu mở row lên, setState bằng điểm và nhận xét có sẵn (nếu có)
        if (selectedId !== rowId) {
            setState({
                score:
                    submission.score !== undefined && submission.score !== null
                        ? submission.score.toString()
                        : "",
                feedback: submission.feedback || "",
            });
        }
    };

    const inputHandle = (e: React.ChangeEvent<HTMLInputElement>) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };

    const feedbackHandle = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
    ) => {
        setState({
            ...state,
            feedback: e.target.value,
        });
    };

    const submittedList = viewSubmissions.filter(
        (s: any) =>
            s.submissionStatus === "ON_TIME" || s.submissionStatus === "LATE"
    );
    const notSubmittedList = viewSubmissions.filter(
        (s: any) => s.submissionStatus === "NOT_SUBMITTED"
    );

    useEffect(() => {
        if (successMessage) {
            toast.success(successMessage);
            dispatch(messageClear());

            if (id && class_id) {
                dispatch(
                    getViewSubmissions({
                        classStudentId: class_id,
                        gradeEventId: id,
                    })
                );
            }
        }
        if (errorMessage) {
            toast.error(errorMessage);
            dispatch(messageClear());
        }
    }, [successMessage, errorMessage, id, class_id]);

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox
                title={t("submission.title", { title: assignment.title })}
            >
                <div className={styles.viewSubmissionsContainer}>
                    <h1 className={styles.viewSubmissionsTitle}>
                        {t("submission.heading", { title: assignment.title })}
                    </h1>
                    <p className={styles.assignmentDeadline}>
                        {t("submission.deadline")}:{" "}
                        {new Date(assignment.deadline).toLocaleString()}
                    </p>

                    {/* BẢNG ĐÃ NỘP */}
                    <div className={styles.submissionsScrollArea}>
                        <div className={styles.submissionsList}>
                            {submittedList.length === 0 ? (
                                <div className={styles.noSubmissions}>
                                    {t("submission.empty")}
                                </div>
                            ) : (
                                <table className={styles.submissionsTable}>
                                    <thead className={styles.submissionsThead}>
                                        <tr>
                                            <th>{t("submission.index")}</th>
                                            <th>{t("submission.studentId")}</th>
                                            <th>
                                                {t("submission.studentName")}
                                            </th>
                                            <th>{t("submission.file")}</th>
                                            <th>
                                                {t("submission.submitTime")}
                                            </th>
                                            <th>{t("submission.status")}</th>
                                            <th>{t("submission.score")}</th>
                                            <th>{t("submission.feedback")}</th>
                                        </tr>
                                    </thead>
                                    <tbody className={styles.submissionsTbody}>
                                        {submittedList.map(
                                            (
                                                submission: any,
                                                index: number
                                            ) => {
                                                const rowId =
                                                    submission.id ||
                                                    submission.studentId;
                                                return (
                                                    <React.Fragment key={rowId}>
                                                        <tr
                                                            className={
                                                                styles.submissionRow
                                                            }
                                                            style={{
                                                                cursor: "pointer",
                                                                background:
                                                                    selectedId ===
                                                                    rowId
                                                                        ? "#f0f7ff"
                                                                        : undefined,
                                                            }}
                                                            onClick={() =>
                                                                handleSelectRow(
                                                                    submission
                                                                )
                                                            }
                                                        >
                                                            <td>{index + 1}</td>
                                                            <td>
                                                                {
                                                                    submission.studentId
                                                                }
                                                            </td>
                                                            <td>
                                                                {
                                                                    submission.studentName
                                                                }
                                                            </td>
                                                            <td>
                                                                {submission.fileUrl ? (
                                                                    <a
                                                                        href={
                                                                            submission.fileUrl
                                                                        }
                                                                        target="_blank"
                                                                        rel="noopener noreferrer"
                                                                        className={
                                                                            styles.fileLink
                                                                        }
                                                                        onClick={(
                                                                            e
                                                                        ) =>
                                                                            e.stopPropagation()
                                                                        }
                                                                    >
                                                                        Xem bài
                                                                        nộp
                                                                    </a>
                                                                ) : (
                                                                    "-"
                                                                )}
                                                            </td>
                                                            <td>
                                                                {submission.submittedAt
                                                                    ? new Date(
                                                                          submission.submittedAt
                                                                      ).toLocaleString()
                                                                    : "-"}
                                                            </td>
                                                            <td>
                                                                {submission.submissionStatus ===
                                                                    "ON_TIME" && (
                                                                    <span
                                                                        className={
                                                                            styles.onTimeStatus
                                                                        }
                                                                    >
                                                                        {t(
                                                                            "submission.onTime"
                                                                        )}
                                                                    </span>
                                                                )}
                                                                {submission.submissionStatus ===
                                                                    "LATE" && (
                                                                    <span
                                                                        className={
                                                                            styles.lateStatus
                                                                        }
                                                                    >
                                                                        {t(
                                                                            "submission.late"
                                                                        )}
                                                                    </span>
                                                                )}
                                                            </td>
                                                            <td>
                                                                {submission.score !==
                                                                    undefined &&
                                                                submission.score !==
                                                                    null &&
                                                                submission.score !==
                                                                    ""
                                                                    ? submission.score
                                                                    : "-"}
                                                            </td>
                                                            <td>
                                                                {submission.feedback
                                                                    ? submission.feedback
                                                                    : "-"}
                                                            </td>
                                                        </tr>
                                                        {selectedId ===
                                                            rowId && (
                                                            <tr>
                                                                <td
                                                                    colSpan={8}
                                                                    className={
                                                                        styles.gradeExpandCell
                                                                    }
                                                                >
                                                                    <form
                                                                        className={
                                                                            styles.gradeExpandRowVertical
                                                                        }
                                                                        onClick={(
                                                                            e
                                                                        ) =>
                                                                            e.stopPropagation()
                                                                        }
                                                                        onSubmit={(
                                                                            e
                                                                        ) => {
                                                                            e.preventDefault();
                                                                            dispatch(
                                                                                gradeSubmission(
                                                                                    {
                                                                                        studentGradeId:
                                                                                            submission.studentGradeId,
                                                                                        score: Number(
                                                                                            state.score
                                                                                        ),
                                                                                        feedback:
                                                                                            state.feedback,
                                                                                    }
                                                                                )
                                                                            );
                                                                        }}
                                                                    >
                                                                        <div
                                                                            className={
                                                                                styles.gradeExpandRow
                                                                            }
                                                                        >
                                                                            <div
                                                                                className={
                                                                                    styles.gradeHorizontalItem
                                                                                }
                                                                            >
                                                                                <label
                                                                                    className={
                                                                                        styles.gradeLabel
                                                                                    }
                                                                                >
                                                                                    {t(
                                                                                        "submission.score"
                                                                                    )}
                                                                                </label>
                                                                                <input
                                                                                    type="number"
                                                                                    min={
                                                                                        0
                                                                                    }
                                                                                    max={
                                                                                        submission.maxScore
                                                                                    }
                                                                                    name="score"
                                                                                    value={
                                                                                        state.score
                                                                                    }
                                                                                    className={
                                                                                        styles.inputScore
                                                                                    }
                                                                                    onChange={
                                                                                        inputHandle
                                                                                    }
                                                                                    placeholder={t(
                                                                                        "submission.scorePlaceholder"
                                                                                    )}
                                                                                />
                                                                            </div>
                                                                            <div
                                                                                className={
                                                                                    styles.gradeHorizontalItem
                                                                                }
                                                                            >
                                                                                <label
                                                                                    className={
                                                                                        styles.gradeLabel
                                                                                    }
                                                                                >
                                                                                    {t(
                                                                                        "submission.feedback"
                                                                                    )}
                                                                                </label>
                                                                                <input
                                                                                    name="feedback"
                                                                                    value={
                                                                                        state.feedback
                                                                                    }
                                                                                    className={
                                                                                        styles.inputFeedback
                                                                                    }
                                                                                    onChange={
                                                                                        feedbackHandle
                                                                                    }
                                                                                    placeholder={t(
                                                                                        "submission.feedback"
                                                                                    )}
                                                                                />
                                                                            </div>
                                                                        </div>
                                                                        <button
                                                                            type="submit"
                                                                            className={
                                                                                styles.gradeButtonFull
                                                                            }
                                                                        >
                                                                            {t(
                                                                                "submission.save"
                                                                            )}
                                                                        </button>
                                                                    </form>
                                                                </td>
                                                            </tr>
                                                        )}
                                                    </React.Fragment>
                                                );
                                            }
                                        )}
                                    </tbody>
                                </table>
                            )}
                        </div>
                    </div>

                    {/* BẢNG SINH VIÊN CHƯA NỘP */}
                    <div className={styles.notSubmittedContainer}>
                        <TypographyHeading
                            tag="span"
                            theme="lg"
                            className={styles.notSubmittedTitle}
                        >
                            {t("submission.notSubmittedTitle")}
                        </TypographyHeading>

                        <table className={styles.notSubmittedTable}>
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>{t("submission.studentId")}</th>
                                    <th>{t("submission.studentName")}</th>
                                    <th>{t("submission.status")}</th>
                                </tr>
                            </thead>
                            <tbody>
                                {notSubmittedList.length === 0 ? (
                                    <tr>
                                        <td
                                            colSpan={4}
                                            style={{
                                                textAlign: "center",
                                                color: "#888",
                                            }}
                                        >
                                            {t("submission.allSubmitted")}
                                        </td>
                                    </tr>
                                ) : (
                                    notSubmittedList.map(
                                        (stu: any, idx: number) => (
                                            <tr key={stu.studentId}>
                                                <td>{idx + 1}</td>
                                                <td>{stu.studentId}</td>
                                                <td>{stu.studentName}</td>
                                                <td>
                                                    <span
                                                        className={
                                                            styles.lateStatus
                                                        }
                                                    >
                                                        {t(
                                                            "submission.notSubmitted"
                                                        )}
                                                    </span>
                                                </td>
                                            </tr>
                                        )
                                    )
                                )}
                            </tbody>
                        </table>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ViewSubmissions;

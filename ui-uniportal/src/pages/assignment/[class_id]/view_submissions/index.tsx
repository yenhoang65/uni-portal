import React, { useState } from "react";
import styles from "./styles.module.css";
import AuthGuard from "@/components/AuthGuard";
import BorderBox from "@/components/BorderBox";

interface Submission {
    id: string;
    student_id: string;
    student_name: string;
    file_url: string;
    submission_time: string;
    is_late: boolean;
    score?: number | null;
    feedback?: string;
}

interface Assignment {
    id: string;
    title: string;
    deadline: string;
}

const mockSubmissions: Submission[] = [
    {
        id: "1",
        student_id: "SV001",
        student_name: "Nguyễn Văn A",
        file_url: "https://example.com/bai-tap-1-sv001.pdf",
        submission_time: "2024-03-10T23:50:00",
        is_late: false,
    },
    // ...các dòng khác giữ nguyên
];

const assignment: Assignment = {
    id: "BT01",
    title: "Bài tập về nhà số 1",
    deadline: "2024-03-11T00:00:00",
};

const ViewSubmissions = () => {
    const [submissions, setSubmissions] =
        useState<Submission[]>(mockSubmissions);
    const [selectedId, setSelectedId] = useState<string | null>(null);
    const [grading, setGrading] = useState<{
        [id: string]: { score: string; feedback: string };
    }>({});

    const handleInputChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
        id: string
    ) => {
        const { name, value } = e.target;
        setGrading((prev) => ({
            ...prev,
            [id]: {
                ...prev[id],
                [name]: value,
            },
        }));
    };

    const handleGrade = (id: string) => {
        const { score = "", feedback = "" } = grading[id] || {};
        setSubmissions((prev) =>
            prev.map((s) =>
                s.id === id
                    ? {
                          ...s,
                          score: score !== "" ? Number(score) : null,
                          feedback: feedback,
                      }
                    : s
            )
        );
        setSelectedId(null);
        setGrading((prev) => ({
            ...prev,
            [id]: { score: "", feedback: "" },
        }));
    };

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox
                title={`Danh sách sinh viên đã nộp "${assignment.title}"`}
            >
                <div className={styles.viewSubmissionsContainer}>
                    <h1 className={styles.viewSubmissionsTitle}>
                        Bài nộp: "{assignment.title}"
                    </h1>
                    <p className={styles.assignmentDeadline}>
                        Hạn nộp:{" "}
                        {new Date(assignment.deadline).toLocaleString()}
                    </p>

                    <div className={styles.submissionsScrollArea}>
                        <div className={styles.submissionsList}>
                            {submissions.length === 0 ? (
                                <div className={styles.noSubmissions}>
                                    Chưa có sinh viên nào nộp bài.
                                </div>
                            ) : (
                                <table className={styles.submissionsTable}>
                                    <thead className={styles.submissionsThead}>
                                        <tr>
                                            <th>STT</th>
                                            <th>Mã SV</th>
                                            <th>Tên SV</th>
                                            <th>File</th>
                                            <th>Thời gian nộp</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>
                                    <tbody className={styles.submissionsTbody}>
                                        {submissions.map(
                                            (submission, index) => (
                                                <React.Fragment
                                                    key={submission.id}
                                                >
                                                    <tr
                                                        className={
                                                            styles.submissionRow
                                                        }
                                                        style={{
                                                            cursor: "pointer",
                                                            background:
                                                                selectedId ===
                                                                submission.id
                                                                    ? "#f0f7ff"
                                                                    : undefined,
                                                        }}
                                                        onClick={() =>
                                                            setSelectedId(
                                                                selectedId ===
                                                                    submission.id
                                                                    ? null
                                                                    : submission.id
                                                            )
                                                        }
                                                    >
                                                        <td>{index + 1}</td>
                                                        <td>
                                                            {
                                                                submission.student_id
                                                            }
                                                        </td>
                                                        <td>
                                                            {
                                                                submission.student_name
                                                            }
                                                        </td>
                                                        <td>
                                                            <a
                                                                href={
                                                                    submission.file_url
                                                                }
                                                                target="_blank"
                                                                rel="noopener noreferrer"
                                                                className={
                                                                    styles.fileLink
                                                                }
                                                                onClick={(e) =>
                                                                    e.stopPropagation()
                                                                }
                                                            >
                                                                {submission.file_url
                                                                    .split("/")
                                                                    .pop()}
                                                            </a>
                                                        </td>
                                                        <td>
                                                            {new Date(
                                                                submission.submission_time
                                                            ).toLocaleString()}
                                                        </td>
                                                        <td>
                                                            {submission.is_late ? (
                                                                <span
                                                                    className={
                                                                        styles.lateStatus
                                                                    }
                                                                >
                                                                    Nộp muộn
                                                                </span>
                                                            ) : (
                                                                <span
                                                                    className={
                                                                        styles.onTimeStatus
                                                                    }
                                                                >
                                                                    Đúng hạn
                                                                </span>
                                                            )}
                                                        </td>
                                                    </tr>
                                                    {selectedId ===
                                                        submission.id && (
                                                        <tr>
                                                            <td
                                                                colSpan={6}
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
                                                                        handleGrade(
                                                                            submission.id
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
                                                                                Chấm
                                                                                điểm
                                                                            </label>
                                                                            <input
                                                                                type="number"
                                                                                min={
                                                                                    0
                                                                                }
                                                                                max={
                                                                                    10
                                                                                }
                                                                                name="score"
                                                                                value={
                                                                                    grading[
                                                                                        submission
                                                                                            .id
                                                                                    ]
                                                                                        ?.score ??
                                                                                    submission.score?.toString() ??
                                                                                    ""
                                                                                }
                                                                                className={
                                                                                    styles.inputScore
                                                                                }
                                                                                onChange={(
                                                                                    e
                                                                                ) =>
                                                                                    handleInputChange(
                                                                                        e,
                                                                                        submission.id
                                                                                    )
                                                                                }
                                                                                placeholder="Điểm"
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
                                                                                Nhận
                                                                                xét
                                                                            </label>
                                                                            <textarea
                                                                                name="feedback"
                                                                                value={
                                                                                    grading[
                                                                                        submission
                                                                                            .id
                                                                                    ]
                                                                                        ?.feedback ??
                                                                                    submission.feedback ??
                                                                                    ""
                                                                                }
                                                                                className={
                                                                                    styles.inputFeedback
                                                                                }
                                                                                onChange={(
                                                                                    e
                                                                                ) =>
                                                                                    handleInputChange(
                                                                                        e,
                                                                                        submission.id
                                                                                    )
                                                                                }
                                                                                placeholder="Nhận xét"
                                                                            />
                                                                        </div>
                                                                    </div>
                                                                    <button
                                                                        type="submit"
                                                                        className={
                                                                            styles.gradeButtonFull
                                                                        }
                                                                    >
                                                                        Lưu
                                                                    </button>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                    )}
                                                </React.Fragment>
                                            )
                                        )}
                                    </tbody>
                                </table>
                            )}
                        </div>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default ViewSubmissions;

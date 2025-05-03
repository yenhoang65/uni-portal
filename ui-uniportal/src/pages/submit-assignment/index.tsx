import { useState } from "react";
import AuthGuard from "@/components/AuthGuard";
import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { Button } from "@/components/Button";
import Link from "next/link";

type Assignment = {
    exercise_id: string;
    title: string;
    deadline: string;
    submitted_at: string | null;
    is_late: boolean;
    file_url: string | null;
};

const AssignmentList = () => {
    const [assignments, setAssignments] = useState<Assignment[]>([
        {
            exercise_id: "1",
            title: "Assignment 1",
            deadline: "2025-05-10",
            submitted_at: "2025-05-09",
            is_late: false,
            file_url: "https://drive.google.com/file1",
        },
        {
            exercise_id: "2",
            title: "Assignment 2",
            deadline: "2025-05-08",
            submitted_at: null,
            is_late: false,
            file_url: null,
        },
        {
            exercise_id: "3",
            title: "Assignment 3",
            deadline: "2025-05-07",
            submitted_at: "2025-05-08",
            is_late: true,
            file_url: "https://drive.google.com/file3",
        },
    ]);

    const [currentAssignmentId, setCurrentAssignmentId] = useState<
        string | null
    >(null);
    const [linkInput, setLinkInput] = useState("");

    const handleSubmitLink = () => {
        if (!linkInput.trim()) return;

        const updated = assignments.map((a) =>
            a.exercise_id === currentAssignmentId
                ? {
                      ...a,
                      submitted_at: new Date().toISOString().split("T")[0],
                      is_late: new Date() > new Date(a.deadline),
                      file_url: linkInput,
                  }
                : a
        );

        setAssignments(updated);
    };

    const notSubmitted = assignments.filter((a) => !a.submitted_at);
    const submittedOnTime = assignments.filter(
        (a) => a.submitted_at && !a.is_late
    );
    const submittedLate = assignments.filter(
        (a) => a.submitted_at && a.is_late
    );

    const renderSection = (title: string, items: Assignment[]) => (
        <>
            <h2 className={styles.sectionTitle}>{title}</h2>
            <div className={styles.assignmentGrid}>
                {items.map((assignment) => {
                    const statusClass = assignment.submitted_at
                        ? assignment.is_late
                            ? styles.late
                            : styles.submitted
                        : styles.notSubmitted;

                    return (
                        <div
                            key={assignment.exercise_id}
                            className={`${styles.assignmentItem} ${statusClass}`}
                        >
                            <h3 className={styles.assignmentTitle}>
                                {assignment.title}
                            </h3>
                            <p>
                                <strong>Deadline:</strong> {assignment.deadline}
                            </p>
                            {assignment.submitted_at ? (
                                <>
                                    <p>
                                        <strong>Submitted:</strong>{" "}
                                        {assignment.submitted_at}{" "}
                                        {assignment.is_late && "(Late)"}
                                    </p>
                                    <p>
                                        <strong>Link:</strong>{" "}
                                        <a
                                            href={assignment.file_url!}
                                            target="_blank"
                                            rel="noreferrer"
                                        >
                                            {assignment.file_url}
                                        </a>
                                    </p>
                                </>
                            ) : (
                                <>
                                    <p style={{ color: "#777" }}>Chưa nộp</p>
                                    <div className={styles.action}>
                                        <Link
                                            href={`/submit-assignment/${assignment.exercise_id}`}
                                            className={styles.submitBtn}
                                        >
                                            Nộp bài tập
                                        </Link>
                                    </div>
                                </>
                            )}
                        </div>
                    );
                })}
            </div>
        </>
    );

    return (
        <AuthGuard allowedRoles={["student"]}>
            <BorderBox title="Danh sách bài tập">
                {renderSection("📌 Bài tập chưa nộp", notSubmitted)}
                {renderSection("✅ Bài tập đã nộp đúng hạn", submittedOnTime)}
                {renderSection("⏰ Bài tập nộp muộn", submittedLate)}
            </BorderBox>
        </AuthGuard>
    );
};

export default AssignmentList;

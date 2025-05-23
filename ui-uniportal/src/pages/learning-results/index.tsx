import React, { useState } from "react";
import styles from "./styles.module.css";
import { FaBook } from "react-icons/fa";
import { TypographyHeading } from "@/components/TypographyHeading";
import { TypographyBody } from "@/components/TypographyBody";
import SelectWithLabel from "@/components/SelectWithLabel";
import AuthGuard from "@/components/AuthGuard";
import BorderBox from "@/components/BorderBox";

// Mock data
const summary = {
    gpa4: 3.49,
    gpa10: 8.44,
    gpa4Rank: "Giỏi",
    gpa10Rank: "Giỏi",
    creditsAccumulated: 138,
    creditsTotal: 138,
    creditsRegistered: 138,
};

const studyResults = [
    {
        subjectCode: "111125",
        subjectName: "Đại số tuyến tính",
        credits: 2,
        coefficient: 1,
        composition: "BTC : 9.5 - CC : 8.5",
        examScore: 9,
        avgScore: 9,
        score: 4,
        letter: "A+",
        isElective: false,
        note: "",
    },
    {
        subjectCode: "921113",
        subjectName: "Giáo dục thể chất 1",
        credits: 1,
        coefficient: 1,
        composition: "D1 : 8",
        examScore: 8,
        avgScore: 8,
        score: 3,
        letter: "B+",
        isElective: false,
        note: "",
    },
    // ... (add more rows as needed)
];

const semesterOptions = [
    { value: "", label: "--- Chọn học kỳ -" },
    { value: "1", label: "Học kỳ 1" },
    { value: "2", label: "Học kỳ 2" },
    { value: "3", label: "Học kỳ hè" },
];

const schoolYearOptions = [
    { value: "", label: "--- Chọn năm học -" },
    { value: "2023-2024", label: "2023-2024" },
    { value: "2024-2025", label: "2024-2025" },
    { value: "2025-2026", label: "2025-2026" },
];

const StudyResult = () => {
    const [semester, setSemester] = useState("");
    const [schoolYear, setSchoolYear] = useState("");

    // Bạn có thể lọc studyResults theo semester/schoolYear nếu muốn

    return (
        <AuthGuard allowedRoles={["student"]}>
            <BorderBox title="Kết quả học tập">
                <div className={styles.container}>
                    <div className={styles.filterRow}>
                        <SelectWithLabel
                            label="Học kỳ"
                            name="semester"
                            value={semester}
                            onChange={(e) => setSemester(e.target.value)}
                            options={semesterOptions}
                            className={styles.selectFilter}
                        />
                        <SelectWithLabel
                            label="Năm học"
                            name="schoolYear"
                            value={schoolYear}
                            onChange={(e) => setSchoolYear(e.target.value)}
                            options={schoolYearOptions}
                            className={styles.selectFilter}
                        />
                    </div>

                    <div className={styles.summaryRow}>
                        <TypographyBody tag="span" theme="md-bold" color="#222">
                            Xếp loại học tập (Hệ 4):{" "}
                            <span className={styles.valueRed}>
                                {summary.gpa4Rank}
                            </span>
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md-bold" color="#222">
                            Xếp loại học tập (Hệ 10):{" "}
                            <span className={styles.valueRed}>
                                {summary.gpa10Rank}
                            </span>
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md-bold" color="#222">
                            TBC học tập (Hệ 4):{" "}
                            <span className={styles.valueRed}>
                                {summary.gpa4}
                            </span>
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md-bold" color="#222">
                            TBC tích lũy (Hệ 10):{" "}
                            <span className={styles.valueRed}>
                                {summary.gpa10}
                            </span>
                        </TypographyBody>
                    </div>
                    <div className={styles.summaryRow}>
                        <TypographyBody tag="span" theme="md-bold" color="#222">
                            TBC tích lũy (Hệ 4):{" "}
                            <span className={styles.valueRed}>
                                {summary.gpa4}
                            </span>
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md-bold" color="#222">
                            Số tín chỉ tích lũy:{" "}
                            <span className={styles.valueRed}>
                                {summary.creditsAccumulated} /{" "}
                                {summary.creditsTotal}
                            </span>
                        </TypographyBody>
                        <TypographyBody tag="span" theme="md-bold" color="#222">
                            Số tín chỉ học tập:{" "}
                            <span className={styles.valueRed}>
                                {summary.creditsRegistered}
                            </span>
                        </TypographyBody>
                    </div>
                    <div className={styles.tableWrapper}>
                        <table className={styles.resultTable}>
                            <thead>
                                <tr>
                                    <th className={styles.center}>STT</th>
                                    <th className={styles.center}>
                                        Mã học phần
                                    </th>
                                    <th>Tên học phần</th>
                                    <th className={styles.center}>
                                        Số tín chỉ
                                    </th>
                                    <th className={styles.center}>Hệ số</th>
                                    <th>Điểm thành phần</th>
                                    <th className={styles.center}>Điểm thi</th>
                                    <th className={styles.center}>TBCHP</th>
                                    <th className={styles.center}>Điểm số</th>
                                    <th className={styles.center}>Điểm chữ</th>
                                    <th className={styles.center}>
                                        Môn tự chọn
                                    </th>
                                    <th>Ghi chú</th>
                                </tr>
                            </thead>
                            <tbody>
                                {studyResults.map((r, idx) => (
                                    <tr
                                        key={r.subjectCode}
                                        className={
                                            idx % 2 === 1
                                                ? styles.rowAlt
                                                : undefined
                                        }
                                    >
                                        <td className={styles.center}>
                                            {idx + 1}
                                        </td>
                                        <td className={styles.center}>
                                            {r.subjectCode}
                                        </td>
                                        <td>{r.subjectName}</td>
                                        <td className={styles.center}>
                                            {r.credits}
                                        </td>
                                        <td className={styles.center}>
                                            {r.coefficient}
                                        </td>
                                        <td>{r.composition}</td>
                                        <td className={styles.center}>
                                            {r.examScore}
                                        </td>
                                        <td className={styles.center}>
                                            {r.avgScore}
                                        </td>
                                        <td className={styles.center}>
                                            {r.score}
                                        </td>
                                        <td className={styles.center}>
                                            {r.letter}
                                        </td>
                                        <td className={styles.center}>
                                            {r.isElective ? (
                                                <span
                                                    className={
                                                        styles.electiveCheck
                                                    }
                                                >
                                                    ✔
                                                </span>
                                            ) : (
                                                ""
                                            )}
                                        </td>
                                        <td>{r.note}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>{" "}
            </BorderBox>
        </AuthGuard>
    );
};

export default StudyResult;

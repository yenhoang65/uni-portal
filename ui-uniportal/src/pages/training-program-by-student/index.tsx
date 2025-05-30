import React, { useEffect, useState } from "react";
import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { TypographyHeading } from "@/components/TypographyHeading";
import { TypographyBody } from "@/components/TypographyBody";
import AuthGuard from "@/components/AuthGuard";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import { getTrainingProgramByStu } from "@/store/reducer/trainingProgramReducer";

const mockTrainingProgram = {
    trainingProgramId: 101,
    trainingProgramName: "Công nghệ thông tin",
    schoolYear: "2024-2025",
    subjects: [
        {
            subjectId: 1,
            subjectName: "Lập trình C++ cơ bản",
            prerequisiteFor: [
                { subjectId: 2, subjectName: "Lập trình C++ nâng cao" },
            ],
        },
        {
            subjectId: 2,
            subjectName: "Lập trình C++ nâng cao",
            prerequisiteFor: [],
        },
        {
            subjectId: 3,
            subjectName: "Cấu trúc dữ liệu & Giải thuật",
            prerequisiteFor: [{ subjectId: 4, subjectName: "Cơ sở dữ liệu" }],
        },
        {
            subjectId: 4,
            subjectName: "Cơ sở dữ liệu",
            prerequisiteFor: [],
        },
    ],
};
// --------- END MOCK DATA ---------

const TrainingProgramByStudent = () => {
    // Nếu muốn fetch từ API, dùng useEffect, ở đây dùng mockData
    const [trainingProgram, setTrainingProgram] =
        useState<typeof mockTrainingProgram>(mockTrainingProgram);

    useEffect(() => {
        // Giả lập load dữ liệu
        setTrainingProgram(mockTrainingProgram);
    }, []);

    const dispatch = useDispatch<AppDispatch>();
    const { subjectFollowTrainingProgram, successMessage, errorMessage } =
        useSelector((state: RootState) => state.trainingProgram);

    useEffect(() => {
        dispatch(getTrainingProgramByStu());
    }, []);

    return (
        <AuthGuard allowedRoles={["student"]}>
            <BorderBox
                title={`Chương trình Đào tạo: ${
                    trainingProgram?.trainingProgramId || ""
                } - ${trainingProgram?.trainingProgramName || ""}`}
            >
                <div className={styles.groupTitle}>
                    <div className={styles.group}>
                        <div>
                            <TypographyHeading tag="span" theme="lg">
                                Mã CTĐT:
                            </TypographyHeading>{" "}
                            {trainingProgram?.trainingProgramId}
                        </div>
                        <div>
                            <TypographyHeading tag="span" theme="lg">
                                Tên CTĐT:
                            </TypographyHeading>{" "}
                            {trainingProgram?.trainingProgramName}
                        </div>
                        <div>
                            <TypographyHeading tag="span" theme="lg">
                                Năm học:
                            </TypographyHeading>{" "}
                            {trainingProgram?.schoolYear}
                        </div>
                    </div>
                </div>
                <div className={styles.action}>
                    <TypographyHeading
                        tag="h5"
                        theme="xl"
                        color="var(--secondary-blue)"
                        className={styles.headingSubject}
                    >
                        Các môn học trong chương trình
                    </TypographyHeading>
                </div>
                {trainingProgram?.subjects?.length > 0 ? (
                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ textAlign: "center" }}>
                                        Mã học phần
                                    </th>
                                    <th>Tên học phần</th>
                                    <th style={{ textAlign: "center" }}>
                                        Môn học phụ thuộc
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {trainingProgram.subjects.map((subject) => (
                                    <tr key={subject.subjectId}>
                                        <td style={{ textAlign: "center" }}>
                                            {subject.subjectId}
                                        </td>
                                        <td>{subject.subjectName}</td>
                                        <td style={{ textAlign: "center" }}>
                                            {subject.prerequisiteFor?.length > 0
                                                ? subject.prerequisiteFor.map(
                                                      (pre, idx) => (
                                                          <span
                                                              key={
                                                                  pre.subjectId
                                                              }
                                                          >
                                                              {pre.subjectName}
                                                              {idx <
                                                              subject
                                                                  .prerequisiteFor
                                                                  .length -
                                                                  1
                                                                  ? ", "
                                                                  : ""}
                                                          </span>
                                                      )
                                                  )
                                                : "—"}
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                ) : (
                    <p className={styles.noSubjects}>
                        Chưa có môn học nào trong chương trình này.
                    </p>
                )}
            </BorderBox>
        </AuthGuard>
    );
};

export default TrainingProgramByStudent;

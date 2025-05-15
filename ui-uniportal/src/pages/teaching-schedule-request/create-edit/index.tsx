import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { Button } from "@/components/Button";
import SelectWithLabel from "@/components/SelectWithLabel";
import AuthGuard from "@/components/AuthGuard";
import Link from "next/link";
import { IoIosArrowBack } from "react-icons/io";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import { getTeachingAssignmentDetail } from "@/store/reducer/teachingAssignment";
import { TypographyHeading } from "@/components/TypographyHeading";
import { TypographyBody } from "@/components/TypographyBody";
import { getListClassroom } from "@/store/reducer/classroomReducer";

const CreateEditTeachingScheduleRequest = () => {
    const dispatch = useDispatch<AppDispatch>();

    const { teachingAssignment } = useSelector(
        (state: RootState) => state.teachingAssignment
    );
    const { classrooms } = useSelector((state: RootState) => state.classroom);

    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [state, setState] = useState({
        assignmentId: 0,
        lecturerId: 0,
        lecturerName: "",
        subjectId: 0,
        subjectName: "",
        subject: {
            subjectId: 0,
            subjectName: "",
            ltCredits: 0, // Lý thuyết credits
            thCredits: 0, // Thực hành credits
            subjectDescription: "",
            subjectCoefficient: 0,
        },
        termClassId: 0,
        className: "",
        semester: "",
        schoolYears: "",
        progress: "",

        status: "",
        scheduleDetails: [
            {
                classroom_id: 0,
                lesson: "",
                date_time: new Date(),
                end_date: "",
                class_type: "",
            },
        ],
        materials: [
            {
                lt: "",
                th: "",
            },
        ],
    });

    const inputHandle = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
        index?: number
    ) => {
        const { name, value } = e.target;
        if (index !== undefined) {
            setState((prevState) => {
                const newScheduleDetails = [...prevState.scheduleDetails];
                newScheduleDetails[index] = {
                    ...newScheduleDetails[index],
                    [name]: value,
                };
                return {
                    ...prevState,
                    scheduleDetails: newScheduleDetails,
                };
            });
        } else {
            setState({
                ...state,
                [name]: value,
            });
        }
    };

    useEffect(() => {
        getListClassroom();
    }, []);

    useEffect(() => {
        if (query.id) {
            dispatch(getTeachingAssignmentDetail(query.id));
        }
    }, [query.id]);

    useEffect(() => {
        if (query.mode === "edit" && query.id) {
            setMode("edit");
        } else {
            setMode("create");
        }
    }, [query.mode, query.id]);

    const handleSubmit = () => {
        const dataToSend = {
            assignmentId: state.assignmentId,
            status: state.status,
            scheduleDetails: state.scheduleDetails,
            materials: state.materials,
        };

        // Log data to ensure it matches the backend format
        console.log("Data to send to backend:", dataToSend);

        // Example of sending the data to backend
        // dispatch(createTeachingAssignment(dataToSend));
        router.push("/teaching-schedule-request");
    };

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox
                title={
                    mode === "create"
                        ? "Tạo Yêu cầu Lịch giảng"
                        : `Đăng ký lịch giảng dạy: ${teachingAssignment.className} - ${teachingAssignment.subject?.subjectName}`
                }
            >
                <section className={styles.container}>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Mã lịch"
                            name="assignmentId"
                            value={String(state.assignmentId)}
                            onChange={inputHandle}
                            type="text"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Môn học"
                            name="subjectName"
                            value={
                                teachingAssignment.subject?.subjectName || ""
                            }
                            onChange={inputHandle}
                            type="text"
                            required
                            disabled
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Lớp học phần"
                            name="className"
                            value={state.className || ""}
                            onChange={inputHandle}
                            type="text"
                            required
                            disabled
                        />
                    </div>

                    <div className={styles.regisDetail}>
                        <TypographyHeading tag="span" theme="lg">
                            Chi tiết đăng ký
                        </TypographyHeading>

                        {/* Conditionally render Lý thuyết section */}
                        {state.subject.ltCredits > 0 && (
                            <div className={styles.regisLT}>
                                <TypographyBody tag="span" theme="md-bold">
                                    Lý thuyết
                                </TypographyBody>
                                <div>
                                    <div className={styles.gridItem}>
                                        <SelectWithLabel
                                            label="Phòng học"
                                            name="classroom_id"
                                            value={
                                                state.scheduleDetails[0]
                                                    .classroom_id
                                            }
                                            onChange={(e) => inputHandle(e, 0)}
                                            options={classrooms.map(
                                                (classroom) => ({
                                                    value:
                                                        classroom.classroomId ||
                                                        "",
                                                    label:
                                                        classroom.classroomName ||
                                                        "",
                                                })
                                            )}
                                            required
                                        />
                                    </div>
                                    <div className={styles.gridItem}>
                                        <InputWithLabel
                                            label="Tiết học"
                                            name="lesson"
                                            value={
                                                state.scheduleDetails[0].lesson
                                            }
                                            onChange={(e) => inputHandle(e, 0)}
                                            type="text"
                                            required
                                        />
                                    </div>
                                    <div className={styles.gridItem}>
                                        <InputWithLabel
                                            label="Thời gian"
                                            name="date_time"
                                            value={String(
                                                state.scheduleDetails[0]
                                                    .date_time
                                            )}
                                            onChange={(e) => inputHandle(e, 0)}
                                            type="datetime-local"
                                            required
                                        />
                                    </div>
                                    <div className={styles.gridItem}>
                                        <InputWithLabel
                                            label="Đề cương"
                                            name="materials_lt"
                                            value={state.materials[0].lt}
                                            onChange={(e) => inputHandle(e)}
                                            type="text"
                                            required
                                        />
                                    </div>
                                </div>
                            </div>
                        )}

                        {/* Conditionally render Thực hành section */}
                        {state.subject.thCredits > 0 && (
                            <div className={styles.regisLT}>
                                <TypographyBody tag="span" theme="md-bold">
                                    Thực hành
                                </TypographyBody>
                                <div>
                                    <div className={styles.gridItem}>
                                        <SelectWithLabel
                                            label="Phòng học"
                                            name="classroom_id"
                                            value={
                                                state.scheduleDetails[1]
                                                    .classroom_id
                                            }
                                            onChange={(e) => inputHandle(e, 1)}
                                            options={classrooms.map(
                                                (classroom) => ({
                                                    value:
                                                        classroom.classroomId ||
                                                        "",
                                                    label:
                                                        classroom.classroomName ||
                                                        "",
                                                })
                                            )}
                                            required
                                        />
                                    </div>
                                    <div className={styles.gridItem}>
                                        <InputWithLabel
                                            label="Tiết học"
                                            name="lesson"
                                            value={
                                                state.scheduleDetails[1].lesson
                                            }
                                            onChange={(e) => inputHandle(e, 1)}
                                            type="text"
                                            required
                                        />
                                    </div>
                                    <div className={styles.gridItem}>
                                        <InputWithLabel
                                            label="Thời gian"
                                            name="date_time"
                                            value={String(
                                                state.scheduleDetails[1]
                                                    .date_time
                                            )}
                                            onChange={(e) => inputHandle(e, 1)}
                                            type="datetime-local"
                                            required
                                        />
                                    </div>
                                    <div className={styles.gridItem}>
                                        <InputWithLabel
                                            label="Đề cương"
                                            name="materials_th"
                                            value={state.materials[0].th}
                                            onChange={(e) => inputHandle(e)}
                                            type="text"
                                            required
                                        />
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>
                </section>

                <div className={styles.buttonContainer}>
                    <Link
                        href="/teaching-schedule-request"
                        className={styles.backButton}
                    >
                        <IoIosArrowBack /> Quay lại
                    </Link>
                    <Button
                        className={styles.buttonAction}
                        onClick={handleSubmit}
                    >
                        {mode === "create" ? "Lưu" : "Đăng ký"}
                    </Button>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default CreateEditTeachingScheduleRequest;

import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import BorderBox from "@/components/BorderBox";
import { lessonTimeMap } from "@/constants/lession";
import styles from "./styles.module.css";
import { IoIosArrowBack } from "react-icons/io";
import Link from "next/link";
import { useState } from "react";
import ModalConfirm from "@/components/ModalConfirm";

moment.updateLocale("en", {
    week: {
        dow: 1, // dow = day of week, 1 = Monday, 0 = Sunday
    },
});
// Khởi tạo localizer cho react-big-calendar
const localizer = momentLocalizer(moment);

// Kiểu dữ liệu môn học
type ClassSubjectType = {
    class_name: string;
    subject_name: string;
    lecturer_name: string;
    lesson_time: string;
    start_date: string;
    week_time: string;
    classroom_name: string;
    tc: number;
    class_type: string;
};

// Dữ liệu mẫu
const classSubjects: ClassSubjectType[] = [
    {
        class_name: "MKT1002",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Nguyễn Văn An",
        lesson_time: "Tiết 1-3",
        start_date: "28/4/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng B101",
        tc: 2,
        class_type: "LT",
    },
    {
        class_name: "CNTT103",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Đào Anh Hiển",
        lesson_time: "Tiết 4-6",
        start_date: "30/4/2025",
        week_time: "Tuần 5-15",
        classroom_name: "Phòng C202",
        tc: 3,
        class_type: "LT",
    },
    {
        class_name: "MKT1005",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Nguyễn Văn An",
        lesson_time: "Tiết 1-5",
        start_date: "3/5/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng B101",
        tc: 1,
        class_type: "TH",
    },
    {
        class_name: "CNTT109",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Đào Anh Hiển",
        lesson_time: "Tiết 4-6",
        start_date: "28/4/2025",
        week_time: "Tuần 5-15",
        classroom_name: "Phòng C202",
        tc: 2,
        class_type: "LT",
    },
    {
        class_name: "ENG101",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Trần Thị Bình",
        lesson_time: "Tiết 7-9",
        start_date: "29/4/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng A303",
        tc: 3,
        class_type: "LT",
    },
    {
        class_name: "MATH202",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Lê Văn Cường",
        lesson_time: "Tiết 1-3",
        start_date: "30/4/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng D404",
        tc: 5,
        class_type: "LT",
    },
    {
        class_name: "MATH202",
        subject_name: "Công nghệ phần mềm",
        lecturer_name: "Lê Văn Cường",
        lesson_time: "Tiết 9-11",
        start_date: "1/5/2025",
        week_time: "Tuần 1-10",
        classroom_name: "Phòng D404",
        tc: 4,
        class_type: "LT",
    },
];

// Parse "Tiết x-y"
function getLessonRange(lessonTime: string): [number, number] {
    const match = lessonTime.match(/Tiết (\d+)-(\d+)/);
    return match ? [parseInt(match[1]), parseInt(match[2])] : [1, 1];
}

const formatTime = (date: moment.Moment, timeStr: string): moment.Moment => {
    return moment(
        `${date.format("YYYY-MM-DD")} ${timeStr}`,
        "YYYY-MM-DD h:mm a"
    );
};

// Tính số buổi học từ tín chỉ và tiết/buổi
function getTotalWeeks(cls: ClassSubjectType): number {
    const [start, end] = getLessonRange(cls.lesson_time);
    const periodsPerSession = end - start + 1;
    const periodsPerCredit = cls.class_type === "TH" ? 30 : 15;
    const totalPeriods = cls.tc * periodsPerCredit;
    return Math.ceil(totalPeriods / periodsPerSession);
}

function getRandomColor(): string {
    const letters = "0123456789ABCDEF";
    let color = "#";
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

function generateEvents(): any[] {
    const events: any[] = [];

    classSubjects.forEach((cls) => {
        const startDate = moment(cls.start_date, "DD/MM/YYYY");
        const [startLesson, endLesson] = getLessonRange(cls.lesson_time);
        const totalWeeks = getTotalWeeks(cls);
        const color = getRandomColor();

        for (let week = 0; week < totalWeeks; week++) {
            const eventDate = startDate.clone().add(week * 7, "days");

            const startTime = formatTime(
                eventDate,
                lessonTimeMap[startLesson].start
            );
            const endTime = formatTime(eventDate, lessonTimeMap[endLesson].end);

            events.push({
                title: `${cls.lesson_time} - 
${cls.subject_name} (${cls.class_name})
- ${cls.lecturer_name}
- ${cls.classroom_name}`,
                start: startTime.toDate(),
                end: endTime.toDate(),
                allDay: false,
                color,
                originalClass: cls,
            });
        }
    });

    return events;
}

// Component chính
const ListClassSubject = () => {
    const events = generateEvents();
    const [selectedClass, setSelectedClass] = useState<ClassSubjectType | null>(
        null
    );
    const [isModalOpen, setIsModalOpen] = useState(false);
    return (
        <BorderBox title="Các lớp đang mở môn Công nghệ phần mềm">
            <Calendar
                className={styles.calendar}
                localizer={localizer}
                formats={{
                    dayFormat: (date, culture, localizer) =>
                        localizer!.format(date, "dddd", culture),
                }}
                events={events}
                startAccessor="start"
                endAccessor="end"
                defaultView="week"
                views={["week"]}
                timeslots={1}
                step={60}
                min={new Date(2024, 0, 1, 6, 0)}
                max={new Date(2024, 0, 1, 23, 59)}
                style={{ height: "75vh" }}
                eventPropGetter={(event) => ({
                    style: {
                        backgroundColor: event.color,
                        color: "white",
                        borderRadius: "4px",
                        padding: "4px",
                        border: "none",
                    },
                })}
                onSelectEvent={(event) => {
                    setSelectedClass(event.originalClass);
                    setIsModalOpen(true);

                    // const cls = event.originalClass;
                    // alert(
                    //     `Lớp: ${cls.subject_name}\nMã lớp: ${cls.class_name}\nGiảng viên: ${cls.lecturer_name}`
                    // );
                }}
            />
            {isModalOpen && selectedClass && (
                <ModalConfirm
                    confirmText="đăng ký học"
                    buttonText="Đăng ký"
                    message={`Bạn muốn đăng ký lớp "${selectedClass.subject_name}" (${selectedClass.class_name}) của giảng viên ${selectedClass.lecturer_name}?`}
                    onConfirm={() => {
                        // TODO: Gọi API hoặc xử lý đăng ký ở đây
                        console.log("Đã xác nhận đăng ký:", selectedClass);
                        setIsModalOpen(false);
                    }}
                    onCancel={() => {
                        setIsModalOpen(false);
                        setSelectedClass(null);
                    }}
                />
            )}

            <Link href={"/credit-registration"} className={styles.backButton}>
                <IoIosArrowBack /> Back
            </Link>
        </BorderBox>
    );
};

export default ListClassSubject;

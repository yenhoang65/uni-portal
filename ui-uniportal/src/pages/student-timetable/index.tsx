// import React, { useState } from "react";
// import styles from "./styles.module.css";

// type ClassSubjectType = {
//     class_name: string;
//     subject_name: string;
//     lecturer_name: string;
//     lesson_time: string;
//     start_date: string;
//     week_time: string;
//     classroom_name: string;
//     tc: number;
//     class_type: string;
// };

// const classSubjects: ClassSubjectType[] = [
//     {
//         class_name: "MKT1002",
//         subject_name: "SEO 2",
//         lecturer_name: "Nguyễn Văn An",
//         lesson_time: "Tiết 1-3",
//         start_date: "28/4/2025",
//         week_time: "Tuần 1-10",
//         classroom_name: "Phòng B101",
//         tc: 2,
//         class_type: "LT",
//     },
//     {
//         class_name: "CNTT103",
//         subject_name: "Công nghệ phần mềm",
//         lecturer_name: "Đào Anh Hiển",
//         lesson_time: "Tiết 4-6",
//         start_date: "30/4/2025",
//         week_time: "Tuần 5-15",
//         classroom_name: "Phòng C202",
//         tc: 3,
//         class_type: "LT",
//     },
//     {
//         class_name: "MKT1005",
//         subject_name: "SEO",
//         lecturer_name: "Nguyễn Văn An",
//         lesson_time: "Tiết 1-5",
//         start_date: "3/5/2025",
//         week_time: "Tuần 1-10",
//         classroom_name: "Phòng B101",
//         tc: 1,
//         class_type: "TH",
//     },
//     {
//         class_name: "CNTT109",
//         subject_name: "Công nghệ phần mềm",
//         lecturer_name: "Đào Anh Hiển",
//         lesson_time: "Tiết 4-6",
//         start_date: "28/4/2025",
//         week_time: "Tuần 5-15",
//         classroom_name: "Phòng C202",
//         tc: 2,
//         class_type: "LT",
//     },
//     {
//         class_name: "ENG101",
//         subject_name: "English",
//         lecturer_name: "Trần Thị Bình",
//         lesson_time: "Tiết 7-9",
//         start_date: "29/4/2025",
//         week_time: "Tuần 1-10",
//         classroom_name: "Phòng A303",
//         tc: 3,
//         class_type: "LT",
//     },
//     {
//         class_name: "MATH202",
//         subject_name: "Mathematics",
//         lecturer_name: "Lê Văn Cường",
//         lesson_time: "Tiết 1-3",
//         start_date: "30/4/2025",
//         week_time: "Tuần 1-10",
//         classroom_name: "Phòng D404",
//         tc: 5,
//         class_type: "LT",
//     },
//     {
//         class_name: "MATH202",
//         subject_name: "Mathematics",
//         lecturer_name: "Lê Văn Cường",
//         lesson_time: "Tiết 9-11",
//         start_date: "1/5/2025",
//         week_time: "Tuần 1-10",
//         classroom_name: "Phòng D404",
//         tc: 4,
//         class_type: "LT",
//     },
// ];

// const days = ["TH 2", "TH 3", "TH 4", "TH 5", "TH 6", "TH 7", "CN"];
// const timeSlots = Array.from({ length: 13 }, (_, i) => `${7 + i}:00`);

// const colors = [
//     "#4caf50",
//     "#0288d1",
//     "#f06292",
//     "#ff9800",
//     "#7b1fa2",
//     "#d32f2f",
// ];

// function getDayOfWeekIndex(dateStr: string): number {
//     const [d, m, y] = dateStr.split("/").map(Number);
//     const day = new Date(y, m - 1, d).getDay();
//     return day === 0 ? 6 : day - 1;
// }

// function getLessonRange(lessonTime: string): [number, number] {
//     const match = lessonTime.match(/Tiết (\d+)-(\d+)/);
//     return match ? [parseInt(match[1]), parseInt(match[2])] : [1, 1];
// }

// function getTotalWeeks(cls: ClassSubjectType): number {
//     const [start, end] = getLessonRange(cls.lesson_time);
//     const periodsPerSession = end - start + 1;
//     const periodsPerCredit = cls.class_type === "TH" ? 30 : 15;
//     const totalPeriods = cls.tc * periodsPerCredit;
//     return Math.ceil(totalPeriods / periodsPerSession);
// }

// function isInCurrentWeek(
//     cls: ClassSubjectType,
//     currentWeekStartDate: Date
// ): boolean {
//     const classStartDate = new Date(
//         cls.start_date.split("/").reverse().join("-")
//     );
//     const totalWeeks = getTotalWeeks(cls);

//     const startWeekTime = new Date(classStartDate);
//     const endWeekTime = new Date(classStartDate);
//     endWeekTime.setDate(classStartDate.getDate() + (totalWeeks - 1) * 7);

//     const monday = new Date(currentWeekStartDate);
//     monday.setDate(monday.getDate());

//     const sunday = new Date(monday);
//     sunday.setDate(monday.getDate() + 6);

//     return !(endWeekTime < monday || startWeekTime > sunday);
// }

// function getWeekRange(date: Date): string {
//     const start = new Date(date);
//     const day = start.getDay();
//     const diff = start.getDate() - day + (day === 0 ? -6 : 1);
//     start.setDate(diff);
//     const end = new Date(start);
//     end.setDate(start.getDate() + 6);
//     return `${start.getDate()}/${start.getMonth() + 1} - ${end.getDate()}/${
//         end.getMonth() + 1
//     }`;
// }

// function getDatesForWeek(startDate: Date): string[] {
//     const dates: string[] = [];
//     const firstDay = new Date(startDate);
//     const day = firstDay.getDay();
//     firstDay.setDate(firstDay.getDate() - day + (day === 0 ? -6 : 1));
//     for (let i = 0; i < 7; i++) {
//         const current = new Date(firstDay);
//         current.setDate(firstDay.getDate() + i);
//         dates.push(`${current.getDate()}`);
//     }
//     return dates;
// }

// export default function Timetable() {
//     const rowHeight = 60;
//     const [currentWeekStartDate, setCurrentWeekStartDate] = useState(
//         new Date()
//     );

//     const weekRange = getWeekRange(currentWeekStartDate);
//     const dates = getDatesForWeek(currentWeekStartDate);

//     const goToPreviousWeek = () => {
//         const prev = new Date(currentWeekStartDate);
//         prev.setDate(currentWeekStartDate.getDate() - 7);
//         setCurrentWeekStartDate(prev);
//     };

//     const goToNextWeek = () => {
//         const next = new Date(currentWeekStartDate);
//         next.setDate(currentWeekStartDate.getDate() + 7);
//         setCurrentWeekStartDate(next);
//     };

//     const today = new Date();

//     return (
//         <div className={styles.container}>
//             <div className={styles.header}>
//                 <span
//                     className={styles.navigationButton}
//                     onClick={goToPreviousWeek}
//                 >
//                     &lt;
//                 </span>
//                 <div className={styles.weekRange}> {weekRange}</div>
//                 <span
//                     className={styles.navigationButton}
//                     onClick={goToNextWeek}
//                 >
//                     &gt;
//                 </span>
//             </div>
//             <div className={styles.gridWrapper}>
//                 <div className={styles.sidebar}>
//                     {timeSlots.map((time, i) => (
//                         <div key={i} className={styles.timeRow}>
//                             {time}
//                         </div>
//                     ))}
//                 </div>
//                 <div className={styles.grid}>
//                     {days.map((day, colIdx) => (
//                         <div key={colIdx} className={styles.dayColumn}>
//                             <div className={styles.dayHeader}>
//                                 <div>{day}</div>
//                                 <div
//                                     className={`${styles.date} ${
//                                         Number(dates[colIdx]) ===
//                                             today.getDate() &&
//                                         currentWeekStartDate.getMonth() ===
//                                             today.getMonth() &&
//                                         currentWeekStartDate.getFullYear() ===
//                                             today.getFullYear()
//                                             ? styles.today
//                                             : ""
//                                     }`}
//                                 >
//                                     {dates[colIdx]}
//                                 </div>
//                             </div>
//                             <div className={styles.dayBody}>
//                                 {timeSlots.map((_, i) => (
//                                     <div
//                                         key={i}
//                                         className={styles.timeSlotLine}
//                                         style={{ top: `${i * rowHeight}px` }}
//                                     />
//                                 ))}
//                                 {classSubjects.map((cls, i) => {
//                                     const classDate = new Date(
//                                         cls.start_date
//                                             .split("/")
//                                             .reverse()
//                                             .join("-")
//                                     );
//                                     const dayIndex = getDayOfWeekIndex(
//                                         cls.start_date
//                                     );

//                                     if (
//                                         dayIndex !== colIdx ||
//                                         !isInCurrentWeek(
//                                             cls,
//                                             currentWeekStartDate
//                                         )
//                                     )
//                                         return null;

//                                     const [start, end] = getLessonRange(
//                                         cls.lesson_time
//                                     );
//                                     const duration = end - start + 1;
//                                     const startHour = 7 + (start - 1);
//                                     const top = (startHour - 7) * rowHeight;

//                                     const color = colors[i % colors.length];

//                                     return (
//                                         <div
//                                             key={i}
//                                             className={styles.event}
//                                             style={{
//                                                 top: `${top}px`,
//                                                 height: `${
//                                                     duration * rowHeight
//                                                 }px`,
//                                                 backgroundColor: color,
//                                             }}
//                                         >
//                                             <strong>{cls.subject_name}</strong>
//                                             <br />
//                                             {cls.class_name}
//                                             <br />
//                                             {cls.lecturer_name}
//                                             <br />
//                                             {cls.classroom_name}
//                                         </div>
//                                     );
//                                 })}
//                             </div>
//                         </div>
//                     ))}
//                 </div>
//             </div>
//         </div>
//     );
// }

import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import BorderBox from "@/components/BorderBox";
import { lessonTimeMap } from "@/constants/lession";

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
        subject_name: "SEO 2",
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
        subject_name: "SEO",
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
        subject_name: "English",
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
        subject_name: "Mathematics",
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
        subject_name: "Mathematics",
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
            });
        }
    });

    return events;
}

// Component chính
const TimeLine = () => {
    const events = generateEvents();

    return (
        <BorderBox title="Thời khóa biểu">
            <Calendar
                localizer={localizer}
                events={events}
                startAccessor="start"
                endAccessor="end"
                defaultView="week"
                views={["month", "week", "day"]}
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
            />
        </BorderBox>
    );
};

export default TimeLine;

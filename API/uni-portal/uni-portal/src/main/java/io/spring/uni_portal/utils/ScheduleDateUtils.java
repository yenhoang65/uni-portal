package io.spring.uni_portal.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDateUtils {

    private static final LocalDate ACADEMIC_YEAR_START = LocalDate.of(2024, 8, 19); // Tuần 1 bắt đầu 19/8/2024
    private static final LocalDate ACADEMIC_YEAR_END = LocalDate.of(2025, 8, 19);   // Tuần 52 kết thúc 19/8/2025
    private static final int LESSONS_PER_CREDIT_THEORY = 15; // 15 tiết cho 1 tín chỉ lý thuyết
    private static final int LESSONS_PER_CREDIT_PRACTICE = 30; // 30 tiết cho 1 tín chỉ thực hành

    public static class ScheduleDates {
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private List<LocalDateTime> lessonDates;

        public ScheduleDates(LocalDateTime startDate, LocalDateTime endDate, List<LocalDateTime> lessonDates) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.lessonDates = lessonDates;
        }

        public LocalDateTime getStartDate() {
            return startDate;
        }

        public LocalDateTime getEndDate() {
            return endDate;
        }

        public List<LocalDateTime> getLessonDates() {
            return lessonDates;
        }
    }

    public static ScheduleDates calculateScheduleDates(LocalDateTime startDate, int credits, String classType, String lessonRange) {
        if (startDate == null || credits <= 0 || classType == null || lessonRange == null) {
            throw new IllegalArgumentException("Invalid input: startDate, credits, classType, and lessonRange must be provided");
        }

        int lessonsPerCredit = classType.equalsIgnoreCase("LT") ? LESSONS_PER_CREDIT_THEORY : LESSONS_PER_CREDIT_PRACTICE;
        int totalLessons = credits * lessonsPerCredit;

        String[] lessonParts = lessonRange.split("-");
        int startLesson = Integer.parseInt(lessonParts[0].trim());
        int endLesson = Integer.parseInt(lessonParts[1].trim());
        int lessonsPerSession = endLesson - startLesson + 1;

        int totalSessions = (int) Math.ceil((double) totalLessons / lessonsPerSession);

        LocalDate start = startDate.toLocalDate();
        if (start.isBefore(ACADEMIC_YEAR_START) || start.isAfter(ACADEMIC_YEAR_END)) {
            throw new IllegalArgumentException("startDate must be within the academic year (19/8/2024 - 19/8/2025)");
        }

        List<LocalDateTime> lessonDates = new ArrayList<>();
        lessonDates.add(startDate);
        LocalDateTime currentDate = startDate;

        for (int i = 1; i < totalSessions; i++) {
            currentDate = currentDate.plusDays(7);
            if (currentDate.toLocalDate().isAfter(ACADEMIC_YEAR_END)) {
                throw new IllegalStateException("Schedule exceeds the academic year end date (19/8/2025)");
            }
            lessonDates.add(currentDate);
        }

        LocalDateTime endDate = lessonDates.get(lessonDates.size() - 1);

        return new ScheduleDates(startDate, endDate, lessonDates);
    }

    public static int getWeekOfAcademicYear(LocalDate date) {
        long daysBetween = ChronoUnit.DAYS.between(ACADEMIC_YEAR_START, date);
        int week = (int) (daysBetween / 7) + 1;
        return Math.min(week, 52);
    }
}

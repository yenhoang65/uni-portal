package io.spring.uni_portal.utils;

import java.time.LocalTime;

public class LessonTimeUtils {

    public static String getLessonSlot(LocalTime inputTime) {
        LocalTime[] startTimes = {
                LocalTime.of(7, 30),  // 1
                LocalTime.of(8, 25),  // 2
                LocalTime.of(9, 25),  // 3
                LocalTime.of(10, 20), // 4
                LocalTime.of(11, 15), // 5
                LocalTime.of(12, 10), // 6
                LocalTime.of(13, 5),  // 7
                LocalTime.of(14, 0),  // 8
                LocalTime.of(15, 0),  // 9
                LocalTime.of(15, 55), // 10
                LocalTime.of(16, 50)  // 11
        };

        LocalTime[] endTimes = {
                LocalTime.of(8, 20),
                LocalTime.of(9, 15),
                LocalTime.of(10, 15),
                LocalTime.of(11, 10),
                LocalTime.of(12, 5),
                LocalTime.of(13, 0),
                LocalTime.of(13, 55),
                LocalTime.of(14, 50),
                LocalTime.of(15, 50),
                LocalTime.of(16, 45),
                LocalTime.of(17, 40)
        };

        for (int i = 0; i < startTimes.length; i++) {
            if (!inputTime.isBefore(startTimes[i]) && inputTime.isBefore(endTimes[i])) {
                return String.valueOf(i + 1); // tiết đơn lẻ
            }
        }

        return null; // ngoài giờ học
    }

    public static String getLessonSlotRange(LocalTime inputTime) {
        int lesson = Integer.parseInt(getLessonSlot(inputTime));
        if (lesson % 2 == 1 && lesson < 11) {
            return lesson + "-" + (lesson + 1);
        } else {
            return String.valueOf(lesson);
        }
    }
}
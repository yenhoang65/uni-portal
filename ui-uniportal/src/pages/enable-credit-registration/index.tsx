import React, { useState, useEffect } from "react";
import BorderBox from "@/components/BorderBox";
import InputWithLabel from "@/components/InputWithLabel";
import { Button } from "@/components/Button";
import styles from "./styles.module.css";
import { TypographyBody } from "@/components/TypographyBody";

interface RegistrationTime {
    id: string;
    startTime: string;
    endTime: string;
    createdAt: string;
}

const RegistrationTimeActivation = () => {
    const [startTime, setStartTime] = useState<string>("");
    const [endTime, setEndTime] = useState<string>("");
    const [activatedTimes, setActivatedTimes] = useState<RegistrationTime[]>(
        []
    );

    const handleStartTimeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setStartTime(e.target.value);
    };

    const handleEndTimeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEndTime(e.target.value);
    };

    const handleActivateTime = () => {
        if (
            !startTime ||
            !endTime ||
            new Date(startTime) >= new Date(endTime)
        ) {
            alert("Thời gian bắt đầu và kết thúc không hợp lệ.");
            return;
        }

        const newActivation: Omit<RegistrationTime, "id" | "createdAt"> = {
            startTime,
            endTime,
        };

        // Gọi API để gửi dữ liệu kích hoạt lên server
        // Sau khi API thành công, cập nhật state activatedTimes
        console.log("Kích hoạt thời gian:", newActivation);
        // Ví dụ giả định API trả về một bản ghi đã có ID và createdAt
        const mockResponse: RegistrationTime = {
            id: Math.random().toString(36).substring(7),
            startTime,
            endTime,
            createdAt: new Date().toISOString(),
        };
        setActivatedTimes([...activatedTimes, mockResponse]);

        // Reset form
        setStartTime("");
        setEndTime("");
    };

    // useEffect để gọi API lấy danh sách thời gian đã kích hoạt khi component mount
    useEffect(() => {
        // Gọi API để lấy danh sách thời gian đã kích hoạt từ server
        // Ví dụ giả định API trả về một mảng các đối tượng RegistrationTime
        const mockData: RegistrationTime[] = [
            {
                id: "1",
                startTime: "2025-04-20T08:00",
                endTime: "2025-04-25T17:00",
                createdAt: "2025-04-18T09:00",
            },
            {
                id: "2",
                startTime: "2025-05-01T10:00",
                endTime: "2025-05-05T16:00",
                createdAt: "2025-04-18T09:30",
            },
        ];
        setActivatedTimes(mockData);
    }, []);

    return (
        <BorderBox title="Kích hoạt Thời gian Đăng ký Tín chỉ">
            <div className={styles.activationForm}>
                <InputWithLabel
                    label="Ngày giờ Bắt đầu"
                    type="datetime-local"
                    value={startTime}
                    onChange={handleStartTimeChange}
                    required
                />
                <InputWithLabel
                    label="Ngày giờ Kết thúc"
                    type="datetime-local"
                    value={endTime}
                    onChange={handleEndTimeChange}
                    required
                />
                <Button
                    onClick={handleActivateTime}
                    className={styles.activateButton}
                >
                    Kích hoạt
                </Button>
            </div>

            <div className={styles.activatedList}>
                <TypographyBody
                    tag="span"
                    theme="md-bold"
                    className={styles.listTitle}
                >
                    Danh sách Thời gian đã kích hoạt
                </TypographyBody>
                {activatedTimes.length > 0 ? (
                    <table className={styles.activatedTable}>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Thời gian Bắt đầu</th>
                                <th>Thời gian Kết thúc</th>
                                <th>Ngày Kích hoạt</th>
                            </tr>
                        </thead>
                        <tbody>
                            {activatedTimes.map((time) => (
                                <tr key={time.id}>
                                    <td>{time.id}</td>
                                    <td>
                                        {new Date(
                                            time.startTime
                                        ).toLocaleString()}
                                    </td>
                                    <td>
                                        {new Date(
                                            time.endTime
                                        ).toLocaleString()}
                                    </td>
                                    <td>
                                        {new Date(
                                            time.createdAt
                                        ).toLocaleString()}
                                    </td>
                                    {/* Thêm các cột khác nếu cần */}
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <TypographyBody
                        tag="span"
                        theme="sm"
                        className={styles.noData}
                    >
                        Chưa có thời gian nào được kích hoạt.
                    </TypographyBody>
                )}
            </div>
        </BorderBox>
    );
};

export default RegistrationTimeActivation;

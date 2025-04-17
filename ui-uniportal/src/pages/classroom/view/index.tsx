import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";

type Classroom = {
    id: string;
    ma: string;
    number_of_seats: number;
    classroom_type: string;
    device: string[];
};

const mockClassroom: Classroom = {
    id: "CLA001",
    ma: "P.101",
    number_of_seats: 30,
    classroom_type: "Lý thuyết",
    device: ["Máy chiếu", "Bảng trắng", "Điều hòa"],
};

const ClassroomDetail = () => {
    const router = useRouter();
    const { query } = router;
    const { id } = query;

    useEffect(() => {}, [id]);

    return (
        <BorderBox title={`Chi tiết phòng học: ${mockClassroom.ma}`}>
            <div className={styles.detailContainer}>
                <div className={styles.detailItem}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.detailLabel}
                    >
                        Mã phòng:
                    </TypographyBody>
                    <TypographyBody tag="span" theme="md">
                        {mockClassroom.ma}
                    </TypographyBody>
                </div>

                <div className={styles.detailItem}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.detailLabel}
                    >
                        Số chỗ ngồi:
                    </TypographyBody>
                    <TypographyBody tag="span" theme="md">
                        {mockClassroom.number_of_seats}
                    </TypographyBody>
                </div>

                <div className={styles.detailItem}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.detailLabel}
                    >
                        Loại phòng:
                    </TypographyBody>
                    <TypographyBody tag="span" theme="md">
                        {mockClassroom.classroom_type}
                    </TypographyBody>
                </div>

                <div className={styles.detailItem}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.detailLabel}
                    >
                        Thiết bị:
                    </TypographyBody>
                    <div>
                        {mockClassroom.device.map((device, index) => (
                            <TypographyBody key={index} tag="span" theme="md">
                                {device}
                                {index < mockClassroom.device.length - 1 &&
                                    ", "}
                            </TypographyBody>
                        ))}
                    </div>
                </div>

                <div className={styles.buttonGroup}>
                    <div
                        className={styles.backButton}
                        onClick={() => router.back()}
                    >
                        <IoIosArrowBack className={styles.backIcon} />
                        <span className={styles.backText}>Back</span>
                    </div>
                    <button
                        className={styles.editButton}
                        onClick={() =>
                            router.push(`/major/create-edit?id=${1}&mode=edit`)
                        }
                    >
                        <AiFillEdit /> Edit
                    </button>
                </div>
            </div>
        </BorderBox>
    );
};

export default ClassroomDetail;

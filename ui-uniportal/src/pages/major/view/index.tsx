import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { TypographyBody } from "@/components/TypographyBody";
import { Button } from "@/components/Button";
import { IoIosArrowBack } from "react-icons/io";
import { AiFillEdit } from "react-icons/ai";

type Major = {
    id: string;
    maNganh: string;
    ten: string;
    moTa?: string;
    ngayThanhLap: string;
    // Bạn có thể thêm các trường thông tin chi tiết khác nếu cần
};

const MajorDetail = () => {
    const router = useRouter();
    const { query } = router;
    const { id } = query;
    const [major, setMajor] = useState<Major | null>(null);

    return (
        <BorderBox title={`Major Detail: Kỹ thuật phần mềm`}>
            <div className={styles.detailContainer}>
                <div className={styles.detailItem}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.detailLabel}
                    >
                        Major Code:
                    </TypographyBody>
                    <TypographyBody tag="span" theme="md">
                        Kỹ thuật phần mềm
                    </TypographyBody>
                </div>

                <div className={styles.detailItem}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.detailLabel}
                    >
                        Name:
                    </TypographyBody>
                    <TypographyBody tag="span" theme="md">
                        Kỹ thuật phần mềm
                    </TypographyBody>
                </div>

                <div className={styles.detailItem}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.detailLabel}
                    >
                        Khoa:
                    </TypographyBody>
                    <TypographyBody tag="span" theme="md">
                        Kỹ thuật phần mềm
                    </TypographyBody>
                </div>

                <div className={styles.detailItem}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.detailLabel}
                    >
                        Description:
                    </TypographyBody>
                    <TypographyBody tag="span" theme="md">
                        Lorem ipsum dolor, sit amet consectetur adipisicing
                        elit. Inventore impedit voluptates mollitia excepturi
                        aliquid repellat voluptas esse quidem ipsam doloribus
                        blanditiis corporis reiciendis non, laborum, quasi
                        aliquam. Aliquid tempora aperiam architecto similique
                        libero provident repudiandae laborum delectus harum
                        autem voluptates exercitationem quasi eius facere magni,
                        doloremque eveniet praesentium veritatis voluptatibus?
                        Ex voluptates incidunt illo dolorum, amet magni quae
                        nemo enim! Consequatur autem sit eaque! Laborum natus,
                        quis ab sit quas alias! Laborum, enim eveniet cumque
                        expedita nostrum similique dolores facilis mollitia
                        blanditiis, reprehenderit dolor fuga delectus, ab velit
                        ullam aliquam! Amet vero, aut repellendus ut rem atque
                        voluptatibus natus vel?
                    </TypographyBody>
                </div>

                <div className={styles.detailItem}>
                    <TypographyBody
                        tag="span"
                        theme="md"
                        className={styles.detailLabel}
                    >
                        Established Date:
                    </TypographyBody>
                    <TypographyBody tag="span" theme="md">
                        1/1/2025
                    </TypographyBody>
                </div>

                <div className={styles.buttonGroup}>
                    <div
                        className={styles.backButton}
                        onClick={() => router.back()}
                    >
                        <IoIosArrowBack className={styles.backIcon} />{" "}
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

export default MajorDetail;

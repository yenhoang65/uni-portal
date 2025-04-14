import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useTranslation } from "react-i18next";
import Image from "next/image";
import { Button } from "@/components/Button";
import { RiEdit2Fill } from "react-icons/ri";
import BasicInfomation from "./components/basic-infomation";
import Title from "./components/title";
import { useRouter } from "next/router";

const Profile = () => {
    const { t } = useTranslation();
    const router = useRouter();
    return (
        <div className={styles.content}>
            <BorderBox title={t("common.title-profile")}>
                <main className={styles.container}>
                    <div className={styles.left}>
                        <div className={styles.userImageProfile}>
                            <Image
                                src={require("./assets/avatar.jpg")}
                                width={180}
                                height={180}
                                alt="Picture of the author"
                                className={styles.userImage}
                            />
                        </div>
                        <div className={styles.basicInfo}>
                            <BasicInfomation
                                title={t("common.teacher-code")}
                                content="1252"
                            />
                            <BasicInfomation
                                title={t("common.full-name")}
                                content="1252"
                            />
                            <BasicInfomation
                                title={t("common.gender")}
                                content="1252"
                            />
                            <BasicInfomation
                                title={t("common.email")}
                                content="1252"
                            />
                            <BasicInfomation
                                title={t("common.address-permanent")}
                                content="1252"
                            />

                            <Button
                                onClick={() =>
                                    router.push("admin/profile/edit")
                                }
                                size="large"
                                className={styles.button}
                            >
                                <RiEdit2Fill />
                                {t("common.update-profile")}
                            </Button>
                        </div>
                    </div>
                    <div className={styles.right}>
                        <div className={styles.basicInformation}>
                            <Title title={t("common.basic-info")} />
                            <div className={styles.informationContent}>
                                <div>
                                    <BasicInfomation
                                        title={t("common.citizen-id")}
                                        content="1252"
                                    />
                                    <br />
                                    <BasicInfomation
                                        title={t("common.phone")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.ethnicity")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.birth-date")}
                                        content="1252"
                                    />
                                </div>
                                <div>
                                    <BasicInfomation
                                        title={t("common.id-issue-place")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.id-issue-date")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.mobile")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.religion")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.birth-place")}
                                        content="1252"
                                    />
                                </div>
                            </div>
                        </div>

                        <div className={styles.positionInformation}>
                            <Title title={t("common.position-info")} />
                            <div className={styles.informationContent}>
                                <div>
                                    <BasicInfomation
                                        title={t("common.academic-rank")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.position")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.major")}
                                        content="1252"
                                    />
                                </div>
                                <div>
                                    <BasicInfomation
                                        title={t("common.degree")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.lecturer-type")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.faculty")}
                                        content="1252"
                                    />
                                </div>
                            </div>
                        </div>

                        <div className={styles.bankInformation}>
                            <Title title={t("common.bank-info")} />
                            <div className={styles.informationContent}>
                                <div>
                                    <BasicInfomation
                                        title={t("common.bank-number")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.tax-number")}
                                        content="1252"
                                    />
                                </div>
                                <div>
                                    <BasicInfomation
                                        title={t("common.bank-name")}
                                        content="1252"
                                    />
                                </div>
                            </div>
                        </div>

                        <div className={styles.relationshipInformation}>
                            <Title title={t("common.relation-info")} />
                            <div className={styles.informationContent}>
                                <div>
                                    <BasicInfomation
                                        title={t("common.parent-name")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.nationality")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.religion")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.job")}
                                        content="1252"
                                    />
                                </div>
                                <div>
                                    <BasicInfomation
                                        title={t("common.birth-year")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.contact-number")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.address-permanent")}
                                        content="1252"
                                    />
                                    <BasicInfomation
                                        title={t("common.current-address")}
                                        content="1252"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </BorderBox>
        </div>
    );
};

export default Profile;

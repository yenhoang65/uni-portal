import { Button } from "@/components/Button";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import Title from "../view/components/title";
import BorderBox from "@/components/BorderBox";
import { useTranslation } from "react-i18next";

const EditProfile = () => {
    const { t } = useTranslation();
    const role = "student";

    return (
        <div className={styles.container}>
            <BorderBox title={t("common.update-profile")}>
                <form action="">
                    <div className={styles.gridContainer}>
                        <div className={styles.gridItem}>
                            {(role as string) === "admin" && (
                                <InputWithLabel
                                    label={t("common.teacher-code")}
                                    name="teacherCode"
                                    value="125213"
                                    type="text"
                                    required
                                    disabled
                                />
                            )}
                            {role === "student" && (
                                <InputWithLabel
                                    label={t("common.student-code")}
                                    name="studentCode"
                                    value="125213"
                                    type="text"
                                    required
                                    disabled
                                />
                            )}
                            <InputWithLabel
                                label={t("common.email")}
                                name="email"
                                value="125213"
                                type="text"
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.full-name")}
                                name="fullName"
                                value="125213"
                                type="text"
                                required
                                disabled
                            />
                            <InputWithLabel
                                label={t("common.address-contact")}
                                name="contactAddress"
                                value="125213"
                                type="text"
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.gender")}
                                name="gender"
                                value="125213"
                                type="text"
                                required
                                disabled
                            />
                            <InputWithLabel
                                label={t("common.birth-date")}
                                name="birthDate"
                                value="125213"
                                type="text"
                                required
                                disabled
                            />
                        </div>
                    </div>

                    <Title
                        title={t("common.basic-info")}
                        className={styles.title}
                    />
                    <div className={styles.gridContainer}>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.citizen-id")}
                                name="citizenId"
                                value="125213"
                                required
                            />
                            <InputWithLabel
                                label={t("common.id-issue-place")}
                                name="issuePlace"
                                value="125213"
                                required
                            />
                            <InputWithLabel
                                label={t("common.religion")}
                                name="religion"
                                value="125213"
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.phone")}
                                name="phone"
                                value="125213"
                                required
                            />
                            <InputWithLabel
                                label={t("common.id-issue-date")}
                                name="issueDate"
                                value="125213"
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.birth-place")}
                                name="birthPlace"
                                value="125213"
                                required
                            />
                            <InputWithLabel
                                label={t("common.ethnicity")}
                                name="ethnicity"
                                value="125213"
                                required
                            />
                        </div>
                    </div>

                    {(role as string) === "admin" && (
                        <>
                            <Title
                                title={t("common.position-info")}
                                className={styles.title}
                            />
                            <div className={styles.gridContainer}>
                                <div className={styles.gridItem}>
                                    <InputWithLabel
                                        label={t("common.academic-rank")}
                                        name="academicRank"
                                        value="125213"
                                        required
                                    />
                                    <InputWithLabel
                                        label={t("common.degree")}
                                        name="degree"
                                        value="125213"
                                        required
                                    />
                                </div>
                                <div className={styles.gridItem}>
                                    <InputWithLabel
                                        label={t("common.position")}
                                        name="position"
                                        value="125213"
                                        required
                                    />
                                    <InputWithLabel
                                        label={t("common.lecturer-type")}
                                        name="lecturerType"
                                        value="125213"
                                        required
                                    />
                                </div>
                                <div className={styles.gridItem}>
                                    <InputWithLabel
                                        label={t("common.major")}
                                        name="major"
                                        value="125213"
                                        required
                                    />
                                    <InputWithLabel
                                        label={t("common.faculty")}
                                        name="faculty"
                                        value="125213"
                                        required
                                    />
                                </div>
                            </div>
                        </>
                    )}

                    {role === "student" && (
                        <>
                            <Title
                                title={t("common.training-info")}
                                className={styles.title}
                            />
                            <div className={styles.gridContainer}>
                                <div className={styles.gridItem}>
                                    <InputWithLabel
                                        label={t("common.education-level")}
                                        name="educationLevel"
                                        value="125213"
                                        required
                                    />
                                    <InputWithLabel
                                        label={t("common.admission-date")}
                                        name="admissionDate"
                                        value="125213"
                                        required
                                    />
                                </div>
                                <div className={styles.gridItem}>
                                    <InputWithLabel
                                        label={t("common.major")}
                                        name="major"
                                        value="125213"
                                        required
                                    />
                                    <InputWithLabel
                                        label={t("common.type-of-training")}
                                        name="typeOfTraining"
                                        value="125213"
                                        required
                                    />
                                </div>
                                <div className={styles.gridItem}>
                                    <InputWithLabel
                                        label={t("common.training-program")}
                                        name="trainingProgram"
                                        value="125213"
                                        required
                                    />
                                    <InputWithLabel
                                        label={t("common.class")}
                                        name="class"
                                        value="125213"
                                        required
                                    />
                                </div>
                            </div>
                        </>
                    )}

                    <Title
                        title={t("common.bank-info")}
                        className={styles.title}
                    />
                    <div className={styles.gridContainer}>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.bank-name")}
                                name="bankName"
                                value="125213"
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.bank-number")}
                                name="bankNumber"
                                value="125213"
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.account-owner")}
                                name="accountOwner"
                                value="125213"
                                required
                            />
                        </div>
                    </div>

                    <Title
                        title={t("common.relation-info")}
                        className={styles.title}
                    />
                    <div className={styles.gridContainer}>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.parent-name")}
                                name="parentName"
                                value="125213"
                                required
                            />
                            <InputWithLabel
                                label={t("common.birth-year")}
                                name="parentBirthYear"
                                value="125213"
                                required
                            />
                            <InputWithLabel
                                label={t("common.nationality")}
                                name="nationality"
                                value="125213"
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.contact-number")}
                                name="parentPhone"
                                value="125213"
                                required
                            />
                            <InputWithLabel
                                label={t("common.religion")}
                                name="parentReligion"
                                value="125213"
                                required
                            />
                            <InputWithLabel
                                label={t("common.address-permanent")}
                                name="parentAddress"
                                value="125213"
                                required
                            />
                        </div>
                        <div className={styles.gridItem}>
                            <InputWithLabel
                                label={t("common.job")}
                                name="parentJob"
                                value="125213"
                                required
                            />
                            <InputWithLabel
                                label={t("common.current-address")}
                                name="parentCurrentAddress"
                                value="125213"
                                required
                            />
                        </div>
                    </div>

                    <div className={styles.actionContent}>
                        <Button
                            onClick={(e) => e.preventDefault()}
                            size="large"
                            className={styles.button}
                        >
                            {t("common.update-profile")}
                        </Button>
                    </div>
                </form>
            </BorderBox>
        </div>
    );
};

export default EditProfile;

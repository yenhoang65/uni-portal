import { TypographyHeading } from "@/components/TypographyHeading";
import styles from "./styles.module.css";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import Image from "next/image";

const Header: React.FC = () => {
    const { t, i18n } = useTranslation();
    const [currentLanguage, setCurrentLanguage] = useState("vi");

    const handleChangeLanguage = () => {
        const nextLang = currentLanguage === "vi" ? "en" : "vi";
        setCurrentLanguage(nextLang);
        i18n.changeLanguage(nextLang);
    };

    const getFlag = (lang: string) => {
        return lang === "vi" ? "VI" : "EN";
    };

    return (
        <div className={styles.headerWrapper}>
            <div className={styles.headerInner}>
                <div className={styles.searchInputWrapper}>
                    <TypographyHeading
                        tag="h1"
                        theme="lg"
                        color="var(--primary-white)"
                    >
                        {t("common.utehy")}
                    </TypographyHeading>
                </div>

                <div className={styles.userInfo}>
                    <div className={styles.userContent}>
                        <TypographyHeading
                            tag="span"
                            theme="lg"
                            color="var(--primary-white)"
                            onClick={handleChangeLanguage}
                            className={styles.buttonChangeLang}
                        >
                            {getFlag(currentLanguage)}
                        </TypographyHeading>

                        <Image
                            src={require("./assets/user-image.webp")}
                            width={40}
                            height={40}
                            alt="Picture of the author"
                            className={styles.userImage}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Header;

import { TypographyHeading } from "@/components/TypographyHeading";
import styles from "./styles.module.css";
import { useTranslation } from "react-i18next";
import { useState } from "react";
import Image from "next/image";
import { FaBell } from "react-icons/fa"; // Import biểu tượng chuông

const Header: React.FC = () => {
    const { t, i18n } = useTranslation();
    const [currentLanguage, setCurrentLanguage] = useState("vi");

    const handleChangeLanguage = () => {
        const nextLang = currentLanguage === "vi" ? "en" : "vi";
        setCurrentLanguage(nextLang);
        i18n.changeLanguage(nextLang);
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
                        <button
                            onClick={handleChangeLanguage}
                            className={styles.buttonChangeLang}
                        >
                            <Image
                                src={
                                    currentLanguage === "vi"
                                        ? require("./assets/vietnam.webp")
                                        : require("./assets/usa.png")
                                }
                                alt={
                                    currentLanguage === "vi"
                                        ? "Cờ Việt Nam"
                                        : "Cờ Hoa Kỳ"
                                }
                                width={30}
                                height={20}
                                objectFit="contain"
                            />
                        </button>
                        <span>
                            <FaBell size={20} color="white" />
                        </span>
                        <Image
                            src={require("./assets/user-image.webp")}
                            width={35}
                            height={35}
                            alt="Ảnh tác giả"
                            className={styles.userImage}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Header;

import { getNav, NavGroup } from "@/navigation";
import styles from "./styles.module.css";
import Link from "next/link";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import Image from "next/image";

const Sidebar: React.FC = () => {
    const router = useRouter();
    const { pathname } = router;
    const [allNav, setAllNav] = useState<NavGroup[]>([]);

    useEffect(() => {
        const navs = getNav(["employee"]);
        setAllNav(navs);
    }, []);

    return (
        <div className={styles.sidebar}>
            <div className={styles.userSection}>
                <div className={styles.avatarWrapper}>
                    <Image
                        src={require("./assets/utehy.webp")}
                        alt="User Avatar"
                        width={70}
                        height={70}
                        className={styles.avatarIcon}
                    />
                </div>
                <span className={styles.username}>ThaoDT46</span>
                <span className={styles.position}>Giảng viên</span>
            </div>

            <div className={styles.navList}>
                {allNav.map((group) => (
                    <div key={group.group} className={styles.navGroup}>
                        <div className={styles.groupTitle}>{group.group}</div>
                        <ul>
                            {group.items.map((item) => (
                                <li key={item.id}>
                                    <Link
                                        href={item.path}
                                        className={`${styles.navItem} ${
                                            pathname.includes(item.path)
                                                ? styles.navItemActive
                                                : ""
                                        }`}
                                    >
                                        <span
                                            className={`${
                                                pathname.includes(item.path)
                                                    ? styles.navIconActive
                                                    : styles.navIcon
                                            }`}
                                        >
                                            {item.icon}
                                        </span>
                                        <span
                                            className={`${
                                                pathname.includes(item.path)
                                                    ? styles.titleActive
                                                    : styles.titleName
                                            }`}
                                        >
                                            {item.title}
                                        </span>
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Sidebar;

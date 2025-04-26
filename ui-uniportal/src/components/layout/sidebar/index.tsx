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
        const navs = getNav(["admin"]);
        setAllNav(navs);
    }, []);

    return (
        <div className={styles.sidebar}>
            <div className={styles.userSection}>
                <div className={styles.avatarWrapper}>
                    <Image
                        src={require("./assets/user.jpg")}
                        alt="User Avatar"
                        width={60}
                        height={60}
                        className={styles.avatarIcon}
                    />
                </div>
                <span className={styles.username}>Nicola Web Design</span>
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
                                            pathname === item.path
                                                ? styles.navItemActive
                                                : ""
                                        }`}
                                    >
                                        <span>{item.icon}</span>
                                        <span className={styles.titleName}>
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

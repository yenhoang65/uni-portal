import { getNav } from "@/navigation";
import styles from "./styles.module.css";
import Link from "next/link";
import { useRouter } from "next/router";
import { ReactNode, useEffect, useState } from "react";

type NavItem = {
    id: number;
    title: string;
    icon: ReactNode;
    role: string[];
    path: string;
};

const Sidebar: React.FC = () => {
    const router = useRouter();
    const { pathname } = router;
    const [allNav, setAllNav] = useState<NavItem[]>([]);

    useEffect(() => {
        const navs = getNav(["admin"]);
        setAllNav(navs);
    }, []);

    return (
        <div className={styles.sidebar}>
            <div className={styles.logoContainer}>
                <Link href="/" className={styles.logo}>
                    Tago.
                </Link>
            </div>

            <div className={styles.navList}>
                <ul>
                    {allNav.map((nav) => (
                        <li key={nav.id}>
                            <Link
                                href={nav.path}
                                className={`${styles.navItem} ${
                                    pathname === nav.path
                                        ? styles.navItemActive
                                        : ""
                                }`}
                            >
                                <span>{nav.icon}</span>
                                <span>{nav.title}</span>
                            </Link>
                        </li>
                    ))}
                    <li>
                        <button className={styles.logoutButton}>
                            <span>Log Out</span>
                        </button>
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default Sidebar;

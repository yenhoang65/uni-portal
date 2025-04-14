import styles from "./styles.module.css";
import Link from "next/link";
import { useRouter } from "next/router";

const Sidebar: React.FC = () => {
    const router = useRouter();
    const { pathname } = router;

    const allNav = [
        { id: 1, path: "/", title: "Dashboard", icon: "🏠" },
        { id: 2, path: "/about", title: "About", icon: "ℹ️" },
        { id: 3, path: "/services", title: "Services", icon: "🛠️" },
        { id: 4, path: "/contact", title: "Contact", icon: "📞" },
        { id: 5, path: "/profile", title: "Profile", icon: "👤" },
        { id: 6, path: "/settings", title: "Settings", icon: "⚙️" },
    ];

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

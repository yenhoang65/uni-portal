import { useEffect, useState } from "react";
import styles from "./styles.module.css";
import Image from "next/image";
import logo from "./assets/utehy_logo.png";
import { useRouter } from "next/router";

const Login = () => {
    // const router = useRouter();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const isFormValid = username.trim() !== "" && password.trim() !== "";

    // const isAuthenticated = !!role && !!userInfo;

    // useEffect(() => {
    //     if (isAuthenticated) {
    //         router.replace("/");
    //     }
    // }, [isAuthenticated, router]);

    // if (isAuthenticated) return null;

    return (
        <div className={styles.wrapper}>
            <div className={styles.left}>
                <Image
                    src={logo}
                    width={250}
                    height={250}
                    alt="Picture of the author"
                    className={styles.logo}
                />
                <h1>
                    TRƯỜNG ĐẠI HỌC <br /> SƯ PHẠM KỸ THUẬT HƯNG YÊN
                </h1>
            </div>

            <div className={styles.right}>
                <span className={styles.title}>CỔNG THÔNG TIN SINH VIÊN</span>

                <form className={styles.form}>
                    <div className={styles.inputGroup}>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Tên đăng nhập"
                            className={styles.input}
                            autoComplete="off"
                        />
                        <label htmlFor="username" className={styles.label}>
                            Tên đăng nhập
                        </label>
                    </div>

                    <div className={styles.inputGroup}>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Mật khẩu"
                            className={styles.input}
                            autoComplete="off"
                        />
                        <label htmlFor="password" className={styles.label}>
                            Mật khẩu
                        </label>
                    </div>

                    <div className={styles.forgotPassword}>Quên mật khẩu</div>

                    <button
                        type="submit"
                        className={`${styles.loginBtn} ${
                            !isFormValid ? styles.disabledBtn : ""
                        }`}
                        disabled={!isFormValid}
                    >
                        Đăng nhập
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Login;

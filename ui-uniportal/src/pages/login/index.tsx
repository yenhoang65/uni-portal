import { useState } from "react";
import styles from "./styles.module.css";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    return (
        <div className={styles.wrapper}>
            {/* Left side */}
            <div className={styles.left}>
                <h1>Hello, Welcome!</h1>
                <p>Don't have an account?</p>
                <button className={styles.registerBtn}>Register</button>
            </div>

            {/* Right side */}
            <div className={styles.right}>
                <h2 className={styles.title}>Login</h2>

                <form className={styles.form}>
                    {/* Username */}
                    <div className={styles.inputGroup}>
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Username"
                            className={styles.input}
                            autoComplete="off"
                        />
                        <label htmlFor="username" className={styles.label}>
                            Username
                        </label>
                    </div>

                    {/* Password */}
                    <div className={styles.inputGroup}>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Password"
                            className={styles.input}
                            autoComplete="off"
                        />
                        <label htmlFor="password" className={styles.label}>
                            Password
                        </label>
                    </div>

                    <div className={styles.forgotPassword}>
                        Forgot password?
                    </div>

                    <button type="submit" className={styles.loginBtn}>
                        Login
                    </button>

                    <div className={styles.socialLogin}>
                        <p>or login with social platforms</p>
                        <div className={styles.socialIcons}>
                            <button className={styles.iconBtn}>G</button>
                            <button className={styles.iconBtn}>f</button>
                            <button className={styles.iconBtn}>GitHub</button>
                            <button className={styles.iconBtn}>in</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default Login;

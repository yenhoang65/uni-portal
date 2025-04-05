import { useState } from "react";

export const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-slate-900 to-cyan-900 p-4">
            <div className="bg-slate-800 p-8 rounded-lg shadow-lg w-full max-w-sm">
                <h2 className="text-white text-2xl font-bold mb-6 text-center">
                    Login
                </h2>
                <form>
                    {/* Username */}
                    <div className="relative w-full mb-6">
                        <input
                            type="text"
                            id="username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Username"
                            className="peer w-full bg-transparent pt-5 pb-2 border-b-2 border-gray-400 text-white placeholder-transparent focus:outline-none focus:border-cyan-400"
                            autoComplete="off"
                        />
                        <label
                            htmlFor="username"
                            className={`absolute left-0 text-gray-400 transition-all duration-200
                peer-placeholder-shown:top-5 peer-placeholder-shown:text-base
                peer-focus:top-0 peer-focus:text-sm peer-focus:text-cyan-400
                ${
                    username ? "top-0 text-sm text-cyan-400" : "top-5 text-base"
                }`}
                        >
                            Username
                        </label>
                    </div>

                    {/* Password */}
                    <div className="relative w-full mb-6">
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Password"
                            className="peer w-full bg-transparent pt-5 pb-2 border-b-2 border-gray-400 text-white placeholder-transparent focus:outline-none focus:border-cyan-400"
                            autoComplete="off"
                        />
                        <label
                            htmlFor="password"
                            className={`absolute left-0 text-gray-400 transition-all duration-200
                peer-placeholder-shown:top-5 peer-placeholder-shown:text-base
                peer-focus:top-0 peer-focus:text-sm peer-focus:text-cyan-400
                ${
                    password ? "top-0 text-sm text-cyan-400" : "top-5 text-base"
                }`}
                        >
                            Password
                        </label>
                    </div>

                    <button
                        type="submit"
                        className="w-full mt-4 bg-cyan-400 hover:bg-cyan-500 text-white font-semibold py-2 rounded-full transition"
                    >
                        Login
                    </button>

                    <p className="text-center text-gray-400 mt-4">
                        Don’t have an account?{" "}
                        <span className="text-cyan-400 cursor-pointer">
                            Sign Up
                        </span>
                    </p>
                </form>
            </div>
        </div>
    );
};

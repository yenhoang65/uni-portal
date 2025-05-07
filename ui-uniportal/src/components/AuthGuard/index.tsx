"use client";

import { AppDispatch, RootState } from "@/store";
import { getUserInfo } from "@/store/reducer/authReducer";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

type Props = {
    allowedRoles?: string[];
    children: React.ReactNode;
};

export default function AuthGuard({ allowedRoles, children }: Props) {
    const router = useRouter();
    const dispatch = useDispatch<AppDispatch>();
    const [authorized, setAuthorized] = useState(false);
    const { role, token } = useSelector((state: RootState) => state.auth);

    useEffect(() => {
        async function checkAuth() {
            if (token && role) {
                if (router.pathname === "/login") {
                    router.push("/dashboard");
                    return;
                }

                if (allowedRoles && !allowedRoles.includes(role)) {
                    router.push("/not-allowed");
                    return;
                }

                setAuthorized(true);
                dispatch(getUserInfo(token));
                return;
            }

            if (!token || !role) {
                if (router.pathname !== "/login") {
                    router.push("/login");
                }
            }
        }

        checkAuth();
    }, [router, allowedRoles, role, token]);

    if (!authorized) {
        return null;
    }

    return <>{children}</>;
}

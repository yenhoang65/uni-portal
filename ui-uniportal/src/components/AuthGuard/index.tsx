"use client";

import { useRouter } from "next/router";
import { useEffect, useState } from "react";

type Props = {
    allowedRoles?: string[];
    children: React.ReactNode;
};

async function getCurrentUser() {
    // TODO: Viết code lấy user từ Cookie hoặc LocalStorage
    // Tạm thời fake user là "admin"
    return {
        role: "admin",
    };
}

export default function AuthGuard({ allowedRoles, children }: Props) {
    const router = useRouter();
    const [authorized, setAuthorized] = useState(false);

    useEffect(() => {
        async function checkAuth() {
            const user = await getCurrentUser();

            if (!user) {
                router.push("/login");
                return;
            }

            if (allowedRoles && !allowedRoles.includes(user.role)) {
                router.push("/not-allowed");
                return;
            }

            setAuthorized(true);
        }

        checkAuth();
    }, [router, allowedRoles]);

    if (!authorized) {
        return null;
    }

    return <>{children}</>;
}

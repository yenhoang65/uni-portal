// import React, { useEffect } from "react";
// import { useRouter } from "next/router";

// interface ProtectRouteProps {
//     route: { path: string; role?: string };
//     children: React.ReactNode;
// }

// const ProtectRoute: React.FC<ProtectRouteProps> = ({ route, children }) => {
//     const router = useRouter();

//     const role = "admin";
//     const userInfo = {
//         userName: "Thaor",
//         role: "admin",
//     };

//     useEffect(() => {
//         if (!role) {
//             router.push("/login");
//         }
//     }, [role, userInfo?.role, route.role, router]);

//     if (role && (!route.role || userInfo?.role === route.role)) {
//         return <>{children}</>;
//     }

//     return null;
// };

// export default ProtectRoute;

import { ReactNode, useEffect } from "react";
import { useRouter } from "next/router";

interface ProtectedRouteProps {
    children: ReactNode;
    route: { path: string; role?: string };
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children, route }) => {
    const router = useRouter();
    const userRole = "admin";

    useEffect(() => {
        if (userRole !== route.role) {
            router.push("/login");
        }
    }, [userRole, route.role, router]);

    return <>{userRole === route.role ? children : null}</>;
};

export default ProtectedRoute;

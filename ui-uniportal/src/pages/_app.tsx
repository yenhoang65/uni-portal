import type { AppProps } from "next/app";
import "../styles/reset.css";
import "../styles/global.css";
import "../styles/base.css";
import "../config-translation";
import MainLayout from "@/components/layout/main";
import { useRouter } from "next/router";
import { useEffect, useMemo } from "react";

const publicRoutes = ["/login", "/not-found"];

const roleBasedRoutes: Record<string, string[]> = {
    admin: [
        "/training-program",
        "/training-program/view",
        "/training-program/create-edit",
        "/activate-teaching-schedule-registration",
        "/enable-credit-registration",
        "/faculty",
        "/faculty/view",
        "/faculty/create-edit",
        "/lecturer_management",
        "/lecturer_management/view",
        "/lecturer_management/create-edit",
        "/major",
        "/major/view",
        "/major/create-edit",
        "/profile/view",
        "/profile/edit",
        "/specialization",
        "/specialization/view",
        "/specialization/create-edit",
        "/student_management",
        "/student_management/view",
        "/student_management/create-edit",
        "/subject",
        "/subject/view",
        "/subject/create-edit",
        "/classroom",
        "/classroom/view",
        "/classroom/create-edit",
        "/class-term-subject",
    ],
    employee: [
        "/classroom",
        "/classroom/view",
        "/classroom/create-edit",
        "/profile/view",
        "/profile/edit",
        "/class-term-subject",
    ],
    lecturer: ["/profile/view", "/profile/edit"],
    student: ["/profile/view", "/profile/edit"],
};

function App({ Component, pageProps }: AppProps) {
    // const router = useRouter();
    // const userRole = "admin";

    // const isPublicPage = useMemo(
    //     () => publicRoutes.includes(router.pathname),
    //     [router.pathname]
    // );
    // const isAllowedRoute = useMemo(() => {
    //     if (!userRole || isPublicPage || router.pathname === "/not-allowed")
    //         return true;
    //     const allowedRoutes = roleBasedRoutes[userRole];
    //     return allowedRoutes?.includes(router.pathname) || false;
    // }, [router.pathname, userRole, isPublicPage]);

    // useEffect(() => {
    //     if (!isPublicPage && !userRole) {
    //         router.push("/login");
    //     } else if (!isPublicPage && !isAllowedRoute) {
    //         router.push("/not-allowed");
    //     }
    // }, [router, isPublicPage, userRole, isAllowedRoute]);

    // if (!isPublicPage && (!userRole || !isAllowedRoute)) {
    //     return null;
    // }

    const router = useRouter();

    const isPublicRoute = useMemo(() => {
        return publicRoutes.includes(router.pathname);
    }, [router.pathname]);

    return (
        <>
            {isPublicRoute ? (
                <Component {...pageProps} />
            ) : (
                <MainLayout>
                    <Component {...pageProps} />
                </MainLayout>
            )}
        </>
    );
}

export default App;

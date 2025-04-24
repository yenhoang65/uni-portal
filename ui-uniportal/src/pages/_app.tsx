import type { AppProps } from "next/app";
import "../styles/reset.css";
import "../styles/global.css";
import "../styles/base.css";
import "../config-translation";
import MainLayout from "@/components/layout/main";
import { useRouter } from "next/router";

export default function App({ Component, pageProps }: AppProps) {
    const router = useRouter();
    const isPublicPage = ["/login", "/not-found"].includes(router.pathname);

    return !isPublicPage ? (
        <MainLayout>
            <Component {...pageProps} />
        </MainLayout>
    ) : (
        <Component {...pageProps} />
    );
}

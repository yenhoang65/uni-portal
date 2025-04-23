import type { AppProps } from "next/app";
import "../styles/reset.css";
import "../styles/global.css";
import "../styles/base.css";
import "../config-translation";
import { privateRoutes } from "@/router/routes/privateRoutes";
import ProtectedRoute from "@/hooks/ProtectedRoute";

export default function App({ Component, pageProps }: AppProps) {
    return <Component {...pageProps} />;
}

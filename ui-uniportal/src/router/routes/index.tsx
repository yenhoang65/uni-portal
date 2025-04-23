import { privateRoutes } from "./privateRoutes";
import ProtectRoute from "./ProtectRoute";
import MainLayout from "@/components/layout/main";

export const getRoutes = () => {
    // privateRoutes.map((r) => {
    //     r.element = <ProtectRoute route={r}> {r.element} </ProtectRoute>;
    // });

    return {
        path: "/",
        element: (
            <MainLayout>
                {privateRoutes.map((route) => route.element)}
            </MainLayout>
        ),
        children: privateRoutes,
    };
};

import { useRoutes } from "react-router-dom";

export const Router = ({ allRoutes }) => {
    const routes = useRoutes([...allRoutes]);

    return routes;
};

import { allNav } from "./allNav";

export const getNav = (roles: string[]) => {
    return allNav.filter((nav) =>
        nav.role.some((role) => roles.includes(role))
    );
};

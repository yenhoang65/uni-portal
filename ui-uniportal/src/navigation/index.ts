import { allNav } from "./allNav";

export const getNav = (roles: string[]) => {
    return allNav.filter((nav) => roles.includes(nav.role));
};

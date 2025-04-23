import { adminRoutes } from "./adminRoutes";
import { employeeRoutes } from "./employeeRoutes";
import { lecturerRoutes } from "./lecturerRoutes";
import { studentRoutes } from "./studentRoutes";

export const privateRoutes = [
    ...adminRoutes,
    ...employeeRoutes,
    ...lecturerRoutes,
    ...studentRoutes,
];

"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

export const getAttendancceSession = createAsyncThunk(
    "attendance/getAttendancceSession",
    async (classStudentId: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(
                `/attendance/sessions/by-class-student/${classStudentId}`
            );

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

export const getListStudentFollowClassSubject = createAsyncThunk(
    "attendance/getListStudentFollowClassSubject",
    async (classStudentId: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(
                `/attendance/students/by-class-student/${classStudentId}`
            );

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

export const markAttendance = createAsyncThunk(
    "attendance/markAttendance",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(`/attendance/mark`, dto);

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

export const attendanceReducer = createSlice({
    name: "attendance",
    initialState: {
        successMessage: "",
        errorMessage: "",
        attendanceSession: [] as any,
        listStudent: [] as any,
    },
    reducers: {
        messageClear: (state) => {
            state.errorMessage = "";
            state.successMessage = "";
        },
    },
    extraReducers: (builder) => {
        builder.addCase(
            getAttendancceSession.fulfilled,
            (state, { payload }) => {
                state.attendanceSession = payload.data;
            }
        );
        builder
            .addCase(
                getListStudentFollowClassSubject.fulfilled,
                (state, { payload }) => {
                    state.listStudent = payload.data;
                }
            )
            .addCase(markAttendance.rejected, (state, { payload }) => {
                if (typeof payload === "string") {
                    state.errorMessage = payload;
                } else if (
                    payload &&
                    typeof payload === "object" &&
                    "message" in payload
                ) {
                    state.errorMessage = (
                        payload as { message: string }
                    ).message;
                } else {
                    state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
                }
            })

            .addCase(markAttendance.fulfilled, (state, { payload }) => {
                state.successMessage = "Điểm danh thành công";
            });
    },
});

export const { messageClear } = attendanceReducer.actions;
export default attendanceReducer.reducer;

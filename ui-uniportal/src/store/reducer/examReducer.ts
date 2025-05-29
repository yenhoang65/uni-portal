"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

export const getExamAll = createAsyncThunk(
    "point/getExamAll",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/grade-types`);

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

export const getExamDetail = createAsyncThunk(
    "point/getExamDetail",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/grade-types/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

export const updatePoint = createAsyncThunk(
    "point/updatePoint",
    async (
        { id, dto }: { id: any; dto: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            const { data } = await api.put(`/grade-types/${id}`, dto);

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

export const deletePoint = createAsyncThunk(
    "point/deletePoint",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(`/grade-types/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

export const createExam = createAsyncThunk(
    "exam/createExam",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(`/grade-types`, dto);

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

export const examReducer = createSlice({
    name: "exam",
    initialState: {
        successMessage: "",
        errorMessage: "",

        allExams: [] as any,
        examsForStu: [] as any,
        examDetail: {} as any,
    },
    reducers: {
        messageClear: (state) => {
            state.errorMessage = "";
            state.successMessage = "";
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(createExam.rejected, (state, { payload }) => {
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
            .addCase(createExam.fulfilled, (state, { payload }) => {
                state.successMessage = "Thêm lịch thi thành công";
            });
    },
});

export const { messageClear } = examReducer.actions;
export default examReducer.reducer;

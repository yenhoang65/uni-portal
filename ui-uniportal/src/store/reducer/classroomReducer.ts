"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type Classroom = {
    classroomId: number | null;
    classroomName: string | null;
    numberOfSeats: number;
    devices: string[];
    status: string | null;
};

type GetParam = {
    currentPage: number;
    parPage: number;
    searchValue: string;
};

export const getListClassroom = createAsyncThunk(
    "classroom/getListClassroom",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/classrooms`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getClassroomDetail = createAsyncThunk(
    "classroom/getClassroomDetail",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/classrooms/${id}`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const updateClassroom = createAsyncThunk(
    "classroom/updateClassroom",
    async (
        { id, dto }: { id: any; dto: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            // Gửi yêu cầu PUT với FormData
            const { data } = await api.put(`/classrooms/${id}`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            // return rejectWithValue(error.response?.data || error.message);
        }
    }
);

export const createClassroom = createAsyncThunk(
    "classroom/createClassroom",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(`/classrooms`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteClassroom = createAsyncThunk(
    "classroom/deleteClassroom",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(`/classrooms/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const classroomReducer = createSlice({
    name: "classroom",
    initialState: {
        successMessage: "",
        errorMessage: "",
        classrooms: [] as Classroom[],
        classroom: {} as Classroom,
    },
    reducers: {
        messageClear: (state) => {
            state.errorMessage = "";
            state.successMessage = "";
        },
    },
    extraReducers: (builder) => {
        builder
            // .addCase(login.pending, (state) => {
            //     state.loader = true;
            // })
            // .addCase(login.rejected, (state, { payload }) => {
            //     state.loader = false;
            // })
            .addCase(getListClassroom.fulfilled, (state, { payload }) => {
                state.classrooms = payload.data;
            })
            .addCase(getClassroomDetail.fulfilled, (state, { payload }) => {
                state.classroom = payload.data;
            })
            .addCase(updateClassroom.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(updateClassroom.fulfilled, (state, { payload }) => {
                state.successMessage = "Update classroom thành công";
            })
            .addCase(createClassroom.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(createClassroom.fulfilled, (state, { payload }) => {
                state.successMessage = "Thêm classroom thành công";
            })
            .addCase(deleteClassroom.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(deleteClassroom.fulfilled, (state, { payload }) => {
                state.successMessage = "Xóa classroom thành công";
            });
    },
});

export const { messageClear } = classroomReducer.actions;
export default classroomReducer.reducer;

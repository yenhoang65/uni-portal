"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type Lecturer = {
    userId: number;
    userName: string;
    admissionDate: Date;
    gender: string;
    phoneNumber: string;
    address: string;
    ethnicGroup: string;
    dateOfBirth: Date;
    religion: string;
    idNumber: string;
    email: string;
    placeOfBirth: string;
    permanentResident: string;
    bank: string;
    bankAccountOwner: string;
    bankAccountNumber: string;
    status: string;

    academicDegree: string;
    graduatedFrom: string;
    position: string;
    majorId: number;

    majorName: string;
};

type GetParam = {
    currentPage: number;
    parPage: number;
    searchValue: string;
};

export const getListLecturer = createAsyncThunk(
    "lecturer/getListLecturer",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/lecturers`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getLecturerDetail = createAsyncThunk(
    "lecturer/getLecturerDetail",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/lecturers/${id}`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const updateLecturer = createAsyncThunk(
    "lecturer/updateLecturer",
    async (
        { id, dto }: { id: any; dto: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            // Gửi yêu cầu PUT với FormData
            const { data } = await api.put(`/lecturers/${id}`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            // return rejectWithValue(error.response?.data || error.message);
        }
    }
);

export const createLecturer = createAsyncThunk(
    "lecturer/createLecturer",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(`/lecturers`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteLecturer = createAsyncThunk(
    "lecturer/deleteLecturer",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(`/lecturers/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const lecturerReducer = createSlice({
    name: "lecturer",
    initialState: {
        successMessage: "",
        errorMessage: "",
        lecturers: [] as Lecturer[],
        lecturer: {} as Lecturer,
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
            .addCase(getListLecturer.fulfilled, (state, { payload }) => {
                state.lecturers = payload.data;
            })
            .addCase(getLecturerDetail.fulfilled, (state, { payload }) => {
                state.lecturer = payload.data;
            })
            .addCase(updateLecturer.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(updateLecturer.fulfilled, (state, { payload }) => {
                state.successMessage = "Update lecturer thành công";
            })
            .addCase(createLecturer.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(createLecturer.fulfilled, (state, { payload }) => {
                state.successMessage = "Thêm lecturer thành công";
            })
            .addCase(deleteLecturer.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(deleteLecturer.fulfilled, (state, { payload }) => {
                state.successMessage = "Xóa lecturer thành công";
            });
    },
});

export const { messageClear } = lecturerReducer.actions;
export default lecturerReducer.reducer;

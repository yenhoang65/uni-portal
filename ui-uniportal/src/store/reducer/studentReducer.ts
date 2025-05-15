"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type Student = {
    userId: number;
    userName: string | "";
    gender: string | "";
    phoneNumber: string | "";
    dateOfBirth: string | "";
    educationLevel: string | "";
    admissionDate: string | "";
    typeOfTraining: string | "";
    specializationId: number;
    specializationName: string | "";
    status: string | "";
    classId: number;

    email: string | "";
    address: string | "";
    ethnicGroup: string | "";
    religion: string | "";

    idNumber: string | "";
    placeOfBirth: string | "";
    permanentResident: string | "";
    bank: string | "";
    bankAccountOwner: string | "";
    bankAccountNumber: string | "";
};

type GetParam = {
    currentPage: number;
    parPage: number;
    searchValue: string | "";
};

export const getListStudent = createAsyncThunk(
    "student/getListstudent",
    async (
        {
            perPage,
            currentPage,
            searchValue,
        }: { perPage: any; currentPage: any; searchValue: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            const { data } = await api.get(
                `/students/paging?currentPage=${currentPage}&perPage=${perPage}&searchValue=${searchValue}`,
                {
                    withCredentials: true,
                }
            );

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getStudentDetail = createAsyncThunk(
    "student/getStudentDetail",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/students/${id}`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const updateStudent = createAsyncThunk(
    "student/updateStudent",
    async (
        { id, dto }: { id: any; dto: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            // Gửi yêu cầu PUT với FormData
            const { data } = await api.put(`/students/${id}`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            // return rejectWithValue(error.response?.data || error.message);
        }
    }
);

export const createStudent = createAsyncThunk(
    "student/createStudent",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(`/students`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteStudent = createAsyncThunk(
    "student/deleteStudent",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(`/students/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const filterStudent = createAsyncThunk(
    "student/filterStudent",
    async (classId: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(
                `/students/filter?classId=${classId}`
            );

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const studentReducer = createSlice({
    name: "student",
    initialState: {
        successMessage: "",
        errorMessage: "",
        students: [] as Student[],
        student: {} as Student,
        totalStudent: 0,
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
            .addCase(getListStudent.fulfilled, (state, { payload }) => {
                state.students = payload.data.content;
                state.totalStudent = payload.data.totalElements;
            })
            .addCase(getStudentDetail.fulfilled, (state, { payload }) => {
                state.student = payload.data;
            })
            .addCase(updateStudent.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(updateStudent.fulfilled, (state, { payload }) => {
                state.successMessage = "Update student thành công";
            })
            .addCase(createStudent.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(createStudent.fulfilled, (state, { payload }) => {
                state.successMessage = "Thêm student thành công";
            })
            .addCase(deleteStudent.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(deleteStudent.fulfilled, (state, { payload }) => {
                state.successMessage = "Xóa student thành công";
            })
            .addCase(filterStudent.fulfilled, (state, { payload }) => {
                state.students = payload.data;
            });
    },
});

export const { messageClear } = studentReducer.actions;
export default studentReducer.reducer;

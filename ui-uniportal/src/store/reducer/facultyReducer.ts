"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type Faculty = {
    facultyId: string | null;
    facultyName: string | null;
    facultyDateOfEstablishment: string | null;
    facultyEmail: string | null;
    facultyPhoneNumber: string | null;
    facultyAddress: string | null;
    facultyDescription: string | null;
    facultyLogo: string | null;
    facultyLogoFile: string | null;
    facultyStatus: string | null;
};

type GetParam = {
    currentPage: number;
    parPage: number;
    searchValue: string;
};

export const getListFaculty = createAsyncThunk(
    "faculty/getListFaculty",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/faculties`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getFacultyDetail = createAsyncThunk(
    "faculty/getFacultyDetail",
    async (id: string, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/faculties/${id}`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const updateFaculty = createAsyncThunk(
    "faculty/updateFaculty",
    async (
        { id, formData }: { id: any; formData: FormData },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            // Gửi yêu cầu PUT với FormData
            const { data } = await api.post(`/faculties/${id}`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            // return rejectWithValue(error.response?.data || error.message);
        }
    }
);

export const createFaculty = createAsyncThunk(
    "faculty/createFaculty",
    async (formData: FormData, { rejectWithValue, fulfillWithValue }) => {
        try {
            // Gửi yêu cầu PUT với FormData
            const { data } = await api.post(`/faculties`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteFaculty = createAsyncThunk(
    "faculty/deleteFaculty",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(`/faculties/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const facultyReducer = createSlice({
    name: "auth",
    initialState: {
        successMessage: "",
        errorMessage: "",
        faculties: [] as Faculty[],
        faculty: {} as Faculty,
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
            .addCase(getListFaculty.fulfilled, (state, { payload }) => {
                state.faculties = payload.data;
            })
            .addCase(getFacultyDetail.fulfilled, (state, { payload }) => {
                state.faculty = payload.data;
            })
            .addCase(updateFaculty.fulfilled, (state, { payload }) => {
                state.successMessage = "Update khoa thành công";
            })
            .addCase(createFaculty.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(createFaculty.fulfilled, (state, { payload }) => {
                state.successMessage = "Thêm khoa thành công";
            })
            .addCase(deleteFaculty.rejected, (state, { payload }) => {
                state.errorMessage = "Xóa thành công";
            })
            .addCase(deleteFaculty.fulfilled, (state, { payload }) => {
                state.successMessage = "Xóa khoa thành công";
            });
    },
});

export const { messageClear } = facultyReducer.actions;
export default facultyReducer.reducer;

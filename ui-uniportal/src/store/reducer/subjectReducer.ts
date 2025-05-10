"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type Subject = {
    subjectId: string | null;
    subjectName: string | null;
    ltCredits: number;
    thCredits: number;
    subjectDescription: string | null;
    subjectCoefficient: number;
};

type GetParam = {
    currentPage: number;
    parPage: number;
    searchValue: string;
};

export const getListSubject = createAsyncThunk(
    "subject/getListSubject",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/subjects`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getSubjectDetail = createAsyncThunk(
    "subject/getSubjectDetail",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/subjects/${id}`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const updateSubject = createAsyncThunk(
    "subject/updateSubject",
    async (
        { id, dto }: { id: any; dto: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            // Gửi yêu cầu PUT với FormData
            const { data } = await api.put(`/subjects/${id}`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            // return rejectWithValue(error.response?.data || error.message);
        }
    }
);

export const createSubject = createAsyncThunk(
    "subject/createSubject",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(`/subjects`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteSubject = createAsyncThunk(
    "subject/deleteSubject",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(`/subjects/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const subjectReducer = createSlice({
    name: "subject",
    initialState: {
        successMessage: "",
        errorMessage: "",
        subjects: [] as Subject[],
        subject: {} as Subject,
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
            .addCase(getListSubject.fulfilled, (state, { payload }) => {
                state.subjects = payload.data;
            })
            .addCase(getSubjectDetail.fulfilled, (state, { payload }) => {
                state.subject = payload.data;
            })
            .addCase(updateSubject.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(updateSubject.fulfilled, (state, { payload }) => {
                state.successMessage = "Update subject thành công";
            })
            .addCase(createSubject.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(createSubject.fulfilled, (state, { payload }) => {
                state.successMessage = "Thêm subject thành công";
            })
            .addCase(deleteSubject.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(deleteSubject.fulfilled, (state, { payload }) => {
                state.successMessage = "Xóa subject thành công";
            });
    },
});

export const { messageClear } = subjectReducer.actions;
export default subjectReducer.reducer;

"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type ClassOffical = {
    classId: number;
    lecturerId: number;
    lecturerName: string | null;
    schoolYear: number;
    trainingProgramId: number;
    trainingProgramName: string | null;
};

type ClassTerm = {
    termclassId: number;
    classname: string;
    progress: string;
    semester: string;
    schoolyears: string;
};

type GetParam = {
    currentPage: number;
    parPage: number;
    searchValue: string;
};

//class offical
export const getListClassOffical = createAsyncThunk(
    "class/getListClassOffical",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/classes`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

//term class
export const getListTermClass = createAsyncThunk(
    "class/getListTermClass",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/term-classes`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getClassTermDetail = createAsyncThunk(
    "classTerm/getClassTermDetail",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/term-classes/${id}`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const updateTermClass = createAsyncThunk(
    "class/updateTermClass",
    async (
        { id, dto }: { id: any; dto: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            const { data } = await api.put(`/term-classes/${id}`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            // return rejectWithValue(error.response?.data || error.message);
        }
    }
);

export const createTermClass = createAsyncThunk(
    "class/createTermClass",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(`/term-classes`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteTermClass = createAsyncThunk(
    "class/deleteTermClass",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(`/term-classes/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const classReducer = createSlice({
    name: "class",
    initialState: {
        successMessage: "",
        errorMessage: "",
        classOfficals: [] as ClassOffical[],
        classOffical: {} as ClassOffical,

        classTerms: [] as ClassTerm[],
        classTerm: {} as ClassTerm,
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
            .addCase(getListClassOffical.fulfilled, (state, { payload }) => {
                state.classOfficals = payload.data;
            })

            //term class
            .addCase(getListTermClass.fulfilled, (state, { payload }) => {
                state.classTerms = payload.data;
            })
            .addCase(getClassTermDetail.fulfilled, (state, { payload }) => {
                state.classTerm = payload.data;
            })
            .addCase(updateTermClass.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(updateTermClass.fulfilled, (state, { payload }) => {
                state.successMessage = "Update class học phần thành công";
            })
            .addCase(createTermClass.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(createTermClass.fulfilled, (state, { payload }) => {
                state.successMessage = "Thêm class học phần thành công";
            })
            .addCase(deleteTermClass.rejected, (state, { payload }) => {
                state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
            })
            .addCase(deleteTermClass.fulfilled, (state, { payload }) => {
                state.successMessage = "Xóa class học phần thành công";
            });
    },
});

export const { messageClear } = classReducer.actions;
export default classReducer.reducer;

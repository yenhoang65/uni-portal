"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type TrainingProgram = {
    trainingProgramId: number;
    trainingCode: string | null;
    trainingProgramName: string | null;
    createdAt: Date;
    specializationId: number;
    specializationName: string;

    subjectId: number;
    schoolYear: string | null;
    subjectType: string | null;
    prerequisiteFor: string;
};

type GetParam = {
    currentPage: number;
    parPage: number;
    searchValue: string;
};

export const getListTrainingProgram = createAsyncThunk(
    "trainingProgram/getListTrainingProgram",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/training-programs`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getTrainingProgramDetail = createAsyncThunk(
    "trainingProgram/getTrainingProgramDetail",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/training-programs/${id}`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const updateTrainingProgram = createAsyncThunk(
    "trainingProgram/updateTrainingProgram",
    async (
        { id, dto }: { id: any; dto: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            // Gửi yêu cầu PUT với FormData
            const { data } = await api.put(`/training-programs/${id}`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            // return rejectWithValue(error.response?.data || error.message);
        }
    }
);

export const createTrainingProgram = createAsyncThunk(
    "trainingProgram/createTrainingProgram",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(`/training-programs`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteTrainingProgram = createAsyncThunk(
    "trainingProgram/deleteTrainingProgram",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(`/training-programs/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const trainingProgramReducer = createSlice({
    name: "trainingProgram",
    initialState: {
        successMessage: "",
        errorMessage: "",
        trainingPrograms: [] as TrainingProgram[],
        trainingProgram: {} as TrainingProgram,
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
            .addCase(getListTrainingProgram.fulfilled, (state, { payload }) => {
                state.trainingPrograms = payload.data;
            });
    },
});

export const { messageClear } = trainingProgramReducer.actions;
export default trainingProgramReducer.reducer;

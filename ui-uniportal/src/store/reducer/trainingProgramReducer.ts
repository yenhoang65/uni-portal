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

export type Subject = {
    subjectId: number;
    subjectName: string;
    subjectType: string;
    prerequisiteFor: {
        subjectId: number;
        subjectName: string;
    }[];
};

export type TrainingProgramWithSubjects = {
    trainingProgramId: number;
    trainingProgramName: string;
    schoolYear: string;
    subjects: Subject[];
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
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
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
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
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
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

//lấy danh sách các môn học theo mã CTĐT
export const getSubjectFollowTrainingProgram = createAsyncThunk(
    "trainingProgram/getSubjectFollowTrainingProgram",
    async (trainingProgramId: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(
                `/intermediaries/search/program/${trainingProgramId}`
            );

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

//xóa môn học ra khỏi CTĐT:
export const deleteSubjectFollowTrainingProgram = createAsyncThunk(
    "trainingProgram/deleteSubjectFollowTrainingProgram",
    async (
        {
            trainingProgramId,
            subjectId,
        }: { trainingProgramId: any; subjectId: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            const { data } = await api.delete(
                `/intermediaries?trainingProgramId=${trainingProgramId}&subjectId=${subjectId}`
            );

            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
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
        subjectFollowTrainingProgram: {} as TrainingProgramWithSubjects,
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
            })
            .addCase(
                getTrainingProgramDetail.fulfilled,
                (state, { payload }) => {
                    state.trainingProgram = payload.data;
                }
            )
            .addCase(createTrainingProgram.rejected, (state, { payload }) => {
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

            .addCase(createTrainingProgram.fulfilled, (state, { payload }) => {
                state.successMessage = "Thêm CTĐT thành công";
            })
            .addCase(deleteTrainingProgram.rejected, (state, { payload }) => {
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
            .addCase(deleteTrainingProgram.fulfilled, (state, { payload }) => {
                state.successMessage = "Xóa CTĐT thành công";
            })
            .addCase(
                getSubjectFollowTrainingProgram.fulfilled,
                (state, { payload }) => {
                    state.subjectFollowTrainingProgram = Array.isArray(
                        payload.data
                    )
                        ? payload.data[0]
                        : null;
                }
            )
            .addCase(
                deleteSubjectFollowTrainingProgram.rejected,
                (state, { payload }) => {
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
                        state.errorMessage =
                            "Đã có lỗi xảy ra, vui lòng thử lại";
                    }
                }
            )
            .addCase(
                deleteSubjectFollowTrainingProgram.fulfilled,
                (state, { payload }) => {
                    state.successMessage = "Xóa môn học thành công";
                }
            );
    },
});

export const { messageClear } = trainingProgramReducer.actions;
export default trainingProgramReducer.reducer;

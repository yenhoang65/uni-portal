"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type ActivateTime = {
    id: number;
    schoolYear: number;
    semester: number;
    startDate: Date;
    endDate: Date;
    status: string | null;
};

type GetParam = {
    currentPage: number;
    parPage: number;
    searchValue: string;
};

//student
export const getListActiveTimeStudent = createAsyncThunk(
    "activeTime/getListActiveTimeStudent",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/registration-period/student`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const createActiveTimeRegisStu = createAsyncThunk(
    "activeTime/createActiveTimeRegisStu",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(
                `/registration-period/student`,
                dto,
                {
                    withCredentials: true,
                }
            );

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteActiveTimeRegisStu = createAsyncThunk(
    "activeTime/deleteActiveTimeRegisStu",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(
                `/registration-period/student/${id}`
            );

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

//lecturer
export const getListActiveTimeLecturer = createAsyncThunk(
    "activeTime/getListActiveTimeLecturer",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/registration-period/teaching`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const createActiveTimeRegisLecturer = createAsyncThunk(
    "activeTime/createActiveTimeRegisLecturer",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(
                `/registration-period/teaching`,
                dto,
                {
                    withCredentials: true,
                }
            );

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteActiveTimeRegisLecturer = createAsyncThunk(
    "activeTime/deleteActiveTimeRegisLecturer",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(
                `/registration-period/teaching/${id}`
            );

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const activateTimeReducer = createSlice({
    name: "activateTime",
    initialState: {
        successMessage: "",
        errorMessage: "",
        activeTimeLecturers: [] as ActivateTime[],
        activeTimeLecturer: {} as ActivateTime,
        activeTimeStudents: [] as ActivateTime[],
        activeTimeStudent: {} as ActivateTime,
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
            .addCase(
                getListActiveTimeStudent.fulfilled,
                (state, { payload }) => {
                    state.activeTimeStudents = payload.data;
                }
            )
            .addCase(
                createActiveTimeRegisStu.fulfilled,
                (state, { payload }) => {
                    state.successMessage = "Thời gian đã được kích hoạt";
                }
            )
            .addCase(
                deleteActiveTimeRegisStu.fulfilled,
                (state, { payload }) => {
                    state.successMessage = "Thời gian kích hoạt đã xóa";
                }
            )

            .addCase(
                getListActiveTimeLecturer.fulfilled,
                (state, { payload }) => {
                    state.activeTimeLecturers = payload.data;
                }
            )
            .addCase(
                createActiveTimeRegisLecturer.fulfilled,
                (state, { payload }) => {
                    state.successMessage = "Thời gian đã được kích hoạt";
                }
            )
            .addCase(
                deleteActiveTimeRegisLecturer.fulfilled,
                (state, { payload }) => {
                    state.successMessage = "Thời gian kích hoạt đã xóa";
                }
            );
    },
});

export const { messageClear } = activateTimeReducer.actions;
export default activateTimeReducer.reducer;

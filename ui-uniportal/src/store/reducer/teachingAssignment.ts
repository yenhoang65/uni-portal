"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type TeachingAssignment = {
    assignmentId: number;
    lecturerId: number;
    lecturerName: string | null;
    subjectId: number;
    subjectName: string | null;
    termClassId: number;
    className: string | null;
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

export const getListTeachingAssignment = createAsyncThunk(
    "teachingAssignment/getListTeachingAssignment",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/teaching-assignment`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getListTeachingAssignmentByLecturerId = createAsyncThunk(
    "teachingAssignment/getListTeachingAssignmentByLecturerId",
    async (token: any, { getState, rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/teaching-assignment`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.message || error.response?.data);
        }
    }
);

export const getTeachingAssignmentDetail = createAsyncThunk(
    "teachingAssignment/getTeachingAssignmentDetail",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(`/teaching-assignment/${id}`, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const updateTeachingAssignment = createAsyncThunk(
    "teachingAssignment/updateTeachingAssignment",
    async (
        { id, dto }: { id: any; dto: any },
        { rejectWithValue, fulfillWithValue }
    ) => {
        try {
            // Gửi yêu cầu PUT với FormData
            const { data } = await api.put(`/teaching-assignment/${id}`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            // return rejectWithValue(error.response?.data || error.message);
        }
    }
);

export const createTeachingAssignment = createAsyncThunk(
    "teachingAssignment/createTeachingAssignment",
    async ({ dto }: { dto: any }, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.post(`/teaching-assignment`, dto, {
                withCredentials: true,
            });

            return fulfillWithValue(data);
        } catch (error) {
            // Xử lý lỗi nếu có
            return rejectWithValue("Đã có lỗi xảy ra, vui lòng thử");
        }
    }
);

export const deleteTeachingAssignment = createAsyncThunk(
    "teachingAssignment/deleteTeachingAssignment",
    async (id: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.delete(`/teaching-assignment/${id}`);

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getClassTermUnAssign = createAsyncThunk(
    "teachingAssignment/getClassTermUnAssign",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(
                `/teaching-assignment/unassigned-classes`
            );

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const teachingAssignmentReducer = createSlice({
    name: "teachingAssignment",
    initialState: {
        successMessage: "",
        errorMessage: "",
        teachingAssignments: [] as TeachingAssignment[],
        teachingAssignment: {} as TeachingAssignment,

        classTermUnAssigns: [] as ClassTerm[],
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
                getListTeachingAssignment.fulfilled,
                (state, { payload }) => {
                    state.teachingAssignments = payload.data;
                }
            )
            .addCase(
                getListTeachingAssignmentByLecturerId.fulfilled,
                (state, { payload }) => {
                    state.teachingAssignments = payload.data;
                }
            )
            .addCase(getClassTermUnAssign.fulfilled, (state, { payload }) => {
                state.classTermUnAssigns = payload.data;
            })
            .addCase(
                getTeachingAssignmentDetail.fulfilled,
                (state, { payload }) => {
                    state.teachingAssignment = payload.data;
                }
            )
            .addCase(
                updateTeachingAssignment.rejected,
                (state, { payload }) => {
                    state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
                }
            )
            .addCase(
                updateTeachingAssignment.fulfilled,
                (state, { payload }) => {
                    state.successMessage =
                        "Update teachingAssignment thành công";
                }
            )
            .addCase(
                createTeachingAssignment.rejected,
                (state, { payload }) => {
                    state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
                }
            )
            .addCase(
                createTeachingAssignment.fulfilled,
                (state, { payload }) => {
                    state.successMessage = "Thêm teachingAssignment thành công";
                }
            )
            .addCase(
                deleteTeachingAssignment.rejected,
                (state, { payload }) => {
                    state.errorMessage = "Đã có lỗi xảy ra, vui lòng thử lại";
                }
            )
            .addCase(
                deleteTeachingAssignment.fulfilled,
                (state, { payload }) => {
                    state.successMessage = "Xóa teachingAssignment thành công";
                }
            );
    },
});

export const { messageClear } = teachingAssignmentReducer.actions;
export default teachingAssignmentReducer.reducer;

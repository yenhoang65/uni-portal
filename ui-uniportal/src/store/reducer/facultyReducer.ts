"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type Faculty = {
    facultyId: number | null;
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
            });
    },
});

export const { messageClear } = facultyReducer.actions;
export default facultyReducer.reducer;

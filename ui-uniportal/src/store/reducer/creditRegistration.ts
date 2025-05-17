"use client";

import api from "@/service/api";
import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";

type Subject = {
    subject: {
        subjectId: number;
        subjectName: string;
        ltCredits: number;
        thCredits: number;
        subjectDescription: string;
        subjectCoefficient: number;
    };
    subjectType: string;
    schoolYear: string;
};

type ClassFollowSubject = {
    classStudentId: number;
    teachingScheduleRequest: {
        assignmentId: number;
        status: string;
        materials: [
            {
                lt: string;
                th: string;
            }
        ];
        scheduleDetails: [
            {
                classroom_id: number;
                lesson: string;
                date_time: string;
                end_date: string;
                class_type: "LT";
            },
            {
                classroom_id: number;
                lesson: string;
                date_time: string;
                end_date: string;
                class_type: "LT";
            }
        ];
    };
    teachingAssignment: {
        lecturerId: number;
        subjectId: number;
        subjectName: string;
        termClassId: number;
        assignmentType: null;
    };
    termClass: {
        classname: string;
        progress: string;
        semester: string;
        schoolyears: string;
    };
    subject: {
        subjectId: number;
        subjectName: string;
        ltCredits: number;
        thCredits: number;
        subjectDescription: string;
        subjectCoefficient: number;
    };
};

type GetParam = {
    currentPage: number;
    parPage: number;
    searchValue: string;
};

export const getSubjectsFollowUser = createAsyncThunk(
    "regis/getSubjectsFollowUser",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const token = window.localStorage.getItem("accessToken");
            const { data } = await api.get(`/training-programs/subjects`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const getClassFollowSubject = createAsyncThunk(
    "regis/getClassFollowSubject",
    async (subjectId: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const { data } = await api.get(
                `/class-student/opened-classes/${subjectId}`
            );

            return fulfillWithValue(data);
        } catch (error) {
            // return rejectWithValue(error.response.data);
        }
    }
);

export const registerTC = createAsyncThunk(
    "regis/registerTC",
    async (dto: any, { rejectWithValue, fulfillWithValue }) => {
        try {
            const token = window.localStorage.getItem("accessToken");
            const { data } = await api.post(
                `/class-registration/register`,
                dto,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

//TKB Lecturer
export const lecturerTimeline = createAsyncThunk(
    "timeline/lecturerTimeline",
    async (_, { rejectWithValue, fulfillWithValue }) => {
        try {
            const token = window.localStorage.getItem("accessToken");
            const { data } = await api.get(`teaching-schedule/status-success`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });

            console.log(data);
            return fulfillWithValue(data);
        } catch (error) {
            const e = error as Error;
            return rejectWithValue(e.message || "An unknown error occurred");
        }
    }
);

//TKB Student

export const creditRegistrationReducer = createSlice({
    name: "creditRegistration",
    initialState: {
        successMessage: "",
        errorMessage: "",
        subjects: [] as Subject[],
        classOpenFollowSubject: [],
        lecturerTimelines: [],
    },
    reducers: {
        messageClear: (state) => {
            state.errorMessage = "";
            state.successMessage = "";
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(getSubjectsFollowUser.fulfilled, (state, { payload }) => {
                state.subjects = payload.data;
            })
            .addCase(getClassFollowSubject.fulfilled, (state, { payload }) => {
                state.classOpenFollowSubject = payload.data;
            })
            .addCase(registerTC.rejected, (state, { payload }) => {
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

            .addCase(registerTC.fulfilled, (state, { payload }) => {
                state.successMessage = "Đăng ký tín chỉ thành công";
            })
            .addCase(lecturerTimeline.fulfilled, (state, { payload }) => {
                state.lecturerTimelines = payload.data;
            });
    },
});

export const { messageClear } = creditRegistrationReducer.actions;
export default creditRegistrationReducer.reducer;

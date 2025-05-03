import React, { useState, useEffect, useRef } from "react";
import { useRouter } from "next/router";
import { Button } from "@/components/Button";
import InputWithLabel from "@/components/InputWithLabel";
import SelectWithLabel from "@/components/SelectWithLabel";
import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import AuthGuard from "@/components/AuthGuard";
import { TypographyBody } from "@/components/TypographyBody";

// Use dynamic import with SSR set to false
const JoditEditor = dynamic(() => import("jodit-react"), {
    ssr: false,
});

import dynamic from "next/dynamic"; // Import dynamic

type State = {
    title: string;
    description: string;
    deadline: string;
    file_url: string;
    class_subject_id: string;
};

const CreateEditAssignment = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [state, setState] = useState<State>({
        title: "",
        description: "",
        deadline: "",
        file_url: "",
        class_subject_id: "",
    });
    const [loading, setLoading] = useState(false);
    const editor = useRef<any>(null);

    const classroomOptions = [
        { value: "101", label: "Classroom 101" },
        { value: "102", label: "Classroom 102" },
        { value: "201", label: "Classroom 201" },
    ];

    const inputHandle = (
        e: React.ChangeEvent<
            HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
        >
    ) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files && e.target.files[0]) {
            setState({ ...state, file_url: e.target.files[0].name });
        }
    };

    const handleSubmit = async () => {
        setLoading(true);
        try {
            if (mode === "create") {
                console.log("Creating assignment:", state);
            } else {
                console.log("Updating assignment:", state);
            }
            router.push("/assignment");
        } catch (error) {
            console.error("Failed to submit assignment:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (query.id) {
            setMode("edit");
            setLoading(true);
            const fetchAssignmentData = async (id: string) => {
                setState({
                    title: "Existing Assignment",
                    description: "Description of the existing assignment.",
                    deadline: "2025-12-31T23:59:00",
                    file_url: "existing_file.pdf",
                    class_subject_id: "101",
                });
                setLoading(false);
            };
            fetchAssignmentData(query.id as string);
        } else {
            setMode("create");
            setState({
                title: "",
                description: "",
                deadline: "",
                file_url: "",
                class_subject_id: classroomOptions[0]?.value || "",
            });
        }
    }, [query.id, classroomOptions]);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <AuthGuard allowedRoles={["admin", "lecturer"]}>
            <BorderBox
                title={
                    mode === "create" ? "Create Assignment" : "Edit Assignment"
                }
            >
                <div className={styles.formContainer}>
                    <div className={styles.formGrid}>
                        <div className={styles.formItem}>
                            <InputWithLabel
                                label="Title"
                                name="title"
                                value={state.title}
                                onChange={inputHandle}
                                type="text"
                                required
                            />
                        </div>
                        <div className={styles.formItem}>
                            <SelectWithLabel
                                label="Classroom"
                                name="class_subject_id"
                                value={state.class_subject_id}
                                onChange={
                                    inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                                }
                                options={classroomOptions}
                                required
                            />
                        </div>
                        <div className={styles.formItem}>
                            <InputWithLabel
                                label="Deadline"
                                name="deadline"
                                value={state.deadline}
                                onChange={inputHandle}
                                type="datetime-local"
                                required
                            />
                        </div>
                        <div className={styles.formItem}>
                            <label htmlFor="file_url" className={styles.label}>
                                File
                            </label>
                            <input
                                id="file_url"
                                name="file_url"
                                type="file"
                                onChange={handleFileChange}
                                className={styles.fileInput}
                            />
                            {state.file_url && (
                                <TypographyBody tag="p" theme="sm">
                                    Selected File: {state.file_url}
                                </TypographyBody>
                            )}
                        </div>
                        <div className={styles.formItemFull}>
                            <label
                                htmlFor="description"
                                className={styles.label}
                            >
                                Description
                            </label>
                            <JoditEditor
                                ref={editor}
                                value={state.description}
                                onChange={(newContent) =>
                                    setState({
                                        ...state,
                                        description: newContent,
                                    })
                                }
                                className={styles.inputDesc}
                            />
                        </div>
                    </div>
                    <div className={styles.button}>
                        <Button
                            className={styles.buttonAction}
                            onClick={handleSubmit}
                            disabled={loading}
                        >
                            {mode === "create" ? "Create" : "Update"}
                        </Button>
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default CreateEditAssignment;

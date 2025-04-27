import AuthGuard from "@/components/AuthGuard";

const CreateEditTrainingProgram = () => {
    return (
        <AuthGuard allowedRoles={["admin"]}>
            <div>CreateEditTrainingProgram</div>;
        </AuthGuard>
    );
};

export default CreateEditTrainingProgram;

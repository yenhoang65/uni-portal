import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import InputWithLabel from "@/components/InputWithLabel";
import { useRouter } from "next/router";
import { useEffect, useRef, useState } from "react"; // Thêm useRef
import { Button } from "@/components/Button";
import SelectWithLabel from "@/components/SelectWithLabel";
import clsx from "clsx";
import { TypographyBody } from "@/components/TypographyBody";
import AuthGuard from "@/components/AuthGuard";

type State = {
    ma: string;
    number_of_seats: string;
    classroom_type: string;
    device: string[];
};

const classroomTypeOptions = [
    { value: "Lý thuyết", label: "Lý thuyết" },
    { value: "Thực hành", label: "Thực hành" },
    { value: "Hội trường", label: "Hội trường" },
    { value: "Phòng Lab", label: "Phòng Lab" },
];

const deviceOptions = [
    { value: "Máy chiếu", label: "Máy chiếu" },
    { value: "Bảng trắng", label: "Bảng trắng" },
    { value: "Điều hòa", label: "Điều hòa" },
    { value: "Máy tính", label: "Máy tính" },
    { value: "Bàn thực hành", label: "Bàn thực hành" },
    { value: "Âm thanh", label: "Âm thanh" },
    { value: "Bảng tương tác", label: "Bảng tương tác" },
    { value: "Thiết bị thí nghiệm", label: "Thiết bị thí nghiệm" },
    // Thêm các thiết bị khác vào đây
];

const CreateEditClassroom = () => {
    const router = useRouter();
    const { query } = router;
    const [mode, setMode] = useState<"create" | "edit">("create");
    const [state, setState] = useState<State>({
        ma: "",
        number_of_seats: "",
        classroom_type: classroomTypeOptions[0]?.value || "",
        device: [],
    });
    const [selectedDevices, setSelectedDevices] = useState<string[]>([]);
    const [isDeviceDropdownOpen, setIsDeviceDropdownOpen] = useState(false);
    const dropdownRef = useRef<HTMLDivElement>(null); // Tạo một ref cho dropdown

    const inputHandle = (
        e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
    ) => {
        setState({
            ...state,
            [e.target.name]: e.target.value,
        });
    };

    const toggleDeviceDropdown = () => {
        setIsDeviceDropdownOpen(!isDeviceDropdownOpen);
    };

    const handleDeviceSelect = (deviceValue: string) => {
        if (selectedDevices.includes(deviceValue)) {
            setSelectedDevices(
                selectedDevices.filter((dv) => dv !== deviceValue)
            );
        } else {
            setSelectedDevices([...selectedDevices, deviceValue]);
        }
    };

    useEffect(() => {
        setState({ ...state, device: selectedDevices });
    }, [selectedDevices]);

    useEffect(() => {
        if (query.mode === "edit" && query.id) {
            setMode("edit");
            // Call API to get classroom data by ID
            // Replace this with your actual API call
            setState({
                ma: "P.101",
                number_of_seats: "30",
                classroom_type: "Lý thuyết",
                device: ["Máy chiếu", "Bảng trắng"],
            });
            setSelectedDevices(["Máy chiếu", "Bảng trắng"]);
        } else {
            setMode("create");
            setState({
                ma: "",
                number_of_seats: "",
                classroom_type: classroomTypeOptions[0]?.value || "",
                device: [],
            });
            setSelectedDevices([]);
        }
    }, [query.mode, query.id]);

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (
                dropdownRef.current &&
                !dropdownRef.current.contains(event.target as Node) &&
                event.target !==
                    document.querySelector(`.${styles.multiSelectInput}`)
            ) {
                setIsDeviceDropdownOpen(false);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [isDeviceDropdownOpen]); // Theo dõi trạng thái mở để thêm/xóa event listener

    const handleSubmit = () => {
        if (mode === "create") {
            // Call API to create classroom with state
            console.log("Creating classroom:", state);
        } else {
            // Call API to update classroom with state
            console.log("Updating classroom:", state);
        }
        // After successful submission, you might want to redirect
        router.push("/classroom"); // Điều hướng đến trang quản lý lớp học
    };

    return (
        <AuthGuard allowedRoles={["admin", "employee"]}>
            <BorderBox
                title={
                    mode === "create" ? "Thêm phòng học" : "Chỉnh sửa phòng học"
                }
            >
                <section className={styles.container}>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Mã phòng"
                            name="ma"
                            value={state.ma}
                            onChange={inputHandle}
                            type="text"
                            required
                            disabled={mode === "edit"}
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <InputWithLabel
                            label="Số chỗ ngồi"
                            name="number_of_seats"
                            value={state.number_of_seats}
                            onChange={inputHandle}
                            type="number"
                            required
                        />
                    </div>
                    <div className={styles.gridItem}>
                        <SelectWithLabel
                            label="Loại phòng"
                            name="classroom_type"
                            value={state.classroom_type}
                            onChange={
                                inputHandle as React.ChangeEventHandler<HTMLSelectElement>
                            }
                            options={classroomTypeOptions}
                            required
                        />
                    </div>
                    <div className={styles.gridItemFull}>
                        <div className={styles.multiSelectContainer}>
                            <TypographyBody tag="span" theme="md-bold">
                                Thiết bị
                            </TypographyBody>
                            <div
                                onClick={toggleDeviceDropdown}
                                className={styles.multiSelectInput}
                            >
                                {selectedDevices.length > 0 ? (
                                    selectedDevices.map((dv) => (
                                        <span
                                            key={dv}
                                            className={styles.selectedDevice}
                                        >
                                            {deviceOptions.find(
                                                (opt) => opt.value === dv
                                            )?.label || dv}
                                        </span>
                                    ))
                                ) : (
                                    <span className={styles.placeholder}>
                                        Chọn thiết bị
                                    </span>
                                )}
                                <span className={styles.arrow}>
                                    {isDeviceDropdownOpen ? "▲" : "▼"}
                                </span>
                            </div>
                            {isDeviceDropdownOpen && (
                                <div
                                    ref={dropdownRef}
                                    className={styles.multiSelectDropdown}
                                >
                                    {deviceOptions.map((option) => (
                                        <div
                                            key={option.value}
                                            className={clsx(
                                                styles.dropdownItem,
                                                {
                                                    [styles.selected]:
                                                        selectedDevices.includes(
                                                            option.value
                                                        ),
                                                }
                                            )}
                                            onClick={() =>
                                                handleDeviceSelect(option.value)
                                            }
                                        >
                                            <input
                                                type="checkbox"
                                                checked={selectedDevices.includes(
                                                    option.value
                                                )}
                                                readOnly
                                            />
                                            {option.label}
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                    </div>
                </section>

                <div className={styles.button}>
                    <Button
                        className={styles.buttonAction}
                        onClick={handleSubmit}
                    >
                        {mode === "create" ? "Lưu" : "Cập nhật"}
                    </Button>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default CreateEditClassroom;

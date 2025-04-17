import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useState } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { FaEye } from "react-icons/fa";
import { AiFillEdit } from "react-icons/ai";
import { MdDeleteForever } from "react-icons/md";
import clsx from "clsx";
import { IoMdAddCircle } from "react-icons/io";
import ModalConfirm from "@/components/ModalConfirm";
import { TypographyBody } from "@/components/TypographyBody";

type ClassroomType = {
    id: string;
    ma: string;
    number_of_seats: number;
    classroom_type: string;
    device: string[];
};

const classrooms: ClassroomType[] = [
    {
        id: "CLA001",
        ma: "P.101",
        number_of_seats: 30,
        classroom_type: "Lý thuyết",
        device: ["Máy chiếu", "Bảng trắng", "Điều hòa"],
    },
    {
        id: "CLA002",
        ma: "Lab.A",
        number_of_seats: 25,
        classroom_type: "Thực hành",
        device: ["Máy tính", "Bàn thực hành", "Máy lạnh"],
    },
    {
        id: "CLA003",
        ma: "H.205",
        number_of_seats: 40,
        classroom_type: "Hội trường",
        device: ["Âm thanh", "Máy chiếu", "Bục giảng"],
    },
    {
        id: "CLA004",
        ma: "P.102",
        number_of_seats: 30,
        classroom_type: "Lý thuyết",
        device: ["Máy chiếu", "Bảng tương tác"],
    },
    {
        id: "CLA005",
        ma: "Lab.B",
        number_of_seats: 20,
        classroom_type: "Thực hành",
        device: ["Thiết bị thí nghiệm", "Máy lạnh"],
    },
];

const Classroom = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteClassroomId, setDeleteClassroomId] = useState<string | null>(
        null
    );

    const handleDelete = () => {
        if (deleteClassroomId) {
            // dispatch(delete_classroom(deleteClassroomId));
            setIsModalOpen(false);
            setDeleteClassroomId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteClassroomId(null);
    };

    const filteredClassrooms = classrooms.filter((classroom) =>
        Object.values(classroom).some((value) =>
            String(value).toLowerCase().includes(searchValue.toLowerCase())
        )
    );

    const startIndex = (currentPage - 1) * parPage;
    const endIndex = startIndex + parPage;
    const currentClassrooms = filteredClassrooms.slice(startIndex, endIndex);

    return (
        <BorderBox title="Quản lý phòng học">
            <div className={styles.box}>
                <div className={styles.add}>
                    <Search
                        setParPage={setParPage}
                        setSearchValue={setSearchValue}
                        searchValue={searchValue}
                    />

                    <Link
                        href={"/classroom/create-edit"}
                        className={styles.buttonAdd}
                    >
                        <IoMdAddCircle /> Thêm mới
                    </Link>
                </div>

                <div className={styles.tableWrapper}>
                    <table className={styles.table}>
                        <thead className={styles.thead}>
                            <tr>
                                <th style={{ minWidth: "80px" }}>No</th>
                                <th style={{ minWidth: "120px" }}>Mã</th>
                                <th style={{ minWidth: "150px" }}>
                                    Số chỗ ngồi
                                </th>
                                <th style={{ minWidth: "150px" }}>
                                    Loại phòng
                                </th>
                                <th style={{ minWidth: "200px" }}>Thiết bị</th>
                                <th style={{ minWidth: "70px" }}>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            {currentClassrooms.map((classroom, index) => (
                                <tr key={classroom.id}>
                                    <td>
                                        {(currentPage - 1) * parPage +
                                            index +
                                            1}
                                    </td>
                                    <td>{classroom.ma}</td>
                                    <td>{classroom.number_of_seats}</td>
                                    <td>{classroom.classroom_type}</td>
                                    <td>
                                        {classroom.device.map((dev, idx) => (
                                            <TypographyBody
                                                key={idx}
                                                tag="span"
                                                className={styles.deviceItem}
                                            >
                                                {dev}
                                                {idx <
                                                    classroom.device.length -
                                                        1 && ", "}
                                            </TypographyBody>
                                        ))}
                                    </td>
                                    <td className={styles.buttonAction}>
                                        <Link
                                            href={`/classroom/view?id=${classroom.id}`}
                                            className={clsx(styles.viewButton)}
                                        >
                                            <FaEye />
                                        </Link>
                                        <Link
                                            href={`/classroom/create-edit?id=${classroom.id}&mode=edit`}
                                            className={clsx(
                                                styles.viewButton,
                                                styles.viewButtonUpdate
                                            )}
                                        >
                                            <AiFillEdit />
                                        </Link>
                                        <Link
                                            onClick={(e) => {
                                                e.preventDefault();
                                                setIsModalOpen(true);
                                                setDeleteClassroomId(
                                                    classroom.id
                                                );
                                            }}
                                            href="#"
                                            className={clsx(
                                                styles.viewButton,
                                                styles.viewButtonDelete
                                            )}
                                        >
                                            <MdDeleteForever />
                                        </Link>

                                        {isModalOpen &&
                                            deleteClassroomId ===
                                                classroom.id && (
                                                <ModalConfirm
                                                    message="Bạn có chắc chắn muốn xóa?"
                                                    onConfirm={handleDelete}
                                                    onCancel={handleCancel}
                                                />
                                            )}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>

                <div className={styles.paginationWrapper}>
                    <Pagination
                        pageNumber={currentPage}
                        setPageNumber={setCurrentPage}
                        totalItem={filteredClassrooms.length}
                        parPage={parPage}
                        showItem={3}
                    />
                </div>
            </div>
        </BorderBox>
    );
};

export default Classroom;

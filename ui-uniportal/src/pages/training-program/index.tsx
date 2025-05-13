import BorderBox from "@/components/BorderBox";
import styles from "./styles.module.css";
import { useEffect, useState } from "react";
import Search from "@/components/Search";
import Pagination from "@/components/Pagination";
import Link from "next/link";
import { FaEye } from "react-icons/fa";
import { AiFillEdit } from "react-icons/ai";
import { MdDeleteForever } from "react-icons/md";
import clsx from "clsx";
import { IoMdAddCircle } from "react-icons/io";
import ModalConfirm from "@/components/ModalConfirm";
import SelectWithLabel from "@/components/SelectWithLabel";
import AuthGuard from "@/components/AuthGuard";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/store";
import { getListSpec } from "@/store/reducer/specializationReducer";
import { getListTrainingProgram } from "@/store/reducer/trainingProgramReducer";

const TrainingProgram = () => {
    const dispatch = useDispatch<AppDispatch>();
    const { specializations } = useSelector(
        (state: RootState) => state.specialization
    );
    const { trainingPrograms } = useSelector(
        (state: RootState) => state.trainingProgram
    );

    const [currentPage, setCurrentPage] = useState(1);
    const [searchValue, setSearchValue] = useState("");
    const [parPage, setParPage] = useState(5);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [deleteProgramId, setDeleteProgramId] = useState<number | null>(null);

    useEffect(() => {
        dispatch(getListSpec());
        dispatch(getListTrainingProgram());
    }, []);

    const handleDelete = () => {
        if (deleteProgramId) {
            // Gọi API hoặc dispatch action để xóa chương trình đào tạo
            console.log("Deleting program with ID:", deleteProgramId);
            setIsModalOpen(false);
            setDeleteProgramId(null);
        }
    };

    const handleCancel = () => {
        setIsModalOpen(false);
        setDeleteProgramId(null);
    };

    return (
        <AuthGuard allowedRoles={["admin"]}>
            <BorderBox title="Quản lý Chương trình Đào tạo">
                <div className={styles.box}>
                    <div className={styles.filterAndAdd}>
                        <div className={styles.searchAndFilter}>
                            <Search
                                setParPage={setParPage}
                                setSearchValue={setSearchValue}
                                searchValue={searchValue}
                            />
                            <div className={styles.filter}>
                                <SelectWithLabel
                                    name="filterSpecialization"
                                    value={""}
                                    onChange={() => {}}
                                    options={[
                                        { value: "", label: "Tất cả" },
                                        ...specializations.map(
                                            (specialization) => ({
                                                value:
                                                    specialization.specializationId ??
                                                    "",
                                                label:
                                                    specialization.specializationName ??
                                                    "",
                                            })
                                        ),
                                    ]}
                                />
                            </div>
                        </div>

                        <Link
                            href={"/training-program/create-edit"}
                            className={styles.buttonAdd}
                        >
                            <IoMdAddCircle /> Thêm mới
                        </Link>
                    </div>

                    <div className={styles.tableWrapper}>
                        <table className={styles.table}>
                            <thead className={styles.thead}>
                                <tr>
                                    <th style={{ width: "80px" }}>STT</th>
                                    <th style={{ width: "200px" }}>Mã CTĐT</th>
                                    <th style={{ minWidth: "250px" }}>
                                        Chuyên ngành
                                    </th>
                                    <th style={{ minWidth: "150px" }}>
                                        Khóa học
                                    </th>

                                    <th style={{ minWidth: "100px" }}>
                                        Hành động
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                {trainingPrograms.map((program, index) => (
                                    <tr key={program.trainingProgramId}>
                                        <td>1</td>
                                        <td>{program.trainingProgramId}</td>
                                        <td>{program.specializationName}</td>
                                        <td>{program.trainingCode}</td>

                                        <td className={styles.buttonAction}>
                                            <Link
                                                href={`/training-program/view?id=${program.trainingProgramId}`}
                                                className={clsx(
                                                    styles.viewButton
                                                )}
                                            >
                                                <FaEye />
                                            </Link>
                                            <Link
                                                href={`/training-program/create-edit?id=${program.trainingProgramId}&mode=edit`}
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
                                                    setDeleteProgramId(
                                                        program.trainingProgramId
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
                                                deleteProgramId ===
                                                    program.trainingProgramId && (
                                                    <ModalConfirm
                                                        message="Are you sure you want to delete?"
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
                            totalItem={trainingPrograms.length}
                            parPage={parPage}
                            showItem={3}
                        />
                    </div>
                </div>
            </BorderBox>
        </AuthGuard>
    );
};

export default TrainingProgram;

import { RiArrowLeftDoubleLine, RiArrowRightDoubleLine } from "react-icons/ri";
import styles from "./styles.module.css";

type PaginationProps = {
    pageNumber: number;
    setPageNumber: (page: number) => void;
    totalItem: number;
    parPage: number;
    showItem: number;
};

const Pagination: React.FC<PaginationProps> = ({
    pageNumber,
    setPageNumber,
    totalItem,
    parPage,
    showItem,
}) => {
    let totalPage = Math.ceil(totalItem / parPage);
    let startPage = pageNumber;
    let dif = totalPage - pageNumber;

    if (dif <= showItem) {
        startPage = totalPage - showItem;
    }

    if (startPage <= 0) {
        startPage = 1;
    }

    let endPage = showItem + startPage;

    const createButton = () => {
        const btns = [];
        for (let i = startPage; i < endPage && i <= totalPage; i++) {
            btns.push(
                <li
                    key={i}
                    onClick={() => setPageNumber(i)}
                    className={
                        pageNumber === i
                            ? `${styles.pageButton} ${styles.active}`
                            : styles.pageButton
                    }
                >
                    {i}
                </li>
            );
        }
        return btns;
    };

    return (
        <ul className={styles.pagination}>
            {pageNumber > 1 && (
                <li
                    onClick={() => setPageNumber(pageNumber - 1)}
                    className={styles.pageButton}
                >
                    <RiArrowLeftDoubleLine />
                </li>
            )}
            {createButton()}
            {pageNumber < totalPage && (
                <li
                    onClick={() => setPageNumber(pageNumber + 1)}
                    className={styles.pageButton}
                >
                    <RiArrowRightDoubleLine />
                </li>
            )}
        </ul>
    );
};

export default Pagination;

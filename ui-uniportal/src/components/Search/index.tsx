import styles from "./styles.module.css";

type SearchProps = {
    setParPage: (value: number) => void;
    searchValue: string;
    setSearchValue: (value: string) => void;
};

const Search: React.FC<SearchProps> = ({
    setParPage,
    searchValue,
    setSearchValue,
}) => {
    return (
        <div className={styles.wrapper}>
            <select
                onChange={(e) => setParPage(parseInt(e.target.value))}
                className={styles.select}
            >
                <option value="5">5</option>
                <option value="10">10</option>
                <option value="20">20</option>
            </select>
            <input
                onChange={(e) => setSearchValue(e.target.value)}
                value={searchValue}
                className={styles.input}
                type="text"
                name="search"
                placeholder="Search..."
            />
        </div>
    );
};

export default Search;

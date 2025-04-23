package io.spring.uni_portal.repository;

import io.spring.uni_portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findTopByOrderByUserIdDesc();
    @Query("SELECT u FROM User u WHERE CAST(u.userId AS string) LIKE :pattern ORDER BY u.userId DESC")
    List<User> findByUserIdLikeCustom(@Param("pattern") String pattern);
    @Query("SELECT u FROM User u WHERE CAST(u.userId AS string) LIKE CONCAT(:prefix, '%')")
    List<User> findUserIdLikeCustom(@Param("prefix") String prefix);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}

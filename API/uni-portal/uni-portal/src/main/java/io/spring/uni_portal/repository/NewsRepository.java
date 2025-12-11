package io.spring.uni_portal.repository;


import io.spring.uni_portal.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByCateNews_CateNewsId(Long cateNewsId);
}

package io.spring.uni_portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cate_news")
public class CateNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_news_id")
    private Long cateNewsId;

    @Column(name = "title")
    private String title;

    public CateNews() {
    }

    // Constructor có tham số
    public CateNews(Long cateNewsId, String title) {
        this.cateNewsId = cateNewsId;
        this.title = title;
    }

    // Getter & Setter
    public Long getCateNewsId() {
        return cateNewsId;
    }

    public void setCateNewsId(Long cateNewsId) {
        this.cateNewsId = cateNewsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

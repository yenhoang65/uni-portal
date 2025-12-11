package io.spring.uni_portal.dto.CateNews;

public class CateNewsResponseDTO {
    private Long cateNewsId;
    private String title;

    public CateNewsResponseDTO() {
    }

    public CateNewsResponseDTO(Long cateNewsId, String title) {
        this.cateNewsId = cateNewsId;
        this.title = title;
    }

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

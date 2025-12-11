package io.spring.uni_portal.dto.CateNews;

public class CateNewsRequestDTO {
    private String title;

    public CateNewsRequestDTO() {
    }

    public CateNewsRequestDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
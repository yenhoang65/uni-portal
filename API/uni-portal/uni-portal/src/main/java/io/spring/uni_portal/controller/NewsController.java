package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.News.NewsDetailWithRelatedDTO;
import io.spring.uni_portal.dto.News.NewsRequestDTO;
import io.spring.uni_portal.dto.News.NewsResponseDTO;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.NewsService.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private INewsService newsService;

    @PostMapping(consumes = {"multipart/form-data"})
    public Response<NewsResponseDTO> create(@ModelAttribute NewsRequestDTO dto) {
        return Response.success("Tạo tin tức thành công", newsService.create(dto));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public Response<NewsResponseDTO> update(@PathVariable Long id, @ModelAttribute NewsRequestDTO dto) {
        return Response.success("Cập nhật tin tức thành công", newsService.update(id, dto));
    }

    @GetMapping("/{id}")
    public Response<NewsResponseDTO> getById(@PathVariable Long id) {
        return Response.success("Lấy chi tiết thành công", newsService.getById(id));
    }

    @GetMapping
    public Response<List<NewsResponseDTO>> getAll() {
        return Response.success("Lấy danh sách thành công", newsService.getAll());
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable Long id) {
        newsService.delete(id);
        return Response.success("Xóa thành công", null);
    }

    // API 1: Lấy danh sách theo cate_news_id
    @GetMapping("/by-cate/{cateNewsId}")
    public Response<Map<String, Object>> getNewsByCate(@PathVariable Long cateNewsId) {
        return Response.success("Lấy tin theo chuyên mục thành công", newsService.getNewsByCateId(cateNewsId));
    }

    // API 2: Lấy chi tiết tin + các tin cùng chuyên mục
    @GetMapping("/cate-new/{newsId}")
    public Response<NewsDetailWithRelatedDTO> getDetailAndRelated(@PathVariable Long newsId) {
        return Response.success("Lấy chi tiết tin và các tin cùng chuyên mục", newsService.getNewsDetailAndRelated(newsId));
    }
}
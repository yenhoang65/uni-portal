package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.CateNews.CateNewsRequestDTO;
import io.spring.uni_portal.dto.CateNews.CateNewsResponseDTO;
import io.spring.uni_portal.service.CateNewsService.ICateNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.spring.uni_portal.response.Response;

@RestController
@RequestMapping("/api/cate-news")
public class CateNewsController {

    @Autowired
    private ICateNewsService cateNewsService;

    @PostMapping
    public Response<CateNewsResponseDTO> create(@RequestBody CateNewsRequestDTO request) {
        CateNewsResponseDTO dto = cateNewsService.create(request);
        return Response.success("Thêm danh mục tin tức thành công", dto);
    }

    @PutMapping("/{id}")
    public Response<CateNewsResponseDTO> update(@PathVariable Long id, @RequestBody CateNewsRequestDTO request) {
        CateNewsResponseDTO dto = cateNewsService.update(id, request);
        return Response.success("Cập nhật danh mục thành công", dto);
    }

    @DeleteMapping("/{id}")
    public Response<String> delete(@PathVariable Long id) {
        cateNewsService.delete(id);
        return Response.success("Xóa danh mục thành công", null);
    }

    @GetMapping("/{id}")
    public Response<CateNewsResponseDTO> getById(@PathVariable Long id) {
        CateNewsResponseDTO dto = cateNewsService.getById(id);
        return Response.success("Lấy chi tiết danh mục thành công", dto);
    }

    @GetMapping
    public Response<List<CateNewsResponseDTO>> getAll() {
        List<CateNewsResponseDTO> list = cateNewsService.getAll();
        return Response.success("Lấy danh sách danh mục thành công", list);
    }
}
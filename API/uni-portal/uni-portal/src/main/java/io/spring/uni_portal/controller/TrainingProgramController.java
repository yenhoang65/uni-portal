package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.TrainingProgram.TrainingProgramDTO;
import io.spring.uni_portal.dto.TrainingProgram.TrainingProgramResponse;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.TrainingProgramService.ITrainingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-programs")
public class TrainingProgramController {

    @Autowired
    private ITrainingProgramService trainingProgramService;

    @PostMapping
    public Response<TrainingProgramResponse> create(@RequestBody TrainingProgramDTO dto) {
        return Response.success("Tạo chương trình đào tạo thành công", trainingProgramService.create(dto));
    }

    @GetMapping("/{id}")
    public Response<TrainingProgramResponse> getById(@PathVariable Long id) {
        return Response.success("Lấy thông tin chương trình đào tạo thành công", trainingProgramService.getById(id));
    }

    @GetMapping
    public Response<List<TrainingProgramResponse>> getAll() {
        return Response.success("Lấy danh sách chương trình đào tạo thành công", trainingProgramService.getAll());
    }

    @PutMapping("/{id}")
    public Response<TrainingProgramResponse> update(@PathVariable Long id, @RequestBody TrainingProgramDTO dto) {
        return Response.success("Cập nhật chương trình đào tạo thành công", trainingProgramService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public Response<TrainingProgramResponse> delete(@PathVariable Long id) {
        TrainingProgramResponse deleted = trainingProgramService.getById(id);
        trainingProgramService.delete(id);
        return Response.success("Xóa chương trình đào tạo thành công", deleted);
    }

    @GetMapping("/search")
    public Response<List<TrainingProgramResponse>> searchByCode(@RequestParam String code) {
        return Response.success("Tìm kiếm theo mã chương trình đào tạo thành công", trainingProgramService.searchByCode(code));
    }
}

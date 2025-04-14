package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.Intermediary.IntermediaryDTO;
import io.spring.uni_portal.dto.Intermediary.IntermediaryResponse;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.IntermediaryService.IIntermediaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intermediaries")
public class IntermediaryController {

    @Autowired
    private IIntermediaryService intermediaryService;

    @PostMapping
    public Response<IntermediaryResponse> create(@RequestBody IntermediaryDTO dto) {
        return Response.success("Gắn môn học vào chương trình thành công", intermediaryService.create(dto));
    }

    @GetMapping
    public Response<List<IntermediaryResponse>> getAll() {
        return Response.success("Lấy danh sách tất cả môn học theo chương trình", intermediaryService.getAll());
    }

    //    xóa 1 môn học ra khỏi CTDT: api/intermediaries?trainingProgramId=3001&subjectId=1001
    @DeleteMapping
    public Response<Void> delete(@RequestParam Long trainingProgramId, @RequestParam Long subjectId) {
        intermediaryService.delete(trainingProgramId, subjectId);
        return Response.success("Xóa thành công", null);
    }

//    /api/intermediaries?trainingProgramId=3002&subjectId=1024
    @GetMapping("/search/program/{trainingProgramId}")
    public Response<List<IntermediaryResponse>> getByProgram(@PathVariable Long trainingProgramId) {
        return Response.success("Lấy môn học theo chương trình đào tạo", intermediaryService.getByTrainingProgram(trainingProgramId));
    }

//    /api/intermediaries/search?name=2022
    @GetMapping("/search")
    public Response<List<IntermediaryResponse>> searchByProgramName(@RequestParam String name) {
        return Response.success("Tìm kiếm theo tên chương trình thành công",
                intermediaryService.searchByProgramName(name));
    }

//    xóa tất cả môn học ra khỏi CTDT : api/intermediaries/program/3003
    @DeleteMapping("/program/{trainingProgramId}")
    public Response<Void> deleteAllSubjectsOfProgram(@PathVariable Long trainingProgramId) {
        intermediaryService.deleteAllSubjectsOfProgram(trainingProgramId);
        return Response.success("Đã xoá toàn bộ môn học khỏi chương trình đào tạo", null);
    }
}

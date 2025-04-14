package io.spring.uni_portal.controller;


import io.spring.uni_portal.dto.Classroom.ClassroomDTO;
import io.spring.uni_portal.dto.Classroom.ClassroomResponse;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.ClassroomService.IClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    @Autowired
    private IClassroomService classroomService;

    @PostMapping
    public Response<ClassroomResponse> create(@RequestBody ClassroomDTO dto) {
        return Response.success("Tạo phòng học thành công", classroomService.createClassroom(dto));
    }

    @GetMapping("/{id}")
    public Response<ClassroomResponse> getById(@PathVariable Long id) {
        return Response.success("Lấy thông tin phòng học thành công", classroomService.getClassroomById(id));
    }

    @GetMapping
    public Response<List<ClassroomResponse>> getAll() {
        return Response.success("Lấy danh sách phòng học thành công", classroomService.getAllClassrooms());
    }

    @PutMapping("/{id}")
    public Response<ClassroomResponse> update(@PathVariable Long id, @RequestBody ClassroomDTO dto) {
        return Response.success("Cập nhật phòng học thành công", classroomService.updateClassroom(id, dto));
    }

    @DeleteMapping("/{id}")
    public Response<ClassroomResponse> delete(@PathVariable Long id) {
        ClassroomResponse deleted = classroomService.getClassroomById(id);
        classroomService.deleteClassroom(id);
        return Response.success("Xoá phòng học thành công", deleted);
    }
}

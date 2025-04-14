package io.spring.uni_portal.service.ClassroomService;

import io.spring.uni_portal.dto.Classroom.ClassroomDTO;
import io.spring.uni_portal.dto.Classroom.ClassroomResponse;

import java.util.List;

public interface IClassroomService {
    ClassroomResponse createClassroom(ClassroomDTO dto);
    ClassroomResponse getClassroomById(Long id);
    List<ClassroomResponse> getAllClassrooms();
    ClassroomResponse updateClassroom(Long id, ClassroomDTO dto);
    void deleteClassroom(Long id);
}

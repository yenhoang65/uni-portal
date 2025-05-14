package io.spring.uni_portal.service.ClassroomService;

import io.spring.uni_portal.dto.Classroom.ClassroomDTO;
import io.spring.uni_portal.dto.Classroom.ClassroomResponse;
import io.spring.uni_portal.model.Classroom;

import java.util.List;
import java.util.Optional;

public interface IClassroomService {
    ClassroomResponse createClassroom(ClassroomDTO dto);
    ClassroomResponse getClassroomById(Long id);
    List<ClassroomResponse> getAllClassrooms();
    ClassroomResponse updateClassroom(Long id, ClassroomDTO dto);
    void deleteClassroom(Long id);
    Optional<Classroom> findById(Long id);
}

package io.spring.uni_portal.service.ClassroomService;

import io.spring.uni_portal.dto.Classroom.ClassroomDTO;
import io.spring.uni_portal.dto.Classroom.ClassroomResponse;
import io.spring.uni_portal.model.Classroom;
import io.spring.uni_portal.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassroomServiceImpl implements IClassroomService {

    @Autowired
    private ClassroomRepository classroomRepository;

    private ClassroomResponse toResponse(Classroom entity) {
        List<String> deviceList = entity.getDevice() == null ? List.of() :
                List.of(entity.getDevice().split("\\s*,\\s*"));
        return new ClassroomResponse(
                entity.getClassroomId(),
                entity.getClassroomName(),
                entity.getNumberOfSeats(),
                deviceList,
                entity.getStatus()
        );
    }

    private Classroom toEntity(ClassroomDTO dto) {
        Classroom entity = new Classroom();
        entity.setClassroomId(dto.getClassroomId());
        entity.setClassroomName(dto.getClassroomName());
        entity.setNumberOfSeats(dto.getNumberOfSeats());
        entity.setDevice(dto.getDevices() != null ? String.join(", ", dto.getDevices()) : "");
        entity.setStatus(dto.getStatus());
        return entity;
    }

    @Override
    public ClassroomResponse createClassroom(ClassroomDTO dto) {
        if (dto.getClassroomId() == null) {
            throw new RuntimeException("classroomId không được để trống");
        }
        Classroom entity = toEntity(dto);
        return toResponse(classroomRepository.save(entity));
    }

    @Override
    public ClassroomResponse getClassroomById(Long id) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phòng học không tồn tại"));
        return toResponse(classroom);
    }

    @Override
    public List<ClassroomResponse> getAllClassrooms() {
        return classroomRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClassroomResponse updateClassroom(Long id, ClassroomDTO dto) {
        Classroom entity = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phòng học không tồn tại"));
        entity.setClassroomName(dto.getClassroomName());
        entity.setNumberOfSeats(dto.getNumberOfSeats());
        entity.setDevice(dto.getDevices() != null ? String.join(", ", dto.getDevices()) : "");
        return toResponse(classroomRepository.save(entity));
    }

    @Override
    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }
}

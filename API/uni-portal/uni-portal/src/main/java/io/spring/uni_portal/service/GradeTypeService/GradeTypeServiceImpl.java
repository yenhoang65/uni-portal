package io.spring.uni_portal.service.GradeTypeService;

import io.spring.uni_portal.dto.GradeType.GradeTypeRequestDTO;
import io.spring.uni_portal.dto.GradeType.GradeTypeResponseDTO;
import io.spring.uni_portal.model.GradeType;
import io.spring.uni_portal.repository.GradeTypeRepository;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeTypeServiceImpl implements IGradeTypeService {
    @Autowired
    private GradeTypeRepository repository;

    private GradeTypeResponseDTO mapToDTO(GradeType gradeType) {
        GradeTypeResponseDTO dto = new GradeTypeResponseDTO();
        dto.setGradeTypeId(gradeType.getGradeTypeId());
        dto.setCode(gradeType.getCode());
        dto.setName(gradeType.getName());
        dto.setCoefficient(gradeType.getCoefficient());
        return dto;
    }

    @Override
    public Response<GradeTypeResponseDTO> create(GradeTypeRequestDTO dto) {
        GradeType gradeType = new GradeType(dto.getCode(), dto.getName(), dto.getCoefficient());
        GradeType saved = repository.save(gradeType);
        return Response.success("Tạo loại điểm thành công", mapToDTO(saved));
    }

    @Override
    public Response<GradeTypeResponseDTO> update(Long id, GradeTypeRequestDTO dto) {
        Optional<GradeType> opt = repository.findById(id);
        if (opt.isEmpty()) return Response.failure("Không tìm thấy loại điểm cần cập nhật");
        GradeType gradeType = opt.get();
        gradeType.setCode(dto.getCode());
        gradeType.setName(dto.getName());
        gradeType.setCoefficient(dto.getCoefficient());
        return Response.success("Cập nhật loại điểm thành công", mapToDTO(repository.save(gradeType)));
    }

    @Override
    public Response<String> delete(Long id) {
        if (!repository.existsById(id)) return Response.failure("Không tìm thấy loại điểm cần xóa");
        repository.deleteById(id);
        return Response.success("Xóa loại điểm thành công", null);
    }

    @Override
    public Response<GradeTypeResponseDTO> getById(Long id) {
        return repository.findById(id)
                .map(g -> Response.success("Lấy thông tin loại điểm thành công", mapToDTO(g)))
                .orElse(Response.failure("Không tìm thấy loại điểm theo ID"));
    }

    @Override
    public Response<List<GradeTypeResponseDTO>> getAll() {
        List<GradeTypeResponseDTO> result = repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return Response.success("Lấy danh sách loại điểm thành công", result);
    }
}

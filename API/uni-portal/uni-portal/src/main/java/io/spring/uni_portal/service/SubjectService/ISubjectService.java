package io.spring.uni_portal.service.SubjectService;


import io.spring.uni_portal.dto.Subject.SubjectDTO;
import io.spring.uni_portal.dto.Subject.SubjectResponse;
import io.spring.uni_portal.model.Subject;

import java.util.List;

public interface ISubjectService {
    SubjectResponse create(SubjectDTO dto);
    SubjectResponse getById(Long id);
    List<SubjectResponse> getAll();
    SubjectResponse update(Long id, SubjectDTO dto);
    void delete(Long id);
    List<SubjectResponse> searchByName(String keyword);
}

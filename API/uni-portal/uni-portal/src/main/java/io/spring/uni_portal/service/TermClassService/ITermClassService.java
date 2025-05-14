package io.spring.uni_portal.service.TermClassService;

import io.spring.uni_portal.dto.TermClass.TermClassDTO;
import io.spring.uni_portal.dto.TermClass.TermClassResponse;
import io.spring.uni_portal.model.TermClass;
import io.spring.uni_portal.response.Response;

import java.util.List;
import java.util.Optional;

public interface ITermClassService {
    Response<TermClassResponse> createTermClass(TermClassDTO termClassDTO);

    Response<List<TermClassResponse>> getAllTermClasses();

    Response<TermClassResponse> getTermClassById(Long id);

    Response<TermClassResponse> updateTermClass(Long id, TermClassDTO termClassDTO);

    Response<Void> deleteTermClass(Long id);
    Response<List<TermClassResponse>> searchTermClassByClassname(String classname);
    Optional<TermClass> findById(Long id);
}

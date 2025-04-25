package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.TermClass.TermClassDTO;
import io.spring.uni_portal.dto.TermClass.TermClassResponse;
import io.spring.uni_portal.response.Response;
import io.spring.uni_portal.service.TermClassService.ITermClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/term-classes")
public class TermClassController {

    @Autowired
    private ITermClassService termClassService;

    @PostMapping
    public ResponseEntity<Response<TermClassResponse>> createTermClass(@RequestBody TermClassDTO termClassDTO) {
        Response<TermClassResponse> result = termClassService.createTermClass(termClassDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<TermClassResponse>> getTermClassById(@PathVariable Long id) {
        Response<TermClassResponse> result = termClassService.getTermClassById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<Response<List<TermClassResponse>>> getAllTermClasses() {
        Response<List<TermClassResponse>> result = termClassService.getAllTermClasses();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<TermClassResponse>> updateTermClass(@PathVariable Long id, @RequestBody TermClassDTO termClassDTO) {
        Response<TermClassResponse> result = termClassService.updateTermClass(id, termClassDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteTermClass(@PathVariable Long id) {
        Response<Void> result = termClassService.deleteTermClass(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<Response<List<TermClassResponse>>> searchTermClassByClassname(@RequestParam String classname) {
        Response<List<TermClassResponse>> response = termClassService.searchTermClassByClassname(classname);
        return ResponseEntity.ok(response);
    }
}

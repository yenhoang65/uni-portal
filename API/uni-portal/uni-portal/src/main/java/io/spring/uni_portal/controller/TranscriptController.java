package io.spring.uni_portal.controller;

import io.spring.uni_portal.dto.Transcript.TranscriptResponseDTO;
import io.spring.uni_portal.service.TranscriptService.ITranscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transcript")
public class TranscriptController {

    @Autowired
    private ITranscriptService transcriptService;

    @GetMapping("/student/{studentId}")
    public ResponseEntity<TranscriptResponseDTO> getTranscript(@PathVariable Long studentId) {
        TranscriptResponseDTO result = transcriptService.getStudentTranscript(studentId);
        return ResponseEntity.ok(result);
    }
}

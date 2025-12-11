package io.spring.uni_portal.service.TranscriptService;

import io.spring.uni_portal.dto.Transcript.TranscriptResponseDTO;

public interface ITranscriptService {
    TranscriptResponseDTO getStudentTranscript(Long studentId);
}

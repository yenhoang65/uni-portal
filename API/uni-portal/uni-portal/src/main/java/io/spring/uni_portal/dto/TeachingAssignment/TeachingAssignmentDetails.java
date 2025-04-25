package io.spring.uni_portal.dto.TeachingAssignment;

import io.spring.uni_portal.model.TeachingAssignment;
import io.spring.uni_portal.model.TermClass;

public class TeachingAssignmentDetails {
    private TeachingAssignment assignment;
    private TermClass termClass;

    public TeachingAssignmentDetails(TeachingAssignment assignment, TermClass termClass) {
        this.assignment = assignment;
        this.termClass = termClass;
    }

    // Getters and Setters
    public TeachingAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(TeachingAssignment assignment) {
        this.assignment = assignment;
    }

    public TermClass getTermClass() {
        return termClass;
    }

    public void setTermClass(TermClass termClass) {
        this.termClass = termClass;
    }
}


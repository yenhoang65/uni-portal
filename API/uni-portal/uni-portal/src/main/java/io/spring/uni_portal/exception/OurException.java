package io.spring.uni_portal.exception;

public class OurException extends RuntimeException {

    // Constructor nhận thông điệp lỗi
    public OurException(String message) {
        super(message);  // Gọi constructor của lớp Exception
    }
}


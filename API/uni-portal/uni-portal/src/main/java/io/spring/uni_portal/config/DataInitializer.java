package io.spring.uni_portal.config;

import io.spring.uni_portal.model.Lecturer;
import io.spring.uni_portal.model.User;
import io.spring.uni_portal.repository.LecturerRepository;
import io.spring.uni_portal.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        final Long defaultAdminId = 12345678L;

        User admin = userRepository.findById(defaultAdminId).orElse(null);
        if (admin == null) {
            admin = new User();
            admin.setUserId(defaultAdminId);
            admin.setUserName("Admin-Uni-Portal");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setAdmissionDate(LocalDate.of(2020, 1, 1));
            admin.setGender("Nam");
            admin.setPhoneNumber("098765432");
            admin.setAddress("Hưng Yên");
            admin.setEthnicGroup("Kinh");
            admin.setDateOfBirth(LocalDate.of(1990, 5, 20));
            admin.setReligion("Không");
            admin.setIdNumber("0123456789");
            admin.setEmail("admin@uni-portal.edu.vn");
            admin.setPlaceOfBirth("Hưng Yên");
            admin.setPermanentResident("Hưng Yên");
            admin.setBank("Vietcombank");
            admin.setBankAccountOwner("Admin-Uni");
            admin.setBankAccountNumber("1234567890123");
            admin.setRole("admin");
            userRepository.save(admin);
        }
    }
}

package io.spring.uni_portal.service.TermClassService;

import io.spring.uni_portal.dto.TermClass.TermClassDTO;
import io.spring.uni_portal.dto.TermClass.TermClassResponse;
import io.spring.uni_portal.model.TermClass;
import io.spring.uni_portal.repository.TermClassRepository;
import io.spring.uni_portal.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TermClassService implements ITermClassService {

    @Autowired
    private TermClassRepository termClassRepository;

    @Override
    public Response<TermClassResponse> createTermClass(TermClassDTO termClassDTO) {


        // Kiểm tra trùng classname
        if (termClassRepository.existsByClassname(termClassDTO.getClassname())) {
            return Response.failure("Tên lớp đã tồn tại.");
        }

        // Nếu không trùng, tạo mới termClass
        TermClass termClass = new TermClass(termClassDTO.getClassname(),
                termClassDTO.getProgress(),
                termClassDTO.getSemester(),
                termClassDTO.getSchoolyears());
        termClass.setTermclassId(generateTermClassId(termClassDTO));
        termClass = termClassRepository.save(termClass);

        TermClassResponse response = mapToResponse(termClass);
        return Response.success("Tạo lớp thành công", response);
    }

    @Override
    public Response<List<TermClassResponse>> getAllTermClasses() {
        List<TermClass> termClasses = termClassRepository.findAll();
        List<TermClassResponse> responses = termClasses.stream().map(this::mapToResponse).toList();
        return Response.success("Lấy danh sách lớp thành công", responses);
    }

    @Override
    public Response<TermClassResponse> getTermClassById(Long id) {
        Optional<TermClass> termClassOpt = termClassRepository.findById(id);
        if (termClassOpt.isPresent()) {
            TermClassResponse response = mapToResponse(termClassOpt.get());
            return Response.success("Lấy thông tin lớp thành công", response);
        } else {
            return Response.failure("Lớp học không tồn tại.");
        }
    }

    @Override
    public Response<TermClassResponse> updateTermClass(Long id, TermClassDTO termClassDTO) {
        Optional<TermClass> termClassOpt = termClassRepository.findById(id);
        if (termClassOpt.isPresent()) {
            TermClass termClass = termClassOpt.get();
            termClass.setClassname(termClassDTO.getClassname());
            termClass.setProgress(termClassDTO.getProgress());
            termClass.setSemester(termClassDTO.getSemester());
            termClass.setSchoolyears(termClassDTO.getSchoolyears());
            termClass = termClassRepository.save(termClass);

            TermClassResponse response = mapToResponse(termClass);
            return Response.success("Cập nhật lớp thành công", response);
        } else {
            return Response.failure("Lớp học không tồn tại.");
        }
    }

    @Override
    public Response<Void> deleteTermClass(Long id) {
        Optional<TermClass> termClassOpt = termClassRepository.findById(id);
        if (termClassOpt.isPresent()) {
            termClassRepository.deleteById(id);
            return Response.success("Xoá lớp thành công", null);
        } else {
            return Response.failure("Lớp học không tồn tại.");
        }
    }

    @Override
    public Response<List<TermClassResponse>> searchTermClassByClassname(String classname) {
        List<TermClass> termClasses = termClassRepository.findByClassnameContaining(classname);
        if (termClasses.isEmpty()) {
            return Response.failure("Không tìm thấy lớp với tên: " + classname);
        }

        List<TermClassResponse> responses = termClasses.stream().map(this::mapToResponse).toList();
        return Response.success("Tìm kiếm lớp thành công", responses);
    }

    private Long generateTermClassId(TermClassDTO termClassDTO) {
        String progress = termClassDTO.getProgress();  // Example: "1" or "2"
        String semester = termClassDTO.getSemester();  // Example: "1", "2", or "3"
        String schoolYear = termClassDTO.getSchoolyears().substring(2); // Get last 2 digits of school year

        // Sử dụng một biến lưu số tăng dần, ví dụ, đây là một số cố định. Thực tế bạn có thể lấy từ CSDL để tự động tăng.
        long sequence = getNextSequenceNumber();

        String termclassIdStr = progress + semester + schoolYear + String.format("%04d", sequence);
        return Long.valueOf(termclassIdStr);
    }

    // Đây là một hàm giả lập việc tăng số tự động, bạn có thể thay thế bằng cơ chế lấy số tự động từ CSDL
    private long getNextSequenceNumber() {
        // Ví dụ: Lấy số tiếp theo từ cơ sở dữ liệu hoặc tạo số tự động tại đây.
        // Thay vì sử dụng System.currentTimeMillis(), bạn có thể duy trì một biến hoặc database để tăng dần số tự động.
        return 1;  // Thay đổi 1 bằng cách tăng dần số tự động
    }

    private TermClassResponse mapToResponse(TermClass termClass) {
        TermClassResponse response = new TermClassResponse();
        response.setTermclassId(termClass.getTermclassId());
        response.setClassname(termClass.getClassname());
        response.setProgress(termClass.getProgress());
        response.setSemester(termClass.getSemester());
        response.setSchoolyears(termClass.getSchoolyears());
        return response;
    }
}

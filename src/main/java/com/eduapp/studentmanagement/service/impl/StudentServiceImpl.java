package com.eduapp.studentmanagement.service.impl;

import com.eduapp.studentmanagement.dto.StudentRequest;
import com.eduapp.studentmanagement.dto.StudentResponse;
import com.eduapp.studentmanagement.entity.Student;
import com.eduapp.studentmanagement.exception.DuplicateResourceException;
import com.eduapp.studentmanagement.exception.ResourceNotFoundException;
import com.eduapp.studentmanagement.repository.StudentRepository;
import com.eduapp.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("A student with email '" + request.getEmail() + "' already exists");
        }

        Student student = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .course(request.getCourse())
                .enrollmentDate(request.getEnrollmentDate())
                .build();

        Student saved = studentRepository.save(student);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {
        Student student = findStudentOrThrow(id);
        return mapToResponse(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = findStudentOrThrow(id);

        if (studentRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new DuplicateResourceException("A student with email '" + request.getEmail() + "' already exists");
        }

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setCourse(request.getCourse());
        student.setEnrollmentDate(request.getEnrollmentDate());

        Student updated = studentRepository.save(student);
        return mapToResponse(updated);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = findStudentOrThrow(id);
        studentRepository.delete(student);
    }

    private Student findStudentOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    private StudentResponse mapToResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .course(student.getCourse())
                .enrollmentDate(student.getEnrollmentDate())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}

package com.eduapp.studentmanagement.service;

import com.eduapp.studentmanagement.dto.StudentRequest;
import com.eduapp.studentmanagement.dto.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request);

    StudentResponse getStudentById(Long id);

    List<StudentResponse> getAllStudents();

    StudentResponse updateStudent(Long id, StudentRequest request);

    void deleteStudent(Long id);
}

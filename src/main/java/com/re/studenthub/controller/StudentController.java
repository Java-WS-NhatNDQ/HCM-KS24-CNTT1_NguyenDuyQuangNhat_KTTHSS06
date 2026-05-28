package com.re.studenthub.controller;

import com.re.studenthub.entity.Student;
import com.re.studenthub.entity.request.StudentRequestDto;
import com.re.studenthub.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService service;

    @GetMapping("")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = service.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Student student = service.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping("")
    public ResponseEntity<Student> postStudent(@RequestBody StudentRequestDto request) {
        Student newStudent = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .gpa(request.getGpa())
                .build();
        Student savedStudent = service.save(newStudent);

        URI location = URI.create("/api/students/" + savedStudent.getId());
        return ResponseEntity.created(location).body(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> putStudent(@PathVariable Long id, @RequestBody StudentRequestDto request) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Student existingStudent = service.getStudentById(id);
        existingStudent.setName(request.getName());
        existingStudent.setEmail(request.getEmail());
        existingStudent.setGpa(request.getGpa());
        Student updatedStudent = service.save(existingStudent);
        return ResponseEntity.ok(updatedStudent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable Long id, Map<String, Object> updates) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Student stu = service.getStudentById(id);
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            switch (entry.getKey()) {
                case "name":
                    stu.setName((String) entry.getValue());
                    break;
                case "email":
                    stu.setEmail((String) entry.getValue());
                    break;
                case "gpa":
                    stu.setGpa(Double.valueOf(entry.getValue().toString()));
                    break;
            }
        }
        service.save(stu);
        return ResponseEntity.ok(stu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

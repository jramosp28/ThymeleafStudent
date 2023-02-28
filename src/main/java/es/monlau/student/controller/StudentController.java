package es.monlau.student.controller;

import es.monlau.student.entity.Student;
import es.monlau.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author javierrampob
 */
@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }
    
    @GetMapping("/students")
    public String listStudents(Model model){
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }
    
    @GetMapping("/students/new")
    public String createStudentForm(Model model){
        //ESTE OBJETO STUDENT ALMACENARA LOS VALORES
        Student student = new Student();
        
        model.addAttribute("student", student);
        
        return "create_student";
    }
    
    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student){
        studentService.saveStudent(student);
        return "redirect:/students";
    }
    
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model){
        Student st = studentService.getStudentById(id);
        
        model.addAttribute("student", st);
        
        return "edit_student";
    }
    
    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model){
        Student existentStudent = studentService.getStudentById(id);
        
        existentStudent.setId(id);
        existentStudent.setFirstName(student.getFirstName());
        existentStudent.setLastName(student.getLastName());
        existentStudent.setEmail(student.getEmail());
        
        studentService.updateStudent(existentStudent);
        
        return "redirect:/students";
    }
    
    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id){
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }
}

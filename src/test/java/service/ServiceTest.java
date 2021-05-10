package service;

import domain.Grade;
import domain.Homework;
import domain.Pair;
import domain.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class ServiceTest {

    public static Service service;

    @org.junit.jupiter.api.BeforeAll
    public static void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void findAllStudents() {
    }

    @org.junit.jupiter.api.Test
    void findAllHomework() {
    }

    @org.junit.jupiter.api.Test
    void findAllGrades() {

    }

    @org.junit.jupiter.api.Test
    void saveStudent() {
        Student hw = new Student("77", "student77", 6);
        int result = service.saveStudent(hw.getID(), hw.getName(), hw.getGroup());
        assertEquals(1, result);
        service.deleteStudent(hw.getID());
    }

    @org.junit.jupiter.api.Test
    @DisplayName("checking if homework save works")
    void saveValidHomework() {
        Homework hw = new Homework("77", "some easy homework", 6, 2);
        int result = service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        assertEquals(1, result);
        service.deleteHomework(hw.getID());
    }

    @org.junit.jupiter.api.Test
    void saveGrade() {
        Grade grade = new Grade(new Pair<String, String>("44","55"), 123, 2, "test");
        int result = service.saveGrade(grade.getID().getObject1(), grade.getID().getObject2(), grade.getGrade(), grade.getDeliveryWeek(), grade.getFeedback());
        assertEquals(-1, result);
//        service.deleteGrade(grade.getID());
    }

    @org.junit.jupiter.api.Test
    void saveGrade_2() {
        Student st = new Student("777", "student777", 6);
        service.saveStudent(st.getID(), st.getName(), st.getGroup());

        Homework hw = new Homework("778", "some easy homework", 6, 2);
        service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());

        Grade grade = new Grade(new Pair<String, String>("777","778"), 123, 2, "test");
        int result = service.saveGrade(grade.getID().getObject1(), grade.getID().getObject2(), grade.getGrade(), grade.getDeliveryWeek(), grade.getFeedback());
        assertEquals(-1, result);

        service.deleteStudent(st.getID());
        service.deleteHomework(hw.getID());
    }

    @org.junit.jupiter.api.Test
    void deleteStudent() {
        int result = service.deleteStudent("77");
        assertEquals(0, result);
    }

    @org.junit.jupiter.api.Test
    void deleteHomework() {
        Homework hw = new Homework("77", "some easy homework", 6, 2);
        service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        int result = service.deleteHomework("77");
        assertEquals(1, result);
    }

    @org.junit.jupiter.api.Test
    void updateStudent() {
//        Student hw = new Student("77", "student77", 6);
//        service.saveStudent(hw.getID(), hw.getName(), hw.getGroup());

    }

    @org.junit.jupiter.api.Test
    void updateHomework() {
        Homework hw = new Homework("77", "some easy homework", 6, 2);
        service.saveHomework(hw.getID(), hw.getDescription(), hw.getDeadline(), hw.getStartline());
        int result = service.updateHomework("77", "new desc", 6,4);
        assertEquals(1, result);
    }

    @org.junit.jupiter.api.Test
    void extendDeadline() {
    }

    @org.junit.jupiter.api.Test
    void createStudentFile() {
    }

    @Test
    void assertAllTest(){
        Student s = new Student("99", "Johan", 533);
        assertAll(
                "student",
                () -> assertEquals("99", s.getID()),
                () -> assertEquals("Johan", s.getName())
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 55, 533})
    void testStudentAddByGroup(int group){
        assumeTrue(group >= 0);
        Student s = new Student("99", "Johan", group);
        int result = service.saveStudent(s.getID(), s.getName(), s.getGroup());
        assertEquals(1, result);
        service.deleteStudent(s.getID());
    }


}
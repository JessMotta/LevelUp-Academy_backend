package com.fiap.hackaton;

import com.fiap.hackaton.domain.entity.*;
import com.fiap.hackaton.domain.enums.*;
import com.fiap.hackaton.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetupInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final ActivityRepository activityRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.info("Inicializando dados...");
        initializeUser();
        initializeTeacher();
        initializeClassroom();
        initializeStudent();
        initializeAcitivity();
        log.info("Dados inicializados com sucesso.");
    }

    private void initializeUser() {
        if (this.userRepository.findAll().isEmpty()) {
            log.info("Criando usuários padrão...");
            User user1 = User.builder()
                    .name("John Doe")
                    .email("johndoe@example.com")
                    .password("123456789")
                    .role(Roles.TEACHER)
                    .build();

            User user2 = User.builder()
                    .name("Jane Doe")
                    .email("janedoe@example.com")
                    .password("123456789")
                    .role(Roles.STUDENT)
                    .build();

            this.userRepository.saveAll(List.of(user1, user2));
            log.info("Usuários criados com sucesso.");
        } else {
            log.info("Usuários já existem, pulando criação...");
        }
    }

    private void initializeTeacher() {
        if (this.teacherRepository.findAll().isEmpty()) {
            log.info("Criando professor padrão...");
            User user = this.userRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Teacher teacher = Teacher.builder()
                    .user(user)
                    .build();

            this.teacherRepository.save(teacher);
            log.info("Professor criado com sucesso.");
        } else {
            log.info("Professores já existem, pulando criação...");
        }
    }

    private void initializeClassroom(){
        if (this.classroomRepository.findAll().isEmpty()) {
            log.info("Criando sala de aula padrão...");
            Teacher teacher = this.teacherRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            Classroom classroom = Classroom.builder()
                    .teacher(teacher)
                    .subject("Matemática")
                    .dayOfWeek(DayOfWeek.SEGUNDA_FEIRA)
                    .timeSpot(1)
                    .build();

            this.classroomRepository.save(classroom);
            log.info("Sala de aula criada com sucesso.");
        } else {
            log.info("Salas de aula já existem, pulando criação...");
        }
    }

    private void initializeStudent() {
        if (this.studentRepository.findAll().isEmpty()) {
            log.info("Criando aluno padrão...");
            User user = this.userRepository.findById(2L)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Classroom classroom = this.classroomRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            Student student = Student.builder()
                    .user(user)
                    .experiencePoints(0)
                    .currentPatent(Patents.INICIANTE)
                    .grade("1º ano A")
                    .classroom(classroom)
                    .build();

            this.studentRepository.save(student);
            log.info("Aluno criado com sucesso.");
        } else {
            log.info("Alunos já existem, pulando criação...");
        }
    }

    private void initializeAcitivity() {
        if (this.activityRepository.findAll().isEmpty()) {
            log.info("Criando atividades padrão...");
            Classroom classroom = this.classroomRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            Activity activity1 = Activity.builder()
                    .name("Questionário")
                    .description("""
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras laoreet arcu quis sapien
                            rutrum, a efficitur dui fringilla. Mauris a semper elit. Duis eu fermentum ligula, non
                            ullamcorper arcu. Nulla euismod mauris nec accumsan euismod. Donec consectetur posuere
                            ante vel pulvinar.
                            """)
                    .type(TypeActivity.QUESTIONARIO)
                    .inGroup(false)
                    .execution(Execution.EM_CASA)
                    .prestigeValue(20)
                    .completed(false)
                    .classroom(classroom)
                    .build();

            Activity activity2 = Activity.builder()
                    .name("Estudo dirigido")
                    .description("""
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras laoreet arcu quis sapien
                            rutrum, a efficitur dui fringilla. Mauris a semper elit. Duis eu fermentum ligula, non
                            ullamcorper arcu. Nulla euismod mauris nec accumsan euismod. Donec consectetur posuere
                            ante vel pulvinar.
                            """)
                    .type(TypeActivity.ESTUDO_DIRIGIDO)
                    .inGroup(true)
                    .studentsPerGroup(2)
                    .execution(Execution.EM_SALA)
                    .prestigeValue(30)
                    .completed(false)
                    .classroom(classroom)
                    .build();

            Activity activity3 = Activity.builder()
                    .name("Apresentação")
                    .description("""
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras laoreet arcu quis sapien
                            rutrum, a efficitur dui fringilla. Mauris a semper elit. Duis eu fermentum ligula, non
                            ullamcorper arcu. Nulla euismod mauris nec accumsan euismod. Donec consectetur posuere
                            ante vel pulvinar.
                            """)
                    .type(TypeActivity.APRESENTACAO)
                    .inGroup(true)
                    .studentsPerGroup(4)
                    .execution(Execution.EM_SALA)
                    .prestigeValue(40)
                    .completed(false)
                    .classroom(classroom)
                    .build();

            Activity activity4 = Activity.builder()
                    .name("Quiz")
                    .description("""
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras laoreet arcu quis sapien
                            rutrum, a efficitur dui fringilla. Mauris a semper elit. Duis eu fermentum ligula, non
                            ullamcorper arcu. Nulla euismod mauris nec accumsan euismod. Donec consectetur posuere
                            ante vel pulvinar.
                            """)
                    .type(TypeActivity.QUIZ)
                    .inGroup(false)
                    .execution(Execution.EM_CASA)
                    .prestigeValue(25)
                    .experienceReceived(7)
                    .completed(true)
                    .classroom(classroom)
                    .build();

            this.activityRepository.saveAll(List.of(activity1, activity2, activity3, activity4));
            log.info("Atividades criadas com sucesso.");
        } else {
            log.info("Atividades já existem, pulando criação...");
        }
    }

}

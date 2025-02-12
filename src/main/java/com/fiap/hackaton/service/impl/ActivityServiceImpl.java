package com.fiap.hackaton.service.impl;

import com.fiap.hackaton.domain.dto.activity.ActivityRequest;
import com.fiap.hackaton.domain.dto.activity.ActivityResponse;
import com.fiap.hackaton.domain.dto.activity.ListActivitiesResponse;
import com.fiap.hackaton.domain.entity.Activity;
import com.fiap.hackaton.domain.entity.Classroom;
import com.fiap.hackaton.repository.ActivityRepository;
import com.fiap.hackaton.service.ActivityService;
import com.fiap.hackaton.service.ClassroomService;
import com.fiap.hackaton.service.ImageService;
import com.fiap.hackaton.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository repository;
    private final ClassroomService classroomService;
    private final StudentService studentService;
    private final ImageService imageService;

    @Override
    public Long createActivity(Long classroomId, ActivityRequest request) {
        log.info("[ActivityServiceImpl] Criando nova atividade para a turma com ID: {}", classroomId);
        Classroom classroom = classroomService.findClassroomEntityById(classroomId);

        Activity activity = new Activity(request);
        activity.setClassroom(classroom);

        this.repository.save(activity);
        log.info("[ActivityServiceImpl] Atividade criada com sucesso com ID: {}", activity.getId());

        return activity.getId();
    }

    @Override
    public ActivityResponse findActivityById(Long id) {
        Activity activity = this.findActivityEntityById(id);
        return new ActivityResponse(activity);
    }

    @Override
    public List<ListActivitiesResponse> listActivities() {
        log.info("[ActivityServiceImpl] Listando todas as atividades");
        return this.repository.findAll()
                .stream()
                .map(ListActivitiesResponse::new)
                .toList();
    }

    @Override
    public Activity findActivityEntityById(Long id) {
        log.info("[ActivityServiceImpl] Buscando atividade com ID: {}", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("[ActivityServiceImpl] Atividade com ID: {} não encontrada", id);
                    return new EntityNotFoundException("Activity not found");
                });
    }

    @Override
    public Long updateActivity(Long id, ActivityRequest activityRequest) {
        Activity activity = this.findActivityEntityById(id);

        log.info("[ActivityServiceImpl] Atualizando atividade com ID: {}", id);
        activity.update(activityRequest);
        this.repository.save(activity);
        log.info("[ActivityServiceImpl] Atividade atualizada com sucesso com ID: {}", activity.getId());

        return activity.getId();
    }

    @Override
    public void deliverAnswers(Long activityId, MultipartFile file) {
        Activity activity = this.findActivityEntityById(activityId);
        log.info("[ActivityServiceImpl] Recebendo respostas para atividade com ID: {}", activityId);

        log.info("[ActivityServiceImpl] Salvando imagem no storage");
        String filename = this.imageService.uploadImage(file);

        log.info("[ActivityServiceImpl] Salvando respostas da atividade");
        activity.setAnswer(filename);
        this.repository.save(activity);
        log.info("[ActivityServiceImpl] Respostas salvas com sucesso para atividade com ID: {}", activityId);
    }

    @Override
    public void deleteActivity(Long activityId) {
        Activity activity = this.findActivityEntityById(activityId);
        log.info("[ActivityServiceImpl] Deletando atividade com ID: {}", activityId);
        this.repository.delete(activity);
        log.info("[ActivityServiceImpl] Atividade deletada com sucesso com ID: {}", activityId);
    }
}

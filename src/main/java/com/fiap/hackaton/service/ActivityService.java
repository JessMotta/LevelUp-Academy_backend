package com.fiap.hackaton.service;

import com.fiap.hackaton.domain.dto.activity.ActivityRequest;
import com.fiap.hackaton.domain.dto.activity.ActivityResponse;
import com.fiap.hackaton.domain.entity.Activity;
import org.springframework.web.multipart.MultipartFile;

public interface ActivityService {
    Long createActivity(Long classroomId, ActivityRequest activityJson);
    ActivityResponse findActivityById(Long activityId);
    Activity findActivityEntityById(Long activityId);
    Long updateActivity(Long activityId, ActivityRequest activityRequest);
    void deliverAnswers(Long activityId, MultipartFile file);
    void evaluateActivity(Long activityId, Long studentId, Integer valueReceived);
    void deleteActivity(Long activityId);
}

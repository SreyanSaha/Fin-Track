package com.fin.service;

import com.fin.dto.NotificationDto;
import com.fin.dto.RequestStateDto;
import com.fin.dto.ServiceResponse;
import com.fin.model.User;
import com.fin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestStateManager {
    private final ConcurrentHashMap<String, RequestStateDto> requestState=new
            ConcurrentHashMap<String, RequestStateDto>();
    private final UserRepository userRepository;

    @Autowired
    RequestStateManager(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public ConcurrentHashMap<String, RequestStateDto> getRequestState() {
        return requestState;
    }

    public void setExportingMonthlyDataState(User user, boolean state, int progress){
        if(!requestState.containsKey(user.getUserEmail())){
            RequestStateDto requestStateDto=new RequestStateDto();
            requestStateDto.setMonthlyDataExporting(state);
            requestStateDto.setMonthlyDataExportingProgress(progress);
            requestState.put(user.getUserEmail(), requestStateDto);
        }
        RequestStateDto requestStateDto = requestState.get(user.getUserEmail());
        requestStateDto.setMonthlyDataExporting(state);
        requestStateDto.setMonthlyDataExportingProgress(progress);
        requestState.put(user.getUserEmail(), requestStateDto);
    }

    public void setBackupYearlyDataState(User user, boolean state, int progress){
        if(!requestState.containsKey(user.getUserEmail())){
            RequestStateDto requestStateDto=new RequestStateDto();
            requestStateDto.setYearlyBackupExporting(state);
            requestStateDto.setYearlyBackupExportingProgress(progress);
            requestState.put(user.getUserEmail(), requestStateDto);
        }
        RequestStateDto requestStateDto = requestState.get(user.getUserEmail());
        requestStateDto.setYearlyBackupExporting(state);
        requestStateDto.setYearlyBackupExportingProgress(progress);
        requestState.put(user.getUserEmail(), requestStateDto);
    }

    public ServiceResponse<List<NotificationDto>> getNotificationDetails(){
        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if(!requestState.containsKey(user.getUserEmail()))
            return new ServiceResponse<>("");
        return new ServiceResponse<>("", requestState.get(user.getUserEmail()).getNotifications());
    }

    @Scheduled(fixedDelay = 15 * 60 * 1000)
    private void garbageCleanup(){
        for(String key: requestState.keySet()){
            RequestStateDto dto = requestState.get(key);
            if(dto.getMonthlyDataExportingProgress()==100 && !dto.getMonthlyDataExportingTitle().isEmpty()) {
                dto.setMonthlyDataExportingProgress(0);
                dto.setMonthlyDataExportingTitle("");
                dto.setMonthlyDataExporting(false);
            }
            
        }
    }
}

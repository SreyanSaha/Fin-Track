package com.fin.service;

import com.fin.dto.RequestStateDto;
import com.fin.model.User;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestStateManager {
    private final ConcurrentHashMap<String, RequestStateDto> requestState=new
            ConcurrentHashMap<String, RequestStateDto>();

    public ConcurrentHashMap<String, RequestStateDto> getRequestState() {
        return requestState;
    }

    public void setExportingMonthlyData(User user){
        if(!requestState.containsKey(user.getUserEmail())){
            RequestStateDto requestStateDto=new RequestStateDto();
            requestStateDto.setMonthlyDataExporting(true);
            requestState.put(user.getUserEmail(), requestStateDto);
        }
        requestState.get(user.getUserEmail()).setMonthlyDataExporting(true);
    }
}

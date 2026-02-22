package com.fin.service;

import com.fin.dto.ServiceResponse;
import com.fin.dto.YearlyReportPublicDto;
import com.fin.model.User;
import com.fin.repository.UserRepository;
import com.fin.repository.YearlyReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearlyReportService {
    private final YearlyReportsRepository yearlyReportsRepository;
    private final UserRepository userRepository;
    private final Validation validation;

    @Autowired
    YearlyReportService(YearlyReportsRepository yearlyReportsRepository, Validation validation, UserRepository userRepository){
        this.yearlyReportsRepository=yearlyReportsRepository;
        this.validation=validation;
        this.userRepository=userRepository;
    }

//    public final ServiceResponse<?> createYearlyReport(YearlyReportCreationDto yearlyReportCreationDto) {
//        if(!validation.validateYear(yearlyReportCreationDto.getyReportYear()))
//            return new ServiceResponse<Boolean>("Invalid year.", false);
//        if(!validation.validateMonth(yearlyReportCreationDto.getyReportMonth()))
//            return new ServiceResponse<Boolean>("Invalid month.", false);
//        if(!validation.validateDoubleAmount(Double.toString(yearlyReportCreationDto.getyReportMonthTarget())))
//            return new ServiceResponse<Boolean>("Invalid amount.", false);
//
//        ClientLogin clientLogin = (ClientLogin)request.getSession().getAttribute("clientLogin");
//
//        YearlyReports yearlyReport=new YearlyReports();
//        yearlyReport.setUserId(clientLogin.getUserId());
//        yearlyReport.setYearlyReportYear(yearlyReportCreationDto.getYearlyReportYear());
//        yearlyReport.setYearlyReportMonth(yearlyReportCreationDto.getYearlyReportMonth());
//        yearlyReport.setYearlyReportMonthTarget(yearlyReportCreationDto.getYearlyReportMonthTarget());
//
//        if(yearlyReportsRepository.isYearlyRecordPresent(yearlyReport))
//            return new ServiceResponse<Boolean>("Record already exists with this year & month.", false);
//
//        ServiceResponse<YearlyReportPublicDto> response = yearlyReportRepository.createYearlyReport(yearlyReport);
//
//        return response;
//    }
//
    public final ServiceResponse<YearlyReportPublicDto> getYearlyReportOfUser() {
        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        List<YearlyReportPublicDto> list = yearlyReportsRepository.getYearlyReportOfUser(user.getUserId());
        if(list.isEmpty())
            return new ServiceResponse<YearlyReportPublicDto>("No records found.", list, false);
        else
            return new ServiceResponse<YearlyReportPublicDto>("Records fetched.", list, true);
    }
//
//    public final ServiceResponse<Boolean> deleteYearlyReport(HttpServletRequest request, int yearlyReportId) {
//        ClientLogin clientLogin = (ClientLogin)request.getSession().getAttribute("clientLogin");
//        if(!yearlyReportRepository.isValidYearlyRecord(yearlyReportId, clientLogin))
//            return new ServiceResponse<Boolean>("Invalid delete request.",false);
//
//        boolean response = yearlyReportRepository.deleteYearlyRecord(yearlyReportId, clientLogin);
//
//        if(response) return new ServiceResponse<Boolean>("Yearly report deleted.",true);
//        return new ServiceResponse<Boolean>("Failed to delete yearly report.",false);
//    }

}

package com.fin.service;

import com.fin.dto.ServiceResponse;
import com.fin.dto.YearlyReportCreationDto;
import com.fin.dto.YearlyReportPublicDto;
import com.fin.model.User;
import com.fin.model.YearlyReports;
import com.fin.repository.UserRepository;
import com.fin.repository.YearlyReportsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class YearlyReportService {
    private final YearlyReportsRepository yearlyReportsRepository;
    private final UserRepository userRepository;
    private final Validation validation;

    @Autowired
    YearlyReportService(YearlyReportsRepository yearlyReportsRepository,
                        Validation validation, UserRepository userRepository){
        this.yearlyReportsRepository=yearlyReportsRepository;
        this.validation=validation;
        this.userRepository=userRepository;
    }

    @Transactional
    public ServiceResponse<Boolean> createYearlyReport(YearlyReportCreationDto yearlyReportCreationDto) {
        if(!validation.validateYear(yearlyReportCreationDto.getYReportYear()))
            return new ServiceResponse<Boolean>("Invalid year.", false);
        if(!validation.validateMonth(yearlyReportCreationDto.getYReportMonth()))
            return new ServiceResponse<Boolean>("Invalid month.", false);
        if(!validation.validateDoubleAmount(Double.toString(yearlyReportCreationDto.getYReportMonthTarget())))
            return new ServiceResponse<Boolean>("Invalid amount.", false);

        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        YearlyReports yearlyReport=new YearlyReports();
        yearlyReport.setUser(user);
        yearlyReport.setYReportYear(yearlyReportCreationDto.getYReportYear());
        yearlyReport.setYReportMonth(yearlyReportCreationDto.getYReportMonth());
        yearlyReport.setYReportMonthTarget(yearlyReportCreationDto.getYReportMonthTarget());

        if(yearlyReportsRepository.isYearlyRecordPresent(yearlyReport.getYReportYear(), yearlyReport.getYReportMonth(), user.getUserId())>0)
            return new ServiceResponse<Boolean>("Record already exists with this year & month.", false);

        YearlyReports report = yearlyReportsRepository.save(yearlyReport);

        return report.getYReportId()!=null?
                new ServiceResponse<Boolean>("Yearly report created.",true):
                new ServiceResponse<Boolean>("Failed to create yearly report.", false);
    }

    public ServiceResponse<YearlyReportPublicDto> getYearlyReportOfUser() {
        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        List<YearlyReportPublicDto> list = yearlyReportsRepository.getYearlyReportOfUser(user.getUserId());
        if(list.isEmpty())
            return new ServiceResponse<YearlyReportPublicDto>("No records found.", list, false);
        else
            return new ServiceResponse<YearlyReportPublicDto>("Records fetched.", list, true);
    }

    @Transactional
    public ServiceResponse<Boolean> deleteYearlyReport(long yearlyReportId) {
        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Optional<YearlyReports> report = yearlyReportsRepository.findYearlyReport(yearlyReportId, user.getUserId());

        if(report.isEmpty())
            return new ServiceResponse<Boolean>("Invalid delete request.",false);

        yearlyReportsRepository.delete(report.get());

        return new ServiceResponse<Boolean>("Yearly report deleted.",true);
    }

}

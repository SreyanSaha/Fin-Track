package com.fin.service;

import com.fin.dto.*;
import com.fin.model.MonthlyReports;
import com.fin.model.User;
import com.fin.model.YearlyReports;
import com.fin.repository.MonthlyReportsRepository;
import com.fin.repository.UserRepository;
import com.fin.repository.YearlyReportsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MonthlyReportService {
    private final UserRepository userRepository;
    private final Validation validation;
    private final MonthlyReportsRepository monthlyReportsRepository;
    private final YearlyReportsRepository yearlyReportsRepository;

    @Autowired
    MonthlyReportService(UserRepository userRepository, Validation validation, MonthlyReportsRepository monthlyReportsRepository,
                         YearlyReportsRepository yearlyReportsRepository){
        this.userRepository=userRepository;
        this.validation=validation;
        this.monthlyReportsRepository=monthlyReportsRepository;
        this.yearlyReportsRepository=yearlyReportsRepository;
    }

    public ServiceResponse<MonthlyReportPublicDto> getMonthlyRecordsOfUser(MonthlyReportFetchDto monthlyRecordsFetchDto) {
        if(!validation.validateYear(monthlyRecordsFetchDto.getYReportYear()))
            return new ServiceResponse<>("Invalid year.", false);
        if(!validation.validateMonth(monthlyRecordsFetchDto.getYReportMonth()))
            return new ServiceResponse<>("Invalid month.", false);
        if(!validation.validateDoubleAmount(Double.toString(monthlyRecordsFetchDto.getYReportMonthTarget())))
            return new ServiceResponse<>("Invalid amount.", false);

        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        List<MonthlyReportPublicDto> list = monthlyReportsRepository.getMonthlyRecordsOfUser(user.getUserId(), monthlyRecordsFetchDto.getYReportMonth(),
                                                                                    monthlyRecordsFetchDto.getYReportYear());
        return list.isEmpty()?
             new ServiceResponse<MonthlyReportPublicDto>("No records found.", list, false):
             new ServiceResponse<MonthlyReportPublicDto>("Fetched records.", list, true);
    }

    @Transactional
    public ServiceResponse<Boolean> createMonthlyReport(MonthlyReportCreationDto monthlyReportCreationDto) {
        if(!validation.validateDoubleAmount(Double.toString(monthlyReportCreationDto.getMonthlyReportAmount())))
            return new ServiceResponse<>("Invalid amount.", false);
        if(!validation.validateDate(String.valueOf(monthlyReportCreationDto.getMonthlyReportDate())))
            return new ServiceResponse<>("Invalid date.", false);

        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        //Optional<YearlyReportPublicDto> yearlyReport=yearlyReportsRepository.isYearlyRecordPresent(monthlyReportCreationDto.getYearlyReportId(), user.getUserId());
        Optional<YearlyReports> yearlyReport=yearlyReportsRepository.findById(monthlyReportCreationDto.getYearlyReportId());
        if(yearlyReport.isEmpty())
            return new ServiceResponse<>("Selected year is invalid.", false);
        if(yearlyReport.get().getYReportYear()!=monthlyReportCreationDto.getMonthlyReportDate().getYear() ||
                yearlyReport.get().getYReportMonth()!=monthlyReportCreationDto.getMonthlyReportDate().getMonthValue())
            return new ServiceResponse<>("The selected date does not belong to the specified year and month.", false);

        //monthlyReportCreationDto.setMonthlyReportId(String.valueOf(UUID.randomUUID()));
        MonthlyReports monthlyReports=new MonthlyReports(monthlyReportCreationDto, yearlyReport.get());

        MonthlyReports report = monthlyReportsRepository.save(monthlyReports);

        return report.getMReportId()!=null?
                new ServiceResponse<Boolean>("Record created successfully.", true):
                new ServiceResponse<Boolean>("Failed to create record.", false);
    }
//
//    public final ServiceResponse<Boolean> updateMonthlyRecord(HttpServletRequest request,
//                                                              MonthlyRecordCreationDto monthlyRecordUpdationDto) {
//        if(!validation.validateDoubleAmount(Double.toString(monthlyRecordUpdationDto.getMonthlyReportAmount())))
//            return new ServiceResponse<>("Invalid amount.", false);
//        if(!validation.validateDate(String.valueOf(monthlyRecordUpdationDto.getMonthlyReportDate())))
//            return new ServiceResponse<>("Invalid date.", false);
//
//        ClientLogin clientLogin = (ClientLogin)request.getSession().getAttribute("clientLogin");
//        Optional<YearlyReportPublicDto> yearlyReport=yearlyReportRepository.isYearlyRecordPresent(monthlyRecordUpdationDto.getYearlyReportId(), clientLogin);
//        if(yearlyReport.isEmpty())
//            return new ServiceResponse<>("Selected year is invalid.", false);
//        if(yearlyReport.get().getYearlyReportYear()!=LocalDate.parse(monthlyRecordUpdationDto.getMonthlyReportDate()).getYear() ||
//                yearlyReport.get().getYearlyReportMonth()!=LocalDate.parse(monthlyRecordUpdationDto.getMonthlyReportDate()).getMonthValue())
//            return new ServiceResponse<>("The selected date does not belong to the specified year and month.", false);
//
//        if(monthlyReportRepository.updateMonthlyRecord(monthlyRecordUpdationDto))
//            return new ServiceResponse<Boolean>("Record updated sucessfully.", true);
//        return new ServiceResponse<Boolean>("Failed to update record.", false);
//    }
//
//    public final ServiceResponse<Boolean> deleteMonthlyReport(HttpServletRequest request, String monthlyReportId) {
//        ClientLogin clientLogin = (ClientLogin)request.getSession().getAttribute("clientLogin");
//        if(!monthlyReportRepository.isValidMonthlylyRecord(monthlyReportId, clientLogin))
//            return new ServiceResponse<Boolean>("Invalid delete request.",false);
//
//        boolean response = monthlyReportRepository.deleteMonthlyRecord(monthlyReportId);
//
//        if(response) return new ServiceResponse<Boolean>("Record deleted.",true);
//        return new ServiceResponse<Boolean>("Failed to delete record.",false);
//    }

}

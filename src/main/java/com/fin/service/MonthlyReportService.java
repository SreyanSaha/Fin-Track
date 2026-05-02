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
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MonthlyReportService {
    private final UserRepository userRepository;
    private final Validation validation;
    private final MonthlyReportsRepository monthlyReportsRepository;
    private final YearlyReportsRepository yearlyReportsRepository;
    private final ExportReportService exportReportService;

    @Autowired
    MonthlyReportService(UserRepository userRepository, Validation validation, MonthlyReportsRepository monthlyReportsRepository,
                         YearlyReportsRepository yearlyReportsRepository, ExportReportService exportReportService){
        this.userRepository=userRepository;
        this.validation=validation;
        this.monthlyReportsRepository=monthlyReportsRepository;
        this.yearlyReportsRepository=yearlyReportsRepository;
        this.exportReportService=exportReportService;
    }

    public ServiceResponse<MonthlyReportPublicDto> getMonthlyRecordsOfUser(MonthlyReportFetchDto monthlyReportFetchDto) {
        if(!validation.validateYear(monthlyReportFetchDto.getYReportYear()))
            return new ServiceResponse<>("Invalid year.", false);
        if(!validation.validateMonth(monthlyReportFetchDto.getYReportMonth()))
            return new ServiceResponse<>("Invalid month.", false);
        if(!validation.validateDoubleAmount(Double.toString(monthlyReportFetchDto.getYReportMonthTarget())))
            return new ServiceResponse<>("Invalid amount.", false);

        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Optional<List<MonthlyReportPublicDto>> list = monthlyReportsRepository.getMonthlyRecordsOfUser(user.getUserId(), monthlyReportFetchDto.getYReportMonth(),
                                                                                            monthlyReportFetchDto.getYReportYear());
        return list.isEmpty()?
             new ServiceResponse<>("No records found.", list.get(), false):
             new ServiceResponse<>("Fetched records.", list.get(), true);
    }

    public ServiceResponse<MonthlyReportPublicDto> exportMonthlyRecordOfUser(MonthlyReportFetchDto monthlyReportFetchDto) {
        if(!validation.validateYear(monthlyReportFetchDto.getYReportYear()))
            return new ServiceResponse<>("Invalid year.", false);
        if(!validation.validateMonth(monthlyReportFetchDto.getYReportMonth()))
            return new ServiceResponse<>("Invalid month.", false);
        if(!validation.validateDoubleAmount(Double.toString(monthlyReportFetchDto.getYReportMonthTarget())))
            return new ServiceResponse<>("Invalid amount.", false);

        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Optional<MonthlyReportPublicDto> record = monthlyReportsRepository.isMonthlyRecordsOfUserPresent(user.getUserId(), monthlyReportFetchDto.getYReportMonth(),
                monthlyReportFetchDto.getYReportYear());

        if(record.isEmpty()) return new ServiceResponse<>("No records found.",false);
        exportReportService.exportRecords(monthlyReportFetchDto, user, user.getUserEmail());
        return new ServiceResponse<>("Your request is being processed. The Excel file will be generated and sent to your registered email once ready.", true);
    }

    @Transactional
    public ServiceResponse<Boolean> createMonthlyReport(MonthlyReportCreationDto monthlyReportCreationDto) {
        if(!validation.validateDoubleAmount(Double.toString(monthlyReportCreationDto.getMonthlyReportAmount())))
            return new ServiceResponse<>("Invalid amount.", false);
        if(!validation.validateDate(String.valueOf(monthlyReportCreationDto.getMonthlyReportDate())))
            return new ServiceResponse<>("Invalid date.", false);

        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Optional<YearlyReports> yearlyReport=yearlyReportsRepository.findYearlyReport(monthlyReportCreationDto.getYearlyReportId(), user.getUserId());
        if(yearlyReport.isEmpty())
            return new ServiceResponse<>("Selected year is invalid.", false);
        if(yearlyReport.get().getYReportYear()!=monthlyReportCreationDto.getMonthlyReportDate().getYear() ||
                yearlyReport.get().getYReportMonth()!=monthlyReportCreationDto.getMonthlyReportDate().getMonthValue())
            return new ServiceResponse<>("The selected date does not belong to the specified year and month.", false);

        MonthlyReports monthlyReports=new MonthlyReports(monthlyReportCreationDto, yearlyReport.get());

        MonthlyReports report = monthlyReportsRepository.saveAndFlush(monthlyReports);

        return report.getMReportId()!=null?
                new ServiceResponse<>("Record created successfully.", true):
                new ServiceResponse<>("Failed to create record.", false);
    }

    @Transactional
    public ServiceResponse<Boolean> updateMonthlyRecord(MonthlyReportEditDto monthlyReportEditDto) {
        if(!validation.validateDoubleAmount(Double.toString(monthlyReportEditDto.getMonthlyReportAmount())))
            return new ServiceResponse<>("Invalid amount.", false);
        if(!validation.validateDate(String.valueOf(monthlyReportEditDto.getMonthlyReportDate())))
            return new ServiceResponse<>("Invalid date.", false);

        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Optional<YearlyReports> yearlyReport=yearlyReportsRepository.findYearlyReport(monthlyReportEditDto.getYearlyReportId(), user.getUserId());
        if(yearlyReport.isEmpty())
            return new ServiceResponse<>("Selected year is invalid.", false);
        if(yearlyReport.get().getYReportYear()!=monthlyReportEditDto.getMonthlyReportDate().getYear() ||
                yearlyReport.get().getYReportMonth()!=monthlyReportEditDto.getMonthlyReportDate().getMonthValue())
            return new ServiceResponse<>("The selected date does not belong to the specified year and month.", false);

        Optional<MonthlyReports> monthlyReports = monthlyReportsRepository.findById(UUID.fromString(monthlyReportEditDto.getMonthlyReportId()));

        if(monthlyReports.isEmpty())
            return new ServiceResponse<>("Selected month is invalid.", false);

        monthlyReports.get().setMReportAmount(monthlyReportEditDto.getMonthlyReportAmount());
        monthlyReports.get().setMReportDate(monthlyReportEditDto.getMonthlyReportDate());
        monthlyReports.get().setMReportNarration(monthlyReportEditDto.getMonthlyReportNarration());

        MonthlyReports report = monthlyReportsRepository.saveAndFlush(monthlyReports.get());

        return report.getMReportId()!=null?
                new ServiceResponse<>("Record updated successfully.", true):
                new ServiceResponse<>("Failed to update record.", false);
    }

    @Transactional
    public ServiceResponse<Boolean> deleteMonthlyReport(String monthlyReportId) {
        User user=userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

        Optional<MonthlyReportPublicDto> monthlyReports = monthlyReportsRepository.getMonthlyRecordOfUser(user.getUserId(), UUID.fromString(monthlyReportId));
        if(monthlyReports.isEmpty())
            return new ServiceResponse<>("Invalid delete request.", false);

        monthlyReportsRepository.deleteById(UUID.fromString(monthlyReportId));

        return new ServiceResponse<>("Record deleted.",true);
    }

}

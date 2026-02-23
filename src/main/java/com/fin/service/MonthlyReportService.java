package com.fin.service;

import com.fin.repository.MonthlyReportsRepository;
import com.fin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MonthlyReportService {
    private final UserRepository userRepository;
    private final Validation validation;
    private final MonthlyReportsRepository monthlyReportsRepository;

    @Autowired
    MonthlyReportService(UserRepository userRepository, Validation validation, MonthlyReportsRepository monthlyReportsRepository){
        this.userRepository=userRepository;
        this.validation=validation;
        this.monthlyReportsRepository=monthlyReportsRepository;

    }

//    public ServiceResponse<MonthlyReport> getMonthlyRecordsOfUser(HttpServletRequest request, MonthlyRecordsFetchDto monthlyRecordsFetchDto) {
//        if(!validation.validateYear(monthlyRecordsFetchDto.getYearlyReportYear()))
//            return new ServiceResponse<>("Invalid year.", false);
//        if(!validation.validateMonth(monthlyRecordsFetchDto.getYearlyReportMonth()))
//            return new ServiceResponse<>("Invalid month.", false);
//        if(!validation.validateDoubleAmount(Double.toString(monthlyRecordsFetchDto.getYearlyReportMonthTarget())))
//            return new ServiceResponse<>("Invalid amount.", false);
//
//        ClientLogin clientLogin = (ClientLogin)request.getSession().getAttribute("clientLogin");
//
//        List<MonthlyReport>list = monthlyReportRepository.getMonthlyRecordsOfUser(monthlyRecordsFetchDto, clientLogin);
//
//        if(list.isEmpty())
//            return new ServiceResponse<MonthlyReport>("No records found.", list, false);
//        else
//            return new ServiceResponse<MonthlyReport>("Fetched records.", list, true);
//    }
//
//    public ServiceResponse<MonthlyRecordCreationDto> createMonthlyRecord(HttpServletRequest request,
//                                                                         MonthlyRecordCreationDto monthlyRecordCreationDto) {
//        if(!validation.validateDoubleAmount(Double.toString(monthlyRecordCreationDto.getMonthlyReportAmount())))
//            return new ServiceResponse<>("Invalid amount.", false);
//        if(!validation.validateDate(String.valueOf(monthlyRecordCreationDto.getMonthlyReportDate())))
//            return new ServiceResponse<>("Invalid date.", false);
//
//        ClientLogin clientLogin = (ClientLogin)request.getSession().getAttribute("clientLogin");
//        Optional<YearlyReportPublicDto> yearlyReport=yearlyReportRepository.isYearlyRecordPresent(monthlyRecordCreationDto.getYearlyReportId(), clientLogin);
//        if(yearlyReport.isEmpty())
//            return new ServiceResponse<>("Selected year is invalid.", false);
//        if(yearlyReport.get().getYearlyReportYear()!=LocalDate.parse(monthlyRecordCreationDto.getMonthlyReportDate()).getYear() ||
//                yearlyReport.get().getYearlyReportMonth()!=LocalDate.parse(monthlyRecordCreationDto.getMonthlyReportDate()).getMonthValue())
//            return new ServiceResponse<>("The selected date does not belong to the specified year and month.", false);
//
//        monthlyRecordCreationDto.setMonthlyReportId(String.valueOf(UUID.randomUUID()));
//
//        if(monthlyReportRepository.createMonthlyRecord(monthlyRecordCreationDto))
//            return new ServiceResponse<MonthlyRecordCreationDto>("Record created sucessfully.", monthlyRecordCreationDto, true);
//        return new ServiceResponse<MonthlyRecordCreationDto>("Failed to create record.", monthlyRecordCreationDto, false);
//    }
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

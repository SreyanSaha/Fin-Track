package com.fin.service;

import com.fin.dto.MonthlyReportFetchDto;
import com.fin.dto.MonthlyReportPublicDto;
import com.fin.mail.MailText;
import com.fin.model.User;
import com.fin.repository.MonthlyReportsRepository;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExportReportService {
    private static final String[] headers={
            "Date",
            "Amount",
            "Narration"
    };
    private final int headerLength=headers.length;
    private final String[] months={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
    private final JavaMailSender javaMailSender;
    private final MonthlyReportsRepository monthlyReportsRepository;
    private final MailText mailText=new MailText();

    @Autowired
    public ExportReportService(JavaMailSender javaMailSender, MonthlyReportsRepository monthlyReportsRepository){
        this.javaMailSender=javaMailSender;
        this.monthlyReportsRepository=monthlyReportsRepository;
    }

    @Async
    public void exportRecords(MonthlyReportFetchDto monthlyReportFetchDto, User user, String email){
        List<MonthlyReportPublicDto> list = monthlyReportsRepository.getMonthlyRecordsOfUser(user.getUserId(), monthlyReportFetchDto.getYReportMonth(),
                monthlyReportFetchDto.getYReportYear());

        double totalAmount=0;
        try(Workbook workbook = new XSSFWorkbook()){
            String name=String.format("Monthly Report {%s-%d}", months[monthlyReportFetchDto.getYReportMonth()-1], monthlyReportFetchDto.getYReportYear());
            Sheet sheet = workbook.createSheet(name);
            Row headerRow=sheet.createRow(0);
            for(int i=0;i<headerLength;i++){
                headerRow.createCell(i).setCellValue(headers[i]);
            }
            for(int i=1;i<=list.size();i++){
                Row dataRow=sheet.createRow(i);
                dataRow.createCell(0).setCellValue(String.valueOf(list.get(i-1).getMReportDate()));
                dataRow.createCell(1).setCellValue(list.get(i-1).getMReportAmount());
                dataRow.createCell(2).setCellValue(list.get(i-1).getMReportNarration());
                totalAmount+=list.get(i-1).getMReportAmount();
            }
            Row lastRow=sheet.createRow(list.size()+2);
            String status=(totalAmount<monthlyReportFetchDto.getYReportMonthTarget())?"Deficit of ₹"+(monthlyReportFetchDto.getYReportMonthTarget()-totalAmount):
                          (totalAmount>monthlyReportFetchDto.getYReportMonthTarget())?"Surplus of ₹"+(totalAmount-monthlyReportFetchDto.getYReportMonthTarget()):
                           "Monthly target met.";
            lastRow.createCell(0).setCellValue(status);
            lastRow.createCell(1).setCellValue("Target: ₹"+monthlyReportFetchDto.getYReportMonthTarget());
            lastRow.createCell(2).setCellValue("Collected: ₹"+totalAmount);
            for (int i = 0; i < headerLength; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out=new ByteArrayOutputStream();
            workbook.write(out);
            sendFileEmail(
                    new ByteArrayResource(out.toByteArray()),
                    email,
                    name,
                    monthlyReportFetchDto.getYReportMonth(),
                    monthlyReportFetchDto.getYReportYear()
                    );
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void sendFileEmail(ByteArrayResource file, String email, String name, int month, int year ){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(mailText.getExportReportMonthlyMailSubject().replace("${month-year}", months[month-1]+"-"+String.valueOf(year)));
            helper.setText(mailText.getExportReportMonthlyMail().replace("${month-year}", months[month-1]+"-"+String.valueOf(year)));
            helper.addAttachment(name+".xlsx", file);
            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

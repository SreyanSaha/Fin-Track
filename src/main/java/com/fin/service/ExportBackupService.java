package com.fin.service;

import com.fin.dto.MonthlyReportPublicDto;
import com.fin.mail.MailText;
import com.fin.model.User;
import com.fin.repository.MonthlyReportsRepository;
import com.fin.repository.YearlyReportsRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExportBackupService {
    private final JavaMailSender javaMailSender;
    private final MonthlyReportsRepository monthlyReportsRepository;
    private final YearlyReportsRepository yearlyReportsRepository;
    private final MailText mailText=new MailText();

    @Autowired
    public ExportBackupService(JavaMailSender javaMailSender, MonthlyReportsRepository monthlyReportsRepository, YearlyReportsRepository yearlyReportsRepository){
        this.javaMailSender=javaMailSender;
        this.monthlyReportsRepository=monthlyReportsRepository;
        this.yearlyReportsRepository=yearlyReportsRepository;
    }

    @Async
    public void exportYearlyBackup(int year, User user){
        Optional<List<Integer>> optionalYearIds = yearlyReportsRepository.getYearIdsByYearAndUser(year, user.getUserId());
        if(optionalYearIds.isEmpty())return;
        List<Integer> yearIds = optionalYearIds.get();
        StringBuilder csvBuilder=new StringBuilder();
        for(int id:yearIds){
            Optional<List<MonthlyReportPublicDto>> optionalList = monthlyReportsRepository.getMonthlyRecordByYearlyReportId(id);
            if(optionalList.isEmpty())continue;
            for(MonthlyReportPublicDto dto:optionalList.get()){
                csvBuilder.append(dto.getMReportId()).append(",")
                        .append(dto.getMReportDate()).append(",")
                        .append(dto.getMReportAmount()).append(",")
                        .append(dto.getMReportNarration()).append(",")
                        .append(dto.getYReportId()).append("\n");
            }
            csvBuilder.append("\n");
        }
        String name="Backup of "+year+".csv";
        sendFileEmail(
                new ByteArrayResource(csvBuilder.toString().getBytes()),
                user.getUserEmail(),
                name,
                year
        );
    }

    private void sendFileEmail(ByteArrayResource file, String email, String name, int year ){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject(mailText.getExportBackupReportYearlyMailSubject().replace("${year}", String.valueOf(year)));
            helper.setText(mailText.getExportBackupReportYearlyMail().replace("${year}", String.valueOf(year)));
            helper.addAttachment(name, file);
            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

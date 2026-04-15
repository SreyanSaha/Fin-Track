package com.fin.service;

import com.fin.dto.MonthlyReportPublicDto;
import com.fin.dto.YearlyReportPublicDto;
import com.fin.model.User;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService {
    private final ConcurrentHashMap<Integer, CacheObject> cacheData=new ConcurrentHashMap<>();

    public void setCacheData(User user, YearlyReportPublicDto yearlyReportPublicDto, MonthlyReportPublicDto monthlyReportPublicDto){
        cacheData.put(user.getUserId(), new CacheObject(yearlyReportPublicDto, monthlyReportPublicDto));
    }
    public ConcurrentHashMap<Integer, CacheObject> getCacheData() {
        return cacheData;
    }
    public void updateCacheData(User user, YearlyReportPublicDto yearlyReportPublicDto, MonthlyReportPublicDto monthlyReportPublicDto){
        
    }






    private class CacheObject{
        private HashMap<Long, YearlyRecords> yearRecordsHashMap;
        CacheObject(YearlyReportPublicDto yearlyReportPublicDto, MonthlyReportPublicDto monthlyReportPublicDto){

        }





        private class YearlyRecords{



            YearlyRecords(YearlyReportPublicDto yearlyReportPublicDto, MonthlyReportPublicDto monthlyReportPublicDto){

            }

            private class MonthlyRecords{

            }
        }

    }
}

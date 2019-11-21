package com.ido.quartz.demo;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;


@PersistJobDataAfterExecution  //多次调用job 都会把上次的dataMap拿过来  JobDataMap
public class PrintWordsJob implements Job {

    public PrintWordsJob() {
        System.out.println("PrintWordsJob-->> 构造器");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        System.out.println("-->job execute begin:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //可以拿到触发器
        Trigger trigger = jobExecutionContext.getTrigger();

        //可以拿到jobDetail
        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        //拿到jobDetail里面放置的dataMap 比如ip url 可以去调用别的机器。
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        for (Map.Entry<String, Object> entry : jobDataMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        jobDataMap.put("count", jobDataMap.getIntValue("count") + 1);

        JobKey jobKey = trigger.getJobKey();
        System.out.println("jobKey  name:" + jobKey.getName() + "  group:" + jobKey.getGroup());


    }
}
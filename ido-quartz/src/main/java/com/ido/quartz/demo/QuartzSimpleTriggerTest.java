package com.ido.quartz.demo;

import com.ido.quartz.demo.listener.MyJobListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 *
 *
 *
 *
 */
public class QuartzSimpleTriggerTest {

      public static void main(String[] args) throws Exception {
          Scheduler scheduler = null;
          try {
              // Grab the Scheduler instance from the Factory
              scheduler = StdSchedulerFactory.getDefaultScheduler();

              // and start it off
              scheduler.start();

              JobDataMap jobDataMap = new JobDataMap();
              jobDataMap.put("ip","192.168.1.1");
              jobDataMap.put("name","xxxxx.clazz");
              jobDataMap.put("count",1);
              // define the job and tie it to our HelloJob class
              JobDetail job = JobBuilder.newJob(PrintWordsJob.class)
                      .withIdentity("job1", "group1")
                      .setJobData(jobDataMap)
                      .build();

              // Trigger the job to run now, and then repeat every 5 seconds
              Trigger trigger = TriggerBuilder.newTrigger().forJob("job1","group1")
                      .withIdentity("trigger1", "group1")
                      .startNow()
                      .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                              .withIntervalInSeconds(5)
                              .repeatForever())
                      .build();

              // Tell quartz to schedule the job using our trigger
              scheduler.scheduleJob(job, trigger);

              while(true){
                  Thread.sleep(1000*10);
              }


          } finally {
              if(scheduler != null){
                  scheduler.shutdown();
              }
          }
      }
  }
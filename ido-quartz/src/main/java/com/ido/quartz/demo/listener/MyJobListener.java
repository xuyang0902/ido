package com.ido.quartz.demo.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author xu.qiang
 * @date 19/11/21
 */
public class MyJobListener implements org.quartz.JobListener {

    @Override
    public String getName() {
        return "myJobListner";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        System.out.println(">>>  jobToBeExecuted");

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println(">>>  jobExecutionVetoed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println(">>>  jobWasExecuted");
    }
}

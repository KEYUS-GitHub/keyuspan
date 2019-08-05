package org.keyus.project.keyuspan.task.controller;

import lombok.extern.slf4j.Slf4j;
import org.keyus.project.keyuspan.api.enums.TaskCronExpressionEnum;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.concurrent.ScheduledFuture;

/**
 * @author keyus
 * @create 2019-08-05  上午9:45
 */
@Slf4j
@RestController
public class DynamicTaskController {

    // 注入spring boot auto configuration注入的任务调度线程池
    // 实现对任务调度线程池的控制
    @Resource(name = "taskScheduler")
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> scheduledFuture;

    private Runnable getTask() {
        return () -> log.info("Worker tell you the time: " + LocalDate.now());
    }

    /**
     * 核心是利用ThreadPoolTaskScheduler的schedule()函数启动，返回一个
     * ScheduledFeature。
     */
    @RequestMapping("/start")
    public String startTask() {
        /*
         * task:定时任务要执行的方法
         * trigger:定时任务执行的时间
         */
        scheduledFuture = threadPoolTaskScheduler.schedule(getTask(), new CronTrigger(TaskCronExpressionEnum.DELETE_FILES_AND_FOLDERS.getExpression()));
        log.info("start task done");
        return "start task done";
    }

    /**
     * 核心是利用ScheduledFeature的cancel()函数。
     */
    @RequestMapping("/stop")
    public String stopTask() {
        if (scheduledFuture != null) {
            /*
             * ScheduledFeature继承了jdk的接口Future, cancel用到参数true表示强制关闭任务。
             * cancel的参数false，表示允许任务执行完毕。
             * 因为这里是周期任务，没有执行完毕的时候，所以用的是强制关闭任务。
             */
            scheduledFuture.cancel(true);
        }
        log.info("stop task done");
        return "stop task done";
    }

    @RequestMapping("/change")
    public String changeTask (@RequestParam("expression") String expression) {
        //1. 停止定时器
        stopTask();
        //2. 修改任务执行计划
        scheduledFuture=threadPoolTaskScheduler.schedule(getTask(), new CronTrigger(expression) );
        //3. 启动定时器
        startTask();
        log.info("change task done");
        return "change task done";
    }
}

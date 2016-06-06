package com.example.pc.vita.Task;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * Created by pc on 2016/3/10.
 */

/*
* 线程管理器
* */
public class TaskManager {

    /*
    * 任务执行队列
    * */
    private static ConcurrentLinkedQueue<BaseTask> runnableTask = null;

    /*
    * 等待队列
    * */
    //private static ConcurrentHashMap<Future, BaseTask> waitingTask = null;


    /*
    * 普通线程池，不限大小
    * */
    private static ExecutorService executor = null;

    /*
    * 定时任务线程池，定时执行队列中的进程
    * */
    private static ScheduledExecutorService scheduledExecutor = null;

    /*
    * 初始化
    * */
    static{
        //初始化普通线程池
        executor= Executors.newCachedThreadPool(new CThreadFactory());
        //初始化定时线程池
    /*    scheduledExecutor = Executors.newScheduledThreadPool(1);
        //开启定时线程：定时检查队列并完成任务
        scheduledExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                if (runnableTask == null )
                {
                    return;
                }

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        BaseTask baseTask=null;
                        baseTask=runnableTask.poll();
                        if(baseTask==null){
                            return;
                        }
                        if(baseTask!=null){
                            executor.submit(baseTask);
                        }
                    }
                });

            }
        },0, 3, TimeUnit.SECONDS);*/
    }


    /*
    * 线程工厂
    * */
    private static class CThreadFactory implements ThreadFactory{
        @Override
        public Thread newThread(Runnable r) {
            Thread thread=new Thread(r);
            return thread;
        }
    }

    /*
    * 外部通过把自定义任务放进这个方法来运行新线程任务
    * 这个线程会到runnnable队列中接受调度，不会立即执行
    * 等待用户发送执行命令时会执行该队列
    * 适用于优先级较低线程（资源加载等）
    * */
    public static void addToQueue(final BaseTask runnable){
        //如果线程池空了要重新建一个
        if (executor==null){
            executor= Executors.newCachedThreadPool(new CThreadFactory());

        }

        executor.submit(new Runnable() {
            @Override
            public void run() {
                runnableTask.offer(runnable);
            }
        });
    }

    /*
    * 这个方法会将队列中所有线程运行并清空队列
    * */
    public static void runQueue(){
        //在这里执行加在任务
        executor.execute(new Runnable() {
            @Override
            public void run() {
                BaseTask runnable = null;
                runnable=runnableTask.poll();
                while (runnable!=null){
                    executor.execute(runnable);
                    runnable=runnableTask.poll();
                }
            }
        });
    }

    /*
    * 外部通过把自定义任务放进这个方法来运行新线程任务
    * 这个线程会立即执行，不进任何队列
    * 适用于高优先级任务（注册登录等）
    * */
    public static void addTask(final BaseTask runnable){

        //如果线程池空了要重新建一个
        if (executor==null){
            executor= Executors.newCachedThreadPool(new CThreadFactory());
        }
        executor.submit(runnable);
    }

}

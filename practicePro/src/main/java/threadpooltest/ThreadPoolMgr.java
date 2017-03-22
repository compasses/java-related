package threadpooltest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by i311352 on 17/03/2017.
 */
public class ThreadPoolMgr extends ThreadGroup {
    int threadPoolSize = SystemConfig.getThreadPoolSize();
    List<Task> taskList= new LinkedList<Task>();

    public ThreadPoolMgr(String threadPollName) {
        super(threadPollName);
        setDaemon(true);
    }
    public synchronized void startThreadPool(){
        if(threadPoolSize == 0 ){
            try{
                throw new Exception();
            }
            catch(Exception exception){
                exception.printStackTrace();
            }
            return;
        }
        if(taskList == null){
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        for(int i = 0; i < threadPoolSize; i++){
            WorkThread t = new WorkThread(i);
            //t.setDaemon(true);
            t.start();
        }

    }


    public synchronized Task getTask(){
        if(taskList == null){
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        while(taskList.size() == 0){
            try{
                wait();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return taskList.remove(0);
    }
    public synchronized void stopThreadPool(){
        if(threadPoolSize == 0){
            try{
                throw new Exception();
            }
            catch(Exception exception){
                exception.printStackTrace();
            }
            return;
        }
        if(taskList == null){
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        taskList.clear();
        threadPoolSize = 0;
        interrupt();
    }

    public synchronized void addTask(Task newTask){
        if(taskList == null){
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        taskList.add(newTask);

        notify();
    }

    private class WorkThread extends Thread {
        public WorkThread(int threadID) {
            super(ThreadPoolMgr.this, ""+threadID);
        }
        public void run() {
            while (!isInterrupted()) {
                Task runTask = getTask();
                if(runTask == null)
                    break;
                runTask.run();
            }
        }
    }

    public static void main(String[] args) {
        ThreadPoolMgr manager = new ThreadPoolMgr("SimplePool");
        manager.startThreadPool();

        for(int i = 0; i < 1115; i++){
            Task task = new TestTask(i);
            manager.addTask(task);
        }
    }
}

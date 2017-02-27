package util;

import java.util.concurrent.*;

/**
 * Created by i311352 on 26/02/2017.
 */
public class TracedThreadPool extends ThreadPoolExecutor {
    public TracedThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                            SynchronousQueue<Runnable> blockingDeque) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, blockingDeque);
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, clientTrace(), Thread.currentThread().getName()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    private Exception clientTrace() {
        return new Exception("Client stack trace");
    }


    private Runnable wrap(final Runnable task, final Exception clientTrace, String clientThreadName) {
        return ()-> {
          try {
              task.run();
          } catch (Exception e) {
              clientTrace.printStackTrace();
              throw  e;
          }
        };
    }
}

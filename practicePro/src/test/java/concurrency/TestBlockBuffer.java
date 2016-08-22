package concurrency;

import mockit.Tested;
import org.apache.tomcat.jni.Time;
import org.apache.tomcat.util.modeler.ParameterInfo;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Created by I311352 on 8/19/2016.
 */
public class TestBlockBuffer {
    GrumpyBoundedBuffer<Long> grumpyBoundedBuffer = new GrumpyBoundedBuffer<Long>();
    ConditionBoundedBuffer<Long> conditionBoundedBuffer;
    BoundedBuffer<Long> boundedBuffer;
    SleepyBoundedBuffer<Long> sleepyBoundedBuffer = new SleepyBoundedBuffer<>();
    ExecutorService exec = Executors.newCachedThreadPool();

    private long startTime;
    private long endTime;


    private void RegisterStartTime() {
        startTime = System.currentTimeMillis();
    }
    private void EndTime() {
        endTime = System.currentTimeMillis();

        System.out.println("Used Time: " + (endTime - startTime));
    }

    @Test
    public void TestGrumpyBoundedBuffer() throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        Random random = new Random();
        IntStream.range(0, 8).forEach(
            idx -> {
                    exec.execute(
                        ()->
                        {
                            try {
                                while (!Thread.interrupted())
                                    grumpyBoundedBuffer.put(random.nextLong());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        });
            }
        );

        IntStream.range(0, 8).forEach(
                idx -> {
                    exec.execute(
                            ()->
                            {
                                try {
                                    while (!Thread.interrupted())
                                        grumpyBoundedBuffer.take();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                }
        );
    }

    @Test
    public void TestSleepBoundedBuffer() {
        Random random = new Random();
        IntStream.range(0, 80).forEach(
                idx -> {
                    exec.execute(
                            ()->
                            {
                                try {
                                    while (!Thread.interrupted())
                                        sleepyBoundedBuffer.put(random.nextLong());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            });
                }
        );

        IntStream.range(0, 8).forEach(
                idx -> {
                    exec.execute(
                            ()->
                            {
                                try {
                                    while (!Thread.interrupted())
                                        sleepyBoundedBuffer.take();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                }
        );
    }
}

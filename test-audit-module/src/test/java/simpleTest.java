import com.yandex.audit.run.api.Event;
import com.yandex.audit.run.impl.EventRun;
import com.yandex.audit.run.impl.EventRunBad;
import com.yandex.audit.run.impl.EventRunFast;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
public class simpleTest {
    private Event someEvent = new EventRun();
    private Event someEventFast = new EventRunFast();
    private Event someEventBad = new EventRunBad();

@Test
    public void test() {
    Event someEvent = new EventRunFast();
        int count = 10_000_000;
        for (long i=0; i< count;i++) {

            someEvent.goSomeStaff();
            if (i%100000 ==0) {
                someEvent.showStat();
            }
        }
        assertEquals(someEvent.getEventCountLastDay(),count);
    }


    @Test
    public void testMulti() throws InterruptedException {

        testMultiThread(someEvent);
    }

    @Test
    public void testMultiFast() throws InterruptedException {

        testMultiThread(someEventFast);
    }

    @Test
    @Ignore
    public void testMultiBad() throws InterruptedException {

        testMultiThread(someEventBad);
    }

    private void testMultiThread(final Event event) throws InterruptedException {
        int threadCount = 3;
        int firstArrSize = 50;
        int secondArrSize = 10000;
        int sleepTime = 20;
        long startDate = new Date().getTime();
        Runnable runAble = () -> {
            for (long j = 0; j < firstArrSize; j++) {
                for (long i = 0; i < secondArrSize; i++) {
                    event.goSomeStaff();
                }

             /*   try {
                    Thread.sleep(Math.round(Math.random() * sleepTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                long threadId = Thread.currentThread().getId();
                System.out.println(threadId);
                System.out.println();
                event.showStat();
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < threadCount; i++) {
            executor.execute(runAble);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");


        System.out.println("end");
        System.out.println( new Date().getTime()- startDate);
        event.showStat();

        assertEquals(threadCount*firstArrSize*secondArrSize, event.getEventCountLastDay());
    }


}

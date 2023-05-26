package gonduyil.executor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

@Slf4j
class RecursiveTaskTests {

    @Test
    void test() throws Exception {

        final int arr[] = new int[1000];
        final Random random = new Random();
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            int temp = random.nextInt(100);
            sum += (arr[i] = temp);
        }

        log.info("sum:{}", sum);

        final ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 提交可分解的PrintTask任务
//        Future<Integer> future = forkJoinPool.submit(new RecursiveTaskDemo(arr, 0, arr.length));
//        System.out.println("计算出来的总和="+future.get());

        final Integer sum2 = forkJoinPool.invoke(new RecursiveTaskDemo(arr, 0, arr.length));
        log.info("sum2:{}", sum2);
        forkJoinPool.shutdown();
    }
}

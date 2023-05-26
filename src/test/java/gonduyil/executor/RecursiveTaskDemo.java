package gonduyil.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

@Slf4j
public class RecursiveTaskDemo extends RecursiveTask<Integer> {

    private static final int MAX = 50;
    private int arr[];
    private int start;
    private int end;


    public RecursiveTaskDemo(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        log.info("compute");
        int sum = 0;
        if ((end - start) < MAX) {
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
            return sum;
        }

        int middle = (start + end) / 2;
        RecursiveTaskDemo left = new RecursiveTaskDemo(arr, start, middle);
        RecursiveTaskDemo right = new RecursiveTaskDemo(arr, middle, end);

        left.fork();
        right.fork();
        return left.join() + right.join();
    }


}

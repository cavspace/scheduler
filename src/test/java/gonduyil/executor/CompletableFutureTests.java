package gonduyil.executor;

import com.google.common.base.Throwables;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@Slf4j
public class CompletableFutureTests {

    /**
     *
     */
    @Test
    void multiQuery() throws ExecutionException, InterruptedException {

        final var combine = CompletableFuture.supplyAsync(() -> {
            log.info("combine");
            return "combine";
        });

        final String s = CompletableFuture.supplyAsync(() -> "first")
                .thenCombine(combine, (f, c) -> f + c)
                .get();

        log.info("result:{}", s);

    }

    @Test
    void usingForkJoinCommonPool() {
        log.info("====================");
        ForkJoinPool.commonPool().execute(() -> log.info("common pool test"));
    }

    @Test
    public void completeTest() throws ExecutionException, InterruptedException {

        final CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.complete("complete result is ok");

        final String result = completableFuture.get();
        log.info(result);
    }

    @Test
    void supplyTest() throws ExecutionException, InterruptedException {

        final var completableFuture = CompletableFuture.supplyAsync(() -> {
            log.info("supplyAsync");
            return "supplyAsyncRes";
        });
        completableFuture.get();


        final String result = completableFuture.get();
        log.info(result);
    }

    @Test
    void test() {

        final Try<String> stringTry = Try
                .ofSupplier(() -> getFromDb01())// try 是和调用外部接口
                .mapTry(a -> a.orElseThrow(() -> new RuntimeException("getFromDb01 cannot get data")) + ":mapTry");


        final Either<String, String> either = stringTry.onFailure(e -> log.error("e:{}", Throwables.getStackTraceAsString(e)))
                .toEither("failed");

        log.info(either.getLeft());

    }

    private Optional<String> getFromDb01() {
        return Optional.ofNullable(null);
    }
}

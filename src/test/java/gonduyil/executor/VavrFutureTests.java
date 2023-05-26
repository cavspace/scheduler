package gonduyil.executor;

import com.google.gson.Gson;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;

@Slf4j
public class VavrFutureTests {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    private static class AggregateDTO {
        private String name;
        private String code;
        private Integer total;
    }


    @Test
    void test() {
        final var aggregateFutures = List.of("web", "ip", "domain", "app")
                .map(code -> Future.of(() -> aggregate("gon", code)));

        // invoke with io, use custom executor
        //     static <T, U> Future<U> fold(Executor executor, Iterable<? extends Future<? extends T>> futures, U zero, BiFunction<? super U, ? super T, ? extends U> f) {
        final var futureMap = Future.fold(aggregateFutures, HashMap.empty(), (a, b) -> a.merge(b));

        final var result = futureMap.get();
        log.info("result:{}", new Gson().toJson(result));
    }


    private HashMap<String, AggregateDTO> aggregate(final String tenantId, final String code) {
        log.info("aggregate|tenantId:{}|code:{}", tenantId, code);
        return HashMap.of(code, AggregateDTO.builder()
                .code(code)
                .name(code)
                .total(new Random().nextInt())
                .build());
    }
}

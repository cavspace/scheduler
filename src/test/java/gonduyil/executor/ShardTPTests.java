package gonduyil.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import gonduyil.tools.ShardTPExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

@Slf4j
public class ShardTPTests {

    @Test
    public void test() {

        final ShardTPExecutor shardTPExecutor = new ShardTPExecutor(
                1024,
                ArrayBlockingQueue.class,
                100,
                new ThreadFactoryBuilder().setNameFormat("shard-tp-%d").build(),
                new ShardTPExecutor.CallerRunsPolicy());


        IntStream.range(1, 100).forEach(index -> {
            shardTPExecutor.execute(new ShardTPExecutor.CustomRunnable() {
                @Override
                public String getShardKey() {
                    return "pre-" + index;
                }

                @Override
                public void run() {
                    log.info("run|index:{}", index);
                }
            });
        });
    }
}

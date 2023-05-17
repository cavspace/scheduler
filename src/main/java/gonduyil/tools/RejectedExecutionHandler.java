package gonduyil.tools;

public interface RejectedExecutionHandler {
    void rejectedExecution(ShardTPExecutor.CustomRunnable r, ShardTPExecutor executor);
}

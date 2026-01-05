import java.util.concurrent.*;
import java.util.function.Supplier;

public class ParallelBusinessService implements AutoCloseable {

    private final ExecutorService executor;

    /**
     * Constructor
     * Initializes ForkJoinPool with parallelism = number of CPU cores
     */
    public ParallelBusinessService() {
        this.executor = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Run a task that returns a value asynchronously
     */
    public <T> CompletableFuture<T> runAsync(Supplier<T> task) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Task started on thread: " + Thread.currentThread().getName());
            T result = task.get();
            System.out.println("Task finished on thread: " + Thread.currentThread().getName());
            return result;
            }, executor);
        }

    /**
     * Run a task that returns void asynchronously
     */
    public CompletableFuture<Void> runAsync(Runnable task) {
        return CompletableFuture.runAsync(task, executor);
    }

    /**
     * Public method: compute sum of an array in parallel
     */
    public CompletableFuture<Integer> parallelSum(int[] arr) {
        return runAsync(() -> forkJoinSum(arr, 0, arr.length));
    }

    /**
     * Recursive ForkJoin-style sum
     */
    private int forkJoinSum(int[] arr, int start, int end) {
        // Threshold for sequential computation
        System.out.println("Computing range [" + start + ", " + end + ") on thread " + Thread.currentThread().getName());
        if (end - start <= 1000) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
            return sum;
        } else {
            int mid = start+(end-start) / 2;
            // Fork left half asynchronously
            ForkJoinTask<Integer> leftTask = ForkJoinPool.commonPool().submit(() -> forkJoinSum(arr, start, mid));
            // Compute right half in current thread
            int right = forkJoinSum(arr, mid, end);
            // Join left half result
            return leftTask.join() + right;
        }
    }

    /**
     * Properly shutdown executor
     */
    @Override
    public void close() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Simple demo
    public static void main(String[] args) {
        int[] array = new int[1000000];
        for (int i = 0; i < array.length; i++) array[i] = i + 1;

        try (ParallelBusinessService service = new ParallelBusinessService()) {
            CompletableFuture<Integer> sumFuture = service.parallelSum(array);
            System.out.println("Sum: " + sumFuture.join());
        }
    }
}

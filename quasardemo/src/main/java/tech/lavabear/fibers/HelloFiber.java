package tech.lavabear.fibers;

import co.paralleluniverse.fibers.DefaultFiberScheduler;
import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.FiberUtil;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableCallable;
import co.paralleluniverse.strands.SuspendableRunnable;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author mitch
 */
public class HelloFiber {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from Thread");
            }
        }).start();

        new Fiber(new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                System.out.println("Hello from Fiber");
            }
        }).start();

        System.out.println(new Fiber(new SuspendableCallable() {
            @Override
            public String run() throws SuspendExecution, InterruptedException {
                return "Hello after Fiber";
            }
        }).start().get());

        System.out.println(DefaultFiberScheduler.getInstance()
                .newFiber(() -> "Hello from Scheduler")
                .start().get());
        
        System.out.println(FiberUtil.runInFiber(() -> "Hello from FiberUtil"));
    }
}

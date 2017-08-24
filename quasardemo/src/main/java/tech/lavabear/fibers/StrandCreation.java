package tech.lavabear.fibers;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.Strand;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates that creating fibers has less overhead than threads
 *
 * @author mitch
 */
public class StrandCreation {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Fibers: " + timedCounter(new ArrayList<>(10_000), false) + " ms");
        System.out.println("Threads: " + timedCounter(new ArrayList<>(10_000), true)+ " ms");
        
        System.out.println("Sleep...");
        Thread.sleep(1000L);
        
        // The order changes how long each takes
        System.out.println("Threads: " + timedCounter(new ArrayList<>(10_000), true)+ " ms");
        System.out.println("Fibers: " + timedCounter(new ArrayList<>(10_000), false) + " ms");
    }

    private static long timedCounter(List<String> strings, boolean use_threads) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10_000; i++) {
            startStrand(use_threads, strings, i);
        }
        return System.currentTimeMillis() - start;
    }

    private static Strand startStrand(boolean use_threads, List<String> strings, final int num) {
        return Strand.of(use_threads
                ? new Thread(() -> strings.add("Hello from Thread: " + num))
                : new Fiber(() -> strings.add("Hello from Fiber: " + num))).start();
    }
}

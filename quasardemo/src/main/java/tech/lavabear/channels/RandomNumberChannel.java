package tech.lavabear.channels;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.FiberUtil;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.channels.Channels;
import co.paralleluniverse.strands.channels.IntChannel;
import co.paralleluniverse.strands.channels.ReceivePort;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mitch
 */
public class RandomNumberChannel {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        IntChannel channel = Channels.newIntChannel(1);
     
        new Fiber(() -> {
            try {
                while(!channel.hasMessage())
                {
                    Fiber.sleep(10);
                }
                System.out.println("Recieved: " + channel.receiveInt());
            } catch (ReceivePort.EOFException ex) {
                Logger.getLogger(RandomNumberChannel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
        
        FiberUtil.runInFiber(() -> {
            try {
                channel.send(new Random().nextInt(), 1L, TimeUnit.SECONDS);
            } catch (TimeoutException ex) {
                Logger.getLogger(RandomNumberChannel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}

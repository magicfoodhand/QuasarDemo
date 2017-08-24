package tech.lavabear.actors;

import co.paralleluniverse.actors.ActorRef;
import co.paralleluniverse.actors.behaviors.FiniteStateMachineActor;
import static co.paralleluniverse.actors.behaviors.FiniteStateMachineActor.TERMINATE;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableCallable;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author mitch
 */
public class FSMActor
{

	public static void main(String[] args) throws InterruptedException, SuspendExecution, ExecutionException
	{
		ActorRef<Object> a = new FiniteStateMachineActor()
		{

			@Override
			protected SuspendableCallable<SuspendableCallable> initialState()
			{
				return this::state1;
			}

			private SuspendableCallable<SuspendableCallable> state1() throws SuspendExecution, InterruptedException
			{
				return receive((m) ->
				{
					if ("a".equals(m))
					{
						System.out.println("State 1 recieved: " + m);
						return this::state2;
					}
					System.out.println("State 1 Ignored: " + m);
					return null; // don't handle message
				});
			}

			private SuspendableCallable<SuspendableCallable> state2() throws SuspendExecution, InterruptedException
			{
				return receive((m) ->
				{
					if ("b".equals(m))
					{
						System.out.println("State 2 recieved: " + m);
						return this::state1;
					}else if ("stop".equals(m))
					{
						System.out.println("STOP");
						return TERMINATE;
					}
					System.out.println("State 2 Ignored: " + m);
					return null; // don't handle message
				});
			}
		}.spawn();

		a.send("a");
		a.send("b");
		a.send("foo");
		a.send("bar");
		a.send("a");
		a.send("a");
		a.send("stop");
		
		// Never recieved
		a.send("a");
	}
}

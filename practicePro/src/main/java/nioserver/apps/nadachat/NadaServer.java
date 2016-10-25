package nioserver.apps.nadachat;

import nioserver.BufferFactory;
import nioserver.impl.DumbBufferFactory;
import nioserver.impl.InputHandlerFactory;
import nioserver.impl.NioDispatcher;
import nioserver.impl.StandardAcceptor;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: ron
 * Date: May 15, 2006
 * Time: 6:20:59 PM
 */
public class NadaServer
{
	private NadaServer()
	{
		// cannot instantiate
	}

	public static void main (String [] args)
		throws IOException
	{
		Executor executor = Executors.newCachedThreadPool();
		BufferFactory bufFactory = new DumbBufferFactory(1024);
		NioDispatcher dispatcher = new NioDispatcher(executor, bufFactory);
		InputHandlerFactory factory = new NadaProtocol();
		StandardAcceptor acceptor = new StandardAcceptor(1234, dispatcher, factory);

		dispatcher.start();
		acceptor.newThread();
	}
}

package framework.online;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import framework.Vec2F;
import framework.Vec2I;

public abstract class AbstractGameServer {
	Server server = new Server(Short.MAX_VALUE, Short.MAX_VALUE);
	int tcpPort, udpPort;
	boolean running = true;
	volatile List<Connection> connected;
	
	public abstract void preStart();
	
	public void registerClasses(Kryo k) {
		k.register(Vec2I.class);
		k.register(Vec2F.class);
	}
	
	public AbstractGameServer(int tcpPort, int udpPort) {
		this.tcpPort = tcpPort;
		this.udpPort = udpPort;
		registerClasses(server.getKryo());
		preStart();
		server.start();
		connected = Collections.synchronizedList(new ArrayList<Connection>());
		try {
			server.bind(tcpPort, udpPort);
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		server.addListener(new Listener() {
			@Override
			public synchronized void received(Connection c, Object o) {
				receive(c, o, c.getID());
			}

			@Override
			public synchronized void connected(Connection c) {
				connected.add(c);
				connect(c, c.getID());
				System.out.println("server: connection");
			}

			@Override
			public synchronized void disconnected(Connection c) {
				connected.remove(c);
				disconnect(c, c.getID());
			}
		});
	}
	
	public abstract void receive(Connection c, Object o, int id);
	public abstract void connect(Connection c, int id);
	public abstract void disconnect(Connection c, int id);
	
	public void stop() {
		server.stop();
	}
	
	public void sendToAll(Object o) {
		sendToAll(o, null);
	}
	
	public void sendToAll(Object o, Connection exception) {
		for(Connection c : connected)
			if(c != exception) c.sendTCP(o);
	}
}

package framework.online;

import java.io.IOException;
import java.net.InetAddress;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import framework.Vec2F;
import framework.Vec2I;

public abstract class AbstractGameClient {
	protected Client client = new Client(Short.MAX_VALUE, Short.MAX_VALUE);
	
	public void registerClasses(Kryo k) {
		k.register(Vec2I.class);
		k.register(Vec2F.class);
	}
	
	public AbstractGameClient(InetAddress ip, int portTCP, int portUDP) {
		registerClasses(client.getKryo());
		client.start();
		try {
			client.connect(5000, ip, portTCP, portUDP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.addListener(new Listener() {
			@Override
			public void received(Connection c, Object o) {
				receive(c, o);
			}
			
			@Override
			public void disconnected(Connection c) {
				disconnect(c);
			}
		});
	}
	
	public abstract void receive(Connection c, Object o);
	public abstract void disconnect(Connection c);
	
	public void stop() {
		client.stop();
	}
}

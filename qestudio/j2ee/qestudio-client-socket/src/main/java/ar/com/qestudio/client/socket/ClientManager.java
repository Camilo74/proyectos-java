package ar.com.qestudio.client.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import ar.com.qestudio.client.socket.api.APIService;
import ar.com.qestudio.core.model.Attached;
import ar.com.qestudio.core.model.Registry;

public class ClientManager implements APIService {

	private Socket conexion;
	private SocketManager socketManager;
	
	public ClientManager() throws UnknownHostException, IOException {
		conexion = new Socket("localhost", 4000);
		socketManager = new SocketManager(conexion);
		socketManager.start();
	}

	//--------------------------
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Registry> findAllRegistries() {
		return (List<Registry>)socketManager.call("findAllRegistries");
	}

	@Override
	public Registry findRegistry(int id) {
		return (Registry) socketManager.call("findRegistry", id);
	}
	
	public void openFile(Registry registry, Attached attached){
		socketManager.openFile(registry,attached);
	}
}
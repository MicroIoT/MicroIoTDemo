package top.microiot.demo.device;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import top.microiot.api.device.IoTDevice;
import top.microiot.api.device.WebsocketDeviceSession;

@SpringBootApplication(exclude={MongoAutoConfiguration.class})
public class DeviceDemoApplication implements CommandLineRunner{
	@Autowired
	private WebsocketDeviceSession session;
	@Autowired
	private GetHandler onGet;
	@Autowired
	private SetHandler onSet;
	@Autowired
	private ActionHandler onAction;
	
	public static void main(String[] args) {
		SpringApplication.run(DeviceDemoApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		new IoTDevice(session, onGet, onSet, onAction);
	}
}

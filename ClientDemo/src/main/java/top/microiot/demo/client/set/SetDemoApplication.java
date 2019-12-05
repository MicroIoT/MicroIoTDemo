package top.microiot.demo.client.set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import top.microiot.api.client.WebsocketClientSession;
import top.microiot.demo.domain.DeviceDef;

@SpringBootApplication(exclude={MongoAutoConfiguration.class})
public class SetDemoApplication implements CommandLineRunner{
	@Autowired
	private WebsocketClientSession session;
	@Autowired
	private SetHandler onSet;
	
	public static void main(String[] args) {
		SpringApplication.run(SetDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		boolean locked= true;
		
		session.setAsync(DeviceDef.ID, DeviceDef.AttributeLocked, locked, onSet);
		
		session.set(DeviceDef.ID, DeviceDef.AttributeLocked, locked);
		System.out.println("sync " + DeviceDef.AttributeLocked + ": " + locked);
	}

}

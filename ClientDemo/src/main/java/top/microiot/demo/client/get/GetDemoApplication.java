package top.microiot.demo.client.get;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import top.microiot.api.client.WebsocketClientSession;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.domain.attribute.Location;

@SpringBootApplication(exclude={MongoAutoConfiguration.class})
public class GetDemoApplication implements CommandLineRunner{
	@Autowired
	private WebsocketClientSession session;
	@Autowired
	private GetHandler onGet;
	
	public static void main(String[] args) {
		SpringApplication.run(GetDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		session.getAsync(DeviceDef.ID, DeviceDef.AttributeLocation, Location.class, onGet);
		
		Location location = session.get(DeviceDef.ID, DeviceDef.AttributeLocation, Location.class);
		System.out.println("sync " + DeviceDef.AttributeLocation +": " + location.getLongitude() + ", " + location.getLatitude());
	}

}

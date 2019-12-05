package top.microiot.demo.client.alarm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import top.microiot.api.client.WebsocketClientSession;
import top.microiot.demo.domain.DeviceDef;

@SpringBootApplication(exclude={MongoAutoConfiguration.class})
public class AlarmDemoApplication implements CommandLineRunner{
	@Autowired
	private WebsocketClientSession wsession;
	@Autowired
	private AlarmHandler onAlarm;
	
	public static void main(String[] args) {
		SpringApplication.run(AlarmDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		wsession.subscribe(DeviceDef.ID, onAlarm);
	}

}

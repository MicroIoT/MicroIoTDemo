package top.microiot.demo.client.action;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;

import top.microiot.api.client.WebsocketClientSession;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.demo.domain.Filter;
import top.microiot.demo.domain.Record;

@SpringBootApplication(exclude={MongoAutoConfiguration.class})
public class ActionDemoApplication implements CommandLineRunner{
	@Autowired
	private WebsocketClientSession session;
	@Autowired
	private ActionHandler onAction;
	
	public static void main(String[] args) {
		SpringApplication.run(ActionDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Filter filter = new Filter(new Date(), new Date());
		
		session.actionAsync(DeviceDef.ID, DeviceDef.ActionGetHistory, filter, new ParameterizedTypeReference<List<Record>>() {}, onAction);
		
		Set<Record> records = session.action(DeviceDef.ID, DeviceDef.ActionGetHistory, filter, new ParameterizedTypeReference<Set<Record>>() {});
		for(Record record : records) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println("sync record:  from: " + sdf.format(record.getStartTime()) + " to: " + sdf.format(record.getEndTime()));
		}
	}

}

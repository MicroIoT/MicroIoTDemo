package top.microiot.demo.device;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import top.microiot.api.device.IoTDevice;
import top.microiot.api.device.WebsocketDeviceSession;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.demo.domain.StateChangedAlarm;
import top.microiot.domain.attribute.Location;

@SpringBootApplication(exclude={MongoAutoConfiguration.class})
public class ReportDemoApplication implements CommandLineRunner{
	@Autowired
	private WebsocketDeviceSession session;
	
	public static void main(String[] args) {
		SpringApplication.run(ReportDemoApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		IoTDevice device = new IoTDevice(session, null, null, null);
		
		StateChangedAlarm alarm = getAlarm();
		device.reportAlarm(DeviceDef.AlarmStateChangedAlarm, alarm);
		
		Map<String, Object> events = new HashMap<String, Object>();
		Location location = getLocation();
		events.put("location", location);
		device.reportEvent(events);
	}
	private StateChangedAlarm getAlarm() {
		Location location = getLocation();
		StateChangedAlarm alarm = new StateChangedAlarm(location, true);
		return alarm;
	}
	private Location getLocation() {
		Random r1 = new Random();
		double x = 180 * r1.nextDouble();
		double y = 90 * r1.nextDouble();
		Location location = new Location(x, y);
		return location;
	}
}

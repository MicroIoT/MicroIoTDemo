package top.microiot.demo.device;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import top.microiot.api.device.stomp.ActionRequestSubscriber;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.demo.domain.Filter;
import top.microiot.demo.domain.Record;
import top.microiot.domain.Device;
import top.microiot.domain.User;
import top.microiot.domain.attribute.Location;
import top.microiot.exception.ValueException;

@Component
@Scope("prototype")
public class ActionHandler extends ActionRequestSubscriber {
	
	@Override
	public void init() {
		addType(DeviceDef.ActionGetHistory, Filter.class);
	}

	@Override
	public Object action(User requester, Device device, String action, Object request) {
		if(action.equals(DeviceDef.ActionGetHistory)) {
			Filter filter = (Filter)request;
			
			List<Record> records = new ArrayList<Record>();
			Random r = new Random();
			int size = r.nextInt(10);

			for(int i = 0; i < size; i ++) {
				Record record = getRecord(filter);
				records.add(record);
			}

			return records;
		}else {
			throw new ValueException("unknonw action: " + action);
		}
	}

	private Record getRecord(Filter filter) {
		Random r = new Random();
		int i = r.nextInt(10000000);
		Date start = new Date();
		start.setTime(start.getTime() + i);

		int j = r.nextInt(10000000);
		Date end = new Date();
		end.setTime(end.getTime() + j);

		Random l = new Random();
		double x = 180 * l.nextDouble();
		double y = 90 * l.nextDouble();
		Location location = new Location(x, y);
		return new Record(location, start, location, end);
	}

}

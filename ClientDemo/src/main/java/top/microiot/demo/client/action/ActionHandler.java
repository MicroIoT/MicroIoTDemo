package top.microiot.demo.client.action;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import top.microiot.api.client.stomp.ActionResponseSubscriber;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.demo.domain.Record;
import top.microiot.domain.Device;

@Component
public class ActionHandler extends ActionResponseSubscriber {

	@Override
	public void onActionResult(Device device, String action, Object request, Object response) {
		if(action.equals(DeviceDef.ActionGetHistory)) {
			@SuppressWarnings("unchecked")
			List<Record> records = (List<Record>)response;
			for(Record record : records) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("async record:  from: " + sdf.format(record.getStartTime()) + " to: " + sdf.format(record.getEndTime()));
			}
		}
		
	}

	@Override
	public void onActionError(Device device, String action, Object request, String error) {
		System.out.println(device.getString() + " async action[" + action + "] error: " + error);
	}

}

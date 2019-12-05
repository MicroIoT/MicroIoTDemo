package top.microiot.demo.client.set;

import org.springframework.stereotype.Component;

import top.microiot.api.client.stomp.SetResponseSubscriber;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.domain.Device;

@Component
public class SetHandler extends SetResponseSubscriber {

	@Override
	public void onSetResult(Device device, String attribute, Object value) {
		if(attribute.equals(DeviceDef.AttributeLocked)) {
			boolean locked = (Boolean)value;
			System.out.println("async " + DeviceDef.AttributeLocked + ": " + locked);
		}
	}

	@Override
	public void onSetError(Device device, String attribute, Object value, String error) {
		System.out.println(device.getString() + " set attribute[" + attribute + "] error: " + error);
	}

}

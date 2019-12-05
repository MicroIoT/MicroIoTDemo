package top.microiot.demo.client.get;

import org.springframework.stereotype.Component;

import top.microiot.api.client.stomp.GetResponseSubscriber;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.domain.Device;
import top.microiot.domain.attribute.Location;

@Component
public class GetHandler extends GetResponseSubscriber {

	@Override
	public void onGetError(Device device, String attribute, String error) {
		System.out.println(device.getString() + " get attribute[" + attribute + "] error:" + error);
	}

	@Override
	public void onGetResult(Device device, String attribute, Object value) {
		if(attribute.equals(DeviceDef.AttributeLocation)) {
			Location location = (Location)value;
			System.out.println("async " + DeviceDef.AttributeLocation + ": " + location.getLongitude() + ", " + location.getLatitude());
		}
	}

}

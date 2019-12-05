package top.microiot.demo.device;

import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import top.microiot.api.device.stomp.GetRequestSubscriber;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.domain.Device;
import top.microiot.domain.User;
import top.microiot.domain.attribute.Location;
import top.microiot.exception.ValueException;

@Component
@Scope("prototype")
public class GetHandler extends GetRequestSubscriber {

	@Override
	public Object getAttributeValue(User requester, Device device, String attribute) {
		if(attribute.equals(DeviceDef.AttributeLocation)) {
			Random r = new Random();
			double x = 180 * r.nextDouble();
			double y = 90 * r.nextDouble();
			Location location = new Location(x, y);
			return location;
		}else if(attribute.equals(DeviceDef.AttributeLocked)) {
			Random r = new Random();
			boolean locked = r.nextBoolean();
			return locked;
		}else {
			throw new ValueException("get unknow attribute: " + attribute);
		}
	}

}

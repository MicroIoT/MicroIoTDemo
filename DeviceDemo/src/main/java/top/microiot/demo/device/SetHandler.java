package top.microiot.demo.device;

import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import top.microiot.api.device.stomp.SetRequestSubscriber;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.demo.domain.StateChangedAlarm;
import top.microiot.domain.Device;
import top.microiot.domain.User;
import top.microiot.domain.attribute.Location;
import top.microiot.exception.ValueException;

@Component
@Scope("prototype")
public class SetHandler extends SetRequestSubscriber {
	
	@Override
	public void init() {
		addType(DeviceDef.AttributeLocked, boolean.class);
	}

	@Override
	public void setAttribute(User requester, Device device, String attribute, Object value) {
		if(attribute.equals(DeviceDef.AttributeLocked)) {
			boolean locked = (boolean)value;
			
			Location location = getLocation();
			
			StateChangedAlarm alarm = new StateChangedAlarm(location, locked);
			this.getWebsocketDeviceSession().getSession().reportAlarm(DeviceDef.AlarmStateChangedAlarm, alarm);
		} else {
			throw new ValueException("set unknonw attribute: " + attribute);
		}
	}

	private Location getLocation() {
		Random l = new Random();
		double x = 180 * l.nextDouble();
		double y = 90 * l.nextDouble();
		Location location = new Location(x, y);
		return location;
	}

}

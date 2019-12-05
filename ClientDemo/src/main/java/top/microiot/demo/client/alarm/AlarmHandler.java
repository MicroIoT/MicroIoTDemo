package top.microiot.demo.client.alarm;

import java.util.Date;

import org.springframework.stereotype.Component;

import top.microiot.api.client.stomp.AlarmSubscriber;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.demo.domain.StateChangedAlarm;
import top.microiot.domain.ManagedObject;

@Component
public class AlarmHandler extends AlarmSubscriber {
	
	@Override
	public void init() {
		addType(DeviceDef.AlarmStateChangedAlarm, StateChangedAlarm.class);
	}

	@Override
	public void onAlarm(ManagedObject notifyObject, String alarmType, Object alarmInfo, Date reportTime, Date receiveTime) {
		if (alarmType.equals(DeviceDef.AlarmStateChangedAlarm)) {
			StateChangedAlarm info = (StateChangedAlarm) alarmInfo;

			System.out.println(DeviceDef.AlarmStateChangedAlarm + ": " + DeviceDef.AttributeLocked  + ":" + info.isLocked() + " from: " + notifyObject.getString());
		} 
	}

}

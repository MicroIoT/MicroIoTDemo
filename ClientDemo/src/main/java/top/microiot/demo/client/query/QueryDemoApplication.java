package top.microiot.demo.client.query;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;

import top.microiot.api.client.HttpClientSession;
import top.microiot.api.dto.RestPage;
import top.microiot.demo.domain.DeviceDef;
import top.microiot.demo.domain.TestChoice;
import top.microiot.domain.Device;
import top.microiot.domain.Event;
import top.microiot.domain.Site;
import top.microiot.domain.SiteType;
import top.microiot.domain.attribute.Location;
import top.microiot.dto.DistinctInfo;
import top.microiot.dto.QueryInfo;
import top.microiot.dto.QueryPageInfo;

@SpringBootApplication(exclude={MongoAutoConfiguration.class})
public class QueryDemoApplication implements CommandLineRunner{
	@Autowired
	private HttpClientSession session;
	
	public static void main(String[] args) {
		SpringApplication.run(QueryDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Device device;
		QueryInfo info = new QueryInfo();
		
		device = session.getEntityById(Device.class, DeviceDef.ID);
		System.out.println(device.getString());
		
		info.setFilter("{ \"attributes.型号.value\": \"a1\"}");
		device = session.getOneEntity(Device.class, info);
		System.out.println(device.getString());
		
		info.setFilter("{ attribute: \"Choice\"}");
		List<Event> events = session.getEntityList(Event.class, info, new ParameterizedTypeReference<List<Event>>() {});
		for(Event event: events) {
			System.out.println(event.getData(TestChoice.class));
		}
		
		info.setFilter("{ name: /^0/}");
		info.setSort("{connected: -1}");
		List<Device> devices = session.getEntityList(Device.class, info, new ParameterizedTypeReference<List<Device>>() {});
		for(Device d: devices) {
			System.out.println(d.getString());
		}
		
		int count = session.count(Site.class, null);
		System.out.println("query count: " + count);
		
		boolean exist = session.exist(Device.class, info);
		System.out.println("query exist: " + exist);
		
		QueryPageInfo page = new QueryPageInfo();
		page.setFilter("{ \"notifyObject.$id\": ObjectId(\"5ddb83fb0e8e3d0001f60ed3\"), \"attribute\": \"location\" }");
		page.setSort("{reportTime:1}");
		page.setPageNumber(0);
		page.setPageSize(10);
		
		Page<Event> eventPage = session.getEntityPage(Event.class, page, new ParameterizedTypeReference<RestPage<Event>>() {});
		for(Event e : eventPage.getContent()) {
			System.out.println(e.getNotifyObject().getId());
			Location location = (Location) e.getData(Location.class);
			System.out.println(location.getLongitude() + "," + location.getLatitude());
		}

		DistinctInfo distinct = new DistinctInfo();
		distinct.setField("attributes.model.value");
		distinct.setReturnClass(Device.class);
		List<String> models = session.getEntityDistinct(Device.class, distinct, new ParameterizedTypeReference<List<String>>() {});
		for(String model : models) {
			System.out.println(model);
		}
		
		distinct.setField("siteType");
		distinct.setReturnClass(SiteType.class);
		List<SiteType> types = session.getEntityDistinct(Site.class, distinct, new ParameterizedTypeReference<List<SiteType>>() {});
		for(SiteType type : types) {
			System.out.println(type.getName());
		}
	}

}

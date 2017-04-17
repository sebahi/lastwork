package gov.loc.workflow.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.loc.workflow.domain.Env;

@Component
public class ObtainServerIPPort {

	@Autowired
	Env environment;
	private String ipadd;
	public String getServerIPPort(){
		MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames;
		try {
			objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
			        Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			String host = InetAddress.getLocalHost().getHostAddress();
	        String port = objectNames.iterator().next().getKeyProperty("port");
	        ipadd = "http" + "://" + host + ":" + port;
	        
		} catch (MalformedObjectNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ipadd;
		
	}
}

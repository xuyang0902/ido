package com.ido.dubbo.debug;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.ido.dubbo.debug.api.HelloDubbo;
import com.ido.dubbo.debug.api.HelloDubboImpl;

/**
 * 使用编程式代码变成，读者容易断点进去看。
 *
 * http://dubbo.apache.org/en-us/docs/user/configuration/api.html
 *
 *  可以指定方法 超时时间，点对点调用等等。都可以用api，这里不展开。
 *
 * @author xu.qiang
 * @date 18/11/28
 */
public class HelloDubboProvider {


    public static void main(String[] args) {

        // Implementation
        HelloDubbo xxxService = new HelloDubboImpl();

        // Application Info
        ApplicationConfig application = new ApplicationConfig();
        application.setName("app_01");

        // Registry Info
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("192.168.1.121:2181");
        registry.setProtocol("zookeeper");

        // Protocol
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(18080);
        protocol.setThreads(10);

        // Exporting
        ServiceConfig<HelloDubbo> service = new ServiceConfig<HelloDubbo>(); // In case of memory leak, please cache.
        service.setApplication(application);
        service.setRegistry(registry); // Use setRegistries() for multi-registry case
        service.setProtocol(protocol); // Use setProtocols() for multi-protocol case
        service.setInterface(HelloDubbo.class);
        service.setRef(xxxService);
        service.setVersion("1.0.0");

        // Local export and register
        service.export();


        while(true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}

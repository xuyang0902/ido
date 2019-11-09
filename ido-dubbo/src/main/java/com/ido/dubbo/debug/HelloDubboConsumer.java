package com.ido.dubbo.debug;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.ido.dubbo.debug.api.HelloDubbo;

/**
 * 使用编程式代码变成，读者容易断点进去看。
 *
 * @author xu.qiang
 * @date 18/11/28
 */
public class HelloDubboConsumer {


    public static void main(String[] args) {


        // Application Info
        ApplicationConfig application = new ApplicationConfig();
        application.setName("app_02");

        // Registry Info
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("192.168.1.121:2181");
        registry.setProtocol("zookeeper");

        // NOTES: ReferenceConfig holds the connections to registry and providers, please cache it for performance.

        // Refer remote service
        ReferenceConfig<HelloDubbo> reference = new ReferenceConfig<HelloDubbo>(); // In case of memory leak, please cache.
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(HelloDubbo.class);
        reference.setVersion("1.0.0");

        // Use xxxService just like a local bean
        HelloDubbo xxxService = reference.get(); // NOTES: Please cache this proxy instance.


        String 不错哦 = xxxService.sayHello("不错哦");
        System.out.println(不错哦);

        while(true){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}

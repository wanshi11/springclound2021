package com.atguigu.springcloud.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import javax.swing.plaf.synth.SynthToggleButtonUI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class MyRoundLB implements  LoadBalancer{

    private AtomicInteger autoInteger = new AtomicInteger(0);


    //final 修饰方便不能被重写
    private final int getCallCount(){
        int current;
        int next;

        do{
            current = this.autoInteger.get();
            next = current >= 2147483647 ? 0 : current+1;

        }while (!this.autoInteger.compareAndSet(current,next));
        System.out.println("MyRoundLB*******第几次访问，次数next: "+next);
        return next;
    }


    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getCallCount() % serviceInstances.size();

        return serviceInstances.get(index);
    }



}

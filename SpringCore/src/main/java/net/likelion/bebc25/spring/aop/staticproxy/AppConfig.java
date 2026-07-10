package net.likelion.bebc25.spring.aop.staticproxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 스프링 컨테이너에 알려주는 앱 설정 클래
@Configuration // 스프링의 설정 정보를 담고 있는 클래스라고 스프링 컨테이너에게 알려주는 어노테이션
public class AppConfig {
    @Bean // 스프링 빈으로 등록 (메소드명인 car가 빈의 이름이 됨)
    public Car car(){
//        Car target = new GasolineCar();
        Car target = new HybridCar();
        Car logProxy = new LogProxy(target);
        return logProxy;
    }

    @Bean // 스프링이 생성해서 관리할 객체
    public Driver driver(Car car){
        return new Driver(car); // DI
    }
}
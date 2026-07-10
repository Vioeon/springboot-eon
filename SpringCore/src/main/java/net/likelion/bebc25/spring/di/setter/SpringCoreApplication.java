package net.likelion.bebc25.spring.di.setter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringCoreApplication {
    void main(){
        // 1. 스프링 컨테이너 생성(Bean(객체) 정보 분석을 위한 config 객체 지정)
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("스프링컨테이너 생성");
        // 2. driver Bean을 컨테이너에서 꺼냄
        Driver driver = context.getBean(Driver.class);
        System.out.println("driver Bean 꺼냄");
        driver.setCar(new HybridCar());
        // 3. 비즈니스 로직 수행
        driver.driveCar();;
    }
}

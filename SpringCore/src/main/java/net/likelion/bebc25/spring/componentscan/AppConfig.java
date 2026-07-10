package net.likelion.bebc25.spring.componentscan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// 스프링 컨테이너에 알려주는 앱 설정 클래
@Configuration // 스프링의 설정 정보를 담고 있는 클래스라고 스프링 컨테이너에게 알려주는 어노테이션
@EnableAspectJAutoProxy // 스프링 컨테이너에 @Aspect 어노테이션이 붙은 Bean들을 찾아서 프록시 처리를 하도록 지시
@ComponentScan(basePackages = "net.likelion.bebc25.spring.componentscan") // 패키지 내에@Component 어노테이션 붙은 클래스들을 Bean으로 자동 등록
public class AppConfig {

}

// 스프링 컨테이너가 하는일
//AppConfig config = new AppConfig();
//Car car = config.car();
//Car car2 = config.car2();
//Driver driver = config.driver(car? car2? 어떤거할지 몰라서 오류남);
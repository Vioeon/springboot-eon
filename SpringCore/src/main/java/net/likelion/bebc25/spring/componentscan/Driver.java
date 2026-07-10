package net.likelion.bebc25.spring.componentscan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Driver {
    //    private GasolineCar car = new GasolineCar(); // 의존 관계
//    @Autowired // 필드 의존성 주입 - spring에 종속??/
    private Car car; // 의존하는 객체

    Driver(){
        System.out.println("Driver 기본 생성자 호출됨");
    }

    // DI 개념 - 생성자 의존성 주입
    @Autowired // 의존성 자동 주입. 생성자가 하나만 있을 경우에는 생략 가능
    public Driver(@Qualifier("gasolineCar") Car car) {
        System.out.println("생성자 인젝션 호출됨: " + car);
        this.car = car;
    }
//    @Autowired // Setter 의존성 자동 주입.
//    public void setCar(Car car){
//        System.out.println("Setter Injection 호출됨.");
//        this.car = car;
//    }
    public void driveCar(int maxSpeed) {
        car.startEngine();
        car.drive();
        car.stopEngine();
    }
}

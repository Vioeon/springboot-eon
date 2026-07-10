package net.likelion.bebc25.intellij;

import org.springframework.stereotype.Component;

@Component
public class Driver {
    //    private GasolineCar car = new GasolineCar(); // 의존 관계
    private Car car; // 의존하는 객체

    // DI 개념 - 의존성 주입
    public Driver(Car car) {
        System.out.println("called Constructor Injection: " + car);
        this.car = car;
    }

    public void driveCar(int maxSpeed) {
        car.startEngine();
        car.drive();
        car.stopEngine();
    }
}

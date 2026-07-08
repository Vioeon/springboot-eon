package net.likelion.bebc25.spring;

public class Driver {
    //    private GasolineCar car = new GasolineCar(); // 의존 관계
    private Car car; // 의존하는 객체

    // DI 개념 - 의존성 주입
    public Driver(Car car) {
        this.car = car;
    }

    public void driveCar() {
        car.startEngine();
        car.drive();
        car.stopEngine();
    }
}

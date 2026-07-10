package net.likelion.bebc25.spring.di.setter;

public class Driver {
    //    private GasolineCar car = new GasolineCar(); // 의존 관계
    private Car car; // 의존하는 객체

    // setter injection
    public void setCar(Car car){
        System.out.println("Setter Injection");
        this.car = car;
    }

    public void driveCar() {
        System.out.println("driveCar");
        car.startEngine();
        car.drive();
        car.stopEngine();
    }
}

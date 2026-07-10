package net.likelion.bebc25.spring.componentscan;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect // 횡단 관심사 클래스 정의
@Component
public class LoggingAspect {
    // *Car로 하면 Car가 들어간 클래스의 메소드마다 횡단관심사 작동
    // *Driver로 하면 Driver 클래스의 메소드기준 횡단관심사 작동
    @Pointcut("execution(* net.likelion.bebc25.spring.componentscan.Driver.*(..))")
    private void springaopPackageMethods(){}

    // 리턴타입(*:어떤거든 다) Driver 클래스의 모든 메소드(*)에서 매개변수(..)있든없든 아무거나 상관없음
    @Before("execution(* net.likelion.bebc25.spring.componentscan.Driver.*(..))")
    public void logBefore(JoinPoint joinPoint){ // 메소드 수행 전에 로그 메세지 출력
        System.out.println("[AOP 로그 BEFORE] 메소드 실행 전에 처리할 코드를 작성합니다.");
//        System.out.println(Arrays.toString(joinPoint.getArgs()));
        Object[] args = joinPoint.getArgs();
        System.out.println(Arrays.toString(args));
    }
    @After("springaopPackageMethods()")
    public void logAfter(){ // 메소드 수행 후에 로그 메세지 출력
        System.out.println("[AOP 로그 AFTER] 메소드 실행 후에 처리할 코드를 작성합니다.");
    }
    @Around("springaopPackageMethods()")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable{ // 메소드 수행 전/후에 로그 메세지 출력
        System.out.println("[AOP 로그 AROUND] 메소드 실행 전에 처리할 코드를 작성합니다.");
        joinPoint.proceed(); // 대상 메소드를 호출한다.
        System.out.println("[AOP 로그 AROUND] 메소드 실행 후에 처리할 코드를 작성합니다.");
    }
}

package net.likelion.bebc25.spring.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// 임시로 파일을 만들어서 파일에 로그를 출력(간단하게) - 실제로는 try catch써서 복잡하게 해야함
//@Component
public class TempFileSupport {
    @Value("resources/temp.log")
    private String filePath; // 파일 경로

    public TempFileSupport(){
        System.out.println("TempFileSupport 생성자 호출됨." + filePath);
    }

    // 1. 어노테이션 지정 방식
    // 네트워크, 파일시스템 연결 같은 무거운 작업
    // 비즈니스 메소드 호출 전에 실행해야 할 작업
    @PostConstruct // 초기화 콜백
    public void init(){
        System.out.println(filePath + " 경로의 FileOutputStream 생성");
    }

    public void writeLog(String msg){
        System.out.println(filePath + "에 로그 저장 : " + msg);
    }

    // Bean이 소멸되기 직전에 호출되며, 리소스 해제·반환·종료 작업을 수행한다.
    @PreDestroy // 소멸 콜백
    public void close(){
        System.out.println(filePath + " 경로의 FileOutputStream 닫기");
    }
}

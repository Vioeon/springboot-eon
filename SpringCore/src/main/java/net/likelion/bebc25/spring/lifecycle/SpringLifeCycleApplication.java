package net.likelion.bebc25.spring.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringLifeCycleApplication {
    public static void main(String[] args) {
        try {
            // 컨테이너 생성
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            // Spring 컨테이너에서 TempFileSupport Bean 조회
            TempFileSupport2 support = context.getBean(TempFileSupport2.class);
            support.writeLog("사용자가 로그인 함.");

            context.close(); // 스프링 컨테이너 종료

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

-- 샘플 게시글 데이터 삽입
INSERT INTO post2 (secret, title, content, created_at, author) VALUES
    (TRUE, '첫 번째 게시글', '오늘도 자바 공부를 열심히 하고 있습니다.', CURRENT_TIMESTAMP, '하루'),
    (FALSE, '두 번째 게시글', '오늘도 스프링 공부를 열심히 하고 있습니다.', CURRENT_TIMESTAMP, '나무');
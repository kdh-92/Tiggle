-- test/resources/sql/test-data.sql
-- 실제 JPA 테이블 구조에 맞춘 데이터

-- 카테고리 데이터 먼저 삽입 (외래 키 제약 조건 만족을 위해)
-- JPA 네이밍 전략에 맞춰 컬럼명 수정 (member_id -> memberId)
INSERT INTO categories (id, name, defaults, memberId, deleted, created_at, updated_at)
VALUES (1, '테스트카테고리', 0, 11, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 기본값용 멤버 추가 (sender_id = -1 대응)
INSERT INTO members (id, email, nickname, provider, providerId, deleted, created_at, updated_at)
VALUES (-1, 'default@example.com', '기본사용자', 'system', 'system_-1', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 멤버 데이터
INSERT INTO members (id, email, nickname, provider, providerId, deleted, created_at, updated_at)
VALUES (10, 'test@example.com', '테스트유저', 'google', 'google_123', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 다른 멤버 (댓글 대상자용)
INSERT INTO members (id, email, nickname, provider, providerId, deleted, created_at, updated_at)
VALUES (11, 'receiver@example.com', '받는사람', 'kakao', 'kakao_456', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 테스트용 거래 데이터
INSERT INTO transactions (id, member_id, category_id, amount, date, content, reason, deleted, created_at, updated_at)
VALUES (27, 11, 1, 50000, '2025-08-07', '실제서비스테스트', '통합테스트용 거래입니다', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 대댓글 테스트용 부모 댓글 데이터
INSERT INTO comments (id, tx_id, parent_id, content, sender_id, receiver_id, deleted, created_at, updated_at)
VALUES (1, 27, NULL, '부모 댓글입니다', 10, 11, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
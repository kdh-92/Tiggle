-- 테스트용 멤버 데이터
INSERT INTO member (id, email, nickname, provider, provider_id, deleted, created_at, updated_at)
VALUES (10, 'test@example.com', '테스트유저', 'google', 'google_123', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 다른 멤버 (댓글 대상자용)
INSERT INTO member (id, email, nickname, provider, provider_id, deleted, created_at, updated_at)
VALUES (11, 'receiver@example.com', '받는사람', 'kakao', 'kakao_456', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 테스트용 거래 데이터 (member_id: 11 - 받는사람이 작성한 거래)
INSERT INTO transaction (id, member_id, category_id, amount, date, content, reason, deleted, created_at, updated_at)
VALUES (27, 11, 1, 50000, '2025-08-07', '실제서비스 테스트', '통합테스트용 거래입니다', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 대댓글 테스트용 부모 댓글 데이터
INSERT INTO comments (id, tx_id, parent_id, content, sender_id, receiver_id, deleted, created_at, updated_at)
VALUES (1, 27, NULL, '부모 댓글입니다', 10, 11, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
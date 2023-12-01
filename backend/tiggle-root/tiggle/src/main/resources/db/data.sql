INSERT INTO members (email, birth, nickname)
VALUES ('csw@email.com', '20000101', '최선우'),
       ('kdh@email.com', '20000102', '권동현'),
       ('jhr@email.com', '20000201', '정해림'),
       ('kjk@email.com', '20000202', '송진경');

INSERT INTO assets (name, `defaults`)
VALUES ('은행', 1),
       ('카드', 1),
       ('현금', 1),
       ('기타', 1);

INSERT INTO categories (name, type, `defaults`)
VALUES ('생활비', 'OUTCOME', 1),
       ('비상금', 'OUTCOME', 1),
       ('유흥비', 'OUTCOME', 1),
       ('고정지출', 'OUTCOME', 1),
       ('고정지출', 'INCOME', 1),
       ('월급', 'INCOME', 1),
       ('부수입', 'INCOME', 1),
       ('기타', 'OUTCOME', 1),
       ('기타', 'INCOME', 1);

INSERT INTO tx_tags (tx_id, member_id, tag_names)
VALUES (3, 2, '태그 이름 1,태그 이름3'),
       (6, 1, '태그 이름 1,태그 이름3');

INSERT INTO transactions (member_id, parent_id, asset_id, category_id, type, amount, date, content, reason, tag_names)
VALUES (1, null,  1, 1, 'OUTCOME', 10000, '20230101', '첫 번째 지출 제목', '첫 번째 지출 내역', null),
       (1, null, 1, 5, 'INCOME', 15000, '20230201', '월급', '첫 번째 수익 내역', null),
       (2, null, 2, 1, 'OUTCOME', 20000, '20230303', '커피', '커피 지출 내역', '태그 이름 1, 태그 이름 3'),
       (3, null, 3, 2, 'OUTCOME', 10000, '20230401', '식비', '식비 지출 내역', null),
       (4, null, 4, 3, 'OUTCOME', 22000, '20230505', '비상금', '비상금 지출 내역', null),
       (1, null, 3, 1, 'OUTCOME', 10000, '20230401', '식비', '식비 지출 내역', '태그 이름 1, 태그 이름 3'),
       (1, 1, 2, 1, 'REFUND', 5000, '20230401', '첫 환불', '첫 번째 지출 일부 환불', null);

INSERT INTO comments (tx_id, content, sender_id, receiver_id)
VALUES (1, '첫 번째 지출 내역 첫 댓글', 3, 1),
       (2, '두 번째 지출 내역 첫 댓글', 4, 2),
       (3, '세 번째 지출 내역 첫 댓글', 1, 3),
       (4, '네 번째 지출 내역 첫 댓글', 1, 4);

INSERT INTO grades (name, image_url, description)
VALUES ('lv1', 'image_url', '등급에 따른 설명');

INSERT INTO reactions (tx_id, type, sender_id, receiver_id)
VALUES (1, 'UP', 3, 1),
       (1, 'DOWN', 2, 1);

INSERT INTO tags (name, `defaults`)
VALUES ('태그 이름 1', 1),
       ('태그 이름 2', 0),
       ('태그 이름 3', 1),
       ('태그 이름 4', 0);

INSERT INTO comments (tx_id, parent_id, content, sender_id, receiver_id, deleted, created_at, updated_at,
                      deleted_at)
VALUES (1, 1, '대댓글 1', 1, 3, DEFAULT, DEFAULT, null, null);
INSERT INTO comments (tx_id, parent_id, content, sender_id, receiver_id, deleted, created_at, updated_at,
                      deleted_at)
VALUES (1, 1, '대댓글 2', 2, 3, DEFAULT, DEFAULT, null, null);

INSERT INTO comments (tx_id, parent_id, content, sender_id, receiver_id, deleted, created_at, updated_at,
                      deleted_at)
VALUES (1, 1, '대댓글 3', 4, 3, DEFAULT, DEFAULT, null, null);

INSERT INTO notifications (sender_id, receiver_id, title, content, type, created_at, viewed_at, image_url)
VALUES (1, 3, "test", "test", "ETC", "2023-11-08 23:03:22", "2023-11-08 23:03:22", "test");

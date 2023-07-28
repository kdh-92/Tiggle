INSERT INTO members (email, birth, nickname)
VALUES ('csw@email.com', '20000101', '최선우'),
       ('kdh@email.com', '20000102', '권동현'),
       ('jhr@email.com', '20000201', '정해림'),
       ('kjk@email.com', '20000202', '송진경');

INSERT INTO transactions (member_id, parent_id, type, amount, date, content, reason)
VALUES (1, null, 'OUTCOME', 10000, '20230101', '첫 번째 지출 제목', '첫 번째 지출 내역'),
       (1, null, 'INCOME', 15000, '20230201', '월급', '첫 번째 수익 내역'),
       (2, null, 'OUTCOME', 20000, '20230303', '커피', '커피 지출 내역'),
       (3, null, 'OUTCOME', 10000, '20230401', '식비', '식비 지출 내역'),
       (4, null, 'OUTCOME', 22000, '20230505', '비상금', '비상금 지출 내역'),
       (1, null, 'OUTCOME', 10000, '20230401', '식비', '식비 지출 내역'),
       (1, 1, 'REFUND', 5000, '20230401', '첫 환불', '첫 번째 지출 일부 환불');

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

INSERT INTO tags (name, `default`)
VALUES ('태그 이름 1', 1),
       ('태그 이름 2', 0),
       ('태그 이름 3', 1),
       ('태그 이름 4', 0);

INSERT INTO tx_tags (tx_id, member_id, tag_name)
VALUES (1, 1, '태그 이름 1,태그 이름3');

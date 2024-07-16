create table if not exists assets
(
    id         bigint auto_increment
    primary key,
    name       varchar(100)                        not null,
    defaults   tinyint   default 0                 null,
    deleted    tinyint   default 0                 null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp                           null,
    deleted_at timestamp                           null
    );

create table if not exists categories
(
    id         bigint auto_increment
    primary key,
    name       varchar(100)                        not null,
    type       enum ('INCOME', 'OUTCOME')          not null,
    defaults   tinyint   default 0                 null,
    deleted    tinyint   default 0                 null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp                           null,
    deleted_at timestamp                           null
    );

create table if not exists grades
(
    id          bigint auto_increment
    primary key,
    name        varchar(30)  not null,
    image_url   varchar(255) not null,
    description varchar(100) not null
    );

create table if not exists members
(
    id          bigint auto_increment
    primary key,
    email       varchar(100)                         not null,
    profile_url varchar(255)                         null,
    birth       date                                 null,
    nickname    varchar(30)                          not null,
    provider    varchar(255)                         null,
    provider_id varchar(255)                         null,
    deleted     tinyint(1) default 0                 null,
    created_at  timestamp  default CURRENT_TIMESTAMP null,
    updated_at  timestamp                            null,
    deleted_at  timestamp                            null
    );

create table if not exists notifications
(
    id          bigint auto_increment
    primary key,
    sender_id   bigint                        null,
    receiver_id bigint                        not null,
    title       varchar(255)                  not null,
    content     varchar(255)                  null,
    type        enum ('TX', 'COMMENT', 'ETC') not null,
    created_at  timestamp                     not null,
    viewed_at   timestamp                     null,
    image_url   varchar(255)                  null,
    constraint notifications_ibfk_1
    foreign key (sender_id) references members (id),
    constraint notifications_ibfk_2
    foreign key (receiver_id) references members (id)
    );

create index idx_notifications_receiver_id
    on notifications (receiver_id);

create index idx_notifications_sender_id
    on notifications (sender_id);

create table if not exists tags
(
    id       bigint auto_increment
    primary key,
    name     varchar(30) not null,
    defaults tinyint     not null
    );

create table if not exists transactions
(
    id          bigint auto_increment
    primary key,
    member_id   bigint                               not null,
    parent_id   bigint                               null,
    asset_id    bigint                               not null,
    category_id bigint                               not null,
    type        enum ('INCOME', 'OUTCOME', 'REFUND') not null,
    image_url   varchar(255)                         null,
    amount      int                                  not null,
    date        date                                 not null,
    content     varchar(255)                         not null,
    reason      varchar(255)                         not null,
    tag_names   varchar(100)                         null,
    deleted     tinyint   default 0                  null,
    created_at  timestamp default CURRENT_TIMESTAMP  null,
    updated_at  timestamp                            null,
    deleted_at  timestamp                            null,
    constraint transactions_ibfk_1
    foreign key (member_id) references members (id),
    constraint transactions_ibfk_2
    foreign key (asset_id) references assets (id),
    constraint transactions_ibfk_3
    foreign key (category_id) references categories (id)
    );

create table if not exists comments
(
    id          bigint auto_increment
    primary key,
    tx_id       bigint                               not null,
    parent_id   bigint                               null,
    content     varchar(255)                         not null,
    sender_id   bigint                               not null,
    receiver_id bigint                               not null,
    deleted     tinyint(1) default 0                 null,
    created_at  timestamp  default CURRENT_TIMESTAMP null,
    updated_at  timestamp                            null,
    deleted_at  timestamp                            null,
    constraint comments_ibfk_1
    foreign key (tx_id) references transactions (id),
    constraint comments_ibfk_2
    foreign key (sender_id) references members (id),
    constraint comments_ibfk_3
    foreign key (receiver_id) references members (id)
    );

create index idx_comments_receiver_id
    on comments (receiver_id);

create index idx_comments_sender_id
    on comments (sender_id);

create index idx_comments_tx_id
    on comments (tx_id);

create table if not exists reactions
(
    id          bigint auto_increment
    primary key,
    tx_id       bigint                              not null,
    type        enum ('UP', 'DOWN')                 not null,
    sender_id   bigint                              not null,
    receiver_id bigint                              not null,
    created_at  timestamp default CURRENT_TIMESTAMP null,
    constraint reactions_ibfk_1
    foreign key (tx_id) references transactions (id),
    constraint reactions_ibfk_2
    foreign key (sender_id) references members (id),
    constraint reactions_ibfk_3
    foreign key (receiver_id) references members (id)
    );

create index idx_reactions_receiver_id
    on reactions (receiver_id);

create index idx_reactions_sender_id
    on reactions (sender_id);

create index idx_reactions_tx_id
    on reactions (tx_id);

create index idx_transactions_asset_id
    on transactions (asset_id);

create index idx_transactions_category_id
    on transactions (category_id);

create index idx_transactions_member_id
    on transactions (member_id);

create table if not exists tx_tags
(
    id         bigint auto_increment
    primary key,
    tx_id      bigint                              not null,
    member_id  bigint                              not null,
    tag_names  varchar(100)                        not null,
    deleted    tinyint   default 0                 null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp                           null,
    deleted_at timestamp                           null
    );


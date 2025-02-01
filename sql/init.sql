-- DDL

--- 회원
create table member
(
    id uuid default gen_random_uuid() constraint member_pk primary key,
    email          varchar(255) constraint email_uk unique not null,
    realname       varchar(10)                 not null,
    nickname       varchar(50)                 not null,
    profile_image  varchar(255),
    created_at     timestamp with time zone    not null,
    updated_at     timestamp with time zone    not null
);
comment on table member is '회원';
comment on column member.id is '회원 ID';
comment on column member.email is '회원 이메일';
comment on column member.realname is '회원 실명';
comment on column member.nickname is '회원 닉네임';
comment on column member.profile_image is '회원 프로필 이미지 경로';

create table member_password
(
    member_id uuid constraint member_password_pk primary key,
    password varchar(128) not null,
    created_at     timestamp with time zone    not null,
    updated_at     timestamp with time zone    not null
);
comment on table member_password is '회원_비밀번호';
comment on column member_password.member_id is '회원 ID';
comment on column member_password.password is '회원 비밀번호';

-- product
create table product
(
    id         uuid default gen_random_uuid() not null
        constraint product_pk
            primary key,
    title      varchar(50),
    content    text,
    created_at timestamp                      not null,
    created_by uuid                           not null,
    updated_at timestamp                      not null,
    updated_by uuid                           not null
);

comment on table product is '작품';
comment on column product.id is '작품 ID';
comment on column product.title is '제목';
comment on column product.content is '내용';
comment on column product.created_at is '생성일시';
comment on column product.created_by is '생성자 ID';
comment on column product.updated_at is '수정일시';
comment on column product.updated_by is '수정자 ID';

alter table product
    owner to postgres;

--product_character
create table product_character
(
    id             uuid default gen_random_uuid() not null
        constraint product_character_pk
            primary key,
    product_id     uuid                           not null,
    intro          text,
    name           varchar(50),
    age            integer,
    gender         varchar(10),
    occupation     text,
    appearance     text,
    personality    text,
    characteristic text,
    relationship   text,
    created_at     timestamp                      not null,
    created_by     uuid                           not null,
    updated_at     timestamp                      not null,
    updated_by     uuid                           not null
);

comment on table product_character is '작품 등장인물';
comment on column product_character.id is '등장인물 ID';
comment on column product_character.product_id is '작품 ID';
comment on column product_character.intro is '소개';
comment on column product_character.name is '이름';
comment on column product_character.age is '나이';
comment on column product_character.gender is '성별';
comment on column product_character.occupation is '직업';
comment on column product_character.appearance is '외모';
comment on column product_character.personality is '성격';
comment on column product_character.characteristic is '특징';
comment on column product_character.relationship is '주요 관계';
comment on column product_character.created_at is '생성일시';
comment on column product_character.created_by is '생성자 ID';
comment on column product_character.updated_at is '수정일시';
comment on column product_character.updated_by is '수정자 ID';

alter table product_character
    owner to postgres;

--product_custom_field
create table product_custom_field
(
    id           uuid default gen_random_uuid() not null
        constraint product_custom_field_pk
            primary key,
    product_id   uuid                           not null,
    section_code varchar(20)                    not null,
    name         varchar(30)                    not null,
    content      text                           not null,
    seq          smallint                       not null,
    created_at   timestamp                      not null,
    created_by   uuid                           not null,
    updated_at   timestamp                      not null,
    updated_by   uuid                           not null
);

comment on table product_custom_field is '작품 커스텀 필드';
comment on column product_custom_field.id is '커스텀 필드 ID';
comment on column product_custom_field.product_id is '작품 ID';
comment on column product_custom_field.section_code is '섹션 코드';
comment on column product_custom_field.name is '이름';
comment on column product_custom_field.content is '내용';
comment on column product_custom_field.seq is '순서';
comment on column product_custom_field.created_at is '생성일시';
comment on column product_custom_field.created_by is '생성자 ID';
comment on column product_custom_field.updated_at is '수정일시';
comment on column product_custom_field.updated_by is '수정자 ID';

alter table product_custom_field
    owner to postgres;

--product_ideanote
create table product_ideanote
(
    id         uuid         not null
        constraint product_ideanote_pk
            primary key,
    title      varchar(100) not null,
    content    text         not null,
    created_at timestamp    not null,
    created_by uuid         not null,
    updated_at timestamp    not null,
    updated_by uuid         not null
);

comment on table product_ideanote is '작품 아이디어 노트';
comment on column product_ideanote.id is '아이디어 노트 ID';
comment on column product_ideanote.title is '제목';
comment on column product_ideanote.content is '내용';
comment on column product_ideanote.created_at is '생성일시';
comment on column product_ideanote.created_by is '생성자 ID';
comment on column product_ideanote.updated_at is '수정일시';
comment on column product_ideanote.updated_by is '수정자 ID';

alter table product_ideanote
    owner to postgres;

-- product_memo
create table product_memo
(
    id         uuid default gen_random_uuid() not null
        constraint product_memo_pk
            primary key,
    product_id uuid                           not null,
    content    text                           not null,
    created_at timestamp                      not null,
    created_by uuid                           not null,
    updated_at timestamp                      not null,
    updated_by uuid                           not null
);

comment on table product_memo is '작품 메모';
comment on column product_memo.id is '메모 ID';
comment on column product_memo.product_id is '작품 ID';
comment on column product_memo.content is '내용';
comment on column product_memo.created_at is '생성일시';
comment on column product_memo.created_by is '생성자 ID';
comment on column product_memo.updated_at is '수정일시';
comment on column product_memo.updated_by is '수정일시';

alter table product_memo
    owner to postgres;

--product_plot
create table product_plot
(
    id           uuid      not null
        constraint product_plot_pk
            primary key,
    exposition   text,
    complication text,
    climax       text,
    resolution   text,
    created_at   timestamp not null,
    created_by   uuid      not null,
    updated_at   timestamp not null,
    updated_by   uuid      not null
);

comment on table product_plot is '작품 줄거리';
comment on column product_plot.id is '줄거리 ID';
comment on column product_plot.exposition is '발단';
comment on column product_plot.complication is '전개';
comment on column product_plot.climax is '위기';
comment on column product_plot.climax is '결말';
comment on column product_plot.created_at is '생성일시';
comment on column product_plot.created_by is '생성자 ID';
comment on column product_plot.updated_at is '수정일시';
comment on column product_plot.updated_by is '수정자 ID';

alter table product_plot
    owner to postgres;

-- product_synopsis
create table product_synopsis
(
    id         uuid        not null
        constraint product_synopsis_pk
            primary key,
    genre      varchar(30) not null,
    length     varchar(100),
    purpose    text,
    logline    text        not null,
    example    text,
    created_at timestamp   not null,
    created_by uuid        not null,
    updated_at timestamp   not null,
    updated_by uuid        not null
);

comment on table product_synopsis is '작품 시놉시스';
comment on column product_synopsis.id is '시놉시스 ID';
comment on column product_synopsis.genre is '장르';
comment on column product_synopsis.length is '분량';
comment on column product_synopsis.purpose is '기획 의도';
comment on column product_synopsis.logline is '로그라인';
comment on column product_synopsis.example is '예시 문장';
comment on column product_synopsis.created_at is '생성일시';
comment on column product_synopsis.created_by is '생성자 ID';
comment on column product_synopsis.updated_at is '수정일시';
comment on column product_synopsis.updated_by is '수정자 ID';

alter table product_synopsis
    owner to postgres;

--product_worldview
create table product_worldview
(
    id         uuid      not null
        constraint product_worldview_pk
            primary key,
    geography  text,
    history    text,
    politics   text,
    society    text,
    religion   text,
    economy    text,
    technology text,
    lifestyle  text,
    language   text,
    culture    text,
    species    text,
    occupation text,
    conflict   text,
    created_at timestamp not null,
    created_by uuid      not null,
    updated_at timestamp not null,
    updated_by uuid      not null
);

comment on table product_worldview is '작품 세계관';
comment on column product_worldview.id is '세계관 ID';
comment on column product_worldview.geography is '지리';
comment on column product_worldview.history is '역사';
comment on column product_worldview.politics is '정치';
comment on column product_worldview.society is '사회';
comment on column product_worldview.religion is '종교';
comment on column product_worldview.economy is '경제';
comment on column product_worldview.technology is '기술';
comment on column product_worldview.lifestyle is '생활';
comment on column product_worldview.language is '언어';
comment on column product_worldview.culture is '문화';
comment on column product_worldview.species is '종족';
comment on column product_worldview.occupation is '직업';
comment on column product_worldview.conflict is '갈등 관계';
comment on column product_worldview.created_at is '생성일시';
comment on column product_worldview.created_by is '생성자 ID';
comment on column product_worldview.updated_at is '수정일시';
comment on column product_worldview.updated_by is '수정자 ID';

alter table product_worldview
    owner to postgres;

-- auto_modify_message
create table auto_modify_message
(
    id         uuid default gen_random_uuid() not null
        constraint auto_modify_message_pk
            primary key,
    product_id uuid                           not null,
    role       varchar(10)                    not null,
    content    text,
    created_at timestamp                      not null,
    created_by uuid                           not null,
    updated_at timestamp                      not null,
    updated_by uuid                           not null
);

comment on table auto_modify_message is '자동 수정 메세지';
comment on column auto_modify_message.id is '자동 수정 메세지 ID';
comment on column auto_modify_message.product_id is '작품 ID';
comment on column auto_modify_message.role is '메세지 송신자';
comment on column auto_modify_message.content is '내용';
comment on column auto_modify_message.created_at is '생성일시';
comment on column auto_modify_message.created_by is '생성자 ID';
comment on column auto_modify_message.updated_at is '수정일시';
comment on column auto_modify_message.updated_by is '수정자 ID';

alter table auto_modify_message
    owner to postgres;

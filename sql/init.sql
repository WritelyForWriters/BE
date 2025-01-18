-- DDL

-- product
create table product
(
    id         uuid default gen_random_uuid() not null
        constraint product_pk
            primary key,
    content    text,
    created_at timestamp with time zone       not null,
    created_by uuid                           not null,
    updated_at timestamp with time zone       not null,
    updated_by uuid                           not null
);

comment on table product is '작품';
comment on column product.id is '작품 ID';
comment on column product.content is '내용';
comment on column product.created_at is '생성일시';
comment on column product.created_by is '생성자 ID';
comment on column product.updated_at is '수정일시';
comment on column product.updated_by is '수정자 ID';

alter table product
    owner to postgres;

-- product_memo
create table product_memo
(
    id         uuid default gen_random_uuid() not null
        constraint product_memo_pk
            primary key,
    product_id uuid                           not null,
    content    text                           not null,
    created_at timestamp with time zone       not null,
    created_by uuid                           not null,
    updated_at timestamp with time zone       not null,
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

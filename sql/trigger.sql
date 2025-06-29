-- create function 'insert_product_history()'
create or replace function insert_product_history()
returns trigger as
$$
begin
    insert into product_history (
        product_id,
        title,
        content,
        created_at,
        created_by,
        updated_at,
        updated_by
    ) values (
        new.id,
        new.title,
        new.content,
        new.created_at,
        new.created_by,
        new.updated_at,
        new.updated_by
    );
    return new;
end;
$$ language plpgsql;

-- create trigger 'trg_product_history'
create trigger trg_product_history
after insert or update on product
for each row
execute function insert_product_history();
/*
 * This file is generated by jOOQ.
 */
package writely.tables;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import writely.Keys;
import writely.Public;
import writely.tables.records.ProductMemoRecord;


/**
 * 작품 메모
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class ProductMemo extends TableImpl<ProductMemoRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.product_memo</code>
     */
    public static final ProductMemo PRODUCT_MEMO = new ProductMemo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductMemoRecord> getRecordType() {
        return ProductMemoRecord.class;
    }

    /**
     * The column <code>public.product_memo.id</code>. 메모 ID
     */
    public final TableField<ProductMemoRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "메모 ID");

    /**
     * The column <code>public.product_memo.product_id</code>. 작품 ID
     */
    public final TableField<ProductMemoRecord, UUID> PRODUCT_ID = createField(DSL.name("product_id"), SQLDataType.UUID.nullable(false), this, "작품 ID");

    /**
     * The column <code>public.product_memo.content</code>. 내용
     */
    public final TableField<ProductMemoRecord, String> CONTENT = createField(DSL.name("content"), SQLDataType.CLOB.nullable(false), this, "내용");

    /**
     * The column <code>public.product_memo.created_at</code>. 생성일시
     */
    public final TableField<ProductMemoRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "생성일시");

    /**
     * The column <code>public.product_memo.created_by</code>. 생성자 ID
     */
    public final TableField<ProductMemoRecord, UUID> CREATED_BY = createField(DSL.name("created_by"), SQLDataType.UUID.nullable(false), this, "생성자 ID");

    /**
     * The column <code>public.product_memo.updated_at</code>. 수정일시
     */
    public final TableField<ProductMemoRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "수정일시");

    /**
     * The column <code>public.product_memo.updated_by</code>. 수정일시
     */
    public final TableField<ProductMemoRecord, UUID> UPDATED_BY = createField(DSL.name("updated_by"), SQLDataType.UUID.nullable(false), this, "수정일시");

    private ProductMemo(Name alias, Table<ProductMemoRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private ProductMemo(Name alias, Table<ProductMemoRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment("작품 메모"), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.product_memo</code> table reference
     */
    public ProductMemo(String alias) {
        this(DSL.name(alias), PRODUCT_MEMO);
    }

    /**
     * Create an aliased <code>public.product_memo</code> table reference
     */
    public ProductMemo(Name alias) {
        this(alias, PRODUCT_MEMO);
    }

    /**
     * Create a <code>public.product_memo</code> table reference
     */
    public ProductMemo() {
        this(DSL.name("product_memo"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ProductMemoRecord> getPrimaryKey() {
        return Keys.PRODUCT_MEMO_PK;
    }

    @Override
    public ProductMemo as(String alias) {
        return new ProductMemo(DSL.name(alias), this);
    }

    @Override
    public ProductMemo as(Name alias) {
        return new ProductMemo(alias, this);
    }

    @Override
    public ProductMemo as(Table<?> alias) {
        return new ProductMemo(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductMemo rename(String name) {
        return new ProductMemo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductMemo rename(Name name) {
        return new ProductMemo(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductMemo rename(Table<?> name) {
        return new ProductMemo(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductMemo where(Condition condition) {
        return new ProductMemo(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductMemo where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductMemo where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductMemo where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductMemo where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductMemo where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductMemo where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductMemo where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductMemo whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductMemo whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}

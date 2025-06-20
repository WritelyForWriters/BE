/*
 * This file is generated by jOOQ.
 */
package writeon.tables;


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

import writeon.Keys;
import writeon.Public;
import writeon.tables.records.ProductPlotRecord;


/**
 * 작품 줄거리
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class ProductPlot extends TableImpl<ProductPlotRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.product_plot</code>
     */
    public static final ProductPlot PRODUCT_PLOT = new ProductPlot();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProductPlotRecord> getRecordType() {
        return ProductPlotRecord.class;
    }

    /**
     * The column <code>public.product_plot.id</code>. 줄거리 ID
     */
    public final TableField<ProductPlotRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "줄거리 ID");

    /**
     * The column <code>public.product_plot.content</code>. 내용
     */
    public final TableField<ProductPlotRecord, String> CONTENT = createField(DSL.name("content"), SQLDataType.CLOB, this, "내용");

    /**
     * The column <code>public.product_plot.created_at</code>. 생성일시
     */
    public final TableField<ProductPlotRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "생성일시");

    /**
     * The column <code>public.product_plot.created_by</code>. 생성자 ID
     */
    public final TableField<ProductPlotRecord, UUID> CREATED_BY = createField(DSL.name("created_by"), SQLDataType.UUID.nullable(false), this, "생성자 ID");

    /**
     * The column <code>public.product_plot.updated_at</code>. 수정일시
     */
    public final TableField<ProductPlotRecord, LocalDateTime> UPDATED_AT = createField(DSL.name("updated_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "수정일시");

    /**
     * The column <code>public.product_plot.updated_by</code>. 수정자 ID
     */
    public final TableField<ProductPlotRecord, UUID> UPDATED_BY = createField(DSL.name("updated_by"), SQLDataType.UUID.nullable(false), this, "수정자 ID");

    private ProductPlot(Name alias, Table<ProductPlotRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private ProductPlot(Name alias, Table<ProductPlotRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment("작품 줄거리"), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.product_plot</code> table reference
     */
    public ProductPlot(String alias) {
        this(DSL.name(alias), PRODUCT_PLOT);
    }

    /**
     * Create an aliased <code>public.product_plot</code> table reference
     */
    public ProductPlot(Name alias) {
        this(alias, PRODUCT_PLOT);
    }

    /**
     * Create a <code>public.product_plot</code> table reference
     */
    public ProductPlot() {
        this(DSL.name("product_plot"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<ProductPlotRecord> getPrimaryKey() {
        return Keys.PRODUCT_PLOT_PK;
    }

    @Override
    public ProductPlot as(String alias) {
        return new ProductPlot(DSL.name(alias), this);
    }

    @Override
    public ProductPlot as(Name alias) {
        return new ProductPlot(alias, this);
    }

    @Override
    public ProductPlot as(Table<?> alias) {
        return new ProductPlot(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductPlot rename(String name) {
        return new ProductPlot(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductPlot rename(Name name) {
        return new ProductPlot(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProductPlot rename(Table<?> name) {
        return new ProductPlot(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductPlot where(Condition condition) {
        return new ProductPlot(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductPlot where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductPlot where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductPlot where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductPlot where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductPlot where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductPlot where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProductPlot where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductPlot whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProductPlot whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}

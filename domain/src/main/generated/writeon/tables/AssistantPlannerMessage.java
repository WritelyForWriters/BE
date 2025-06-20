/*
 * This file is generated by jOOQ.
 */
package writeon.tables;


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
import writeon.tables.records.AssistantPlannerMessageRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class AssistantPlannerMessage extends TableImpl<AssistantPlannerMessageRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.assistant_planner_message</code>
     */
    public static final AssistantPlannerMessage ASSISTANT_PLANNER_MESSAGE = new AssistantPlannerMessage();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AssistantPlannerMessageRecord> getRecordType() {
        return AssistantPlannerMessageRecord.class;
    }

    /**
     * The column <code>public.assistant_planner_message.assistant_id</code>.
     */
    public final TableField<AssistantPlannerMessageRecord, UUID> ASSISTANT_ID = createField(DSL.name("assistant_id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.assistant_planner_message.genre</code>.
     */
    public final TableField<AssistantPlannerMessageRecord, String> GENRE = createField(DSL.name("genre"), SQLDataType.VARCHAR(30).nullable(false), this, "");

    /**
     * The column <code>public.assistant_planner_message.logline</code>.
     */
    public final TableField<AssistantPlannerMessageRecord, String> LOGLINE = createField(DSL.name("logline"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.assistant_planner_message.section</code>.
     */
    public final TableField<AssistantPlannerMessageRecord, String> SECTION = createField(DSL.name("section"), SQLDataType.VARCHAR(30).nullable(false), this, "");

    private AssistantPlannerMessage(Name alias, Table<AssistantPlannerMessageRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private AssistantPlannerMessage(Name alias, Table<AssistantPlannerMessageRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.assistant_planner_message</code> table
     * reference
     */
    public AssistantPlannerMessage(String alias) {
        this(DSL.name(alias), ASSISTANT_PLANNER_MESSAGE);
    }

    /**
     * Create an aliased <code>public.assistant_planner_message</code> table
     * reference
     */
    public AssistantPlannerMessage(Name alias) {
        this(alias, ASSISTANT_PLANNER_MESSAGE);
    }

    /**
     * Create a <code>public.assistant_planner_message</code> table reference
     */
    public AssistantPlannerMessage() {
        this(DSL.name("assistant_planner_message"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<AssistantPlannerMessageRecord> getPrimaryKey() {
        return Keys.ASSISTANT_PLANNER_MESSAGE_PK;
    }

    @Override
    public AssistantPlannerMessage as(String alias) {
        return new AssistantPlannerMessage(DSL.name(alias), this);
    }

    @Override
    public AssistantPlannerMessage as(Name alias) {
        return new AssistantPlannerMessage(alias, this);
    }

    @Override
    public AssistantPlannerMessage as(Table<?> alias) {
        return new AssistantPlannerMessage(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public AssistantPlannerMessage rename(String name) {
        return new AssistantPlannerMessage(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AssistantPlannerMessage rename(Name name) {
        return new AssistantPlannerMessage(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public AssistantPlannerMessage rename(Table<?> name) {
        return new AssistantPlannerMessage(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public AssistantPlannerMessage where(Condition condition) {
        return new AssistantPlannerMessage(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public AssistantPlannerMessage where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public AssistantPlannerMessage where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public AssistantPlannerMessage where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public AssistantPlannerMessage where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public AssistantPlannerMessage where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public AssistantPlannerMessage where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public AssistantPlannerMessage where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public AssistantPlannerMessage whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public AssistantPlannerMessage whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}

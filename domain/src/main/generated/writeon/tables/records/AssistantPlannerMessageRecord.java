/*
 * This file is generated by jOOQ.
 */
package writeon.tables.records;


import java.util.UUID;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

import writeon.tables.AssistantPlannerMessage;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class AssistantPlannerMessageRecord extends UpdatableRecordImpl<AssistantPlannerMessageRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.assistant_planner_message.assistant_id</code>.
     */
    public AssistantPlannerMessageRecord setAssistantId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.assistant_planner_message.assistant_id</code>.
     */
    public UUID getAssistantId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.assistant_planner_message.genre</code>.
     */
    public AssistantPlannerMessageRecord setGenre(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.assistant_planner_message.genre</code>.
     */
    public String getGenre() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.assistant_planner_message.logline</code>.
     */
    public AssistantPlannerMessageRecord setLogline(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.assistant_planner_message.logline</code>.
     */
    public String getLogline() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.assistant_planner_message.section</code>.
     */
    public AssistantPlannerMessageRecord setSection(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.assistant_planner_message.section</code>.
     */
    public String getSection() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AssistantPlannerMessageRecord
     */
    public AssistantPlannerMessageRecord() {
        super(AssistantPlannerMessage.ASSISTANT_PLANNER_MESSAGE);
    }

    /**
     * Create a detached, initialised AssistantPlannerMessageRecord
     */
    public AssistantPlannerMessageRecord(UUID assistantId, String genre, String logline, String section) {
        super(AssistantPlannerMessage.ASSISTANT_PLANNER_MESSAGE);

        setAssistantId(assistantId);
        setGenre(genre);
        setLogline(logline);
        setSection(section);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised AssistantPlannerMessageRecord
     */
    public AssistantPlannerMessageRecord(writeon.tables.pojos.AssistantPlannerMessage value) {
        super(AssistantPlannerMessage.ASSISTANT_PLANNER_MESSAGE);

        if (value != null) {
            setAssistantId(value.getAssistantId());
            setGenre(value.getGenre());
            setLogline(value.getLogline());
            setSection(value.getSection());
            resetChangedOnNotNull();
        }
    }
}

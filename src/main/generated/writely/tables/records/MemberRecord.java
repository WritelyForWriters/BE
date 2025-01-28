/*
 * This file is generated by jOOQ.
 */
package writely.tables.records;


import java.time.OffsetDateTime;
import java.util.UUID;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

import writely.tables.Member;


/**
 * 회원
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class MemberRecord extends UpdatableRecordImpl<MemberRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.member.id</code>. 회원 ID
     */
    public MemberRecord setId(UUID value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.member.id</code>. 회원 ID
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.member.email</code>. 회원 이메일
     */
    public MemberRecord setEmail(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.member.email</code>. 회원 이메일
     */
    public String getEmail() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.member.nickname</code>. 회원 닉네임
     */
    public MemberRecord setNickname(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.member.nickname</code>. 회원 닉네임
     */
    public String getNickname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.member.profile_image</code>. 회원 프로필 이미지 경로
     */
    public MemberRecord setProfileImage(String value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.member.profile_image</code>. 회원 프로필 이미지 경로
     */
    public String getProfileImage() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.member.created_at</code>.
     */
    public MemberRecord setCreatedAt(OffsetDateTime value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.member.created_at</code>.
     */
    public OffsetDateTime getCreatedAt() {
        return (OffsetDateTime) get(4);
    }

    /**
     * Setter for <code>public.member.updated_at</code>.
     */
    public MemberRecord setUpdatedAt(OffsetDateTime value) {
        set(5, value);
        return this;
    }

    /**
     * Getter for <code>public.member.updated_at</code>.
     */
    public OffsetDateTime getUpdatedAt() {
        return (OffsetDateTime) get(5);
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
     * Create a detached MemberRecord
     */
    public MemberRecord() {
        super(Member.MEMBER);
    }

    /**
     * Create a detached, initialised MemberRecord
     */
    public MemberRecord(UUID id, String email, String nickname, String profileImage, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        super(Member.MEMBER);

        setId(id);
        setEmail(email);
        setNickname(nickname);
        setProfileImage(profileImage);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised MemberRecord
     */
    public MemberRecord(writely.tables.pojos.Member value) {
        super(Member.MEMBER);

        if (value != null) {
            setId(value.getId());
            setEmail(value.getEmail());
            setNickname(value.getNickname());
            setProfileImage(value.getProfileImage());
            setCreatedAt(value.getCreatedAt());
            setUpdatedAt(value.getUpdatedAt());
            resetChangedOnNotNull();
        }
    }
}

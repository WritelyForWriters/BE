/*
 * This file is generated by jOOQ.
 */
package writely.tables.pojos;


import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;


/**
 * 회원
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final String email;
    private final String nickname;
    private final String profileImage;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    public Member(Member value) {
        this.id = value.id;
        this.email = value.email;
        this.nickname = value.nickname;
        this.profileImage = value.profileImage;
        this.createdAt = value.createdAt;
        this.updatedAt = value.updatedAt;
    }

    public Member(
        UUID id,
        String email,
        String nickname,
        String profileImage,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
    ) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Getter for <code>public.member.id</code>. 회원 ID
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Getter for <code>public.member.email</code>. 회원 이메일
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Getter for <code>public.member.nickname</code>. 회원 닉네임
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Getter for <code>public.member.profile_image</code>. 회원 프로필 이미지 경로
     */
    public String getProfileImage() {
        return this.profileImage;
    }

    /**
     * Getter for <code>public.member.created_at</code>.
     */
    public OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Getter for <code>public.member.updated_at</code>.
     */
    public OffsetDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Member other = (Member) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.email == null) {
            if (other.email != null)
                return false;
        }
        else if (!this.email.equals(other.email))
            return false;
        if (this.nickname == null) {
            if (other.nickname != null)
                return false;
        }
        else if (!this.nickname.equals(other.nickname))
            return false;
        if (this.profileImage == null) {
            if (other.profileImage != null)
                return false;
        }
        else if (!this.profileImage.equals(other.profileImage))
            return false;
        if (this.createdAt == null) {
            if (other.createdAt != null)
                return false;
        }
        else if (!this.createdAt.equals(other.createdAt))
            return false;
        if (this.updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        }
        else if (!this.updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = prime * result + ((this.nickname == null) ? 0 : this.nickname.hashCode());
        result = prime * result + ((this.profileImage == null) ? 0 : this.profileImage.hashCode());
        result = prime * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Member (");

        sb.append(id);
        sb.append(", ").append(email);
        sb.append(", ").append(nickname);
        sb.append(", ").append(profileImage);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(updatedAt);

        sb.append(")");
        return sb.toString();
    }
}

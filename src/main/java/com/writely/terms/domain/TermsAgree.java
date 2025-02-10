package com.writely.terms.domain;

import com.writely.common.domain.BaseTimeEntity;
import com.writely.terms.domain.enums.TermsCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "terms_agree")
@IdClass(TermsAgreeId.class)
public class TermsAgree extends BaseTimeEntity {

    @Id
    @Column(name = "terms_cd", nullable = false)
    private TermsCode termsCd;

    @Id
    @Column(name = "member_id", nullable = false)
    private UUID memberId;

}

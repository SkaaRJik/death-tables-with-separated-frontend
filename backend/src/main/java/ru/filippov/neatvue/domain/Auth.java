package ru.filippov.neatvue.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "auth")
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Auth extends BaseEntity {


    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fk_user_id")
    private User user;


    @Column(name = "refresh_token")
    private String refreshToken;

    @Column
    @NotBlank
    private String ip;

    @Column
    @NotBlank
    private String os;

    @Column
    @NotBlank
    private String browser;





}

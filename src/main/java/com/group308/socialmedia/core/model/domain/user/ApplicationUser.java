package com.group308.socialmedia.core.model.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group308.socialmedia.core.model.domain.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@Data
@Entity
@Table(name = "APP_USER")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GenericGenerator(name = "TABLE_SEQUENCE", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "none"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM
                        , value = "app_user_seq")
        }
)
public class ApplicationUser extends AbstractEntity {

    @Column(name="user_name")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="profile_picture")
    private String profilePicture;

    @Column(name = "IS_DLTD")
    private boolean deleted;

    @Column(name = "IS_ACTV")
    private boolean active;

    @Column(name = "deactivated_until")
    private Date deactivatedUntil;

    @Column(name="last_pass_reset_date")
    @JsonIgnore
    private Date lastPasswordResetDate;

}

package com.group308.socialmedia.core.model.domain.user;

import com.group308.socialmedia.core.model.domain.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by isaozturk on 19.11.2018
 */
@Data
@Entity
@Table(name = "APP_USER_ROLE_REL")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@GenericGenerator(name = "TABLE_SEQUENCE", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "none"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM
                        , value = "seq_app_user_role_rel")
        }
)
public class ApplicationUserRoleRelation extends AbstractEntity {

    @Column(name="user_id")
    private Long userId;

    @Column(name="role_id")
    private Long roleId;

}

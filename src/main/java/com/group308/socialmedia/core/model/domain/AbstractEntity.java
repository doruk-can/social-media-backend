package com.group308.socialmedia.core.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by isaozturk on 1.09.2019
 */
@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@GenericGenerator(name = "TABLE_SEQUENCE", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @org.hibernate.annotations.Parameter(name = "optimizer", value = "none"),
                @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM
                        , value = "INV_BASE_ENTITY_SEQ")
        }
)
public abstract class AbstractEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TABLE_SEQUENCE")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "CUSER", nullable = false)
    private String createdUser;

    @Column(name = "CDATE", nullable = false)
    private Date createdDate;

    @Column(name = "UUSER")
    private String updatedUser;

    @Column(name = "UDATE")
    private Date updatedDate;

    @PrePersist
    public void onPrePersist() {
        String username="";
        if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())){
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        setCreatedUser(username);
        setCreatedDate(new Date());
    }

    @PreUpdate
    public void onPreUpdate() {

        String username="";
        if (Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())){
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        setUpdatedUser(username);
        setUpdatedDate(new Date());
    }

    @PreRemove
    public void onPreRemove() {
        setUpdatedDate(new Date());
    }
}

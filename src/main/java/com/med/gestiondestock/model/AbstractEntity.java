package com.med.gestiondestock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Data
@MappedSuperclass
// va automatiquement m-à-j les champs dans la base données lors de la création d'un objet ou modification
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;


    @CreatedDate
    @Column(nullable = false , updatable = false)
    private Instant creationDate; //depuis java 11 On peut faire eu lieu de Date Instant

    @LastModifiedDate
    @Column()
    private Instant lastModifiedDate;

    /**
     * @PrePersist
     *     void prePersist(){
     *         creationDate = Instant.now();
     *     }
     *
     *     @PreUpdate
     *     void preUpdate(){
     *         lastModifiedDate = Instant.now();
     *     }
     *
        */
    }

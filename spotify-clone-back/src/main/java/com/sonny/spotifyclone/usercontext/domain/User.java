package com.sonny.spotifyclone.usercontext.domain;

import com.sonny.spotifyclone.sharedkernel.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "spotify_user")
@Data
public class User extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_generator", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email")
    private String email;

    private Subscription subscription = Subscription.FREE;

    @Column(name = "image_url")
    private String imageUrl;
}

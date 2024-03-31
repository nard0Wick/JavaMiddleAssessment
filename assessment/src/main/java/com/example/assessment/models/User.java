package com.example.assessment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public User(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_lastName")
    private String lastName;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    /*@OneToMany(targetEntity = Meeting.class,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id",
            referencedColumnName = "user_id")
    private Set<Meeting> meetings;*/

    /*@ManyToMany(fetch = FetchType.LAZY,
                mappedBy = "userSet")
    @JsonBackReference*/
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "guests",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "meeting_id",
                    referencedColumnName = "meeting_id")
    )
    private Set<Meeting> meetingSet;
}

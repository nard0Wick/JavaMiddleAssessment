package com.example.assessment.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "meetings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {

    public Meeting(String title, Date date, String resume, String guests) {
        this.title = title;
        this.date = date;
        this.resume = resume;
        this.guests = guests;
    }

    @Id
    @Column(name = "meeting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meeting_title")
    private String title;

    @Column(name = "meeting_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @Column(name = "meeting_resume")
    private String resume;

    @Column(name = "guests")
    private String guests;

    /*@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "guests",
            joinColumns = @JoinColumn(name = "meeting_id",
                    referencedColumnName = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id")
    )*/
    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "meetingSet")
    @JsonBackReference
    Set<User> userSet;
}

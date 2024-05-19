package com.example.oro3.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="conference_room_id")
    private ConferenceRoom room;

    @ManyToMany
    @JoinTable(
            name = "conference_participants",
            joinColumns = @JoinColumn(name = "conference_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Person> participants = new ArrayList<>();

    @OneToMany(mappedBy = "conference")
    private List<Presentation> presentations = new ArrayList<>();
}

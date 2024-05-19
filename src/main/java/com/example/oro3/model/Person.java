package com.example.oro3.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    @Enumerated(EnumType.STRING)
    private PersonType type;

    @OneToMany(mappedBy = "presenter")
    private List<Presentation> presentationsPerformed = new ArrayList<>();

    @ManyToMany(mappedBy = "participants")
    private List<Conference> conferencesAttended = new ArrayList<>();
}

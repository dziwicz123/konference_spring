package com.example.oro3;

import com.example.oro3.model.Conference;
import com.example.oro3.model.Person;
import com.example.oro3.model.PersonType;
import com.example.oro3.model.Presentation;
import com.example.oro3.repo.RepoConference;
import com.example.oro3.repo.RepoPerson;
import com.example.oro3.repo.RepoPresentation;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.oro3.model.PersonType.STUDENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class Oro3ApplicationTests {

    @Autowired
    private RepoConference repoConference;

    @Autowired
    private RepoPerson repoPerson;

    @Autowired
    private RepoPresentation repoPresentation;

    Conference conference1;
    Conference conference2;

    Person person1;
    Person person2;
    Person person3;
    Person person4;
    Person person5;
    Person person6;
    Person person7;
    Person person8;
    Person person9;

    Presentation presentation1;
    Presentation presentation2;
    Presentation presentation3;
    Presentation presentation4;

    @BeforeEach
    void setupDatabase() {
        person1 = Person.builder()
                .name("John Doe")
                .country("USA")
                .type(PersonType.SCIENTIST)
                .build();
        person2 = Person.builder()
                .name("Anna Smith")
                .country("UK")
                .type(STUDENT)
                .build();
        person3 = Person.builder()
                .name("Carlos Ray")
                .country("Spain")
                .type(PersonType.ORGANIZER)
                .build();
        person4 = Person.builder()
                .name("Lisa Ray")
                .country("France")
                .type(PersonType.SCIENTIST)
                .build();
        person5 = Person.builder()
                .name("Mohammed Ali")
                .country("UAE")
                .type(STUDENT)
                .build();
        person6 = Person.builder()
                .name("Chang Wong")
                .country("China")
                .type(PersonType.ORGANIZER)
                .build();
        person7 = Person.builder()
                .name("Sarah Connor")
                .country("USA")
                .type(PersonType.SCIENTIST)
                .build();
        person8 = Person.builder()
                .name("James Kirk")
                .country("Canada")
                .type(STUDENT)
                .build();
        person9 = Person.builder()
                .name("Leila Khaled")
                .country("Jordan")
                .type(PersonType.ORGANIZER)
                .build();

        List<Person> allPersons = List.of(person1, person2, person3, person4, person5, person6, person7, person8, person9);
        repoPerson.saveAll(allPersons);

        List<Person> participants1 = List.of(person1, person2, person4, person5);
        List<Person> participants2 = List.of(person3, person4, person5, person7, person8, person9);

        conference1 = Conference.builder()
                .name("Tech Conference 2024")
                .participants(participants1)
                .build();

        conference2 = Conference.builder()
                .name("Future Innovations 2024")
                .participants(participants2)
                .build();

        repoConference.saveAll(List.of(conference1, conference2));

        presentation1 = Presentation.builder()
                .topic("Quantum Computing")
                .time(LocalDateTime.now())
                .conference(conference1)
                .presenter(person3)
                .build();

        presentation2 = Presentation.builder()
                .topic("Artificial Intelligence")
                .time(LocalDateTime.now().plusHours(2))
                .conference(conference2)
                .presenter(person6)
                .build();

        presentation3 = Presentation.builder()
                .topic("Machine Learning Advances")
                .time(LocalDateTime.now().plusDays(1))
                .conference(conference1)
                .presenter(person6)
                .build();

        presentation4 = Presentation.builder()
                .topic("The Future of Blockchain")
                .time(LocalDateTime.now().plusDays(1).plusHours(3))
                .conference(conference2)
                .presenter(person8)
                .build();

        repoPresentation.saveAll(List.of(presentation1, presentation2, presentation3, presentation4));
    }

    @Test
    void AllParticipants() {
        List<Person> participantsList = repoPerson.findAllParticipantsByConferenceId(conference1.getId());
        assertEquals(4, participantsList.size());
    }

    @Test
    void AllParticipantsByType() {
        List<Person> participantsListStudent = repoPerson.findParticipantsByConferenceIdAndPersonType(conference2.getId(), STUDENT);
        List<Person> participantsListScientist = repoPerson.findParticipantsByConferenceIdAndPersonType(conference2.getId(), PersonType.SCIENTIST);
        List<Person> participantsListOrganizer = repoPerson.findParticipantsByConferenceIdAndPersonType(conference2.getId(), PersonType.ORGANIZER);
        assertEquals(2, participantsListStudent.size());
        assertEquals(2, participantsListScientist.size());
        assertEquals(2, participantsListOrganizer.size());
    }

    @Test
    void AllParticipantsByCountry() {
        List<Person> participantsList = repoPerson.findAllParticipantsByCountryAndConference(conference1.getId(), "USA");
        assertEquals(1, participantsList.size());
    }

    @Test
    void PresentationTopicsByConference() {
        List<String> presentationTopics = repoPresentation.findPresentationTopicsByConferenceId(conference1.getId());
        assertEquals(2, presentationTopics.size());
        assertTrue(presentationTopics.contains("Quantum Computing"));
        assertTrue(presentationTopics.contains("Machine Learning Advances"));
    }


    @Test
    void MostPresentationPerson() {
        List<Person> topPresenters = repoPerson.findTopPresenter();
        assertEquals(person6, topPresenters.get(0));
    }

    @Test
    void PresentationsByConference() {
        Long number = repoPresentation.countPresentationsByConference(conference1.getId());
        assertEquals(2, number);
    }
}
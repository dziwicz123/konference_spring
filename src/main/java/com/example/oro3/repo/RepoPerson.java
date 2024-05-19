package com.example.oro3.repo;

import com.example.oro3.model.Person;
import com.example.oro3.model.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoPerson extends JpaRepository<Person, Long> {
    @Query("SELECT p FROM Person p JOIN p.conferencesAttended c WHERE c.id = :conferenceId")
    List<Person> findAllParticipantsByConferenceId(@Param("conferenceId") Long conferenceId);

    @Query("SELECT p FROM Person p JOIN p.conferencesAttended c WHERE c.id = :conferenceId AND p.type = :personType")
    List<Person> findParticipantsByConferenceIdAndPersonType(@Param("conferenceId") Long conferenceId, @Param("personType") PersonType personType);

    @Query("SELECT p FROM Person p " +
            "JOIN p.conferencesAttended c " +
            "WHERE c.id = :conferenceId AND p.country = :country")
    List<Person> findAllParticipantsByCountryAndConference(@Param("conferenceId") Long conferenceId, @Param("country") String country);

    @Query("SELECT p FROM Person p JOIN p.presentationsPerformed pp GROUP BY p ORDER BY COUNT(pp) DESC")
    List<Person> findTopPresenter();
}

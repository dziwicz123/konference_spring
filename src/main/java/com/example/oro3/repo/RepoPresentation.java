package com.example.oro3.repo;

import com.example.oro3.model.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepoPresentation extends JpaRepository<Presentation, Long> {

    @Query("SELECT p.topic FROM Presentation p WHERE p.conference.id = :conferenceId")
    List<String> findPresentationTopicsByConferenceId(@Param("conferenceId") Long conferenceId);

    @Query("SELECT COUNT(p) FROM Presentation p WHERE p.conference.id = :conferenceId")
    Long countPresentationsByConference(@Param("conferenceId") Long conferenceId);
}

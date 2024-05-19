package com.example.oro3.repo;

import com.example.oro3.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
public interface RepoConference extends JpaRepository<Conference, Long> {
}

package com.example.schoolManagement.groupe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupRepository extends JpaRepository<Group,Long> {
    Set<Group> findByLevelId(Long aLong);
}

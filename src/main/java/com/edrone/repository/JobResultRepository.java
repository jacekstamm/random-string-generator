package com.edrone.repository;

import com.edrone.entity.JobResults;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobResultRepository extends JpaRepository<JobResults, UUID> {
}

package com.edrone.entity;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "JOB_RESULTS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JobResults {

    @Id
    private UUID id;
    private String fileNameOnAws;

}

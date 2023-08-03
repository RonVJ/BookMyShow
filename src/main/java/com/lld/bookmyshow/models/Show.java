package com.lld.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Show extends BaseModel{
    @ManyToOne
    private Auditorium auditorium;

    @ManyToOne
    private Movie movie;
    private Date startTime;
    private Date endTime;

    @Enumerated(EnumType.ORDINAL)
    private Language Language;
}

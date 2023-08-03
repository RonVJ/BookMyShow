package com.lld.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Auditorium extends BaseModel{
    String name;

    @OneToMany
    List<Seat> seats;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    List<Feature> features;
}

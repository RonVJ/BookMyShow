package com.lld.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class City extends BaseModel{
    @OneToMany(mappedBy = "city")
    private List<Theatre> theatres;
    private String name;
}

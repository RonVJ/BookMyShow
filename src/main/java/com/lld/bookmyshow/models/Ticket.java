package com.lld.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Ticket extends BaseModel{
    private long amount;

    @OneToOne
    private Show show;
    @ManyToMany
    private List<Seat> seats;
    private Date timeOfBooking;
    @OneToMany(mappedBy = "ticket")
    private List<Payment> payments;
    @ManyToOne
    private User bookedBy;
    @Enumerated(EnumType.ORDINAL)
    private TicketStatus ticketStatus;
}

package com.lld.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookTicketResponseDto {
    private Long ticketId;
    private Long amount;
    private List<Long> seatNumbers;
    private String auditoriumName;
    private String status;
    private String message;
}

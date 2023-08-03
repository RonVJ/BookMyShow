package com.lld.bookmyshow.dtos;

import com.lld.bookmyshow.models.ShowSeat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookTicketRequestDto {
    private List<Long> seatIds;
    private Long userId;
    private Long showId;
}

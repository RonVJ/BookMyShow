package com.lld.bookmyshow.controllers;

import com.lld.bookmyshow.dtos.BookTicketRequestDto;
import com.lld.bookmyshow.dtos.BookTicketResponseDto;
import com.lld.bookmyshow.models.Ticket;
import com.lld.bookmyshow.repository.TicketRepository;
import com.lld.bookmyshow.services.TicketService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Getter
@Setter
public class TicketController {
    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    public BookTicketResponseDto bookTicket(BookTicketRequestDto request) {
        Long userId = request.getUserId();
        List<Long> seats = request.getSeatIds();
        Long showId = request.getShowId();
        BookTicketResponseDto response = new BookTicketResponseDto();

        try {
            Ticket ticket = ticketService.bookTicket(userId, seats, showId);
            response.setTicketId(ticket.getId());
            response.setStatus("SUCCESS");
            response.setAmount(ticket.getAmount());
//            response.setSeatNumbers(ticket.getSeats().);
        }
        catch (Exception e) {
            response.setStatus("FAILURE");
            response.setMessage(e.getMessage());
        }
        return response;

    }
}

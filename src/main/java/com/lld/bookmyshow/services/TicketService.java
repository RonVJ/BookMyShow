package com.lld.bookmyshow.services;

import com.lld.bookmyshow.exceptions.SeatNotAvailableException;
import com.lld.bookmyshow.exceptions.ShowDoesNotExistException;
import com.lld.bookmyshow.exceptions.UserDoesNotExistException;
import com.lld.bookmyshow.models.*;
import com.lld.bookmyshow.repository.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class TicketService {
    private UserRepository userRepository;
    private SeatRepository seatRepository;
    private ShowRepository showRepository;
    private TicketRepository ticketRepository;
    private ShowSeatRepository showSeatRepository;

    @Autowired
    public TicketService(UserRepository userRepository
            , SeatRepository seatRepository
            , ShowRepository showRepository
            , TicketRepository ticketRepository
            , ShowSeatRepository showSeatRepository) {
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
    }

    public Ticket bookTicket(Long userId
            , List<Long> seatNumbers
            , Long showId) throws ShowDoesNotExistException, UserDoesNotExistException, SeatNotAvailableException {
        List<Seat> seats = seatRepository.findAllByIdIn(seatNumbers);
        Optional<Show> showOptional = showRepository.findById(showId);
        if(showOptional.isEmpty()) {
            throw new ShowDoesNotExistException();
        }
        Show show = showOptional.get();

        List<ShowSeat> showSeats = getAndLockSeats(seats, show);

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) {
            throw new UserDoesNotExistException();
        }
        User user = userOptional.get();

        Ticket ticket = new Ticket();
        ticket.setBookedBy(user);
        ticket.setTicketStatus(TicketStatus.PROCESSING);
        ticket.setTimeOfBooking(new Date());
        ticket.setSeats(seats);
        ticket.setShow(show);
        ticket.setAmount(0);
        return ticketRepository.save(ticket);
    }

//    @Transactional(isolation= Isolation.SERIALIZABLE, timeout=2)
    public List<ShowSeat> getAndLockSeats(List<Seat> seats
            , Show show) throws SeatNotAvailableException{
        List<ShowSeat> showSeats = showSeatRepository.findAllBySeatInAndShow(seats, show);

        for(ShowSeat showSeat: showSeats) {
            if (!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)
                    || (showSeat.getShowSeatStatus().equals(ShowSeatStatus.LOCKED)
                    && (Math.abs((new Date()).getTime() - showSeat.getLockedAt().getTime()) > 15 * 60 * 1000)))) {
                throw new SeatNotAvailableException();
            }
        }

        List<ShowSeat> savedShowSeats = new ArrayList<>();

        for(ShowSeat showSeat:showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }

        return savedShowSeats;
    }
}

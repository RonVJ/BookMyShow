package com.lld.bookmyshow.repository;

import com.lld.bookmyshow.models.Seat;
import com.lld.bookmyshow.models.Show;
import com.lld.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findAllBySeatInAndShow(List<Seat> seats, Show show);
}

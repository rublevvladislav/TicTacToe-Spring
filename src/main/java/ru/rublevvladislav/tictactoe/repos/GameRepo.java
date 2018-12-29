package ru.rublevvladislav.tictactoe.repos;

import ru.rublevvladislav.tictactoe.domain.Game;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Date;

public interface GameRepo extends PagingAndSortingRepository<Game, String> {
    Page<Game> findByWinnerLike(String winner, Pageable pageable);
    Page<Game> findAllByOrderByDateDesc(Pageable pageable);
}

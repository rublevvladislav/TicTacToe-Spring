package ru.rublevvladislav.tictactoe;

import ru.rublevvladislav.tictactoe.GameLogic.BigField;
import ru.rublevvladislav.tictactoe.domain.Game;
import ru.rublevvladislav.tictactoe.repos.GameRepo;
import ru.rublevvladislav.tictactoe.ArtificialIntelligence.Random;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.*;

import org.slf4j.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Date;

@Controller
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired
    GameRepo gameDAO;

    private BigField game;

    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/game", method = GET)
    public String game(HttpServletRequest request, Model model) {
    game = getGameFromSession(request);
    if (game == null) {
      return "redirect:/game/new";
    } else {
      model.addAttribute("game", game.getField());
      model.addAttribute("check", game.isGameOver());
      model.addAttribute("winner", game.whoWinner());
      model.addAttribute("whichField", game.getWhichField());
      model.addAttribute("gameMode", game.getGameMode());
    }
    return "game";
    }

    @RequestMapping(value = "/game/new", method = GET)
    public String newGame(@RequestParam String gameMode, HttpServletRequest request) {
    game = new BigField();
    game.setGameMode(Integer.parseInt(gameMode));
    setGameIntoSession(request, game);
    return "redirect:/game";
  }

    @RequestMapping(value = "/game/move", method = GET)
    public String play(@RequestParam String pos, HttpServletRequest request) {
    game = getGameFromSession(request);
    if (pos == null) {
      game.move(100);
    } else {
      game.move(Integer.parseInt(pos));
      if (!game.isGameOver() && game.getGameMode() == 0){
        Random.PlayMove(game);
      }
      if (game.isGameOver()){
        Game gameEnt = new Game();
        Date date = new Date();
        gameEnt.setDate(date);
        gameEnt.setWinner(game.whoWinner());
        if(game.getGameMode() == 0){
          gameEnt.setGameMode("Player vs Computer");
        }
        else{
          gameEnt.setGameMode("Co-op");
        }
        gameDAO.save(gameEnt);
      }
    }
    return "redirect:/game";
  }

  @RequestMapping(value = "/list")
  public String list(@RequestParam(defaultValue="0", required=false) String pg, Model model, @PageableDefault(value = 5) Pageable pageable){
    Page<Game> page = gameDAO.findAllByOrderByDateDesc(new PageRequest(Integer.parseInt(pg), 5));
    model.addAttribute("games", page);
    return "list";
  }

  private BigField getGameFromSession(HttpServletRequest request) {
    HttpSession session = request.getSession();
    return (BigField) session.getAttribute("game");
  }

  private void setGameIntoSession(HttpServletRequest request, BigField game) {
    HttpSession session = request.getSession();
    session.setAttribute("game", game);
  }
}

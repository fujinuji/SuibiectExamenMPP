package scs.mpp.exam.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scs.mpp.exam.entites.Game;
import scs.mpp.exam.repository.GameRepository;

@RestController
@RequestMapping("/game")
public class GameRestController {
    private GameRepository gameRepository = new GameRepository();

    @GetMapping("/{id}")
    private ResponseEntity<?> getGame(@PathVariable("id") String id, @RequestParam("aa") String aa) {
        System.out.println(aa);
        try {
            Game game = gameRepository.getById(id);
            return new ResponseEntity<>(game, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

package scs.mpp.exam.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scs.mpp.exam.entites.Round;
import scs.mpp.exam.repository.RoundRepository;

import java.util.List;

@RestController
@RequestMapping("/round")
public class RoundRestController {
    private final RoundRepository roundRepository = new RoundRepository();

    @GetMapping
    public ResponseEntity<?> getRounds(@RequestParam("gameId") String gameId, @RequestParam("player") String player) {
        try {
            List<Round> rounds = roundRepository.getByPlayerAndGame(player, gameId);
            return new ResponseEntity<>(rounds, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

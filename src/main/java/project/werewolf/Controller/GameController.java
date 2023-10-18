package project.werewolf.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.werewolf.Service.GameService;
import project.werewolf.Service.PlayerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    @GetMapping("/reset")
    public void resetGame() throws Exception {
        gameService.resetGame();
    }
}

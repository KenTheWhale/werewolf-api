package project.werewolf.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.werewolf.Response.RespondData;
import project.werewolf.Service.PlayerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RequestControllers {

    private final PlayerService playerService;

    @GetMapping("/get-player")
    public ResponseEntity<List<RespondData>> getAllPlayer() throws Exception{
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @PostMapping("/kill/{position}")
    public void kill(@PathVariable String position) throws Exception{
        playerService.kill(Integer.parseInt(position));
    }

    @PostMapping("/save/{position}")
    public void save(@PathVariable String position) throws Exception{
        playerService.save(Integer.parseInt(position));
    }

    @PostMapping("couple/{position}")
    public void couple(@PathVariable String position) throws Exception{
        playerService.couple(Integer.parseInt(position));
    }

    @PostMapping("effect/{position}")
    public void effect(@PathVariable String position) throws Exception{
        playerService.effect(Integer.parseInt(position));
    }

    @PostMapping("uneffect/{position}")
    public void uneffect(@PathVariable String position) throws Exception{
        playerService.uneffect(Integer.parseInt(position));
    }

    @PostMapping("hide/{position}")
    public void hide(@PathVariable String position) throws Exception{
        playerService.hide(Integer.parseInt(position));
    }

    @PostMapping("unhide/{position}")
    public void unhide(@PathVariable String position) throws Exception{
        playerService.unhide(Integer.parseInt(position));
    }

    @PostMapping("research/{position}")
    public void research(@PathVariable String position) throws Exception{
        playerService.research(Integer.parseInt(position));
    }

    @PostMapping("immortal/{position}")
    public void immortal(@PathVariable String position) throws Exception{
        playerService.immortal(Integer.parseInt(position));
    }

    @PostMapping("bitten/{position}")
    public void bitten(@PathVariable String position) throws Exception{
        playerService.bitten(Integer.parseInt(position));
    }

    @PostMapping("usedie/{position}")
    public void usedie(@PathVariable String position) throws Exception{
        playerService.usedie(Integer.parseInt(position));
    }

    @PostMapping("usesave/{position}")
    public void usesave(@PathVariable String position) throws Exception{
        playerService.usesave(Integer.parseInt(position));
    }

    @PostMapping("sick/{position}")
    public void sick(@PathVariable String position) throws Exception{
        playerService.sick(Integer.parseInt(position));
    }

    @PostMapping("unsick/{position}")
    public void unsick(@PathVariable String position) throws Exception{
        playerService.unsick(Integer.parseInt(position));
    }

    @GetMapping("/get-night-function-role")
    public ResponseEntity<List<RespondData>> getNightFunctionRole() throws Exception {
        return ResponseEntity.ok(playerService.getNightFunctionRole());
    }

    @GetMapping("/reset-game")
    public void resetGame() throws Exception {
        playerService.resetGame();
    }


}

package project.werewolf.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.werewolf.Response.RespondData;
import project.werewolf.Service.PlayerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerControllers {

    private final PlayerService playerService;

    @GetMapping("/get/{beginValue}")
    public ResponseEntity<List<RespondData>> getAllPlayer(@PathVariable String beginValue) throws Exception{
        return ResponseEntity.ok(playerService.getAllPlayers(Integer.parseInt(beginValue)));
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

    @PostMapping("un-effect/{position}")
    public void unEffect(@PathVariable String position) throws Exception{
        playerService.unEffect(Integer.parseInt(position));
    }

    @PostMapping("hide/{position}")
    public void hide(@PathVariable String position) throws Exception{
        playerService.hide(Integer.parseInt(position));
    }

    @PostMapping("un-hide/{position}")
    public void unHide(@PathVariable String position) throws Exception{
        playerService.unHide(Integer.parseInt(position));
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

    @PostMapping("use-die/{position}")
    public void useDie(@PathVariable String position) throws Exception{
        playerService.useDie(Integer.parseInt(position));
    }

    @PostMapping("use-save/{position}")
    public void useSave(@PathVariable String position) throws Exception{
        playerService.useSave(Integer.parseInt(position));
    }

    @PostMapping("sick/{position}")
    public void sick(@PathVariable String position) throws Exception{
        playerService.sick(Integer.parseInt(position));
    }

    @PostMapping("un-sick/{position}")
    public void unSick(@PathVariable String position) throws Exception{
        playerService.unSick(Integer.parseInt(position));
    }

    @GetMapping("/get-night-function-role")
    public ResponseEntity<List<RespondData>> getNightFunctionRole() throws Exception {
        return ResponseEntity.ok(playerService.getNightFunctionRole());
    }

    @PostMapping("/set-night-called/{position}")
    public void setCalledAtNight(@PathVariable String position) throws Exception{
        playerService.setCalledAtNight(Integer.parseInt(position));
    }

}

package project.werewolf.Service;

import project.werewolf.Response.RespondData;

import java.util.List;

public interface PlayerService {
    List<RespondData> getAllPlayers() throws Exception;

    void kill(int position) throws Exception;

    void save(int position) throws Exception;

    void couple(int position) throws Exception;

    void effect(int position) throws Exception;

    void hide(int position) throws Exception;

    void research(int position) throws Exception;

    void immortal(int position) throws Exception;

    void bitten(int position) throws Exception;

    void usedie(int position) throws Exception;

    void usesave(int position) throws Exception;

    void sick(int position) throws Exception;

    List<RespondData> getNightFunctionRole() throws Exception;

    void uneffect(int position) throws Exception;

    void unhide(int position) throws Exception;

    void unsick(int position) throws Exception;

    void resetGame() throws Exception;
}

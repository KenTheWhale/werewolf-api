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

    void useDie(int position) throws Exception;

    void useSave(int position) throws Exception;

    void sick(int position) throws Exception;

    List<RespondData> getNightFunctionRole() throws Exception;

    void unEffect(int position) throws Exception;

    void unHide(int position) throws Exception;

    void unSick(int position) throws Exception;

    void setCalledAtNight(int position) throws Exception;
}

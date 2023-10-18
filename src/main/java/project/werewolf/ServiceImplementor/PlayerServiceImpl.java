package project.werewolf.ServiceImplementor;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.werewolf.Enums.Constants;
import project.werewolf.GoogleSheet.SheetUtils;
import project.werewolf.Response.RespondData;
import project.werewolf.Service.PlayerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService {
    @Override
    public List<RespondData> getAllPlayers() throws Exception {
        Sheets sheetService = SheetUtils.getSheetService();
        String range = "player_data!B2:N30";

        ValueRange response = sheetService.spreadsheets().values()
                .get(Constants.SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();
        List<RespondData> dataList = new ArrayList<>();
        if (values != null && !values.isEmpty()) {
            int position = 0;
            for (List<Object> row : values) {
                dataList.add(
                        RespondData
                                .builder()
                                .position(position)
                                .value(1)
                                .name(row.get(0).toString())
                                .role(row.get(1).toString())
                                .isDead(checkBoolean(row.get(2)))
                                .isEffected(checkBoolean(row.get(3)))
                                .isCoupled(checkBoolean(row.get(4)))
                                .isHidden(checkBoolean(row.get(5)))
                                .isResearched(checkBoolean(row.get(6)))
                                .isImmortal(checkBoolean(row.get(7)))
                                .isBitten(checkBoolean(row.get(8)))
                                .useDie(checkBoolean(row.get(9)))
                                .useSave(checkBoolean(row.get(10)))
                                .isSick(checkBoolean(row.get(11)))
                                .hasNightFunction(checkBoolean(row.get(12)))
                                .build()
                );
                position++;
            }
        }
        return dataList;
    }

    @Override
    public void kill(int position) throws Exception {
        setToDie(position);
    }

    @Override
    public void save(int position) throws Exception {
        setToLive(position);
    }

    @Override
    public void couple(int position) throws Exception {
        changeStatus(position, "F", "TRUE");
    }

    @Override
    public void effect(int position) throws Exception {
        changeStatus(position, "E", "TRUE");
    }

    @Override
    public void uneffect(int position) throws Exception {
        changeStatus(position, "E", "FALSE");
    }

    @Override
    public void hide(int position) throws Exception {
        changeStatus(position, "G", "TRUE");
    }

    @Override
    public void unhide(int position) throws Exception {
        changeStatus(position, "G", "FALSE");
    }

    @Override
    public void research(int position) throws Exception {
        changeStatus(position, "H", "TRUE");
    }

    @Override
    public void immortal(int position) throws Exception {
        changeStatus(position, "I", "TRUE");
    }

    @Override
    public void bitten(int position) throws Exception {
        changeStatus(position, "J", "TRUE");
    }

    @Override
    public void usedie(int position) throws Exception {
        changeStatus(position, "K", "TRUE");
    }

    @Override
    public void usesave(int position) throws Exception {
        changeStatus(position, "L", "TRUE");
    }

    @Override
    public void sick(int position) throws Exception {
        changeStatus(position, "M", "TRUE");
    }

    @Override
    public void unsick(int position) throws Exception {
        changeStatus(position, "M", "FALSE");
    }

    @Override
    public List<RespondData> getNightFunctionRole() throws Exception {
        Sheets sheetService = SheetUtils.getSheetService();
        String range = "player_data!A2:N30";

        ValueRange response = sheetService.spreadsheets().values()
                .get(Constants.SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();
        List<RespondData> dataList = new ArrayList<>();
        List<RespondData> finalDataList = new ArrayList<>();
        if (values != null && !values.isEmpty()) {
            int position = 0;
            for (List<Object> row : values) {
                dataList.add(
                        RespondData
                                .builder()
                                .position(position)
                                .value(1)
                                .name(row.get(1).toString())
                                .role(row.get(2).toString())
                                .isDead(checkBoolean(row.get(3)))
                                .isEffected(checkBoolean(row.get(4)))
                                .isCoupled(checkBoolean(row.get(5)))
                                .isHidden(checkBoolean(row.get(6)))
                                .isResearched(checkBoolean(row.get(7)))
                                .isImmortal(checkBoolean(row.get(8)))
                                .isBitten(checkBoolean(row.get(9)))
                                .useDie(checkBoolean(row.get(10)))
                                .useSave(checkBoolean(row.get(11)))
                                .isSick(checkBoolean(row.get(12)))
                                .hasNightFunction(checkBoolean(row.get(13)))
                                .build()
                );
                position++;
            }
            for (RespondData line : dataList) {
                if (line.isHasNightFunction()) {
                    finalDataList.add(line);
                }
            }
        }
        return finalDataList;
    }

    Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Override
    public void resetGame() throws Exception {
        Sheets sheetService = SheetUtils.getSheetService();
        for (int i = 0; i < getAllPlayers().size(); i++) {
                changeStatus(i, "A", "");
                changeStatus(i, "B", "");
                changeStatus(i, "C", "");
        }
    }

    private boolean checkBoolean(Object input) {
        return input.toString().equalsIgnoreCase("TRUE");
    }

    private void setToDie(int position) throws Exception {
        changeStatus(position, "D", "TRUE");
    }

    private void setToLive(int position) throws Exception {
        changeStatus(position, "D", "FALSE");
    }

    private void changeStatus(int position, String columnName, Object newValue) throws Exception {
        Sheets sheetService = SheetUtils.getSheetService();

        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(newValue)));

        UpdateValuesResponse result = sheetService.spreadsheets().values()
                .update(Constants.SPREADSHEET_ID, columnName + (position + 2), body)
                .setValueInputOption("RAW")
                .execute();
    }
}

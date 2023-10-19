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

    Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Override
    public List<RespondData> getAllPlayers(int beginValue) throws Exception {
        if (beginValue == 1) {
            insertData();
        }
        Sheets sheetService = SheetUtils.getSheetService();
        String range = Constants.SHEET_NAME + "!" + Constants.START_POSITION + ":" + Constants.END_POSITION;

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
                                .oneTimeFunction(checkBoolean(row.get(13)))
                                .build()
                );
                position++;
            }
        }
        return dataList;
    }

    private void insertData() throws Exception {
        Sheets sheetService = SheetUtils.getSheetService();
        String range = Constants.SHEET_NAME_BEGIN + "!" + Constants.START_POSITION_BEGIN + ":" + Constants.END_POSITION_BEGIN;

        ValueRange response = sheetService.spreadsheets().values()
                .get(Constants.SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();
        List<String> dataList = new ArrayList<>();
        if (values != null && !values.isEmpty()) {
            for (List<Object> row : values) {
                dataList.add(
                        row.get(0).toString()
                                + "#"
                                + row.get(1).toString()
                );
            }
        }

        int position = 0;
        for (int i = 0; i < dataList.size(); i++) {
            String[] arr = dataList.get(i).split("#");
            changeStatusBegin(position, "A", arr[0]);
            changeStatusBegin(position, "B", arr[1]);
            position++;
        }
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
    public void unEffect(int position) throws Exception {
        changeStatus(position, "E", "FALSE");
    }

    @Override
    public void hide(int position) throws Exception {
        changeStatus(position, "G", "TRUE");
    }

    @Override
    public void unHide(int position) throws Exception {
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
    public void useDie(int position) throws Exception {
        changeStatus(position, "K", "TRUE");
    }

    @Override
    public void useSave(int position) throws Exception {
        changeStatus(position, "L", "TRUE");
    }

    @Override
    public void sick(int position) throws Exception {
        changeStatus(position, "M", "TRUE");
    }

    @Override
    public void unSick(int position) throws Exception {
        changeStatus(position, "M", "FALSE");
    }

    @Override
    public List<RespondData> getNightFunctionRole() throws Exception {
        List<RespondData> dataList = getAllPlayers(0);
        List<RespondData> finalDataList = new ArrayList<>();

        for (RespondData line : dataList) {
            if (line.isHasNightFunction()) {
                finalDataList.add(line);
            }
        }
        return finalDataList;
    }

    @Override
    public void setCalledAtNight(int position) throws Exception {
        changeStatus(position, "O", "TRUE");
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
                .setValues(Arrays.asList(Arrays.asList("=IF(B2 = \"\";\"\";" + newValue + "())")));

        sheetService.spreadsheets().values()
                .update(Constants.SPREADSHEET_ID, columnName + (position + 2), body)
                .setValueInputOption("USER_ENTERED")
                .execute();
    }

    private void changeStatusBegin(int position, String columnName, Object newValue) throws Exception {
        Sheets sheetService = SheetUtils.getSheetService();

        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(newValue)));

        Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);
        log.info("Column: " + columnName + (position + 2));

        sheetService.spreadsheets().values()
                .update(Constants.SPREADSHEET_ID, Constants.SHEET_NAME + "!" + columnName + (position + 2), body)
                .setValueInputOption("RAW")
                .execute();
    }


}

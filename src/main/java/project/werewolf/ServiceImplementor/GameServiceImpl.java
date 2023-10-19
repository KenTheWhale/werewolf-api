package project.werewolf.ServiceImplementor;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.werewolf.Enums.Constants;
import project.werewolf.GoogleSheet.SheetUtils;
import project.werewolf.Response.RespondData;
import project.werewolf.Service.GameService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {


    public List<RespondData> getAllPlayers() throws Exception {
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

    @Override
    public void resetGame() throws Exception {
        for (int i = 0; i < getAllPlayers().size(); i++) {
            char title = 'A';
            for (int j = 0; j < 3; j++) {
                changeStatusBegin(i, Character.toString(title + j));
            }

            for (int k = 3; k < RespondData.class.getDeclaredFields().length - 1; k++) {
                if (!(title + k == 'N' || title + k == 'O')){
                    changeStatus(i, Character.toString(title + k), "FALSE");
                }

            }
        }
    }

    private boolean checkBoolean(Object input) {
        return input.toString().equalsIgnoreCase("TRUE");
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

    private void changeStatusBegin(int position, String columnName) throws Exception {
        Sheets sheetService = SheetUtils.getSheetService();

        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList("")));

        sheetService.spreadsheets().values()
                .update(Constants.SPREADSHEET_ID, columnName + (position + 2), body)
                .setValueInputOption("RAW")
                .execute();
    }
}

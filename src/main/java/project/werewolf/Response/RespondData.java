package project.werewolf.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondData {
    private int position;
    private int value;
    private String name;
    private String role;
    private boolean isDead;
    private boolean isEffected;
    private boolean isCoupled;
    private boolean isHidden;
    private boolean isResearched;
    private boolean isImmortal;
    private boolean isBitten;
    private boolean useDie;
    private boolean useSave;
    private boolean isSick;
    private boolean hasNightFunction;
    private boolean oneTimeFunction;
}

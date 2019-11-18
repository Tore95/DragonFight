package model.dlv;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import model.enums.PlayerAction;

@Id("do")
public class Do {

    @Param(0)
    private PlayerAction action;

    public Do() {}

    public String getAction() {
        return action.toString().toLowerCase();
    }
    public PlayerAction getRealAction() {
        return action;
    }

    public void setAction(String action) {
        String act = action.substring(1,action.length() - 1);
        switch (act) {
            case "punch": this.action = PlayerAction.PUNCH; break;
            case "kick": this.action = PlayerAction.KICK; break;
            case "aura1": this.action = PlayerAction.AURA1; break;
            case "aura2": this.action = PlayerAction.AURA2; break;
            case "final": this.action = PlayerAction.FINAL; break;
        }
    }

}

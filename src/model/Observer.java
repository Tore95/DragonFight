package model;

import model.dlv.AuraPack;
import model.dlv.PlayerOnePack;

public interface Observer {
    void update(AuraPack pack);
    void update(PlayerOnePack pack);
}

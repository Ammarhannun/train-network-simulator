package unsw.stations;

import unsw.utils.Position;

public class CentralStation extends Station {
    public CentralStation(String stationId, Position position) {
        super(stationId, position, 8);
    }

    @Override
    public String getType() {
        return "CentralStation";
    }
}

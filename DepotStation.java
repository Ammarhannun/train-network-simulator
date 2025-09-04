package unsw.stations;

import unsw.utils.Position;

public class DepotStation extends Station {
    public DepotStation(String stationId, Position position) {
        super(stationId, position, 8);
    }

    @Override
    public String getType() {
        return "DepotStation";
    }
}

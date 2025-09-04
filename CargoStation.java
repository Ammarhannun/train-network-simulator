package unsw.stations;

import unsw.utils.Position;

public class CargoStation extends Station {
    public CargoStation(String stationId, Position position) {
        super(stationId, position, 4);
    }

    @Override
    public String getType() {
        return "CargoStation";
    }
}

package unsw.stations;

import unsw.utils.Position;

public class PassengerStation extends Station {
    public PassengerStation(String stationId, Position position) {
        super(stationId, position, 2);
    }

    @Override
    public String getType() {
        return "PassengerStation";
    }
}

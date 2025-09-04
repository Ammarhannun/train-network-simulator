package unsw.trains;

import java.util.List;

import unsw.utils.Position;

public class PassengerTrain extends Train {
    public PassengerTrain(String trainId, String currentStationId, List<String> route, Position position,
            boolean isLinear) {
        super(trainId, currentStationId, route, position, isLinear);
    }

    @Override
    public String getType() {
        return "PassengerTrain";
    }

    @Override
    public double getBaseSpeed() {
        return 2;
    }

}

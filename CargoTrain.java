package unsw.trains;

import java.util.List;

import unsw.utils.Position;

public class CargoTrain extends Train {
    public CargoTrain(String trainId, String currentStationId, List<String> route, Position position,
            boolean isLinear) {
        super(trainId, currentStationId, route, position, isLinear);
    }

    @Override
    public String getType() {
        return "CargoTrain";
    }

    @Override
    public double getBaseSpeed() {
        return 3;
    }

}

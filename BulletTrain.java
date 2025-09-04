package unsw.trains;

import java.util.List;

import unsw.utils.Position;

public class BulletTrain extends Train {
    public BulletTrain(String trainId, String currentStationId, List<String> route, Position position,
            boolean isLinear) {
        super(trainId, currentStationId, route, position, isLinear);
    }

    @Override
    public String getType() {
        return "BulletTrain";
    }

    @Override
    public double getBaseSpeed() {
        return 5;
    }

}

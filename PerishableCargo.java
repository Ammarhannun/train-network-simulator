package unsw.loads;

import java.util.List;
import java.util.Map;

import unsw.stations.Station;
import unsw.trains.Train;
import unsw.utils.*;

public class PerishableCargo extends Cargo {
    private int minPerish;

    public PerishableCargo(String loadId, String startStationId, String destStationId, double weight, int minPerish) {
        super(loadId, startStationId, destStationId, weight);
        this.minPerish = minPerish;
    }

    public int getMinPerish() {
        return minPerish;
    }

    public void lessenMinPerish() {
        minPerish--;
    }

    public boolean isPerished() {
        return minPerish <= 0;
    }

    public boolean canReach(Train train, Station destination, Map<String, Station> stations) {
        double totalDistance = 0;
        Position currentPosition = train.getPosition();
        int index = train.getNextIndex();
        List<String> route = train.getRoute();
        int steps = 0;
        int maxSteps = route.size() * 2;
        boolean goingForward = train.isForward();

        while (!route.get(index).equals(destination.getStationId()) && steps < maxSteps) {
            Station nextStation = stations.get(route.get(index));
            totalDistance += currentPosition.distance(nextStation.getPosition());
            currentPosition = nextStation.getPosition();
            steps++;

            if (train.isLinear()) {
                if (goingForward) {
                    if (index == route.size() - 1) {
                        goingForward = false;
                        index--;
                    } else {
                        index++;
                    }
                } else {
                    if (index == 0) {
                        goingForward = true;
                        index++;
                    } else {
                        index--;
                    }
                }
            } else {
                index = (index + 1) % route.size();
            }
        }

        if (route.get(index).equals(destination.getStationId())) {
            totalDistance += currentPosition.distance(destination.getPosition());
            steps++;
        } else {

            return false;
        }

        double speed = train.getSpeed();
        double travelTime = totalDistance / speed;
        double totalTime = travelTime + steps;

        return totalTime <= minPerish;
    }

    @Override
    public String getType() {
        return "PerishableCargo";
    }
}

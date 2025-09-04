package unsw.trains;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import unsw.utils.Position;
import unsw.loads.*;
import unsw.stations.*;

public abstract class Train {
    private String trainId;
    private String currentStationId;
    private List<String> route;
    private Position position;
    private List<Load> loads;
    private boolean isLinear;
    private boolean forward;
    private int nextIndex;

    public Train(String trainId, String currentStationId, List<String> route, Position position, boolean isLinear) {
        this.trainId = trainId;
        this.currentStationId = currentStationId;
        this.route = route;
        this.position = position;
        this.loads = new ArrayList<>();
        this.isLinear = isLinear;
        this.forward = true;

    }

    public boolean isLinear() {
        return isLinear;
    }

    public boolean isForward() {
        return forward;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getCurrentStationId() {
        return currentStationId;
    }

    public List<String> getRoute() {
        return route;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public void setCurrentStationId(String stationId) {
        this.currentStationId = stationId;
    }

    public List<Load> getLoads() {
        return loads;
    }

    public void addLoad(Station currentStation, Map<String, Station> stations) {
        List<Load> stationLoads = new ArrayList<>(currentStation.getLoads());
        for (Load load : stationLoads) {
            if (!load.isValidTrain(getType()) || isFull() || !load.routeHasDestination(this.route)) {
                continue;
            }

            if (load.getType().equals("PerishableCargo")) {
                PerishableCargo perishable = (PerishableCargo) load;
                Station destination = stations.get(perishable.getDestStationId());

                if (!perishable.canReach(this, destination, stations)) {
                    continue;
                }
            }

            loads.add(load);
            currentStation.removeLoad(load);
        }
    }

    public void removeLoad() {
        loads.removeIf(load -> load.getDestStationId().equals(getCurrentStationId()));
    }

    public void removeOneLoad(Load load) {
        this.loads.remove(load);
    }

    public int getNextIndex() {
        return this.nextIndex;
    }

    public void setNextIndex(int next) {
        this.nextIndex = next;
    }

    public void updateNextIndex() {
        int routeSize = route.size();

        if (isLinear) {
            if (forward) {
                if (nextIndex == routeSize - 1) {
                    forward = false;
                    nextIndex--;
                } else {
                    nextIndex++;
                }
            } else {
                if (nextIndex == 0) {
                    forward = true;
                    nextIndex++;
                } else {
                    nextIndex--;
                }
            }
        } else {

            nextIndex = (nextIndex + 1) % routeSize;
        }
    }

    public String getNextStationId() {
        return route.get(nextIndex);
    }

    public abstract double getBaseSpeed();

    public double getSpeed() {
        double totalWeight = 0;

        for (Load load : getLoads()) {
            if (load.getType().equals("Cargo") || load.getType().equals("PerishableCargo")) {
                totalWeight += load.getWeight();
            }
        }

        double remove = 1 - (0.0001 * totalWeight);
        if (remove < 0) {
            remove = 0;
        }
        return getBaseSpeed() * remove;
    }

    public boolean isFull() {
        double totalWeight = 0;

        for (Load load : loads) {
            totalWeight += load.getWeight();
        }

        switch (getType()) {
        case "PassengerTrain":
            return totalWeight >= 3500;

        case "CargoTrain":
            return totalWeight >= 5000;

        case "BulletTrain":
            return totalWeight >= 5000;

        default:
            return true;
        }
    }

    public void removeWhileMoving() {
        List<Load> remove = new ArrayList<>();

        for (Load load : loads) {
            if (load.getType().equals("PerishableCargo")) {
                PerishableCargo pCargo = (PerishableCargo) load;
                pCargo.lessenMinPerish();

                if (pCargo.isPerished()) {
                    remove.add(pCargo);
                }
            }
        }

        for (Load loadDead : remove) {
            loads.remove(loadDead);
        }
    }

    public abstract String getType();
}

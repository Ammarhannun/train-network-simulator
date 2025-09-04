package unsw.stations;

import unsw.utils.Position;

import java.util.ArrayList;
import java.util.List;

import unsw.trains.*;
import unsw.loads.*;

public abstract class Station {
    private String stationId;
    private Position position;
    private int trainCapacity;
    private List<Train> trains;
    private List<Load> loads;

    public Station(String stationId, Position position, int trainCapacity) {
        this.stationId = stationId;
        this.position = position;
        this.trainCapacity = trainCapacity;
        this.trains = new ArrayList<>();
        this.loads = new ArrayList<>();
    }

    public String getStationId() {
        return stationId;
    }

    public Position getPosition() {
        return position;
    }

    public int getTrainCapacity() {
        return trainCapacity;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public void addTrain(Train train) {
        trains.add(train);
    }

    public void removeTrain(Train train) {
        trains.remove(train);
    }

    public List<Load> getLoads() {
        return loads;
    }

    public void addLoad(Load load) {
        loads.add(load);
    }

    public void removeLoad(Load load) {
        loads.remove(load);
    }

    public boolean isFull() {
        return trains.size() >= trainCapacity;
    }

    public void updatePerishables() {
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

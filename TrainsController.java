package unsw.trains;

import java.util.*;

import unsw.exceptions.InvalidRouteException;
import unsw.response.models.*;
import unsw.utils.*;
import unsw.stations.*;
import unsw.loads.*;

/**
 * The controller for the Trains system.
 *
 * The method signatures here are provided for you. Do NOT change the method signatures.
 */
public class TrainsController {
    // Add any fields here if necessary
    private Map<String, Station> stations = new HashMap<>();
    private Map<String, Track> tracks = new HashMap<>();
    private Map<String, Train> trains = new HashMap<>();

    public void createStation(String stationId, String type, double x, double y) {
        // Todo: Task ai
        Position position = new Position(x, y);
        Station station = null;

        switch (type) {
        case "PassengerStation":
            station = new PassengerStation(stationId, position);
            break;
        case "CargoStation":
            station = new CargoStation(stationId, position);
            break;
        case "CentralStation":
            station = new CentralStation(stationId, position);
            break;
        case "DepotStation":
            station = new DepotStation(stationId, position);
            break;
        default:
            System.out.println("Type is invalid");
        }

        stations.put(stationId, station);
    }

    public void createTrack(String trackId, String fromStationId, String toStationId) {
        // Todo: Task aii

        Track track = new Track(trackId, fromStationId, toStationId);

        tracks.put(trackId, track);
    }

    public void createTrain(String trainId, String type, String stationId, List<String> route)
            throws InvalidRouteException {
        // Todo: Task aiii
        if (!route.contains(stationId)) {
            throw new InvalidRouteException("Starting station is incompatible with the route");
        }

        Station currentStation = stations.get(stationId);
        Position position = currentStation.getPosition();
        Train train = null;
        boolean isLinear = true;

        switch (type) {
        case "PassengerTrain":
            train = new PassengerTrain(trainId, stationId, route, position, isLinear);
            break;
        case "CargoTrain":
            train = new CargoTrain(trainId, stationId, route, position, isLinear);
            break;
        case "BulletTrain":
            isLinear = Helper.isLinearRoute(route, tracks);
            train = new BulletTrain(trainId, stationId, route, position, isLinear);
            break;
        default:
            System.out.println("Type is invalid");
        }

        int nextIndex = Helper.getNextIndex(stationId, route, isLinear);
        if (train == null) {
            System.err.println("Failed to create train");
            return;
        }
        train.setNextIndex(nextIndex);
        trains.put(trainId, train);
        currentStation.addTrain(train);

    }

    public List<String> listStationIds() {
        // Todo: Task aiv

        return new ArrayList<>(stations.keySet());
    }

    public List<String> listTrackIds() {
        // Todo: Task av
        return new ArrayList<>(tracks.keySet());
    }

    public List<String> listTrainIds() {
        // Todo: Task avi
        return new ArrayList<>(trains.keySet());
    }

    public TrainInfoResponse getTrainInfo(String trainId) {
        // Todo: Task avii
        Train train = trains.get(trainId);
        String location = train.getCurrentStationId();
        String type = train.getType();
        Position position = train.getPosition();

        List<LoadInfoResponse> loadResponses = new ArrayList<>();
        List<Load> loads = train.getLoads();
        for (Load load : loads) {
            loadResponses.add(new LoadInfoResponse(load.getLoadId(), load.getType()));
        }

        return new TrainInfoResponse(trainId, location, type, position, loadResponses);
    }

    public StationInfoResponse getStationInfo(String stationId) {
        // Todo: Task aviii
        Station station = stations.get(stationId);
        String type = station.getType();
        Position position = station.getPosition();
        List<Train> trains = station.getTrains();

        List<TrainInfoResponse> trainResponses = new ArrayList<>();
        for (Train train : trains) {
            trainResponses.add(getTrainInfo(train.getTrainId()));
        }

        List<LoadInfoResponse> loadResponses = new ArrayList<>();
        List<Load> loads = station.getLoads();
        for (Load load : loads) {
            loadResponses.add(new LoadInfoResponse(load.getLoadId(), load.getType()));
        }

        return new StationInfoResponse(stationId, type, position, loadResponses, trainResponses);
    }

    public TrackInfoResponse getTrackInfo(String trackId) {
        // Todo: Task aix
        Track track = tracks.get(trackId);
        String from = track.getFromStationId();
        String to = track.getToStationId();
        TrackType type = track.getType();
        int durability = track.getDurability();

        return new TrackInfoResponse(trackId, from, to, type, durability);
    }

    public void simulate() {
        // Todo: Task bi
        List<Train> sortTrains = new ArrayList<>(trains.values());
        sortTrains.sort(Comparator.comparing(Train::getTrainId));

        for (Station station : stations.values()) {
            station.updatePerishables();
        }

        for (Train train : sortTrains) {
            train.removeWhileMoving();
            Station nextStation = stations.get(train.getNextStationId());
            if (train.getCurrentStationId() != null
                    && train.getPosition().equals(stations.get(train.getCurrentStationId()).getPosition())) {
                Station currStation = stations.get(train.getCurrentStationId());
                train.removeLoad();
                train.addLoad(currStation, stations);
                Position newPosition = train.getPosition().calculateNewPosition(nextStation.getPosition(),
                        train.getSpeed());
                train.setPosition(newPosition);
                currStation.removeTrain(train);
                train.setCurrentStationId(null);

            } else {
                if (train.getPosition().isInBound(nextStation.getPosition(), train.getSpeed())) {
                    if (nextStation.isFull()) {
                        continue;
                    } else {
                        train.setPosition(nextStation.getPosition());
                        train.setCurrentStationId(nextStation.getStationId());
                        train.updateNextIndex();
                        nextStation.addTrain(train);
                    }
                } else {
                    Position newPosition = train.getPosition().calculateNewPosition(nextStation.getPosition(),
                            train.getSpeed());
                    train.setPosition(newPosition);
                }
            }

        }
    }

    /**
     * Simulate for the specified number of minutes. You should NOT modify
     * this function.
     */
    public void simulate(int numberOfMinutes) {
        for (int i = 0; i < numberOfMinutes; i++) {
            simulate();
        }
    }

    public void createPassenger(String startStationId, String destStationId, String passengerId) {
        // Todo: Task bii
        Station station = stations.get(startStationId);
        String type = station.getType();

        if (!type.equals("PassengerStation") && !type.equals("CentralStation")) {
            System.out.println("Type is invalid");
        }
        Passenger passenger = new Passenger(passengerId, startStationId, destStationId);
        stations.get(startStationId).addLoad(passenger);

    }

    public void createCargo(String startStationId, String destStationId, String cargoId, int weight) {
        // Todo: Task bii
        Station station = stations.get(startStationId);
        String type = station.getType();

        if (!type.equals("CargoStation") && !type.equals("CentralStation")) {
            System.out.println("Type is invalid");
        }
        Cargo cargo = new Cargo(cargoId, startStationId, destStationId, weight);
        stations.get(startStationId).addLoad(cargo);
    }

    public void createPerishableCargo(String startStationId, String destStationId, String cargoId, int weight,
            int minsTillPerish) {
        // Todo: Task biii
        Station station = stations.get(startStationId);
        String type = station.getType();

        if (!type.equals("CargoStation") && !type.equals("CentralStation")) {
            System.out.println("Type is invalid");
        }
        PerishableCargo pCargo = new PerishableCargo(cargoId, startStationId, destStationId, weight, minsTillPerish);
        stations.get(startStationId).addLoad(pCargo);
    }

    public void createTrack(String trackId, String fromStationId, String toStationId, boolean isBreakable) {
        // Todo: Task ci
    }

    public void createPassenger(String startStationId, String destStationId, String passengerId, boolean isMechanic) {
        // Todo: Task cii
    }
}

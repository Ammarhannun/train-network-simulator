package unsw.loads;

import java.util.List;

public abstract class Load {
    private String loadId;
    private String startStationId;
    private String destStationId;

    public Load(String loadId, String startStationId, String destStationId) {
        this.loadId = loadId;
        this.startStationId = startStationId;
        this.destStationId = destStationId;
    }

    public String getLoadId() {
        return loadId;
    }

    public String getCurrentStationId() {
        return startStationId;
    }

    public String getDestStationId() {
        return destStationId;
    }

    public boolean routeHasDestination(List<String> route) {
        return route.contains(getDestStationId());
    }

    public abstract String getType();

    public abstract Boolean isValidTrain(String trainType);

    public abstract double getWeight();

}

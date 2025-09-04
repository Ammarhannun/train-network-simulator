package unsw.loads;

public class Passenger extends Load {
    public Passenger(String loadId, String startStationId, String destStationId) {
        super(loadId, startStationId, destStationId);
    }

    @Override
    public String getType() {
        return "Passenger";
    }

    @Override
    public Boolean isValidTrain(String trainType) {
        return (trainType.equals("PassengerTrain") || trainType.equals("BulletTrain"));
    }

    @Override
    public double getWeight() {
        return 70;
    }

}

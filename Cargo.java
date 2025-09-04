package unsw.loads;

public class Cargo extends Load {
    private double weight;

    public Cargo(String loadId, String startStationId, String destStationId, double weight) {
        super(loadId, startStationId, destStationId);
        this.weight = weight;
    }

    @Override
    public String getType() {
        return "Cargo";
    }

    @Override
    public Boolean isValidTrain(String trainType) {
        return (trainType.equals("CargoTrain") || trainType.equals("BulletTrain"));
    }

    @Override
    public double getWeight() {
        return weight;
    }

}

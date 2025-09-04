package unsw.trains;

import unsw.utils.TrackType;

public class Track {
    private String trackId;
    private String fromStationId;
    private String toStationId;
    private int durability = 10;

    public Track(String trackId, String fromStationId, String toStationId) {
        this.trackId = trackId;
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
    }

    public String getId() {
        return trackId;
    }

    public String getFromStationId() {
        return fromStationId;
    }

    public String getToStationId() {
        return toStationId;
    }

    public TrackType getType() {
        return TrackType.NORMAL;
    }

    public int getDurability() {
        return durability;
    }

}

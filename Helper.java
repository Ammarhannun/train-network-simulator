package unsw.trains;

import java.util.List;
import java.util.Map;

public class Helper {
    public static boolean isLinearRoute(List<String> route, Map<String, Track> tracks) {

        for (int i = 0; i < route.size() - 1; i++) {
            if (!hasTrackBetween(route.get(i), route.get(i + 1), tracks)) {
                return false;
            }
        }

        String first = route.get(0);
        String last = route.get(route.size() - 1);
        return !hasTrackBetween(first, last, tracks);
    }

    private static boolean hasTrackBetween(String stationA, String stationB, Map<String, Track> tracks) {
        for (Track track : tracks.values()) {
            String from = track.getFromStationId();
            String to = track.getToStationId();
            if ((from.equals(stationA) && to.equals(stationB)) || (from.equals(stationB) && to.equals(stationA))) {
                return true;
            }
        }
        return false;
    }

    public static int getNextIndex(String currentStationId, List<String> route, boolean isLinear) {
        int currentIndex = route.indexOf(currentStationId);

        if (isLinear) {
            if (currentIndex == route.size() - 1) {
                return currentIndex - 1;
            } else {
                return currentIndex + 1;
            }
        } else {
            return (currentIndex + 1) % route.size();
        }
    }
}

# Train Network Simulator

## Introduction
This project is a train network simulator implemented in Java. It models the movement of trains across different types of stations and tracks, handling both passenger and cargo transport. The system supports perishable cargo, weight-based speed adjustments, and error handling for invalid routes. It was developed as part of a university assignment with a strong focus on object oriented design, modularity, and testability.

---

## Features
- **Train System**
  - Passenger and cargo trains, including bullet trains
  - Support for perishable cargo that must be delivered before expiration
  - Speed adjustments based on cargo load

- **Stations and Tracks**
  - Multiple station types (central, cargo, depot, passenger)
  - Track management with configurable track types and connections
  - Route validation with exceptions for invalid paths

- **Simulation Control**
  - TrainsController to manage train scheduling and movement
  - Response classes for tracking loads, stations, and routes
  - Error handling through custom exceptions

---

## Project Structure
```
src/
├── App.java                     # Main entry point
├── TrainsController.java        # Core simulation controller
│
├── Train.java                   # Base train class
├── CargoTrain.java
├── PassengerTrain.java
├── BulletTrain.java
│
├── Cargo.java                   # Base cargo class
├── PerishableCargo.java
├── Load.java
├── Passenger.java
│
├── Station.java                 # Base station class
├── CentralStation.java
├── CargoStation.java
├── DepotStation.java
├── PassengerStation.java
│
├── Track.java
├── TrackType.java
│
├── Position.java                # Position and mapping utilities
├── Helper.java                  # Utility functions
│
├── LoadInfoResponse.java        # Response objects
├── StationInfoResponse.java
├── TrackInfoResponse.java
├── TrainInfoResponse.java
│
├── InvalidRouteException.java   # Error handling
├── UNSWException.java
│
└── README.md                    # Documentation
```

---

## Getting Started

### Clone the repository
```bash
git clone https://github.com/USERNAME/train-network-simulator.git
cd train-network-simulator
```

### Compile and Run
Using `javac`:
```bash
javac -d bin src/*.java
java -cp bin App
```

If a Gradle build file is present:
```bash
./gradlew build
./gradlew run
```

---

## Testing
The project includes unit tests to validate:
- Train creation and speed adjustment
- Cargo loading/unloading including perishable cargo expiration
- Station and track connectivity
- Route validation with exceptions

Run tests with:
```bash
./gradlew test
```

---

## Roadmap
- Add visualisation of train movement across the network
- Extend cargo system with additional types (e.g. hazardous, high priority)
- Introduce scheduling and timetabling logic

---

## License
This project was created as part of a university assignment and is intended for educational purposes.

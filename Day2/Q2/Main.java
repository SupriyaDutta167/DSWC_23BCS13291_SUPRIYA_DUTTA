package Day2.Q2;

abstract class DeliveryDrone{
    protected String droneId;

    public DeliveryDrone(String droneId){
        this.droneId= droneId;
    }

    public abstract void deliverPackage();
}

interface Airborne{
    void flyToDestination();

    default void requestAirTrafficClearance(){
        System.out.println("Air Traffic Clearance Granted.");
    }
}

interface GroundBased{
    void navigateSidewalks();
}

class Quadcopter extends DeliveryDrone implements Airborne{
    public Quadcopter(String droneId){
        super(droneId);
    }

    @Override
    public void flyToDestination(){
        System.out.println("Quadcopter" + droneId+ " flying to destinationj.");
    }

    @Override
    public void deliverPackage(){
        requestAirTrafficClearance();
        flyToDestination();
        System.out.println("Package delivered by Quadcopter.\n");
    }
}

class CityRover extends DeliveryDrone implements GroundBased{
    public CityRover(String droneId){
        super(droneId);
    }

    @Override
    public void navigateSidewalks(){
        System.out.println("CityRover "+ droneId+ "navigating sidewalks.");
    }

    @Override
    public void deliverPackage(){
        navigateSidewalks();
        System.out.println("package delivered by City Rover.\n");
    }
}

class HybridVTOL extends DeliveryDrone implements Airborne, GroundBased{
    public HybridVTOL(String droneId){
        super(droneId);
    }

    @Override
    public void flyToDestination(){
        System.out.println("HybridVTOL " + droneId+ " flying to Destination.");
    }

    @Override
    public void navigateSidewalks(){
        System.out.println("HybridVTOL "+ droneId+ " navigating on ground.");
    }

    @Override
    public void deliverPackage(){
        requestAirTrafficClearance();
        flyToDestination();
        navigateSidewalks();
        System.out.println("Package delivered by HybridVTOL.\n");
    }
}
public class Main {
    public static void main(String[] args){
        DeliveryDrone d1 = new Quadcopter("Q101");
        DeliveryDrone d2 = new CityRover("C202");
        DeliveryDrone d3= new HybridVTOL("H303");

        d1.deliverPackage();
        d2.deliverPackage();
        d3.deliverPackage();
    }
}

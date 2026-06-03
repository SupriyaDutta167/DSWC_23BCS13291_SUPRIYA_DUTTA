package Day2.Q1;

//we made this interface so that we can implement in every smart device which is battery operated...
//as well as to trigger if recharge needed
interface BatteryOperated{
    int getBatteryLevel();
    void triggerRechargeAlert();
}

//-> devices
abstract class SmartDevice{
    protected String deviceId;
    protected String deviceName;

    public SmartDevice(String deviceId, String deviceName){
        this.deviceId = deviceId;
        this.deviceName = deviceName;   
    }

    public abstract void runDiagnostic();//-> will be implemented at each of diff devices
}

//it will not have any battery
class SmartLight extends SmartDevice {
    public SmartLight(String deviceId, String deviceName){
        super(deviceId, deviceName);
    }

    @Override
    public void runDiagnostic(){
        System.out.println("SmartLight [" + deviceName +"] : Turning ON/OFF for diagnostic." );
    }
}

class SmartCamera extends SmartDevice implements BatteryOperated{
    private int batteryLevel;

    public SmartCamera(String deviceId, String deviceName, int batteryLevel){
        super(deviceId, deviceName);
        this.batteryLevel = batteryLevel;
    }

    @Override
    public void runDiagnostic(){
        System.out.println("SmartCamera [" + deviceName+ "] : Checking lens and recordinng fro diagnostic.");
    }

    @Override
    public int getBatteryLevel(){
        return batteryLevel;
    }

    @Override
    public void triggerRechargeAlert(){
        System.out.println("Recharge Alert! Camera " + deviceName+ " battery is low.");
    }
}

class SmartLock extends SmartDevice implements BatteryOperated{
    private int batteryLevel;

    public SmartLock(String deviceId, String deviceName, int batteryLevel){
        super(deviceId, deviceName);
        this.batteryLevel= batteryLevel;
    }

    @Override
    public void runDiagnostic(){
        System.out.println("SmartLock [" + deviceName+ "] : Locking/Unlocking fro diagnostic.");
    }

    @Override
    public int getBatteryLevel(){
        return batteryLevel;
    }

    @Override
    public void triggerRechargeAlert(){
        System.out.println("Recharge Alert! Lock " + deviceName + " battery is low.");
    }
}

class HomeHub{
    public void executeNightlyRoutine(SmartDevice[] devices){
        for(SmartDevice device : devices){
            device.runDiagnostic();

            if(device instanceof BatteryOperated){
                BatteryOperated batteryDevice= (BatteryOperated) device;

                if(batteryDevice.getBatteryLevel()<20){
                    batteryDevice.triggerRechargeAlert();
                }
            }
            System.out.println();
        }
    }
}

public class EcoSmartSystem{
    
    public static void main(String[] args){
        SmartDevice[] devices= {
            new SmartLight("L101", "Living Room Light"),
            new SmartCamera("C201", "Front Door Camera", 15),
            new SmartLock("S301", "Main gate Lock", 10),
            new SmartCamera("C202", "Garage Camera", 75)
        };

        HomeHub hub= new HomeHub();
        hub.executeNightlyRoutine(devices);
    }
}

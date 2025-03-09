package org.projetoredes.abstractions;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;

import java.util.List;

public abstract class ClientCommandsHandler implements ClientCommands {
    protected String manageCommands(String command){
        switch(command){
            case "connectiontest":
                return "connected";
            case "processor":
                return String.valueOf(getProcessorsQuantity());
            case "freeram":
                return String.format("%.1f", getFreeRam()) + " GB";
            case "freedisk":
                return String.format("%.0f", getFreeDisk()) + " GB";
            case "temp":
                return getProcessorTemperature() + " °C";
            case "media":
                return String.format("%.2f", getMedia());
            default:
                return "Comando invalido.";
        }
    }


    @Override
    public int getProcessorsQuantity() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        return processor.getPhysicalProcessorCount();
    }

    @Override
    public double getFreeRam() {
        SystemInfo si = new SystemInfo();
        long ramBytesAvailable = si.getHardware().getMemory().getAvailable();
        return (ramBytesAvailable / 1073741824.0);
    }

    @Override
    public double getFreeDisk() {
        SystemInfo si = new SystemInfo();

        List<OSFileStore> fileSystem = si.getOperatingSystem().getFileSystem().getFileStores();
        long freeDiskBytes = 0;
        for(OSFileStore store : fileSystem){
            if(store.getMount().charAt(0) == 'C' || store.getMount().charAt(0) == '/'){
                freeDiskBytes += store.getUsableSpace();
            }
        }

        return (freeDiskBytes / 1073741824.0);
    }

    @Override
    public double getProcessorTemperature() {
        SystemInfo si = new SystemInfo();
        return si.getHardware().getSensors().getCpuTemperature();
    }

    @Override
    public double getMedia(){
        return (getProcessorsQuantity() + getFreeRam() + getFreeDisk() + getProcessorTemperature()) / 4;
    }
}

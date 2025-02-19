package org.projetoredes.abstractions;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSFileStore;
import org.projetoredes.enums.Commands;

import java.util.List;

public abstract class ClientCommandsHandler implements ClientSocket {
    
    public String manageCommands(String command) {
        Commands commandEnum = Commands.fromString(command);
        
        if (commandEnum == null) {
            return "Command not found";
        }
        
        switch (commandEnum) {
            case Processor:
                return String.valueOf(getProcessorsQuantity());
            case FreeRam:
                return String.format("%.1f", getFreeRam());
            case FreeDisk:
                return String.valueOf(getFreeDisk());
            case ProcessorTemp:
                return String.valueOf(getProcessorTemperature());
            default:
                return "Command not found";
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
        for (OSFileStore store : fileSystem) {
            if (store.getMount().charAt(0) == 'C' || store.getMount().charAt(0) == '/') {
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
}

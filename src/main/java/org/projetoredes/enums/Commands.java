package org.projetoredes.enums;

public enum Commands {
    Processor("processor"),
    FreeRam("freeram"),
    FreeDisk("freedisk"),
    ProcessorTemp("processortemp");

    private final String command;

    Commands(String command){
        this.command = command;
    }
    
    public String getCommand() {
        return command;
    }

    public static Commands fromString(String command) {
        for (Commands c : Commands.values()) {
            if (c.getCommand().equalsIgnoreCase(command)) {
                return c;
            }
     }
     return null;
    }
}

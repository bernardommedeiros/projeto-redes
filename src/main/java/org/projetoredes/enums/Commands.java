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
}

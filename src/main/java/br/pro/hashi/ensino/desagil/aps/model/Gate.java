package br.pro.hashi.ensino.desagil.aps.model;

public abstract class Gate implements Emitter, Receiver {
    private final String name;
    private final int inputSize;

    protected Gate(String name, int inputSize) {
        this.name = name;
        this.inputSize = inputSize;
    }

    public String toString() {
        return name;
    }

// --Commented out by Inspection START (04/04/2020 12:32):
//    public int getInputSize() {
//        return inputSize;
//    }
// --Commented out by Inspection STOP (04/04/2020 12:32)
}

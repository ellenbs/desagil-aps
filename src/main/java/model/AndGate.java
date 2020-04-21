package model;

public class AndGate extends Gate {
    private final NandGate nand0;
    private final NandGate nand;

    public AndGate() {
        super("AND", 2);

        nand0 = new NandGate();
        nand = new NandGate();

        nand.connect(0, nand0);
        nand.connect(1, nand0);
    }

    @Override
    public boolean read() {
        return nand.read();
    }

    @Override
    public void connect(int inputIndex, Emitter emitter) {
        if (inputIndex < 0 || inputIndex > 1) {
            throw new IndexOutOfBoundsException(inputIndex);
        }
        nand0.connect(inputIndex, emitter);
    }
}

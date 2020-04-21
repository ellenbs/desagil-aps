package model;

public class OrGate extends Gate {
    private
    final NandGate nand2;
    private final NandGate nand;
    private final NandGate nand1;

    public OrGate() {
        super("OR", 2);

        nand = new NandGate();
        nand1 = new NandGate();
        nand2 = new NandGate();

        nand2.connect(0, nand);
        nand2.connect(1, nand1);
    }

    @Override
    public boolean read() {
        return nand2.read();
    }


    @Override
    public void connect(int inputIndex, Emitter emitter) {
        if (inputIndex < 0 || inputIndex > 1) {
            throw new IndexOutOfBoundsException(inputIndex);
        } else if (inputIndex == 0) {
            nand.connect(0, emitter);
            nand.connect(1, emitter);
        } else {
            nand1.connect(0, emitter);
            nand1.connect(1, emitter);
        }
    }
}

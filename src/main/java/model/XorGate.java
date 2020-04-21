package model;

public class XorGate extends Gate {
    private final NandGate nand3;
    private final NandGate nand0;
    private final NandGate nand1;
    private final NandGate nand2;


    public XorGate() {
        super("XOR", 2);

        nand0 = new NandGate();
        nand1 = new NandGate();
        nand2 = new NandGate();
        nand3 = new NandGate();

        nand0.connect(0, nand1);
        nand0.connect(1, nand2);
        nand1.connect(1, nand3);
        nand2.connect(0, nand3);

    }

    @Override
    public boolean read() {
        return nand0.read();
    }

    @Override
    public void connect(int inputIndex, Emitter emitter) {
        if (inputIndex < 0 || inputIndex > 1) {
            throw new IndexOutOfBoundsException(inputIndex);
        } else if (inputIndex == 0) {
            nand1.connect(0, emitter);
            nand3.connect(0, emitter);

        } else {
            nand3.connect(1, emitter);
            nand2.connect(1, emitter);
        }
    }
}

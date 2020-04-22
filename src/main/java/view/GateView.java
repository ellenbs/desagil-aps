package view;

import model.Gate;
import model.Light;
import model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.LinkedList;

public class GateView extends FixedPanel implements ActionListener, MouseListener {
    private final Gate portao;

    private final LinkedList<JCheckBox> entradas;
    private final LinkedList<Switch> cabos;

    private final Image image;

    //Links das imagens retiradas da internet:
    //https://en.wikipedia.org/wiki/Electronic_symbol

    private final Light luz;

    public GateView(Gate portao) {

        // Como subclasse de FixedPanel, esta classe agora
        // exige que uma largura e uma altura sejam fixadas.
        super(245, 200);

        this.portao = portao;

        entradas = new LinkedList<>();
        cabos = new LinkedList<>();

        int inputSize = portao.getInputSize();


        for (int i = 0; i < inputSize; i++) {
            entradas.add(i, new JCheckBox());
            cabos.add(i, new Switch());

            portao.connect(i, cabos.get(i));
        }

        luz = new Light(255, 0, 0);

        // Usamos esse carregamento nos Desafios, vocês lembram?
        String name = portao.toString() + ".PNG";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        int y = 0;
        int incremento = 200 / inputSize;
        int inicial = (incremento / 2);

        for (JCheckBox caixa : entradas) {
            caixa.addActionListener(this);
            add(caixa, 7, inicial + y, 20, 25);
            System.out.println(inicial + y);
            y += incremento - 40;


        }


        addMouseListener(this);

        update();

    }

    // Toda componente Swing tem uma lista de observadores
    // que reagem quando algum evento de mouse acontece.
    // Usamos o método addMouseListener para adicionar a
    // própria componente, ou seja "this", nessa lista.
    // Só que addMouseListener espera receber um objeto
    // do tipo MouseListener como parâmetro. É por isso que
    // adicionamos o "implements MouseListener" lá em cima.

    private void update() {

        for (int i = 0; i < portao.getInputSize(); i++) {
            boolean verifica;
            verifica = entradas.get(i).isSelected();
            if (verifica) {
                cabos.get(i).turnOn();
            } else {
                cabos.get(i).turnOff();
            }
        }

        luz.connect(0, portao);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        // Se o clique foi dentro do quadrado colorido...
        if ((x - 200) * (x - 200) + (y - 92) * (y - 92) < 100) {

            // ...então abrimos a janela seletora de cor..

            luz.setColor(JColorChooser.showDialog(this, null, Color.RED));
            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 10, 5, 200, 200, this);

        // Desenha um quadrado cheio.
        g.setColor(luz.getColor());
        g.fillOval(190, 82, 20, 20);
        repaint();

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}

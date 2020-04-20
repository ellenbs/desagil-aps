package br.pro.hashi.ensino.desagil.aps.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {
    private final Gate portao;

    private final JCheckBox entrada;
    private final JCheckBox entrada_1;

    private final Switch cabo1;
    private final Switch cabo2;

    private final boolean c;

    private final Image image;

    //Links das imagens retiradas da internet:
    //https://en.wikipedia.org/wiki/Electronic_symbol

    private final Light luz;

    public GateView(Gate portao) {

        // Como subclasse de FixedPanel, esta classe agora
        // exige que uma largura e uma altura sejam fixadas.
        super(245, 200);

        this.portao = portao;
g
        entrada = new JCheckBox();
        entrada_1 = new JCheckBox();

        cabo1 = new Switch();
        cabo2 = new Switch();

        luz = new Light(255, 0, 0);

        portao.connect(0, cabo1);

        // Usamos esse carregamento nos Desafios, vocês lembram?
        String name = portao.toString() + ".PNG";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        entrada.addActionListener(this);

        addMouseListener(this);

        update();

        if (portao.getInputSize() > 1) {
            c = true;

            portao.connect(1, cabo2);
            add(entrada, 7, 60, 20, 25);
            add(entrada_1, 7, 125, 20, 25);

            entrada_1.addActionListener(this);

        } else {
            c = false;
            add(entrada, 7, 95, 20, 25);
        }
    }

    // Toda componente Swing tem uma lista de observadores
    // que reagem quando algum evento de mouse acontece.
    // Usamos o método addMouseListener para adicionar a
    // própria componente, ou seja "this", nessa lista.
    // Só que addMouseListener espera receber um objeto
    // do tipo MouseListener como parâmetro. É por isso que
    // adicionamos o "implements MouseListener" lá em cima.

    private void update() {
        boolean entrada1;

        entrada1 = entrada.isSelected();

        if (!entrada1) {
            cabo1.turnOff();
        } else {
            cabo1.turnOn();
        }

        luz.connect(0, portao);

        if (c) {
            boolean entrada2;

            entrada2 = entrada_1.isSelected();

            if (!entrada2) {
                cabo2.turnOff();
            } else {
                cabo2.turnOn();
            }
        }
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
        if ((x - 200) * (x - 200) + (y - 105) * (y - 105) < 100) {

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
        g.fillOval(190, 95, 20, 20);
        repaint();

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}

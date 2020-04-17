package br.pro.hashi.ensino.desagil.aps.model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GateView extends FixedPanel implements ActionListener, MouseListener {
    private final Gate portao;

    private final JCheckBox entrada;
    private final JCheckBox entrada_1;

    private final JCheckBox saida;

    private final Switch cabo1;
    private final Switch cabo2;

    private final boolean c;

    // Novos atributos necessários para esta versão da interface.
    private Color color;

    public GateView(Gate portao) {

        // Como subclasse de FixedPanel, esta classe agora
        // exige que uma largura e uma altura sejam fixadas.
        super(245, 200);

        this.portao = portao;



        entrada = new JCheckBox();
        entrada_1 = new JCheckBox();

        cabo1 = new Switch();
        cabo2 = new Switch();

        portao.connect(0, cabo1);

        saida = new JCheckBox();

        JLabel entradaLabel = new JLabel("Entrada:");
        JLabel saidaLabel = new JLabel("Saída:");

        add(entradaLabel, 10, 10, 75, 25);
        add(entrada, 10, 40, 150, 25);
        add(saidaLabel, 10, 100, 75, 25);
        add(saida, 10, 130, 150, 25);
        // Inicializamos o atributo de cor simplesmente como preto.
        color = Color.BLACK;

        entrada.addActionListener(this);
        saida.setEnabled(false);

        addMouseListener(this);

        update();

        if (portao.getInputSize() > 1) {
            c = true;

            portao.connect(1, cabo2);
            add(entrada_1, 10, 70, 150, 25);

            entrada_1.addActionListener(this);

        }else{c = false;}


        //radiusField.addActionListener(this);


        // Toda componente Swing tem uma lista de observadores
        // que reagem quando algum evento de mouse acontece.
        // Usamos o método addMouseListener para adicionar a
        // própria componente, ou seja "this", nessa lista.
        // Só que addMouseListener espera receber um objeto
        // do tipo MouseListener como parâmetro. É por isso que
        // adicionamos o "implements MouseListener" lá em cima.

    }

    private void update() {
        boolean entrada1;

        entrada1 = entrada.isSelected();

        if (!entrada1) {
            cabo1.turnOff();
        } else {
            cabo1.turnOn();
        }

        if (portao.read()) {
            saida.setSelected(true);
        } else {
            saida.setSelected(false);
        }

        if (c) {
            boolean entrada2;

            entrada2 = entrada_1.isSelected();

            if (!entrada2) {
                cabo2.turnOff();
            } else {
                cabo2.turnOn();
            }

            if (portao.read()) {
                saida.setSelected(true);
            } else {
                saida.setSelected(false);
            }
        }
    }


//        boolean result = gate.connect(entrada_nova, gate.read());

//        saida.setText(Boolean.toString(result));

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
        if (x >= 210 && x < 235 && y >= 311 && y < 336) {

            // ...então abrimos a janela seletora de cor...
            color = JColorChooser.showDialog(this, null, color);

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
        // g.drawImage(image, 10, 80, 221, 221, this);

        // Desenha um quadrado cheio.
        g.setColor(color);
        g.fillRect(210, 311, 25, 25);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Breakout extends JPanel implements KeyListener, ActionListener {
    private int x = 20, y = 200, dx = 2, dy = 2, tamanho = 10;
    private boolean vivo = false;
    private int barraX = 150, fase = 1, pontos = 0;
    private ArrayList<Rectangle> blocos = new ArrayList<>();
    private Timer timer;

    public Breakout() {
        setPreferredSize(new Dimension(400, 450));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(10, this);
        inicio();
        timer.start();
    }

    public void inicio() {
        dx = 2;
        dy = 3;
        x = 20;
        y = 200;
        vivo = true;
        barraX = 150;
        blocos.clear();
        for (int c = 0; c < fase; c++) {
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 8; i++) {
                    blocos.add(new Rectangle(i * 50, j * 20, 50, 20));
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(barraX, 430, 100, 20);
        if (vivo) {
            for (Rectangle bloco : blocos) {
                g.setColor(Color.BLUE);
                g.fillRect(bloco.x, bloco.y, bloco.width, bloco.height);
            }
            g.setColor(Color.GREEN);
            g.fillOval(x, y, tamanho, tamanho);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (vivo) {
            x += dx;
            y += dy;
            if (y >= 430 - tamanho) {
                if (x > barraX && x < barraX + 100) {
                    dy = -dy;
                } else {
                    vivo = false;
                    pontos = 0;
                }
            }
            if (y <= 0 + tamanho) {
                dy = -dy;
            }
            if (x >= 400 - tamanho || x <= 0 + tamanho) {
                dx = -dx;
            }
            for (int i = blocos.size() - 1; i >= 0; i--) {
                Rectangle bloco = blocos.get(i);
                if (bloco.intersects(new Rectangle(x, y, tamanho, tamanho))) {
                    dy = -dy;
                    blocos.remove(i);
                    pontos++;
                }
            }
            if (blocos.isEmpty()) {
                fase++;
                inicio();
            }
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            barraX -= 40;
            if (barraX < 0) {
                barraX = 0;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            barraX += 40;
            if (barraX > 300) {
                barraX = 300;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Breakout");
        Breakout breakout = new Breakout();
        frame.add(breakout);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

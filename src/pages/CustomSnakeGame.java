package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class CustomSnakeGame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private DrawingPanel drawingPanel;
    private GamePanel gamePanel;

    public CustomSnakeGame() {
        setTitle("커스텀 스네이크 게임");
        // [수정] EXIT_ON_CLOSE는 메인 앱까지 종료시키므로 DISPOSE_ON_CLOSE로 변경
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        drawingPanel = new DrawingPanel(this);
        gamePanel = new GamePanel(this);

        mainPanel.add(drawingPanel, "DRAW");
        mainPanel.add(gamePanel, "GAME");

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void showGame(BufferedImage snakeImage) {
        gamePanel.setSnakeImage(snakeImage);
        cardLayout.show(mainPanel, "GAME");
        gamePanel.requestFocusInWindow();
        gamePanel.startGame();
    }
    
    public void showDrawing() {
        cardLayout.show(mainPanel, "DRAW");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomSnakeGame());
    }
}

class DrawingPanel extends JPanel {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 700;
    private static final int CANVAS_SIZE = 400;

    private BufferedImage canvas;
    private Graphics2D g2d;
    private int lastX, lastY;
    private Color currentColor = Color.GREEN;
    private int brushSize = 10;
    private CustomSnakeGame parent;

    public DrawingPanel(CustomSnakeGame parent) {
        this.parent = parent;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.LIGHT_GRAY);

        canvas = new BufferedImage(CANVAS_SIZE, CANVAS_SIZE, BufferedImage.TYPE_INT_ARGB);
        g2d = canvas.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearCanvas();

        setupMouseListeners();
    }

    private void clearCanvas() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
    }

    private void setupMouseListeners() {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int canvasX = (WIDTH - CANVAS_SIZE) / 2;
                int canvasY = 50;

                if (e.getX() >= canvasX && e.getX() <= canvasX + CANVAS_SIZE &&
                    e.getY() >= canvasY && e.getY() <= canvasY + CANVAS_SIZE) {
                    lastX = e.getX() - canvasX;
                    lastY = e.getY() - canvasY;
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int canvasX = (WIDTH - CANVAS_SIZE) / 2;
                int canvasY = 50;

                if (e.getX() >= canvasX && e.getX() <= canvasX + CANVAS_SIZE &&
                    e.getY() >= canvasY && e.getY() <= canvasY + CANVAS_SIZE) {
                    int x = e.getX() - canvasX;
                    int y = e.getY() - canvasY;

                    g2d.setColor(currentColor);
                    g2d.setStroke(new BasicStroke(brushSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2d.drawLine(lastX, lastY, x, y);

                    lastX = x;
                    lastY = y;
                    repaint();
                }
            }
        };

        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        g2.drawString("뱀 디자인을 그려주세요!", 180, 30);

        int canvasX = (WIDTH - CANVAS_SIZE) / 2;
        int canvasY = 50;
        g2.drawImage(canvas, canvasX, canvasY, null);
        g2.setColor(Color.BLACK);
        g2.drawRect(canvasX - 1, canvasY - 1, CANVAS_SIZE + 1, CANVAS_SIZE + 1);

        int controlY = canvasY + CANVAS_SIZE + 30;

        g2.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        g2.setColor(Color.BLACK);
        g2.drawString("색상:", 50, controlY);
        Color[] colors = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW,
                         Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA};
        for (int i = 0; i < colors.length; i++) {
            g2.setColor(colors[i]);
            g2.fillRect(110 + i * 40, controlY - 15, 30, 30);
            if (colors[i].equals(currentColor)) {
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(110 + i * 40, controlY - 15, 30, 30);
            }
        }

        g2.setColor(Color.BLACK);
        g2.drawString("브러시 크기: " + brushSize, 50, controlY + 50);

        // --- [수정] 버튼 그리기 영역 ---
        drawButton(g2, "지우기", 50, controlY + 80, 100, 40);
        drawButton(g2, "크기 +/-", 170, controlY + 80, 100, 40);
        drawButton(g2, "게임 시작!", 290, controlY + 80, 150, 40);
        // '돌아가기' 버튼 추가
        drawButton(g2, "돌아가기", 460, controlY + 80, 100, 40);
    }

    private void drawButton(Graphics2D g2, String text, int x, int y, int w, int h) {
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(x, y, w, h);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, w, h);
        g2.drawString(text, x + (w - g2.getFontMetrics().stringWidth(text)) / 2, y + h / 2 + 7);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int canvasY = 50;
                int controlY = canvasY + CANVAS_SIZE + 30;

                // 색상 선택
                Color[] colors = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW,
                                 Color.ORANGE, Color.PINK, Color.CYAN, Color.MAGENTA};
                for (int i = 0; i < colors.length; i++) {
                    Rectangle rect = new Rectangle(110 + i * 40, controlY - 15, 30, 30);
                    if (rect.contains(e.getPoint())) {
                        currentColor = colors[i];
                        repaint();
                        return;
                    }
                }

                // 지우기 버튼
                if (new Rectangle(50, controlY + 80, 100, 40).contains(e.getPoint())) {
                    clearCanvas();
                    repaint();
                }

                // 브러시 크기 버튼
                if (new Rectangle(170, controlY + 80, 100, 40).contains(e.getPoint())) {
                    brushSize = (brushSize % 20) + 5;
                    repaint();
                }

                // 게임 시작 버튼
                if (new Rectangle(290, controlY + 80, 150, 40).contains(e.getPoint())) {
                    parent.showGame(copyImage(canvas));
                }
                
                // --- [수정] '돌아가기' 버튼 클릭 핸들러 추가 ---
                if (new Rectangle(460, controlY + 80, 100, 40).contains(e.getPoint())) {
                    // CustomSnakeGame JFrame 창을 닫습니다.
                    parent.dispose(); 
                }
            }
        });
    }

    private BufferedImage copyImage(BufferedImage source) {
        BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics2D g2 = copy.createGraphics();
        g2.drawImage(source, 0, 0, null);
        g2.dispose();
        return copy;
    }
}


class GamePanel extends JPanel implements ActionListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 100;
    
    private final int[] x = new int[GAME_UNITS];
    private final int[] y = new int[GAME_UNITS];
    private int bodyParts = 3;
    private int applesEaten = 0;
    private int appleX, appleY;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Random random;
    private BufferedImage snakeImage;
    private CustomSnakeGame parent;
    
    public GamePanel(CustomSnakeGame parent) {
        this.parent = parent;
        random = new Random();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
    }
    
    public void setSnakeImage(BufferedImage img) {
        this.snakeImage = img;
    }
    
    public void startGame() {
        bodyParts = 3;
        applesEaten = 0;
        direction = 'R';
        
        for (int i = 0; i < bodyParts; i++) {
            x[i] = UNIT_SIZE * (5 - i);
            y[i] = UNIT_SIZE * 5;
        }
        
        newApple();
        running = true;
        
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    private void draw(Graphics g) {
        if (running) {
            // 그리드 (옵션)
            g.setColor(new Color(30, 30, 30));
            for (int i = 0; i < HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, WIDTH, i * UNIT_SIZE);
            }
            
            // 사과
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            
            // 뱀
            for (int i = 0; i < bodyParts; i++) {
                if (snakeImage != null) {
                    g.drawImage(snakeImage, x[i], y[i], UNIT_SIZE, UNIT_SIZE, null);
                } else {
                    if (i == 0) g.setColor(Color.GREEN);
                    else g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            
            // 점수
            g.setColor(Color.WHITE);
            g.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            g.drawString("점수: " + applesEaten, 10, 25);
            
        } else {
            gameOver(g);
        }
    }
    
    private void newApple() {
        appleX = random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }
    
    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        
        switch (direction) {
            case 'U': y[0] -= UNIT_SIZE; break;
            case 'D': y[0] += UNIT_SIZE; break;
            case 'L': x[0] -= UNIT_SIZE; break;
            case 'R': x[0] += UNIT_SIZE; break;
        }
    }
    
    private void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    
    private void checkCollisions() {
        // 자기 몸과 충돌
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
            }
        }
        
        // 벽과 충돌
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            running = false;
        }
        
        if (!running) {
            timer.stop();
        }
    }
    
    private void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("맑은 고딕", Font.BOLD, 50));
        FontMetrics fm = getFontMetrics(g.getFont());
        g.drawString("게임 오버!", (WIDTH - fm.stringWidth("게임 오버!")) / 2, HEIGHT / 2 - 50);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        fm = getFontMetrics(g.getFont());
        g.drawString("점수: " + applesEaten, (WIDTH - fm.stringWidth("점수: " + applesEaten)) / 2, HEIGHT / 2 + 20);
        
        g.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        fm = getFontMetrics(g.getFont());
        g.drawString("SPACE: 다시 시작  ESC: 그림판으로", 
                    (WIDTH - fm.stringWidth("SPACE: 다시 시작  ESC: 그림판으로")) / 2, 
                    HEIGHT / 2 + 80);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') direction = 'D';
                    break;
                case KeyEvent.VK_SPACE:
                    if (!running) startGame();
                    break;
                
                // --- [수정] ESC 키 로직 변경 ---
                case KeyEvent.VK_ESCAPE:
                    if (!running) {
                        // 게임 오버 상태: 그림판으로 돌아가기
                        parent.showDrawing();
                    } else {
                        // 게임 실행 중: 메인 메뉴로 돌아갈지 묻기
                        int choice = JOptionPane.showConfirmDialog(
                            parent, 
                            "게임을 종료하고 메인 메뉴로 돌아가시겠습니까?", 
                            "게임 종료", 
                            JOptionPane.YES_NO_OPTION
                        );
                        
                        if (choice == JOptionPane.YES_OPTION) {
                            running = false;
                            timer.stop();
                            parent.dispose(); // CustomSnakeGame 창 닫기
                        }
                    }
                    break;
            }
        }
    }
}
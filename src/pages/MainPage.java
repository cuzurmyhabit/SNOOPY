package pages;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel {

    private Main mainApp; // [수정] MainApp -> Main

    // [수정] MainApp -> Main
    public MainPage(Main mainApp) {
        this.mainApp = mainApp;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(100, 70, 100, 70));

        // --- START! 버튼 ---
        JButton startButton = new JButton("START!");
        startButton.setFont(new Font("Arial", Font.BOLD, 55));
        StyleManager.setCustomButtonStyle(startButton, Color.BLACK, Color.WHITE, new Dimension(0, 200));
        
        startButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new CustomSnakeGame());
        });
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(startButton);
        add(centerPanel, BorderLayout.CENTER);

        // --- 하단 버튼 2개 (랭킹, 맵 커스텀) ---
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.setBackground(Color.WHITE);

        JButton rankButton = new JButton("랭킹 보기");
        StyleManager.setCustomButtonStyle(rankButton, Color.WHITE, Color.BLACK, new Dimension(0, 45));
        rankButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        
        // [수정] 랭킹 보기 버튼 클릭 시 "RANKING" 패널 표시
        rankButton.addActionListener(e -> mainApp.showPanel("RANKING"));
        
        bottomPanel.add(rankButton);

        JButton mapButton = new JButton("맵 커스텀");
        StyleManager.setCustomButtonStyle(mapButton, Color.WHITE, Color.BLACK, new Dimension(0, 45));
        mapButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        
        mapButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new CustomSnakeGame());
        });
        
        bottomPanel.add(mapButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
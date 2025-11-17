package pages;

import javax.swing.*;
import java.awt.*;

// [수정] 클래스 이름을 Main으로 변경
public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    // [수정] 생성자 이름을 Main으로 변경
    public Main() {
        // --- 기본 프레임 설정 ---
        setTitle("Snoopy App");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- CardLayout 설정 ---
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- 각 화면(패널) 생성 ---
        // [수정] MainApp 대신 'this' (Main 객체) 전달
        LoginPage loginPage = new LoginPage(this);
        SignUpPage signUpPage = new SignUpPage(this);
        MainPage mainPage = new MainPage(this);
        RankingPage rankingPage = new RankingPage(this);

        // --- mainPanel에 각 화면 추가 ---
        mainPanel.add(loginPage, "LOGIN");
        mainPanel.add(signUpPage, "SIGNUP");
        mainPanel.add(mainPage, "MAIN");
        mainPanel.add(rankingPage, "RANKING");

        // --- 프레임에 mainPanel 추가 ---
        add(mainPanel);

        // 프레임을 보이게 설정
        setVisible(true);
    }

    /**
     * CardLayout의 화면을 전환하는 공용 메소드
     * @param panelName "LOGIN", "SIGNUP", "MAIN" 등
     */
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    // --- main 메소드 (프로그램 실행) ---
    public static void main(String[] args) {
        // [수정] Main 클래스의 인스턴스 생성
        SwingUtilities.invokeLater(() -> new Main());
    }
}
package pages;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;

public class LoginPage extends JPanel {

    private Main main;

    public LoginPage(Main main) {
        this.main = main;
        
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);

        // --- 로고 ---
        try {
            // [수정] logo.png를 pages 폴더 내에서 찾도록 경로 수정
            Image img = ImageIO.read(getClass().getResource("logo.png")); 
            Image scaledImg = img.getScaledInstance(150, -1, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImg));
            add(logoLabel, gbc);
        } catch (Exception e) {
            JLabel logoPlaceholder = new JLabel("SNOOPY (pages/logo.png 없음)");
            logoPlaceholder.setFont(new Font("SansSerif", Font.BOLD, 20));
            logoPlaceholder.setHorizontalAlignment(JLabel.CENTER);
            add(logoPlaceholder, gbc);
        }

        // --- 아이디 필드 ---
        JTextField idField = new JTextField(20);
        StyleManager.setupPlaceholder(idField, "아이디 입력");
        StyleManager.setCustomFieldStyle(idField);
        add(idField, gbc);

        // --- 비밀번호 필드 ---
        JPasswordField passField = new JPasswordField(20);
        StyleManager.setupPlaceholder(passField, "비밀번호 입력");
        StyleManager.setCustomFieldStyle(passField);
        add(passField, gbc);

        // --- 로그인 버튼 ---
        JButton loginButton = new JButton("로그인");
        StyleManager.setCustomButtonStyle(loginButton, Color.BLACK, Color.WHITE, new Dimension(0, 45));
        
        // [수정] MainApp의 showPanel 메소드 호출
        loginButton.addActionListener(e -> main.showPanel("MAIN"));
        add(loginButton, gbc);

        // --- 회원가입 버튼 (링크 스타일) ---
        JButton signUpButton = new JButton("회원가입");
        StyleManager.setCustomLinkStyle(signUpButton);

        // [수정] MainApp의 showPanel 메소드 호출
        signUpButton.addActionListener(e -> main.showPanel("SIGNUP"));
        
        gbc.insets = new Insets(0, 50, 10, 50);
        add(signUpButton, gbc);
    }
}
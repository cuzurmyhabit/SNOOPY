package pages;

import javax.swing.*;
import java.awt.*;

public class SignUpPage extends JPanel {

    private Main main;

    public SignUpPage(Main main) {
        this.main = main;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);

        // --- 제목 ---
        JLabel title = new JLabel("회원가입");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setHorizontalAlignment(JLabel.CENTER);
        gbc.insets = new Insets(10, 50, 20, 50);
        add(title, gbc);

        gbc.insets = new Insets(10, 50, 10, 50);

        // --- 이름 필드 ---
        JTextField nameField = new JTextField(20);
        StyleManager.setupPlaceholder(nameField, "이름 입력");
        StyleManager.setCustomFieldStyle(nameField);
        add(nameField, gbc);

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

        // --- 가입하기 버튼 ---
        JButton signUpButton = new JButton("가입하기");
        StyleManager.setCustomButtonStyle(signUpButton, Color.BLACK, Color.WHITE, new Dimension(0, 45));
        
        signUpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다!");
            main.showPanel("LOGIN"); // [수정] MainApp의 showPanel 메소드 호출
        });
        add(signUpButton, gbc);

        // --- 돌아가기 버튼 (링크 스타일) ---
        JButton backButton = new JButton("로그인 화면으로 돌아가기");
        StyleManager.setCustomLinkStyle(backButton);
        backButton.addActionListener(e -> main.showPanel("LOGIN")); // [수정]
        
        gbc.insets = new Insets(0, 50, 10, 50);
        add(backButton, gbc);
    }
}
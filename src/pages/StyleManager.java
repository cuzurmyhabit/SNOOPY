package pages;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.text.JTextComponent;

public class StyleManager {

    /**
     * 텍스트 필드, 패스워드 필드에 공통 스타일 적용
     */
    public static void setCustomFieldStyle(JComponent field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(0, 45)); // 높이 설정 (가로는 레이아웃이 조절)
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 2), // 둥근 느낌의 테두리
            BorderFactory.createEmptyBorder(5, 15, 5, 15) // 안쪽 여백
        ));
    }

    /**
     * 버튼에 공통 스타일 적용 (색상, 폰트, 크기)
     */
    public static void setCustomButtonStyle(JButton button, Color bg, Color fg, Dimension size) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setPreferredSize(size);
        button.setFocusPainted(false);
        button.setBorderPainted(false); // 테두리 없음 (검은 버튼용)
        button.setOpaque(true); // Mac에서 배경색이 보이게 함
    }

    /**
     * '회원가입', '돌아가기' 같은 링크 스타일 버튼
     */
    public static void setCustomLinkStyle(JButton button) {
        button.setForeground(Color.GRAY);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 올리면 손가락 모양
    }

    /**
     * 텍스트 필드에 'placeholder' (안내 문구) 기능 추가
     */
    public static void setupPlaceholder(JTextComponent component, String placeholder) {
        component.setText(placeholder);
        component.setForeground(Color.GRAY);

        // JPasswordField의 경우 초기 '•' 문자 숨기기
        if (component instanceof JPasswordField) {
            ((JPasswordField) component).setEchoChar((char) 0);
        }

        component.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String currentText = (component instanceof JPasswordField) 
                    ? String.valueOf(((JPasswordField) component).getPassword())
                    : component.getText();

                if (currentText.equals(placeholder)) {
                    component.setText("");
                    component.setForeground(Color.BLACK);
                    // JPasswordField의 경우 '•' 문자 다시 보이기
                    if (component instanceof JPasswordField) {
                        ((JPasswordField) component).setEchoChar('•');
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String currentText = (component instanceof JPasswordField)
                    ? String.valueOf(((JPasswordField) component).getPassword())
                    : component.getText();

                if (currentText.isEmpty()) {
                    component.setText(placeholder);
                    component.setForeground(Color.GRAY);
                    // JPasswordField의 경우 '•' 문자 다시 숨기기
                    if (component instanceof JPasswordField) {
                        ((JPasswordField) component).setEchoChar((char) 0);
                    }
                }
            }
        });
    }
}
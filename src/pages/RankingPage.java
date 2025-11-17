package pages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RankingPage extends JPanel {

    private Main main;

    public RankingPage(Main main) {
        this.main = main;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // --- 1. 제목 ---
        JLabel title = new JLabel("랭킹");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, BorderLayout.NORTH);

        // --- 2. 랭킹 테이블 (임시 데이터) ---
        
        // 테이블 컬럼 이름
        String[] columnNames = {"순위", "이름", "점수"};

        // 테이블 데이터 (임시)
        // 나중에는 이 부분을 파일이나 DB에서 불러오도록 수정해야 합니다.
        Object[][] data = {
            {1, "Snoopy", 5200},
            {2, "Woodstock", 4500},
            {3, "Charlie", 3100},
            {4, "Lucy", 2500},
            {5, "Linus", 1200}
        };

        // 테이블 모델 생성 (셀 수정 불가)
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        
        // 테이블 스타일 설정
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);
        
        // 테이블을 스크롤 패널에 추가
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. 돌아가기 버튼 ---
        JButton backButton = new JButton("메인 화면으로 돌아가기");
        StyleManager.setCustomButtonStyle(backButton, Color.BLACK, Color.WHITE, new Dimension(0, 45));
        
        // 버튼 클릭 시 "MAIN" 패널 표시
        backButton.addActionListener(e -> main.showPanel("MAIN"));
        add(backButton, BorderLayout.SOUTH);
    }
}
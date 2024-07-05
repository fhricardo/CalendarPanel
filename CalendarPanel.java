import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class CalendarPanel extends JPanel {
    private YearMonth currentYearMonth;
    private JLabel monthLabel;
    private JPanel calendarPanel;
    private DateTimeFormatter monthFormatter;

    public CalendarPanel() {
        setLayout(new BorderLayout());
        currentYearMonth = YearMonth.now();
        monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

        // Cabeçalho do mês
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton previousButton = new JButton("<");
        previousButton.addActionListener(e -> previousMonth());
        monthLabel = new JLabel(currentYearMonth.format(monthFormatter));
        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> nextMonth());

        headerPanel.add(previousButton);
        headerPanel.add(monthLabel);
        headerPanel.add(nextButton);
        add(headerPanel, BorderLayout.NORTH);

        // Painel do calendário
        calendarPanel = new JPanel(new GridLayout(0, 7));
        updateCalendar();
        add(calendarPanel, BorderLayout.CENTER);
    }

    private void updateCalendar() {
        calendarPanel.removeAll();
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : daysOfWeek) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            calendarPanel.add(dayLabel);
        }

        LocalDate startDate = currentYearMonth.atDay(1);
        int startDayOfWeek = startDate.getDayOfWeek().getValue();
        for (int i = 1; i < startDayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        int daysInMonth = currentYearMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            int finalDay = day;
            dayButton.addActionListener(e -> {
                // Aqui você pode adicionar a lógica para lidar com a seleção do dia
                JOptionPane.showMessageDialog(null, "Você selecionou o dia " + finalDay);
            });
            calendarPanel.add(dayButton);
        }

        revalidate();
        repaint();
    }

    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        monthLabel.setText(currentYearMonth.format(monthFormatter));
        updateCalendar();
    }

    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        monthLabel.setText(currentYearMonth.format(monthFormatter));
        updateCalendar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calendário");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.add(new CalendarPanel());
            frame.setVisible(true);
        });
    }
}

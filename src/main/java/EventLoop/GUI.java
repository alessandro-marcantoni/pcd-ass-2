package EventLoop;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Date;

public class GUI {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    private final JFrame frame;
    private JTextField departure, arrival;
    private String time;
    private Date date;
    private JTable table;
    private JScrollPane scroll;

    public GUI() {
        this.frame = new JFrame("Assignment-2");

        this.createInputForm();
        this.createResultsTable();

        // this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // set the frame as large as the screen
        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setLayout(null);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    private void createInputForm() {
        // DEPARTURE
        final JLabel departure_label = new JLabel("Departure Station:");
        departure_label.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));

        this.departure = new JTextField();
        this.departure.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.15), (int) (HEIGHT * 0.06));

        // INVERT BTN
        final JButton invert = new JButton("@");
        invert.setBounds((int) (WIDTH * 0.22), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.046), (int) (HEIGHT * 0.06));

        // ARRIVAL
        final JLabel arrival_label = new JLabel("Arrival Station:");
        arrival_label.setBounds((int) (WIDTH * 0.29), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));

        this.arrival = new JTextField();
        this.arrival.setBounds((int) (WIDTH * 0.29), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.15), (int) (HEIGHT * 0.06));

        // PICKER
        final JLabel date = new JLabel("Departure Date & Time:");
        date.setBounds((int) (WIDTH * 0.47), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));

        final DateTimePicker picker = new DateTimePicker();
        picker.setBounds((int) (WIDTH * 0.47), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.06));

        this.frame.add(invert);
        this.frame.add(departure_label);
        this.frame.add(this.departure);
        this.frame.add(arrival_label);
        this.frame.add(this.arrival);
        this.frame.add(date);
        this.frame.add(picker);
    }

    private void createResultsTable() {
        String[] columns = {"Departure", "Arrival", "Duration", "Train"};
        Object[][] data = {{"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"},
                {"CESENA 11:43", "PESARO 13:21", "1:48", "Regionale Veloce 1256"}};

        table = new JTable(data, columns);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < columns.length; i++) table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        scroll = new JScrollPane(table);
        scroll.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.3), (int) (WIDTH * 0.88), (int) (HEIGHT * 0.5));

        this.frame.add(scroll);
    }

}

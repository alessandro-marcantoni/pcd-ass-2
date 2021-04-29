package EventLoop;

import com.github.lgooddatepicker.components.DateTimePicker;
import io.vertx.core.Future;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GUI {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    private final JFrame frame;
    private JTextField departure, arrival;
    private JTextArea details;
    private DateTimePicker picker;

    private final TrainSolution library = new TrainSolutionLibrary();

    private boolean running = false;

    public GUI() {
        this.frame = new JFrame("Assignment-2");

        this.createInputForm();
        this.fillTable(new ArrayList<>());

        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setLayout(null);
        this.frame.setResizable(false);
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
        final JButton invert = new JButton("< >");
        invert.setBounds((int) (WIDTH * 0.23), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.052), (int) (HEIGHT * 0.06));

        invert.addActionListener(e -> {
            String tmp = this.departure.getText();
            this.departure.setText(this.arrival.getText());
            this.arrival.setText(tmp);
        });

        // ARRIVAL
        final JLabel arrival_label = new JLabel("Arrival Station:");
        arrival_label.setBounds((int) (WIDTH * 0.31), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));

        this.arrival = new JTextField();
        this.arrival.setBounds((int) (WIDTH * 0.31), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.15), (int) (HEIGHT * 0.06));

        // PICKER
        final JLabel date = new JLabel("Departure Date:");
        date.setBounds((int) (WIDTH * 0.5), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));
        final JLabel time = new JLabel("Time:");
        time.setBounds((int) (WIDTH * 0.7), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));

        this.picker = new DateTimePicker();
        picker.setBounds((int) (WIDTH * 0.5), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.06));

        // SEARCH BTN
        final JButton search = new JButton("Search");
        search.setBounds((int) (WIDTH * 0.85), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.08), (int) (HEIGHT * 0.06));

        search.addActionListener(e -> {
            final SolutionDetails details = new SolutionDetails(this.departure.getText(), this.arrival.getText(), this.picker.getDatePicker().getDate().toString(), this.picker.getTimePicker().getTime().toString());
            Future<List<Solution>> future = library.getTrainSolutions(details);
            future.onSuccess(this::fillTable);
        });

        // DETAILS BOX
        final JLabel train_details = new JLabel("Train Details:");
        train_details.setBounds((int) (WIDTH * 0.7), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.04));

        final JTextField train = new JTextField();
        train.setBounds((int) (WIDTH * 0.78), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        final JButton monitor = new JButton("Go");
        monitor.setBounds((int) (WIDTH * 0.83), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));
        final JButton stop = new JButton("X");
        stop.setBounds((int) (WIDTH * 0.88), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        monitor.addActionListener(e -> {
            if(!train.getText().isEmpty()) {
                this.running = true;
                this.fillDetails(train.getText());

            }
        });

        stop.addActionListener(e -> this.running = false);

        this.details = new JTextArea();
        this.details.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        final JScrollPane panel = new JScrollPane(this.details);
        panel.setBounds((int) (WIDTH * 0.7), (int) (HEIGHT * 0.25), (int) (WIDTH * 0.23), (int) (HEIGHT * 0.6));

        this.frame.add(invert);
        this.frame.add(search);
        this.frame.add(departure_label);
        this.frame.add(this.departure);
        this.frame.add(arrival_label);
        this.frame.add(this.arrival);
        this.frame.add(date);
        this.frame.add(time);
        this.frame.add(picker);
        this.frame.add(train_details);
        this.frame.add(panel);
        this.frame.add(train);
        this.frame.add(monitor);
        this.frame.add(stop);
    }

    private String getTime(Date date) {
        return (date.getHours() < 10 ? "0"+date.getHours() : date.getHours()) +":"+ (date.getMinutes() < 10 ? "0"+date.getMinutes() : date.getMinutes());
    }

    private void fillTable(List<Solution> solutions) {
        String[] columns = {"DEPARTURE", "ARRIVAL", "DURATION", "TRAIN"};
        Object[][] data = new Object[solutions.size()][];
        for(int i = 0; i < solutions.size(); i++) {
            data[i] = new Object[]{solutions.get(i).getOrigin()+ " >> " + getTime(solutions.get(i).getDepartureTime()),
                                   solutions.get(i).getDestination() + " >> " + getTime(solutions.get(i).getArrivalTime()),
                                   solutions.get(i).getDuration(),
                                   solutions.get(i).getTrains()};
        }

        JTable table = new JTable(data, columns);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < columns.length; i++) table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        table.setRowHeight(40);
        table.getColumn("DURATION").setPreferredWidth(5);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.25), (int) (WIDTH * 0.6), (int) (HEIGHT * 0.45));

        this.frame.add(scroll);
    }

    private void fillDetails(String train) {
        Future<Map<String, String>> future = library.getRealTimeTrainInfo(train);
        future.onSuccess(map -> this.details.setText(
                "-----------------------------------------------------\n" +
                "Departure Station: \n" +
                map.get("dep_station") + "\n\n" +
                "Programmed Departure: \n" +
                map.get("dep_prog") + "\n\n" +
                "Effective Departure: \n" +
                map.get("dep_eff") + "\n\n" +
                checkTrainArrived(map) +
                "-----------------------------------------------------\n" +
                "Arrival Station: \n" +
                map.get("arr_station") + "\n\n" +
                "Programmed Arrival: \n" +
                map.get("arr_prog") + "\n\n" +
                "Effective Arrival: \n" +
                map.get("arr_eff") + "\n\n" +
                "-----------------------------------------------------\n" +
                map.get("info"))
        );
    }

    private String checkTrainArrived(Map<String, String> map) {
        if(Integer.parseInt(map.get("size")) < 3) {
            return "";
        } else {
            return "-----------------------------------------------------\n" +
                    "Last Seen Station: \n" +
                    map.get("last_station") + "\n\n" +
                    "Programmed Arrival: \n" +
                    map.get("last_prog") + "\n\n" +
                    "Effective Arrival: \n" +
                    map.get("last_eff") + "\n\n";
        }
    }

}

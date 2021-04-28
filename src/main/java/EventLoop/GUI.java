package EventLoop;

import com.github.lgooddatepicker.components.DateTimePicker;
import io.vertx.core.Future;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
import java.util.List;

public class GUI {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    private final JFrame frame;
    private JTextField departure, arrival;
    private DateTimePicker picker;

    private final TrainSolution library = new TrainSolutionLibrary();

    public GUI() {
        this.frame = new JFrame("Assignment-2");

        this.createInputForm();
        //this.createResultsTable();
        this.fillTable(new ArrayList<>());

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

        this.frame.add(invert);
        this.frame.add(search);
        this.frame.add(departure_label);
        this.frame.add(this.departure);
        this.frame.add(arrival_label);
        this.frame.add(this.arrival);
        this.frame.add(date);
        this.frame.add(time);
        this.frame.add(picker);
    }

    /*private void createResultsTable() {
        String[] columns = {"DEPARTURE", "ARRIVAL", "DURATION", "TRAIN"};
        Object[][] data = {};

        table = new JTable(data, columns);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < columns.length; i++) table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        scroll = new JScrollPane(table);
        scroll.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.3), (int) (WIDTH * 0.88), (int) (HEIGHT * 0.5));

        this.frame.add(scroll);
    }*/

    private void fillTable(List<Solution> solutions) {
        String[] columns = {"DEPARTURE", "ARRIVAL", "DURATION", "TRAIN"};
        Object[][] data = new Object[solutions.size()][];
        for(int i = 0; i < solutions.size(); i++) {
            data[i] = new Object[]{solutions.get(i).getOrigin()+ " -> " +
                                           (solutions.get(i).getDepartureTime().getHours() < 10 ? "0"+solutions.get(i).getDepartureTime().getHours() : solutions.get(i).getDepartureTime().getHours())
                                           +":"+
                                           (solutions.get(i).getDepartureTime().getMinutes() < 10 ? "0"+solutions.get(i).getDepartureTime().getMinutes() : solutions.get(i).getDepartureTime().getMinutes()),
                                   solutions.get(i).getDestination() + " -> " +
                                           (solutions.get(i).getArrivalTime().getHours() < 10 ? "0"+solutions.get(i).getArrivalTime().getHours() : solutions.get(i).getArrivalTime().getHours())
                                           +":"+
                                           (solutions.get(i).getDepartureTime().getMinutes() < 10 ? "0"+solutions.get(i).getDepartureTime().getMinutes() : solutions.get(i).getDepartureTime().getMinutes()),
                                   solutions.get(i).getDuration(),
                                   solutions.get(i).getTrains()
                                   };
        }
        JTable table = new JTable(data, columns);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < columns.length; i++) table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.3), (int) (WIDTH * 0.88), (int) (HEIGHT * 0.5));

        this.frame.add(scroll);
    }

}

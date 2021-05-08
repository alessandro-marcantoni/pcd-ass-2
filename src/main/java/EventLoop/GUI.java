package EventLoop;

import EventLoop.library.TrainSolution;
import EventLoop.library.TrainSolutionLibrary;
import EventLoop.model.Parameters;
import EventLoop.model.Solution;
import com.github.lgooddatepicker.components.DateTimePicker;
import io.vertx.core.Future;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GUI {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;

    private final JFrame frame;
    private JTextField departure, arrival;
    private JTextArea details, station;
    private DateTimePicker picker;

    private JTextField trainField, stationField;

    private boolean mode = false;

    private final TrainSolution library = new TrainSolutionLibrary(this);

    public GUI() {
        this.frame = new JFrame("Assignment-2");

        this.createGUI();
        this.fillTable(new ArrayList<>());

        this.frame.setSize(WIDTH, HEIGHT);
        this.frame.setLayout(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public String getTrainCode() {
        return this.trainField.getText();
    }

    public JTextArea getTrainArea() {
        return this.details;
    }

    public String getStationCode() {
        return this.stationField.getText();
    }

    public JTextArea getStationArea() {
        return this.station;
    }

    public String getStationMode(){
        return mode ? "arrivi" : "partenze";
    }

    private void createGUI() {
        // DEPARTURE
        final JLabel departureLabel = new JLabel("Stazione Partenza:");
        departureLabel.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));

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
        final JLabel arrivalLabel = new JLabel("Stazione Destinazione:");
        arrivalLabel.setBounds((int) (WIDTH * 0.31), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));

        this.arrival = new JTextField();
        this.arrival.setBounds((int) (WIDTH * 0.31), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.15), (int) (HEIGHT * 0.06));

        // PICKER
        final JLabel date = new JLabel("Data Partenza:");
        date.setBounds((int) (WIDTH * 0.5), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));
        final JLabel time = new JLabel("Ora:");
        time.setBounds((int) (WIDTH * 0.7), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));

        this.picker = new DateTimePicker();
        picker.setBounds((int) (WIDTH * 0.5), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.06));

        // SEARCH BTN
        final JButton search = new JButton("Cerca");
        search.setBounds((int) (WIDTH * 0.85), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.08), (int) (HEIGHT * 0.06));

        search.addActionListener(e -> {
            final Parameters parameters = new Parameters(this.departure.getText(), this.arrival.getText(), this.picker.getDatePicker().getDate().toString(), this.picker.getTimePicker().getTime().toString());
            Future<List<Solution>> future = library.getTrainSolutions(parameters);
            future.onSuccess(this::fillTable);
        });

        // DETAILS BOX
        final JLabel trainDetails = new JLabel("Dettagli Treno:");
        trainDetails.setBounds((int) (WIDTH * 0.66), (int) (HEIGHT * 0.17), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.04));

        this.trainField = new JTextField();
        this.trainField.setBounds((int) (WIDTH * 0.76), (int) (HEIGHT * 0.17), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        this.details = new JTextArea();
        this.details.setEditable(false);
        this.details.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        final JScrollPane trainPanel = new JScrollPane(this.details);
        trainPanel.setBounds((int) (WIDTH * 0.66), (int) (HEIGHT * 0.22), (int) (WIDTH * 0.27), (int) (HEIGHT * 0.65));

        final JButton monitorTrain = new JButton(">");
        monitorTrain.setBounds((int) (WIDTH * 0.83), (int) (HEIGHT * 0.17), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));
        final JButton stopTrain = new JButton("X");
        stopTrain.setBounds((int) (WIDTH * 0.88), (int) (HEIGHT * 0.17), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        monitorTrain.addActionListener(e -> {
            if(!this.trainField.getText().isEmpty()) {
                this.library.startTrainMonitoring();
            }
        });

        stopTrain.addActionListener(e -> {
            this.library.stopTrainMonitoring();
            this.details.setText("");
        });

        // STATION BOX
        final JLabel stationDetails = new JLabel("Dettagli Stazione:");
        stationDetails.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.66), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.04));

        this.stationField = new JTextField();
        this.stationField.setBounds((int) (WIDTH * 0.17), (int) (HEIGHT * 0.66), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        final JCheckBox check = new JCheckBox("arrivi ( / partenze )");
        check.setBounds((int) (WIDTH * 0.38), (int) (HEIGHT * 0.66), (int) (WIDTH * 0.15), (int) (HEIGHT * 0.04));
        check.addActionListener(e -> {
            JCheckBox cb = (JCheckBox) e.getSource();
            mode = cb.isSelected();
        });

        this.station = new JTextArea();
        this.station.setEditable(false);
        this.station.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        final JScrollPane stationPanel = new JScrollPane(this.station);
        stationPanel.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.71), (int) (WIDTH * 0.6), (int) (HEIGHT * 0.16));

        final JButton monitorStation = new JButton(">");
        monitorStation.setBounds((int) (WIDTH * 0.25), (int) (HEIGHT * 0.66), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));
        final JButton stopStation = new JButton("X");
        stopStation.setBounds((int) (WIDTH * 0.3), (int) (HEIGHT * 0.66), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        monitorStation.addActionListener(e -> {
            if(!this.stationField.getText().isEmpty()) {
                this.library.startStationMonitoring();
            }
        });

        stopStation.addActionListener(e -> {
            this.library.stopStationMonitoring();
            this.station.setText("");
        });

        this.frame.add(invert);
        this.frame.add(search);
        this.frame.add(departureLabel);
        this.frame.add(this.departure);
        this.frame.add(arrivalLabel);
        this.frame.add(this.arrival);
        this.frame.add(date);
        this.frame.add(time);
        this.frame.add(this.picker);

        this.frame.add(trainDetails);
        this.frame.add(trainPanel);
        this.frame.add(this.trainField);
        this.frame.add(monitorTrain);
        this.frame.add(stopTrain);

        this.frame.add(stationDetails);
        this.frame.add(stationPanel);
        this.frame.add(this.stationField);
        this.frame.add(check);
        this.frame.add(monitorStation);
        this.frame.add(stopStation);
    }

    private String getTime(Date date) {
        return (date.getHours() < 10 ? "0"+date.getHours() : date.getHours()) +":"+ (date.getMinutes() < 10 ? "0"+date.getMinutes() : date.getMinutes());
    }

    private void fillTable(List<Solution> solutions) {
        String[] columns = {"PARTENZA", "ARRIVO", "DURATA", "TRENO"};
        Object[][] data = new Object[solutions.size()][];
        for(int i = 0; i < solutions.size(); i++) {
            data[i] = new Object[]{
                    solutions.get(i).getOrigin()+ " >> " + getTime(solutions.get(i).getDepartureTime()),
                    solutions.get(i).getDestination() + " >> " + getTime(solutions.get(i).getArrivalTime()),
                    solutions.get(i).getDuration(),
                    solutions.get(i).getTrains().toString()
                            .replaceAll("\\[", "")
                            .replaceAll("\\]", "")
                            .replaceAll(",", " -")
            };
        }

        JTable table = new JTable(data, columns);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < columns.length; i++) table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        table.setRowHeight(40);
        table.getColumn("DURATA").setPreferredWidth(5);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scroll.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.6), (int) (HEIGHT * 0.45));

        this.frame.add(scroll);
    }

}

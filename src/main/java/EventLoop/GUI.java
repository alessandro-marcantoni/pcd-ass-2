package EventLoop;

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

    private final TrainSolution library = new TrainSolutionLibrary();

    private boolean running = false;

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

    private void createGUI() {
        // DEPARTURE
        final JLabel departure_label = new JLabel("Stazione Partenza:");
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
        final JLabel arrival_label = new JLabel("Stazione Destinazione:");
        arrival_label.setBounds((int) (WIDTH * 0.31), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.04));

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
            //this.library.getRealTimeStationInfo("S01700");
        });

        // DETAILS BOX
        final JLabel train_details = new JLabel("Dettagli Treno");
        train_details.setBounds((int) (WIDTH * 0.7), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.04));

        final JTextField train_field = new JTextField();
        train_field.setBounds((int) (WIDTH * 0.78), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        this.details = new JTextArea();
        this.details.setEditable(false);
        this.details.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        final JScrollPane train_panel = new JScrollPane(this.details);
        train_panel.setBounds((int) (WIDTH * 0.7), (int) (HEIGHT * 0.25), (int) (WIDTH * 0.23), (int) (HEIGHT * 0.6));

        final JButton monitor_train = new JButton(">");
        monitor_train.setBounds((int) (WIDTH * 0.83), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));
        final JButton stop_train = new JButton("X");
        stop_train.setBounds((int) (WIDTH * 0.88), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        monitor_train.addActionListener(e -> {
            if(!train_field.getText().isEmpty()) {
                this.running = true;
                this.fillTrainDetails(train_field.getText());
            }
        });

        stop_train.addActionListener(e -> this.running = false);

        // STATION BOX
        final JLabel station_details = new JLabel("Dettagli Stazione");
        station_details.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.7), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.04));

        final JTextField station_field = new JTextField();
        station_field.setBounds((int) (WIDTH * 0.146), (int) (HEIGHT * 0.7), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        this.station = new JTextArea();
        this.station.setEditable(false);
        this.station.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        final JScrollPane station_panel = new JScrollPane(this.station);
        station_panel.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.75), (int) (WIDTH * 0.6), (int) (HEIGHT * 0.1));

        final JButton monitor_station = new JButton(">");
        monitor_station.setBounds((int) (WIDTH * 0.196), (int) (HEIGHT * 0.7), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));
        final JButton stop_station = new JButton("X");
        stop_station.setBounds((int) (WIDTH * 0.246), (int) (HEIGHT * 0.7), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.04));

        monitor_station.addActionListener(e -> {
            if(!station_field.getText().isEmpty()) {
                this.station.setText("wow");
            }
        });

        stop_station.addActionListener(e -> {});

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
        this.frame.add(train_panel);
        this.frame.add(train_field);
        this.frame.add(monitor_train);
        this.frame.add(stop_train);

        this.frame.add(station_details);
        this.frame.add(station_panel);
        this.frame.add(station_field);
        this.frame.add(monitor_station);
        this.frame.add(stop_station);
    }

    private String getTime(Date date) {
        return (date.getHours() < 10 ? "0"+date.getHours() : date.getHours()) +":"+ (date.getMinutes() < 10 ? "0"+date.getMinutes() : date.getMinutes());
    }

    private void fillTable(List<Solution> solutions) {
        String[] columns = {"PARTENZA", "ARRIVO", "DURATA", "TRENO"};
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
        table.getColumn("DURATA").setPreferredWidth(5);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scroll.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.25), (int) (WIDTH * 0.6), (int) (HEIGHT * 0.45));

        this.frame.add(scroll);
    }

    private void fillTrainDetails(String train) {
        Future<Details> future = library.getRealTimeTrainInfo(train);
        future.onSuccess(detail -> {
            this.details.setText(
                "-----------------------------------------------------\n" +
                "Stazione di Partenza: \n" +
                detail.getDepartureStation() + "\n\n" +
                "Partenza Programmata: \n" +
                detail.getProgrammedDeparture() + "\n\n" +
                "Partenza Effettiva: \n" +
                detail.getEffectiveDeparture() + "\n\n" +
                checkTrainArrived(detail) +
                "-----------------------------------------------------\n" +
                "Stazione di Arrivo: \n" +
                detail.getArrivalStation() + "\n\n" +
                "Arrivo Programmato: \n" +
                detail.getProgrammedArrival() + "\n\n" +
                "Arrivo Effettivo: \n" +
                detail.getEffectiveArrival() + "\n\n" +
                "-----------------------------------------------------\n" +
                detail.getInformation()
            );
        });
    }

    private String checkTrainArrived(Details detail) {
        if(detail .getSize() < 3) { // train arrived
            return "";
        } else {
            return "-----------------------------------------------------\n" +
                    "Ultima Fermata Effettuata: \n" +
                    detail.getLastStation() + "\n\n" +
                    "Arrivo Programmato: \n" +
                    detail.getProgrammedLast() + "\n\n" +
                    "Arrivo Effettivo: \n" +
                    detail.getEffectiveLast() + "\n\n";
        }
    }

}

package Tasks.view;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Tasks.commons.Pair;
import Tasks.commons.Parameter;
import Tasks.controller.Controller;

public class GUI {

	private static final int WIDTH = 1000;
	private static final int HEIGHT = 500;
	private static final String DEFAULT_PATH = System.getProperty("user.dir");
	private int current = 0;

	private final JFrame frame;
	private JTextField directory, excluded, words, performance;
	private JTextArea results;

	private Controller controller;

	public GUI() {
		this.frame = new JFrame("Assignment-02");

		this.createDirectoryInput();
		this.createExcludedInput();
		this.createWordsInput();
		this.createResultsOutput();
		this.createPerformancePanel();
		this.createStartButton();
		this.createStopButton();
		this.createResetButton();

		this.frame.setSize(WIDTH, HEIGHT);
		this.frame.setLayout(null);
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		this.frame.setVisible(true);
	}

	public void modelUpdated(final Pair<Map<String,Integer>,Integer> occurrences) {
		String t = "";
		for (String word: occurrences.getFirst().keySet().stream().sorted((a, b) -> occurrences.getFirst().get(b) - occurrences.getFirst().get(a)).collect(Collectors.toList())) {
			t += word + "\t -     " + occurrences.getFirst().get(word) + " times \n";
		}
		String finalT = t;
		SwingUtilities.invokeLater(() -> {
			this.results.setText("");
			this.results.setText(finalT);
			this.performance.setText("Processed a total of " + occurrences.getSecond() + " words");
		});
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	private void update() {
		if (this.words.getText().isEmpty()) {
			this.current = 0;
		} else {
			this.current = Integer.parseInt(this.words.getText());
		}
	}

	private void createDirectoryInput() {
		final JLabel label = new JLabel("Select the directory containing PDF documents to process:");
		label.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.1 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.05));

		final JButton btn = new JButton("Browse");
		btn.setBounds((int) (WIDTH * 0.36), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.09), (int) (HEIGHT * 0.05));

		btn.addActionListener(e -> {
			final JFileChooser chooser = new JFileChooser(DEFAULT_PATH);
			chooser.setDialogTitle("Select the folder with PDF documents");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showOpenDialog(null);
			final File f = chooser.getSelectedFile();
			if(f != null) {
				final String folder = f.getAbsolutePath();
				this.directory.setText(folder);
			}
		});

		this.directory = new JTextField();
		this.directory.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.3),
				(int) (HEIGHT * 0.05));
		this.directory.setText("");

		this.frame.add(label);
		this.frame.add(btn);
		this.frame.add(this.directory);
	}

	private void createExcludedInput() {
		final JLabel label = new JLabel("Select the file containing the words to exclude:");
		label.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.2 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.05));

		final JButton btn = new JButton("Browse");
		btn.setBounds((int) (WIDTH * 0.36), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.09), (int) (HEIGHT * 0.05));

		btn.addActionListener(e -> {
			final JFileChooser chooser = new JFileChooser(DEFAULT_PATH);
			chooser.setDialogTitle("Select the words-exclusion file");
			chooser.showOpenDialog(null);
			final File f = chooser.getSelectedFile();
			if(f != null) {
				final String file = f.getAbsolutePath();
				this.excluded.setText(file);
			}
		});

		this.excluded = new JTextField();
		this.excluded.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.2), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.05));
		this.excluded.setText("");

		this.frame.add(btn);
		this.frame.add(label);
		this.frame.add(this.excluded);
	}

	private void createWordsInput() {
		final JLabel label = new JLabel("Select a number of words to display:");
		label.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.3 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.05));

		final JButton dec = new JButton("-");
		dec.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.3), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.05));
		final JButton inc = new JButton("+");
		inc.setBounds((int) (WIDTH * 0.25), (int) (HEIGHT * 0.3), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.05));

		inc.addActionListener(e -> {
			current++;
			this.words.setText("" + current);
		});
		dec.addActionListener(e -> {
			if (current != 0) {
				current--;
				this.words.setText("" + current);
			}
		});

		this.words = new JTextField();
		this.words.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				update();
			}
			public void removeUpdate(DocumentEvent e) {
				update();
			}
			public void insertUpdate(DocumentEvent e) {
				update();
			}
		});
		this.words.setBounds((int) (WIDTH * 0.15), (int) (HEIGHT * 0.3), (int) (WIDTH * 0.05), (int) (HEIGHT * 0.05));
		this.words.setText("" + current);

		this.frame.add(inc);
		this.frame.add(dec);
		this.frame.add(label);
		this.frame.add(this.words);
	}
	
	private void createPerformancePanel() {
		final JLabel label = new JLabel("Performance:");
		label.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.5 - 20), (int) (WIDTH * 0.4), (int) (HEIGHT * 0.05));
		
		this.performance = new JTextField();
		this.performance.setEditable(false);
		this.performance.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.5), (int) (WIDTH * 0.3), (int) (HEIGHT * 0.05));
		
		this.frame.add(label);
		this.frame.add(this.performance);
	}

	private void createResultsOutput() {
		final JLabel label = new JLabel("Results:");
		label.setBounds((int) (WIDTH * 0.5), (int) (HEIGHT * 0.025), (int) (WIDTH * 0.5), (int) (HEIGHT * 0.1));
		this.results = new JTextArea("");
		this.results.setBounds((int) (WIDTH * 0.5), (int) (HEIGHT * 0.1), (int) (WIDTH * 0.45), (int) (HEIGHT * 0.75));
		this.frame.add(label);
		this.frame.add(this.results);
	}

	private void createStartButton() {
		final JButton start = new JButton("Start");
		start.setBounds((int) (WIDTH * 0.05), (int) (HEIGHT * 0.75), (int) (WIDTH * 0.2), (int) (HEIGHT * 0.1));
		start.addActionListener(e -> {
			if (this.directory.getText().isEmpty() || this.excluded.getText().isEmpty()) {
				JOptionPane.showMessageDialog(frame,
						"Missing input(s).",
						"Input Error",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				Parameter.INSTANCE.setParameters(this.directory.getText(), this.excluded.getText(),
						Integer.parseInt(this.words.getText()));
				try {
					this.controller.notifyStarted();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});
		this.frame.add(start);
	}

	private void createStopButton() {
		final JButton stop = new JButton("Stop");
		stop.setBounds((int) (WIDTH * 0.25), (int) (HEIGHT * 0.75), (int) (WIDTH * 0.2), (int) (HEIGHT * 0.1));
		stop.addActionListener(e -> this.controller.notifyStopped());
		this.frame.add(stop);
	}

	private void createResetButton() {
		final JButton reset = new JButton("Reset All");
		reset.setBounds((int) (WIDTH * 0.36), (int) (HEIGHT * 0.3), (int) (WIDTH * 0.09), (int) (HEIGHT * 0.05));

		reset.addActionListener(e -> {
			this.current = 0;
			this.words.setText("" + current);
			this.excluded.setText("");
			this.directory.setText("");
		});

		this.frame.add(reset);
	}
}

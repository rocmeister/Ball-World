package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

/**
 * The View class is the specific instance/extension of the JFrame class
 * that we are using to create our desired GUI window.
 * @param <TStrategyDropListItem> The type of the items put on the update strategy drop lists.
 * @param <TPaintDropListItem> The type of the items put on the paint strategy drop lists.
 * @author Peter Dulworth (psd2)
 * @author Sophia Jefferson (sgj1)
 */
public class View<TStrategyDropListItem, TPaintDropListItem> extends JFrame {

	/**
	 * Generated serialVersionUID to suppress warning message in Eclipse.
	 */
	private static final long serialVersionUID = -3317320981608676115L;

	/**
	 * The content pane.
	 */
	private JPanel contentPane;

	/**
	 * The text field placed in the north panel.
	 */
	private JTextField inputTF;

	/**
	 * Adapter that the view uses to communicate to the model for non-repetitive control tasks such as manipulating strategies.
	 */
	private IV2MControlAdapter<TStrategyDropListItem, TPaintDropListItem> v2mControlAdapter;
	//	private IV2MControlAdapter<?> v2mControlAdapter = IV2MControlAdapter.NULL;

	/**
	 * Adapter that the view uses to communicate to the model for repetitive update tasks such as painting.
	 */
	private IV2MUpdateAdapter v2mUpdateAdapter;
	//	private IV2MUpdateAdapter v2mUpdateAdapter = IV2MUpdateAdapter.NULL;

	/**
	 * The panel that contains the controls.
	 */
	private JPanel controlPnl;

	/**
	 * The panel that gets painted on.
	 */
	private JPanel canvasPnl;

	/**
	 * The button that creates a switcher ball.
	 */
	private JButton makeSwitcherBtn;

	/**
	 * The top drop list. 
	 */
	private JComboBox<TStrategyDropListItem> dropListTop;

	/**
	 * The bottom drop list.
	 */
	private JComboBox<TStrategyDropListItem> dropListBottom;

	/**
	 * The combine button.
	 */
	private JButton combineBtn;

	/**
	 * The button that adds to the drop lists.
	 */
	private JButton addBtn;

	/**
	 * The button that switches the switcher balls.
	 */
	private JButton switchBtn;

	/**
	 * The input panel.
	 */
	private JPanel inputPnl;

	/**
	 * The switcher panel.
	 */
	private JPanel switcherPnl;

	/**
	 * The combine panel.
	 */
	private JPanel cbPanel;

	/**
	 * The paint strategy panel.
	 */
	private JPanel paintPanel;

	/**
	 * The text field for the paint strategies.
	 */
	private JTextField paintTF;

	/**
	 * The button that adds paint strategy in text field to the drop down.
	 */
	private JButton addPaintBtn;

	/**
	 * The paint strategy drop down holding all added paint strategies.
	 */
	private JComboBox<TPaintDropListItem> paintDropList;

	/**
	 * Constructor that creates/initializes the frame.
	 * @param v2mControlAdapter adapter from the view to the mode that handles control.
	 * @param v2mUpdateAdapter adapter from the view to the model that handles updating.
	 */
	public View(IV2MControlAdapter<TStrategyDropListItem, TPaintDropListItem> v2mControlAdapter, IV2MUpdateAdapter v2mUpdateAdapter) {
		this.v2mControlAdapter = v2mControlAdapter;
		this.v2mUpdateAdapter = v2mUpdateAdapter; // take the settings from the anonymous inner class you pass in the controller and apply them 
		initGUI();
	}

	/**
	 * Method that initializes the GUI window and its components (created 
	 * through WindowBuilder). Paints shapes based on the button and has 
	 * a text field that can be toggled with a button.
	 */
	private void initGUI() {
		// Initialize the frame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 881, 450);
		contentPane = new JPanel();
		contentPane.setToolTipText("Panel holding the entirety of the tool.");
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// Create a special panel with an overridden paintComponent method.
		canvasPnl = new JPanel() {
			/**
			 * Quick Fix to suppress warning.
			 */
			private static final long serialVersionUID = 2142918265458861003L;

			Boolean init = false;

			/**
			* Overridden paintComponent method to paint a shape in the panel.
			* @param g The Graphics object to paint on.
			**/
			public void paintComponent(Graphics g) { // gets called every time pnlCenter.repaint() is called
				super.paintComponent(g); // Do everything normally done first, e.g. clear the screen.
				if (init) // don't paint the first time
					v2mUpdateAdapter.paint(g);
				init = true; // paint every other time
			}
		};
		canvasPnl.setBorder(null);

		canvasPnl.setName("Center Panel");
		canvasPnl.setToolTipText("Panel in which the balls appear.");
		contentPane.add(canvasPnl, BorderLayout.CENTER);

		// The north panel that contains the changeable text field and button.
		controlPnl = new JPanel();
		controlPnl.setToolTipText("Panel containing controls for the tool.");
		controlPnl.setBackground(new Color(0, 255, 127));
		contentPane.add(controlPnl, BorderLayout.NORTH);

		inputPnl = new JPanel();
		inputPnl.setBackground(Color.YELLOW);
		inputPnl.setToolTipText("Panel holding the text field and add-to-list button.");
		controlPnl.add(inputPnl);
		inputPnl.setLayout(new GridLayout(2, 1, 0, 0));

		// The text field that the user can change the label to.
		inputTF = new JTextField();
		inputPnl.add(inputTF);
		inputTF.setHorizontalAlignment(SwingConstants.CENTER);
		inputTF.setText("Kill");
		inputTF.setToolTipText("Name of strategy, XXX, from model.XXXStrategy");
		inputTF.setColumns(1);

		// Add to lists button
		addBtn = new JButton("Add to lists");
		addBtn.addActionListener((e) -> {
			TStrategyDropListItem stratFac = v2mControlAdapter.addStrategy(inputTF.getText());
			dropListTop.addItem(stratFac);
			dropListBottom.addItem(stratFac);
		});
		inputPnl.add(addBtn);
		addBtn.setToolTipText("Add strategy to both droplists. The rest of the classname is automatically added.");

		// The button that the user can press to clear all the balls from the screen.
		JButton clearAllBtn = new JButton("ClearAll");
		clearAllBtn.setToolTipText("Clears all balls from the screen.");
		clearAllBtn.addActionListener((e) -> {
			v2mControlAdapter.clearBalls();
		});

		cbPanel = new JPanel();
		cbPanel.setBorder(new LineBorder(Color.CYAN, 3));
		cbPanel.setToolTipText("Panel for making balls of selected type and/or for making combined strategy balls. ");
		controlPnl.add(cbPanel);
		cbPanel.setLayout(new GridLayout(4, 1, 0, 0));

		// The button that the user can press to create a new ball.
		JButton makeSelectedBtn = new JButton("Make Selected Ball");
		cbPanel.add(makeSelectedBtn);
		makeSelectedBtn.setToolTipText("Instantiate a Ball with the strategy selected in the upper droplist.");
		makeSelectedBtn.addActionListener((e) -> {
			v2mControlAdapter.makeBall(dropListTop.getItemAt(dropListTop.getSelectedIndex()),
					paintDropList.getItemAt(paintDropList.getSelectedIndex()));
		});

		dropListTop = new JComboBox<TStrategyDropListItem>();
		dropListTop.setToolTipText("Upper drop down list containing all added balls/strategies");
		cbPanel.add(dropListTop);
		dropListTop.setAlignmentY(Component.TOP_ALIGNMENT);
		dropListTop.setAlignmentX(Component.LEFT_ALIGNMENT);

		dropListBottom = new JComboBox<TStrategyDropListItem>();
		dropListBottom.setToolTipText("Lower drop down list containing all added balls/strategies");
		cbPanel.add(dropListBottom);
		dropListBottom.setAlignmentX(Component.LEFT_ALIGNMENT);
		dropListBottom.setAlignmentY(Component.TOP_ALIGNMENT);

		combineBtn = new JButton("Combine!");
		combineBtn.addActionListener((e) -> {
			TStrategyDropListItem combinedStratFac = v2mControlAdapter.combineStrategies(
					dropListTop.getItemAt(dropListTop.getSelectedIndex()),
					dropListBottom.getItemAt(dropListBottom.getSelectedIndex()));
			dropListTop.addItem(combinedStratFac);
			dropListBottom.addItem(combinedStratFac);
		});
		cbPanel.add(combineBtn);
		combineBtn.setMaximumSize(new Dimension(159, 29));
		combineBtn.setToolTipText(
				"Combine the selected strategies from both droplists into a single strategy that is added to both droplists.");
		combineBtn.setAlignmentY(Component.TOP_ALIGNMENT);

		switcherPnl = new JPanel();
		switcherPnl.setBorder(
				new TitledBorder(null, "Switcher Controls", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		switcherPnl.setToolTipText("Panel related to Switcher balls.");
		controlPnl.add(switcherPnl);
		switcherPnl.setLayout(new GridLayout(2, 1, 0, 0));

		makeSwitcherBtn = new JButton("Make Switcher");
		makeSwitcherBtn.addActionListener((e) -> {
			v2mControlAdapter.makeSwitcherBall(paintDropList.getItemAt(paintDropList.getSelectedIndex()));
		});
		switcherPnl.add(makeSwitcherBtn);
		makeSwitcherBtn.setToolTipText("Instantiate a Ball that can switch strategies.");
		makeSwitcherBtn.setAlignmentY(Component.TOP_ALIGNMENT);

		switchBtn = new JButton("Switch!");
		switchBtn.addActionListener((e) -> {
			v2mControlAdapter.switchStrategy(dropListTop.getItemAt(dropListTop.getSelectedIndex()));
		});
		switcherPnl.add(switchBtn);
		switchBtn.setToolTipText(
				"Switch the strategy on all switcher balls to the currently selected strategy in the upper droplist.");
		switchBtn.setMaximumSize(new Dimension(134, 29));
		switchBtn.setMinimumSize(new Dimension(134, 29));
		switchBtn.setAlignmentY(0.0f);
		controlPnl.add(clearAllBtn);

		paintPanel = new JPanel();
		paintPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Paint Strategies",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		paintPanel.setToolTipText("Control panel for paint strategies.");
		controlPnl.add(paintPanel);
		paintPanel.setLayout(new GridLayout(3, 1, 0, 0));

		paintTF = new JTextField();
		paintTF.setText("Ball");
		paintTF.setToolTipText("Text field for paint strategy to use.");
		paintPanel.add(paintTF);
		paintTF.setColumns(10);

		addPaintBtn = new JButton("Add");
		addPaintBtn.setToolTipText("Button to add specified paint strategy to dropdown.");
		addPaintBtn.addActionListener((e) -> {
			TPaintDropListItem paintStrat = v2mControlAdapter.addPaintStrategy(paintTF.getText());
			paintDropList.addItem(paintStrat);
		});
		paintPanel.add(addPaintBtn);

		paintDropList = new JComboBox<TPaintDropListItem>();
		paintDropList.setToolTipText("Dropdown containing added paint strategies.");
		paintPanel.add(paintDropList);
	}

	/**
	 * @return The height of the center panel.
	 */
	public Integer getPanelHeight() {
		return canvasPnl.getHeight();
	}

	/**
	 * @return the width of the center panel.
	 */
	public Integer getPanelWidth() {
		return canvasPnl.getWidth();
	}

	/**
	 * This method repaints the center panel.
	 */
	public void update() {
		canvasPnl.repaint();
	}

	/**
	 * @return Returns the center panel.
	 */
	public Component getComponent() {
		return canvasPnl;
	}

	/**
	 * Starts the already initialized frame, making it visible 
	 * and ready to interact with the user. 
	 */
	public void start() {
		setVisible(true);
	}

}

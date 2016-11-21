package projects.cyberDetective.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import projects.cyberDetective.Algorithms;
import projects.cyberDetective.BeamDetector;
import projects.cyberDetective.DetectiveGame;
import projects.cyberDetective.Graph;
import projects.cyberDetective.OccupancySensor;
import projects.cyberDetective.SensorRecording;
import projects.cyberDetective.Vertex;

import common.ui.awt.applet.container.AwtApplet;

public class CyberDetectiveDemoApplet extends AwtApplet  implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1290636242422686146L;

	private EnvPanel envPanel = null;
	JTextArea ta = null;
	JTextField sensorText = null;
	JTextField storyText = null;
	JButton run = null;
	JTextArea resultText = null;
	Map<Integer, Vertex> vertexIdMap = new HashMap<Integer, Vertex>();
	
	/**
	 * Additional application init code
	 * 
	 */
	@Override
	public void appInit(){


		GridBagLayout desktopLayout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JPanel newSimuPanel = new JPanel(desktopLayout);
		newSimuPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), " Cyber Detective: Simulation "));

		envPanel = new EnvPanel(Environment.createExampleEnvironment(DetectiveGame.getBasicGame()));
		envPanel.setPreferredSize(new Dimension(envPanel.canvasWidth, envPanel.canvasHeight));
		envPanel.setLayout(new BorderLayout());

		c.gridx = 0;
		c.insets = new Insets(1,1,1,1);
		c.gridwidth = 6;
		c.gridy = 0;
		desktopLayout.setConstraints(envPanel, c);
		newSimuPanel.add(envPanel);

		c.fill = GridBagConstraints.HORIZONTAL;

		ta = new JTextArea();
		ta.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), " Instructions "));
		ta.setPreferredSize(new Dimension(envPanel.canvasWidth, 80));
		ta.setEditable(false);
		c.gridy ++;
		desktopLayout.setConstraints(ta, c);
		newSimuPanel.add(ta);
		
		JLabel storyLabel = new JLabel("Story String:     ");
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy ++;
		desktopLayout.setConstraints(storyLabel, c);
		newSimuPanel.add(storyLabel);
		
		storyText = new JTextField();
		c.gridx = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1;
		desktopLayout.setConstraints(storyText, c);
		newSimuPanel.add(storyText);
		
		JLabel sensorLabel = new JLabel("Sensor Recordings:   ");
		c.weightx = 0;
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy ++;
		desktopLayout.setConstraints(sensorLabel, c);
		newSimuPanel.add(sensorLabel);
		
		sensorText = new JTextField();
		c.gridx = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1;
		desktopLayout.setConstraints(sensorText, c);
		newSimuPanel.add(sensorText);
		
		final JLabel smLabel = new JLabel("Single/Multiple Agents:     ");
		final JRadioButton singleButton = new JRadioButton("Single Agent");
		final JRadioButton multiButton = new JRadioButton("Multiple Agents");
		final ButtonGroup bg = new ButtonGroup();
		bg.add(singleButton);
		bg.add(multiButton);
		singleButton.setSelected(true);

		c.gridx = 0;
		c.gridy ++;
		c.gridwidth = 2;
		c.weightx = 0;
		desktopLayout.setConstraints(smLabel, c);
		newSimuPanel.add(smLabel);

		c.gridx = 2;
		c.gridwidth = 1;
		c.weightx = 0.5;
		desktopLayout.setConstraints(singleButton, c);
		newSimuPanel.add(singleButton);
		
		c.gridx = 4;
		c.gridwidth = 2;
		c.weightx = 0.5;
		desktopLayout.setConstraints(multiButton, c);
		newSimuPanel.add(multiButton);

		GridBagLayout dL = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		JPanel buttonPanel = new JPanel(dL);
		
		run = new JButton("Run Validation");
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = 1;
		gc.gridwidth = 1;
		gc.weightx = 0.5;
		dL.setConstraints(run, gc);
		buttonPanel.add(run);
		
		JButton reset = new JButton("Reset");
		gc.gridx = 1;
		gc.gridwidth = 1;
		gc.weightx = 0.5;
		dL.setConstraints(reset, gc);
		buttonPanel.add(reset);
		
		c.gridx = 0;
		c.gridy ++;
		c.gridwidth = 6;
		c.weightx = 1;
		desktopLayout.setConstraints(buttonPanel, c);
		newSimuPanel.add(buttonPanel);
		
		resultText = new JTextArea();
		resultText.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), " Output "));
		resultText.setPreferredSize(new Dimension(envPanel.canvasWidth, 120));
		resultText.setEditable(false);
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridy ++;
		desktopLayout.setConstraints(resultText, c);
		newSimuPanel.add(resultText);
		
		envPanel.addMouseListener(this);

		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				storyText.setText("");
				sensorText.setText("");
				resultText.setText("");
				envPanel.env.game = DetectiveGame.getBasicGame();
				startSimulation();
			}});
		
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String story = storyText.getText();
				String sensorString = sensorText.getText();
				if(story.length() == 0){
					resultText.setText("Nothing to validate.");
					return;
				}
				DetectiveGame game = envPanel.env.game;
				Graph g = game.graph;
				for(int i = 0; i < story.length(); i++){
					game.story.addVertex(g.vertexNameMap.get(story.substring(i, i + 1)));
				}

				OccupancySensor O1 = new OccupancySensor(g.vertexNameMap.get("o1"));  
				OccupancySensor O2 = new OccupancySensor(g.vertexNameMap.get("o2"));
				BeamDetector B1 = new BeamDetector("b1", new Vertex[]{g.vertexNameMap.get("b1u"), g.vertexNameMap.get("b1d")});
				BeamDetector B2 = new BeamDetector("b2", new Vertex[]{g.vertexNameMap.get("b2r"), g.vertexNameMap.get("b2l")});
				
				String[] sensors = sensorString.split(",");
				
				if(singleButton.isSelected()){
					for(int i = 0; i< sensors.length; i ++){
						if(sensors[i].equals("o1"))
						{
							game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.ACTIVATION));
							game.obHis.addSensorRecording(new SensorRecording(O1, SensorRecording.DEACTIVATION));
						}
						else if(sensors[i].equals("o2"))
						{
							game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.ACTIVATION));
							game.obHis.addSensorRecording(new SensorRecording(O2, SensorRecording.DEACTIVATION));
						}else if(sensors[i].equals("b1"))
						{
							game.obHis.addSensorRecording(new SensorRecording(B1, SensorRecording.ACTIVATION));
						}else if(sensors[i].equals("b2"))
						{
							game.obHis.addSensorRecording(new SensorRecording(B2, SensorRecording.ACTIVATION));
						}
					}
					
					game.updateStartingVertex(g.vertexNameMap.get(story.substring(0, 1)));
					boolean result = Algorithms.validateAgentStory(game.graph, g.vertexNameMap.get("SV"), game.story, game.obHis);
					resultText.setText((result?"Valid story.":"Inconsistent story."));
					if(result == true){
						String s = Algorithms.getAgentStory(game.graph, g.vertexNameMap.get("SV"), game.story, game.obHis);
						resultText.setText((result?("Valid story.\nA possible path: " + s):"Inconsistent story."));
					}
				}
				else{
					boolean o1active = false;
					boolean o2active = false;
					for(int i = 0; i< sensors.length; i ++){
						if(sensors[i].equals("o1"))
						{
							o1active = !o1active;
							game.obHis.addSensorRecording(new SensorRecording(O1, o1active?SensorRecording.ACTIVATION:SensorRecording.DEACTIVATION));
						}
						else if(sensors[i].equals("o2"))
						{
							o2active = !o2active;
							game.obHis.addSensorRecording(new SensorRecording(O2, o2active?SensorRecording.ACTIVATION:SensorRecording.DEACTIVATION));
						}else if(sensors[i].equals("b1"))
						{
							game.obHis.addSensorRecording(new SensorRecording(B1, SensorRecording.ACTIVATION));
						}else if(sensors[i].equals("b2"))
						{
							game.obHis.addSensorRecording(new SensorRecording(B2, SensorRecording.ACTIVATION));
						}
					}
					
					game.updateStartingVertex(g.vertexNameMap.get(story.substring(0, 1)));
					boolean result = Algorithms.validateAgentStoryMulti(game.graph, g.vertexNameMap.get("SV"), game.story, game.obHis);
					resultText.setText((result?"Valid story.":"Inconsistent story."));
				}
				
				game.story.visitedVertices.clear();
				game.obHis.sensorRecordings.clear();
			}
		});
		
		
		add(newSimuPanel);
		startSimulation();
		
	}

	public void startSimulation(){
		ta.setText("Please pick a starting room from A, B, C to begin");
		DetectiveGame game = envPanel.env.game;
		vertexIdMap.clear();
		vertexIdMap.put(game.graph.vertexNameMap.get("A").id, game.graph.vertexNameMap.get("A"));
		vertexIdMap.put(game.graph.vertexNameMap.get("B").id, game.graph.vertexNameMap.get("B"));
		vertexIdMap.put(game.graph.vertexNameMap.get("C").id, game.graph.vertexNameMap.get("C"));
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int x = (int)(arg0.getX() * envPanel.scalingFactor / envPanel.canvasWidth);
		int y = (int)(arg0.getY() * envPanel.scalingFactor / envPanel.canvasWidth);
		Vertex v = envPanel.env.getClickedVertex(x, y);
		if(v!=null && vertexIdMap.get(v.id) != null){
			DetectiveGame game = envPanel.env.game;
			// Update lists
			if(game.roomIds.contains(v.id)){
				storyText.setText(storyText.getText() + v.name);
			}
			else{
				String text = sensorText.getText();
				if(text.length() > 0){
					text = text + "," + v.name.substring(0, 2);
				}
				else{
					text = v.name.substring(0, 2);
				}
				
				if(!game.occuIds.contains(v.id)){
					v = v.assoVertex;
				}
				
				sensorText.setText(text);
			}
			// Get next available vertices
			StringBuffer buf = new StringBuffer("Current location: ");
			buf.append(v.name);
			buf.append("\nReachable features: ");
			vertexIdMap.clear();
			Vertex[] ns = v.neighbors.toArray(new Vertex[0]);
			for(int i = 0;i < ns.length; i ++){
				if(ns[i].name.equals("SV"))continue;
				vertexIdMap.put(ns[i].id, ns[i]);
				buf.append(ns[i].name + ", ");
			}
			vertexIdMap.put(v.id, v);
			buf.append(v.name);
			buf.append("\nPlease click on one of the above features or run validation.");
			ta.setText(buf.toString());
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}

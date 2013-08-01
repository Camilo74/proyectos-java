package ar.com.epidataconsulting.drools;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;


public class CandyCrush extends JFrame {

	private static final long serialVersionUID = -3212156787523519357L;
	private static Logger logger = Logger.getLogger(CandyCrush.class);
	
	private JLabel screenLabel;
	private Rectangle captureRect;
	private Point initialClick;
	public static Position[][] matriz;

	private JButton botonCapturar;
	private JLabel tituloInicio;
	private JLabel tituloSalida;
	private JButton botonSalida;
	private JButton botonJugar;

	private final int COLUMNAS = 9;
	private final int FILAS = 9;

	private StatefulKnowledgeSession ksession;
	private WorkingMonitor workinMonitor;
	
	//----------------------------------------------------------------

	public CandyCrush() {
		configureLayout();
		configureListener();
		configureDrools();
	}

	private void configureDrools() {
		try {
			ksession = createStatefulKnowledSession();
			ksession.setGlobal("util", new MouseUtils());
		    new Thread() {
		        @Override
		        public void run() {
		            ksession.fireUntilHalt();
		        }
		    }.start();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

    private StatefulKnowledgeSession createStatefulKnowledSession() throws Exception {
		//KnowledgeBuilder: Has a Collection of DRL files, so our rules set can be devided in several files
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		//Auto load local file
		kbuilder.add(ResourceFactory.newClassPathResource("changeset.xml", getClass()),ResourceType.CHANGE_SET);
		//show error
		if(kbuilder.hasErrors()){
			logger.info(kbuilder.getErrors().toString());
		}
		//KnowledgeBaseConfiguration: We use this class to set the Event Processing Node as STRING
		KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
		config.setOption(ClockTypeOption.get("pseudo"));
		//KnowledgeBase: We create our KnowledgeBase considering the Collection of DRL files the KnowledgeBuilder
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		//StatefulKnowledgeSession : Once we have our KnowledgeBase we create a Session to use it.
		return kbase.newStatefulKnowledgeSession(config,null);
    }
	
	private void configureLayout(){
		//panel inicio
		JPanel panelInicio = new JPanel(new BorderLayout());
		tituloInicio = new JLabel("Oprima el boton para iniciar");
		panelInicio.add(tituloInicio, BorderLayout.NORTH);
		
		botonCapturar = new JButton("Capturar");
		panelInicio.add(botonCapturar , BorderLayout.CENTER);
		
		//panel de captura
		JPanel panelScreenshot = new JPanel(new BorderLayout());
		screenLabel = new JLabel("Imagen no disponible");
		panelScreenshot.add(screenLabel, BorderLayout.CENTER);

		//panel de salida
		JPanel panelSalida = new JPanel(new BorderLayout());
		tituloSalida = new JLabel("Oprima el boton para finalizar");
		panelSalida.add(tituloSalida, BorderLayout.NORTH);
		
		botonSalida = new JButton("Salir");
		panelSalida.add(botonSalida, BorderLayout.EAST);
		
		botonJugar = new JButton("Jugar");
		panelSalida.add(botonJugar, BorderLayout.CENTER);		
		
		Container container = getContentPane();
		container.setLayout(new CardLayout());
		container.add("inicio",panelInicio);
		container.add("capture",panelScreenshot);
		container.add("exit",panelSalida);
		
		setBounds(10, 10, 200, 50);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setExtendedState(java.awt.Frame.NORMAL);
		setUndecorated(true);
		setVisible(true);
	}
	
	private void configureListener() {

		botonCapturar.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {
				//minimizamos asi capturamos la pantalla limpia
				setExtendedState(java.awt.Frame.ICONIFIED);
				
				try {
					Robot robot = new Robot();
					final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					final BufferedImage screen = robot.createScreenCapture(new Rectangle(screenSize));
					
					final BufferedImage screenCopy = new BufferedImage(screen.getWidth(), screen.getHeight(), screen.getType());
					screenLabel.setIcon(new ImageIcon(screenCopy));
					
					repaint(screen, screenCopy);
					screenLabel.repaint();
					
					CardLayout layout = (CardLayout) getContentPane().getLayout();
					layout.next(getContentPane());
					setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
					toFront();
					pack();
					
					//listener screen
					screenLabel.addMouseMotionListener(new MouseMotionAdapter() {
						Point start = new Point();
						@Override
						public void mouseMoved(MouseEvent me) {
							start = me.getPoint();
							repaint(screen, screenCopy);
							screenLabel.repaint();
						}
						@Override
						public void mouseDragged(MouseEvent me) {
							Point end = me.getPoint();
							captureRect = new Rectangle(start, new Dimension(end.x - start.x, end.y - start.y));
							repaint(screen, screenCopy);
							screenLabel.repaint();
						}
					});
				} catch (Exception e2) {
					e2.printStackTrace();
				}						
			}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {}
		});

		//listener keyboard
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
					break;
				case KeyEvent.VK_P:
					pausar(e);
				case KeyEvent.VK_ENTER:
					logger.info("Dimention:" + captureRect);
					
					setExtendedState(java.awt.Frame.NORMAL);
					CardLayout layout = (CardLayout) getContentPane().getLayout();
					
					JPanel currentContainer = (JPanel)getContentPane().getComponent(1);
					((JLabel)currentContainer.getComponent(0)).setIcon(null);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					setLocation(dim.width / 2, dim.height / 2);
					
					layout.next(getContentPane());
					pack();

					int top = captureRect.y;
					int fanchoTotal = captureRect.height;
					int fanchoBloque = fanchoTotal / FILAS;
					int fanchoBloqueDiv2 = fanchoBloque / 2;					
					
					int left = captureRect.x;
					int canchoTotal = captureRect.width;
					int canchoBloque = canchoTotal / COLUMNAS;
					int canchoBloqueDiv2 = canchoBloque / 2;
					
					matriz = new Position[FILAS][COLUMNAS];
					for (int fila = 0; fila < FILAS; fila++) {
						for (int col = 0; col < COLUMNAS; col++) {
							matriz[fila][col] = new Position(
									left + canchoBloqueDiv2 + (canchoBloque * col),
									top + fanchoBloqueDiv2 + (fanchoBloque * ((FILAS - 1) - fila)),
									fila,col
							);
						}
					}
					break;
				}
			}
		});

		botonSalida.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		botonJugar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pausar(e);
			}
		});
		movible(tituloInicio);
		movible(tituloSalida);
	}

	@SuppressWarnings("deprecation")
	public void pausar(AWTEvent e){
		JButton boton = (JButton) e.getSource();
		if("Jugar".equalsIgnoreCase(boton.getText())){
			workinMonitor = new WorkingMonitor();
			workinMonitor.start();
			boton.setText("Pausar");
		}else{
			workinMonitor.stop();
			boton.setText("Jugar");
		}
	}
	
	public void movible(JLabel label){
		label.addMouseMotionListener(new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			initialClick = e.getPoint();
			getComponentAt(initialClick);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// get location of Window
			int thisX = getLocation().x;
			int thisY = getLocation().y;

			// Determine how much the mouse moved since the initial click
			int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
			int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

			// Move window to this position
			int X = thisX + xMoved;
			int Y = thisY + yMoved;
			setLocation(X, Y);	
		}
	});		
	}
	
	public void repaint(BufferedImage orig, BufferedImage copy) {
		Graphics2D g = copy.createGraphics();
		g.drawImage(orig, 0, 0, null);
		if (captureRect != null) {
			g.setColor(Color.GREEN);
			g.draw(captureRect);
			g.setColor(new Color(255, 255, 255, 150));
			g.fill(captureRect);
		}
		g.dispose();
	}

	public class WorkingMonitor extends Thread{
	   public void run(){
			try {
				do {
					for (int fila = 0; fila < FILAS; fila++) {
						for (int col = 0; col < COLUMNAS; col++) {
							try {
								Position pos = matriz[fila][col];
								ksession.insert(new Position(pos));
							} catch (Exception e) {
								logger.error(e.getMessage());
							}
						}
					}
				} while (true);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	   }
	}
	
}
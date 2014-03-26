/**
 * Copyright (C) 1997-2010 Junyang Gu <mikejyg@gmail.com>
 * 
 * This file is part of javaiPacman.
 *
 * javaiPacman is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * javaiPacman is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with javaiPacman.  If not, see <http://www.gnu.org/licenses/>.
 */

package mikejyg.javaipacman.pacman;

import static java.lang.System.out;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Timer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingWorker;
//for printing the value so it can be seen
//for multi-threading

/**
 * the main class of the pacman game 
 */
public class cpcman extends Frame
implements Runnable, KeyListener, ActionListener, WindowListener
{
	private static final long serialVersionUID = 3582431359568375379L;
	// the timer
	Thread timer;
	int timerPeriod=12;  // in miliseconds

	// the timer will increment this variable to signal a frame
	int signalMove=0;

	// for graphics
	final int canvasWidth=368;
	final int canvasHeight=288+1;

	// the canvas starting point within the frame
	int topOffset;
	int leftOffset;

	// the draw point of maze within the canvas
	final int iMazeX=16;
	final int iMazeY=16;

	// the off screen canvas for the maze
	Image offScreen;
	Graphics offScreenG;

	// the objects    
	cmaze maze;
	cpac pac;
	cpowerdot powerDot;
	Vector<cghost> ghosts;

	// game counters
	final int PAcLIVE=3;
	int numberOfGhosts = 4;
	int maxNumberOfGhosts =8;
	int pacRemain;
	int changePacRemain;  // to signal redraw remaining pac

	// score
	int score;
	int hiScore;
	int scoreGhost;	// score of eat ghost, doubles every time
	int changeScore;	// signal score change
	int changeHiScore;  // signal change of hi score

	String currEmotion;	
	boolean newEmotion = false;
	
	//music
	boolean isPlaying = false;
	boolean firstSong = true;
	boolean isNotFirstState = false;
	
	// score images
	Image imgScore;
	Graphics imgScoreG;
	Image imgHiScore;
	Graphics imgHiScoreG;

	// game status
	final int INITIMAGE=100;  // need to wait before paint anything
	final int STARTWAIT=0;  // wait before running
	final int RUNNING=1;
	final int DEADWAIT=2;   // wait after dead
	final int SUSPENDED=3;  // suspended during game
	int gameState;

	final int WAITCOUNT=100;	// 100 frames for wait states
	int wait;	// the counter
	int ttime;
	int temote;

	// rounds
	int round;  // the round of current game;

	// whether it is played in a new maze
	boolean newMaze;
	Timer timers = new Timer();
	// GUIs
	MenuBar menuBar;
	Menu help;
	MenuItem about;

	// the direction specified by key
	int pacKeyDir;
	int key=0;
	final int NONE=0;
	final int SUSPEND=1;  // stop/start
	final int BOSS=2;      // boss
	
	//Initialize Audio Player Files
	
	Path pathPP = Paths.get("PowerPellet.wav");
	Path absPathPP = pathPP.toAbsolutePath();
	File inputFilePP = new File(absPathPP.toString());
	
	Path pathEG = Paths.get("EatGhost.wav");
	Path absPathEG = pathEG.toAbsolutePath();
	File inputFileEG = new File(absPathEG.toString());
	
	Path path = Paths.get("PacManDie.wav");
	Path absPath = path.toAbsolutePath();
	File inputFile = new File(absPath.toString());
	
	Path path1up = Paths.get("1up.wav");
	Path absPath1up = path1up.toAbsolutePath();
	File inputFile1up = new File(absPath1up.toString());
	
	
	/********************************************************
	 * MUSIC FILES
	 */
	Path pathEM = Paths.get("songSpeed1.wav");
	Path absPathEM = pathEM.toAbsolutePath();
	File inputFile1 = new File(absPathEM.toString());

	Path pathEM2 = Paths.get("songSpeed2.wav");
	Path absPathEM2 = pathEM2.toAbsolutePath();
	File inputFile2 = new File(absPathEM2.toString());
	
	Path pathEM3 = Paths.get("songSpeed3.wav");
	Path absPathEM3 = pathEM3.toAbsolutePath();
	File inputFile3 = new File(absPathEM3.toString());
	
	Path pathEM4 = Paths.get("songSpeed3.wav");
	Path absPathEM4 = pathEM4.toAbsolutePath();
	File inputFile4 = new File(absPathEM4.toString());

	Path pathEM5 = Paths.get("songSpeed4.wav");
	Path absPathEM5 = pathEM5.toAbsolutePath();
	File inputFile5 = new File(absPathEM5.toString());
	
	Path pathEM6 = Paths.get("songSpeed5.wav");
	Path absPathEM6 = pathEM6.toAbsolutePath();
	File inputFile6 = new File(absPathEM6.toString());
	
	Path pathEM7 = Paths.get("songSpeed6.wav");
	Path absPathEM7 = pathEM7.toAbsolutePath();
	File inputFile7 = new File(absPathEM7.toString());
	
	Path pathEM8 = Paths.get("songSpeed7.wav");
	Path absPathEM8 = pathEM8.toAbsolutePath();
	File inputFile8 = new File(absPathEM8.toString());
	
	/*********************************************************
	 * 
	 */
	
	
	String eatPowerPellet = inputFilePP.toString();
	String eatGhost = inputFileEG.toString();
	String pacDie = inputFile.toString();
	String life = inputFile1up.toString();
	
	String emotion;
	
	Clip clip;
	Clip emote;
	
	//Music
	public void playSound(String filename)
    {
	    try
	    {
	        Clip clip = AudioSystem.getClip();
	        clip.open(AudioSystem.getAudioInputStream(new File(filename)));
	        clip.start();
	        //startRound();
	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace(System.out);
	    }
    }
	
	public void emoteSong(String song)
	{
		int affectState;
		
	    try
	    {	
	    	emote = AudioSystem.getClip();
	    	affectState = Global.affectiveState;
	    	
	    	switch(affectState)
	    	{
		    	case 1 	: 	emotion = inputFile1.toString();
    						break;
		    	case 2	: 	emotion = inputFile2.toString();
							break;
		    	case 3	: 	emotion = inputFile3.toString();
    						break;
		    	case 4	: 	emotion = inputFile4.toString();
							break;
		    	case 5	: 	emotion = inputFile5.toString();
							break;
		    	case 6	: 	emotion = inputFile6.toString();
							break;
		    	case 7	: 	emotion = inputFile7.toString();
							break;
		    	case 8	: 	emotion = inputFile8.toString();
							break;
	    	}

	    	emote.open(AudioSystem.getAudioInputStream(new File(emotion)));
	    	emote.start();
	    	
	    	firstSong = false;
	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace(System.out);
	    }
	}
	
	public void setNumberOfGhosts(int ghostAmount)
	{
		numberOfGhosts = ghostAmount;
	}
	////////////////////////////////////////////////
	// initialize the object
	// only called once at the beginning
	////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public cpcman()
	{
		super("P*C MAN");

		Properties configFile = new Properties();
		InputStream input = null;
		
		try{
			input = new FileInputStream("pacconfig.properties");
			configFile.load(input);
			
			String stime = (configFile.getProperty("time"));
			
			ttime = Integer.parseInt(stime);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		// init variables
		hiScore=0;

		gameState=INITIMAGE;

		initGUI();

		addWindowListener(this);

		addKeyListener(this);

		about.addActionListener(this);

		setSize(canvasWidth, canvasHeight);
        
        worker.execute();		//Executes another thread to update the affective state value every 10 sec

		show();

		// System.out.println("cpcman done");

	}

	void initGUI()
	{
		menuBar=new MenuBar();
		help=new Menu("HELP");
		about=new MenuItem("About");

		help.add(about);
		menuBar.add(help);

		setMenuBar(menuBar);

		addNotify();  // for updated inset information

		// System.out.println("initGUI done.");
	}

	public void initImages()
	{
		// initialize off screen drawing canvas
		offScreen=createImage(cmaze.iWidth, cmaze.iHeight); 
		if (offScreen==null)
			System.out.println("createImage failed");
		offScreenG=offScreen.getGraphics();

		// initialize maze object
		maze = new cmaze(this, offScreenG);

		/*
		 * initialize the amount of ghost in the game by allocating an array of the cghost class
		 * Possibly make this a vector, to make it easier to add more ghosts.
		 */
		ghosts = new Vector<cghost>();
		for (int i=0; i<numberOfGhosts; i++)
		{
			Color color;
			if (i==0)
				color =Color.red;
			else if (i==1)
				color=Color.blue;
			else if (i==2)
				color=Color.white;
			else if (i==3)
				color=Color.orange;
			else if (i==4)
				color=Color.magenta;
			else if (i==5)
				color=Color.pink;
			else if(i==6)
				color=Color.cyan;
			else
				color=Color.yellow;
			ghosts.addElement(new cghost(this, offScreenG, maze, color));;
		}

		// initialize power dot object
		powerDot = new cpowerdot(this, offScreenG, ghosts);

		// initialize pac object
		//      	pac = new cpac(this, offScreenG, maze, powerDot, ghosts);
		pac = new cpac(this, offScreenG, maze, powerDot);

		// initialize the score images
		imgScore=createImage(150,16);
		imgScoreG=imgScore.getGraphics();
		imgHiScore=createImage(150,16);
		imgHiScoreG=imgHiScore.getGraphics();

		imgHiScoreG.setColor(Color.black);
		imgHiScoreG.fillRect(0,0,150,16);
		imgHiScoreG.setColor(Color.red);
		imgHiScoreG.setFont(new Font("Helvetica", Font.BOLD, 12));
		imgHiScoreG.drawString("HI SCORE", 0, 14);

		imgScoreG.setColor(Color.black);
		imgScoreG.fillRect(0,0,150,16);
		imgScoreG.setColor(Color.green);
		imgScoreG.setFont(new Font("Helvetica", Font.BOLD, 12));
		imgScoreG.drawString("SCORE", 0, 14);

	}

	void startTimer()
	{   
		// start the timer
		timer = new Thread(this);
		timer.start();
	}

	void startGame()
	{
		pacRemain=PAcLIVE;
		changePacRemain=1;

		score=0;
		changeScore=1;

		newMaze=true;

		round=1;
		
		//playSound();
		startRound();
		
        newEmotion = true;
		isPlaying = false;    

	}

	void startRound()
	{
		// new round for maze?
		if (newMaze==true)
		{
			maze.start();
			powerDot.start();
			newMaze=false;
		}

		maze.draw();	// draw maze in off screen buffer

		pac.start();
		pacKeyDir=ctables.DOWN;
		for (int i=0; i<ghosts.size(); i++)
		{	
			ghosts.elementAt(i).start(i,round);
		}
		

		gameState=STARTWAIT;
		wait=WAITCOUNT;
	}

	///////////////////////////////////////////
	// paint everything
	///////////////////////////////////////////
	public void paint(Graphics g)
	{
		if (gameState == INITIMAGE)
		{
			// System.out.println("first paint(...)...");

			// init images, must be done after show() because of Graphics
			//Sets the number of ghosts etc.
			initImages();

			// set the proper size of canvas
			Insets insets=getInsets();

			topOffset=insets.top;
			leftOffset=insets.left;

			//  System.out.println(topOffset);
			//  System.out.println(leftOffset);

			setSize(canvasWidth+insets.left+insets.right,
					canvasHeight+insets.top+insets.bottom);

			setResizable(false);

			// now we can start timer
			startGame();	  

			startTimer();

		}

		g.setColor(Color.black);
		g.fillRect(leftOffset,topOffset,canvasWidth, canvasHeight);

		changeScore=1;
		changeHiScore=1;
		changePacRemain=1;

		paintUpdate(g);
	}

	void paintUpdate(Graphics g)
	{
		// updating the frame

		powerDot.draw();

	
		for (int i=0; i<ghosts.size(); i++)
			ghosts.elementAt(i).draw();

		pac.draw();

		// display the offscreen
		g.drawImage(offScreen, 
				iMazeX+ leftOffset, iMazeY+ topOffset, this); 

		// display extra information
		if (changeHiScore==1)
		{
			imgHiScoreG.setColor(Color.black);
			imgHiScoreG.fillRect(70,0,80,16);
			imgHiScoreG.setColor(Color.red);
			imgHiScoreG.drawString(Integer.toString(hiScore), 70,14);
			g.drawImage(imgHiScore, 
					8+ leftOffset, 0+ topOffset, this);

			changeHiScore=0;
		}

		if (changeScore==1)
		{
			imgScoreG.setColor(Color.black);
			imgScoreG.fillRect(70,0,80,16);
			imgScoreG.setColor(Color.green);
			imgScoreG.drawString(Integer.toString(score), 70,14);
			g.drawImage(imgScore, 
					158+ leftOffset, 0+ topOffset, this);

			changeScore=0;
		}

		// update pac life info
		if (changePacRemain==1)
		{
			int i;
			for (i=1; i<pacRemain; i++)
			{
				g.drawImage(pac.imagePac[0][0], 
						16*i+ leftOffset, 
						canvasHeight-18+ topOffset, this);
			}
			g.drawImage(powerDot.imageBlank, 
					16*i+ leftOffset, 
					canvasHeight-17+ topOffset, this); 

			changePacRemain=0;
		}
		
		//If the state was recently updated then draw an emoji
		if(Global.changedState) {
			//Load an image from a file
			BufferedImage img = null;
			try {
				switch (Global.affectiveState) {
			        case 1:  img = ImageIO.read(new File("emojis/engaged.png"));
			                 break;
			        case 2:  img = ImageIO.read(new File("emojis/bored.png"));
			                 break;
			        case 3:  img = ImageIO.read(new File("emojis/frustrated.png"));
			                 break;
			        case 4:  img = ImageIO.read(new File("emojis/meditated.png"));
			                 break;
			        case 5:  img = ImageIO.read(new File("emojis/agreed.png"));
			                 break;
			        case 6:  img = ImageIO.read(new File("emojis/concentrated.png"));//
			                 break;
			        case 7:  img = ImageIO.read(new File("emojis/disagreed.png"));
			                 break;
			        case 8:  img = ImageIO.read(new File("emojis/interested.png"));
			                 break;
			        default: img = ImageIO.read(new File("emojis/agreed.png"));
			                 break;
				}
			} catch (IOException e) {
				
			}
			
			g.drawImage(img, -32 + canvasWidth, canvasHeight - 23 + topOffset, null);
			
			//Global.changedState = false; //@TODO
		}
		
	}

	////////////////////////////////////////////////////////////
	// controls moves
	// this is the routine running at the background of drawings
	////////////////////////////////////////////////////////////
	void move()
	{
		int k;

		int oldScore=score;


		for (int i=0; i<ghosts.size(); i++)
			ghosts.elementAt(i).move(pac.iX, pac.iY, pac.iDir);

		k=pac.move(pacKeyDir);

		if (k==1)	// eaten a dot
		{
			changeScore=1;
			score+= 10 * ((round+1)/2) ;
		}
		else if (k==2)	// eaten a powerDot
		{
			playSound(eatPowerPellet);	//play eatPowerPellet Audio
			scoreGhost=200;
		}
		else if (k==3)	//<------------------------ eaten a one-up
		{
			playSound(life);	//play eatPowerPellet Audio
			pacRemain++; //<<---------------------One-up
			changePacRemain=1;
		}

		if (maze.iTotalDotcount==0)
		{
			gameState=DEADWAIT;
			wait=WAITCOUNT;
			newMaze=true;
			round++;
			return;
		}

		for (int i=0; i<ghosts.size(); i++)
		{
			k=ghosts.elementAt(i).testCollision(pac.iX, pac.iY);
			if (k==1)	// kill pac
			{
				playSound(pacDie);	//play pacDie Audio
				emote.stop();		//stop emote song
				pacRemain--;
				changePacRemain=1;
				gameState=DEADWAIT;	// stop the game
				wait=WAITCOUNT;
				return;	
			}
			else if (k==2)	// caught by pac
			{
				playSound(eatGhost);	//play eatGhost Audio
				score+= scoreGhost * ((round+1)/2) ;
				changeScore=1;
				scoreGhost*=2;
			}		
		}

		if (score>hiScore)
		{
			hiScore=score;
			changeHiScore=1;
		}

		if ( changeScore==1 )
		{
			if ( score/10000 - oldScore/10000 > 0 )
			{
				pacRemain++;			// bonus
				changePacRemain=1;
			}
		}
	}	

	///////////////////////////////////////////
	// this is the routine draw each frames
	///////////////////////////////////////////
	public void update(Graphics g)
	{
		// System.out.println("update called");
		if (gameState == INITIMAGE)
			return;

		// seperate the timer from update
		if (signalMove!=0)
		{
			// System.out.println("update by timer");
			signalMove=0;

			if (wait!=0)
			{
				wait--;
				return;
			}

			switch (gameState)
			{
			case STARTWAIT: 
				if (pacKeyDir==ctables.UP)	// the key to start game
					gameState=RUNNING;
				else
					return;
				break;
			case RUNNING:
				if (key==SUSPEND)
					gameState=SUSPENDED;
				else
					move();
				break;
			case DEADWAIT:
				if (pacRemain>0)
					startRound();
				else
					startGame();
				gameState=STARTWAIT;
				wait=WAITCOUNT;
				pacKeyDir=ctables.DOWN;
				break;
			case SUSPENDED:
				if (key==SUSPEND)
					gameState=RUNNING;
				break;
			}
			key=NONE;
		}

		paintUpdate(g);	
	}

	///////////////////////////////////////
	// process key input
	///////////////////////////////////////
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_RIGHT:
		//case KeyEvent.VK_L:
			pacKeyDir=ctables.RIGHT;
			// e.consume();
			break;
		case KeyEvent.VK_UP:
			pacKeyDir=ctables.UP;
			// e.consume();
			break;
		case KeyEvent.VK_LEFT:
			pacKeyDir=ctables.LEFT;
			// e.consume();
			break;
		case KeyEvent.VK_DOWN:
			pacKeyDir=ctables.DOWN;
			// e.consume();
			break;
		case KeyEvent.VK_S:
			key=SUSPEND;
			break;
		case KeyEvent.VK_B:
			key=BOSS;
			break;
		}
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	/////////////////////////////////////////////////
	// handles menu event
	/////////////////////////////////////////////////
	public void actionPerformed(ActionEvent e)
	{
		if (gameState==RUNNING)
			key=SUSPEND;
		new cabout(this);
		// e.consume();
	}

	///////////////////////////////////////////////////
	// handles window event
	///////////////////////////////////////////////////
	public void windowOpened(WindowEvent e)
	{}

	public void windowClosing(WindowEvent e)
	{}

	public void windowClosed(WindowEvent e)
	{}

	public void windowIconified(WindowEvent e)
	{}

	public void windowDeiconified(WindowEvent e)
	{}

	public void windowActivated(WindowEvent e)
	{}

	public void windowDeactivated(WindowEvent e)
	{}

	/////////////////////////////////////////////////
	// the timer
	/////////////////////////////////////////////////
	public void run()
	{
		while (true)
		{	
			try { Thread.sleep(timerPeriod); } 
			catch (InterruptedException e)
			{
				return;
			}

			signalMove++;
			repaint();
						
			if(newEmotion)
			{
				emotionChange();
				adjustGhosts();
			}
		}
	}
	
	public void adjustGhosts()
	{	
		if(isNotFirstState)
		{
			if(Global.affectiveState == 1)
			{
				addGhost();
				if(ghosts.size() == maxNumberOfGhosts)
				{
					increaseSpeedOfAllGhosts();
				}
			}
			else if (Global.affectiveState == 2)
			{
				addGhost();
				increaseSpeedOfAllGhosts();
			}
			else if (Global.affectiveState == 3)
			{
				removeGhost();
			
			}
			else 
			{
				increaseSpeedOfAllGhosts();
				
			}
			
			paintUpdate(this.getGraphics());
			
		}
		else
			isNotFirstState = true;
	}
	
	public void addGhost()
	{
		if(ghosts.size() < maxNumberOfGhosts)
		{
			ghosts.addElement(new cghost(this, offScreenG, maze, Color.GRAY));
			ghosts.lastElement().start(ghosts.size(), round);
			repaint();
		}
	}
	
	public void removeGhost()
	{
		if (ghosts.size() > numberOfGhosts)
		{
			ghosts.remove(ghosts.size()-1);
			repaint();
		}
	}
	
	public void increaseSpeedOfAllGhosts()
	{
		for(cghost ghost: ghosts)
		{
			ghost.speed.increaseSpeed();
		}
	}
	public void decreaseSpeedOfAllGhosts()
	{
		for(cghost ghost: ghosts)
		{
			ghost.speed.decreaseSpeed();
		}
	}
	
	public void emotionChange()
	{
		int affectState = Global.affectiveState;
		
		if (affectState > 0)
		{
			if(firstSong)
			{
				emoteSong(emotion);
				newEmotion = false;
			}
			else
			{
				emote.stop();
				
				emoteSong(emotion);
				newEmotion = false;
			}
			
		}
		else
			newEmotion = false;
	}

	// for applet the check state
	boolean finalized=false;

	public void dispose()
	{
		//      timer.stop();	// deprecated
		// kill the thread
		timer.interrupt();

		// the off screen canvas
//		Image offScreen=null;
		offScreenG.dispose();
		offScreenG=null;

		// the objects    
		maze=null;
		pac=null;
		powerDot=null;
		for (int i=0; i<numberOfGhosts; i++)
			ghosts.setElementAt(null, i);
		ghosts=null;

		// score images
		imgScore=null;
		imgHiScore=null;
		imgScoreG.dispose();
		imgScoreG=null;
		imgHiScoreG.dispose();
		imgHiScoreG=null;

		// GUIs
		menuBar=null;
		help=null;
		about=null;

		emote.stop();
		super.dispose();

		finalized=true;
	}

	public boolean isFinalized() {
		return finalized;
	}

	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}
    
    
    /**********Multi-threading**********/
    
	SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()	{
		@Override
		protected Void doInBackground()	throws Exception{
			while(true){
				try {
					AffectiveStateAccessor.getAffectiveState();
					newEmotion = true;	
					} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.println(Global.affectiveState);
				Thread.sleep(ttime);			//wait 10 seconds before updating affective state
			}
			//return null;
		}
	};
	
}

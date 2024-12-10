import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class gamepanel extends JPanel implements ActionListener {

    static final int screenwidth = 600;
    static final int screenheight = 600;
    static final int unitsize = 30;
    static final int Highscore=0;
    static final int gameunits = (screenwidth * screenheight) / unitsize;
    static final int delay = 75;
    final int x[] = new int[gameunits];
    final int y[] = new int[gameunits];
    int bodyparts = 6;
    int appleeaten = 0;
    int applex;
    int appley;
    char direction = 'R'; 
    boolean running = false;
    Timer timer;
    Random random;

    gamepanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screenwidth, screenheight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdaptor());
        startgame();
    }

    public void startgame() {
        newapple();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
    	if(running) {
//        for (int i = 0; i < screenheight / unitsize; i++) {
//            g.drawLine(i * unitsize, 0, i * unitsize, screenheight);
//            g.drawLine(0, i * unitsize, screenwidth, i * unitsize);
//        }
    		g.setColor(Color.white);
        g.fillOval(applex,appley, unitsize,unitsize);
        for(int i=0;i<bodyparts;i++) {
        	if(i==0) {
        		//g.setColor(Color.red);
        		g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        		g.fillRect(x[i], y[i], unitsize, unitsize);
        	}
        	else {
        		//g.setColor(Color.green);
        		g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        		g.fillRect(x[i], y[i], unitsize, unitsize);
        	}
        }
        g.setColor(Color.red);
    	g.setFont(new Font("Ink Free",Font.BOLD,40));
    	FontMetrics metrics=getFontMetrics(g.getFont());
    	g.drawString("Score"+appleeaten,(screenwidth-metrics.stringWidth("Score"+appleeaten))/2,g.getFont().getSize());
    	}
    	else {
    		gameover(g);
    	}
    }

    public void newapple() {
    	applex=random.nextInt((int)(screenwidth/unitsize))*unitsize;
    	appley=random.nextInt((int)(screenheight/unitsize))*unitsize;
    }

    public void move() {
    	for(int i=bodyparts;i>0;i--) {
    		x[i]=x[i-1];
    		y[i]=y[i-1];
    	}
    	switch(direction) {
    	case 'U':
    		y[0]=y[0]-unitsize;
    		break;
    	
        case 'D':
		    y[0]=y[0]+unitsize;
		    break;
	
        case 'R':
	         x[0]=x[0]+unitsize;
	         break;

        case 'L':
	         x[0]=x[0]-unitsize;
	         break;
}
    }

    public void checkappple() {
    	if((x[0]==applex)&&(y[0]==appley)) {
    		bodyparts++;
    		appleeaten++;
    		newapple();
    	}
    	
    }

    public void checkcollision() {
    	//head with body
    	for(int i=bodyparts;i>0;i--) {
    		if((x[0]==x[i])&&(y[0]==y[i])) {
    			running=false;
    		}
    	}
    	//head touches border
    	if(x[0]<0) {
    		running =false;
    	}
    	if(x[0]>screenwidth) {
    		running =false;
    	}
    	if(y[0]<0) {
    		running =false;
    	}
    	if(y[0]>screenheight) {
    		running =false;
    	}
    	if(!running) {
    		timer.stop();
    	}
    }

    public void gameover(Graphics g) {
    	 
    	g.setColor(Color.red);
    	g.setFont(new Font("Ink Free",Font.BOLD,75));
    	FontMetrics metrics2=getFontMetrics(g.getFont());
    	g.drawString("GAME OVER",(screenwidth-metrics2.stringWidth("GAME OVER"))/2,screenheight/2);
    	 g.setColor(Color.red);
       	g.setFont(new Font("Ink Free",Font.BOLD,40));
       	FontMetrics metrics1=getFontMetrics(g.getFont());
       	g.drawString("Score"+appleeaten,(screenwidth-metrics1.stringWidth("Score"+appleeaten))/2,g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkappple();
            checkcollision();
        }
        repaint();
    }

    public class MyKeyAdaptor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
        	switch(e.getKeyCode()) {
        	case KeyEvent.VK_LEFT:
        		if(direction!='R') {
        			direction='L';
        		}
        		break;
        	case KeyEvent.VK_RIGHT:
        		if(direction!='L') {
        			direction='R';
        		}
        		break;
        	case KeyEvent.VK_UP:
        		if(direction!='D') {
        			direction='U';
        		}
        		break;
        	case KeyEvent.VK_DOWN:
        		if(direction!='U') {
        			direction='D';
        		}
        		break;
        	}
        }
    }
}

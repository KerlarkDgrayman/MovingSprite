import java.util.Random;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.lang.IllegalArgumentException;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Drawing extends JComponent{

	Hero hero = new Hero();

	public int height = 0;
	public int width = 0;
	
	public int x = 50;
	public int y = 50;

	public int state = 0;

	public BufferedImage image;
	public BufferedImage backg;
	public BufferedImage slime;
	public URL resource = getClass().getResource("run0.png");
	public URL resource2 = getClass().getResource("run0R.png");
	public URL resource3 = getClass().getResource("attack0.png");
	public URL resource4 = getClass().getResource("idle0.png");
	
	public Random randomizer;
	public int enemyC;

	Monsters[] monsters = new Monsters[10];

	public Drawing(){
		randomizer = new Random();
		spawnEnemy();

		try{
			slime = ImageIO.read(resource4);
			image = ImageIO.read(resource);
			backg = ImageIO.read(getClass().getResource("JFrameBG.jpg"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		height = image.getHeight();
		width = image.getWidth();

		Startgame();
	}

	public void Startgame(){
		Thread gameThread = new Thread(new Runnable(){
			public void run(){
				while(true){
					try{
						for(int c = 0; c < monsters.length; c++){
							if(monsters[c]!=null){
								monsters[c].moveTo(x, y);
								repaint();
							}
						}
						Thread.sleep(100);
					}
					catch(InterruptedException e){
						e.printStackTrace();
					}
					
				}
			}
		});
		gameThread.start();
	}
	public void spawnEnemy(){
		if(enemyC < 10){
			monsters[enemyC] = new Monsters(randomizer.nextInt(500), randomizer.nextInt(500));
			enemyC++;
		}
	}
	public void attackAnimation(){
		Thread thread1 = new Thread(new Runnable(){
				public void run(){
					for (int ctr = 0; ctr < 5; ctr++){
						try{
							if(ctr == 4){
								resource = getClass().getResource("run0.png");
							}
							else{
								resource = getClass().getResource("attack"+ctr+".png");
							}

							try{
								image = ImageIO.read(resource);
							}
							catch(IOException e){
								e.printStackTrace();
							}
							repaint();
							Thread.sleep(100);
						} catch(InterruptedException e){
							e.printStackTrace();
						}
					}
					for(int x = 0; x < monsters.length; x++){
						if(monsters[x]!=null){
							if(monsters[x].contact){
								monsters[x].life = monsters[x].life - 10;
							}
						}
					}
				}
		});
		thread1.start();
	}
	public void reloadImage(){
		if(state==0){
			resource = getClass().getResource("run0.png");
		}
		else if(state==1){
			resource = getClass().getResource("run1.png");
		}
		else if(state==2){
			resource = getClass().getResource("run2.png");
		}
		else if(state==3){
			resource = getClass().getResource("run3.png");
		}
		else if(state==4){
			resource = getClass().getResource("run4.png");
		}
		else if(state==5){
			resource = getClass().getResource("run5.png");
			state = 0;
		}
		state++;
		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}	
	public void reloadImage2(){	
		if(state == 0){
			resource2 = getClass().getResource("run0R.png");
		}
		else if(state==1){
			resource2 = getClass().getResource("run1R.png");
		}
		else if(state==2){
			resource2 = getClass().getResource("run2R.png");
		}
		else if(state==3){
			resource2 = getClass().getResource("run3R.png");
		}
		else if(state==4){
			resource2 = getClass().getResource("run4R.png");
		}
		else if(state==5){
			resource2 = getClass().getResource("run5R.png");
			state = 0;
		}
		state++;
		try{
			image = ImageIO.read(resource2);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	public void reloadImage3(){	
		if(state == 0){
			resource3 = getClass().getResource("attack0.png");
		}
		else if(state == 1){
			resource3 = getClass().getResource("attack1.png");
		}
		else if(state == 2){
			resource3 = getClass().getResource("attack2.png");
		}
		else if(state == 3){
			resource3 = getClass().getResource("attack3.png");
			state = 0;
		}
		state++;	
		try{
			image = ImageIO.read(resource3);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void moveUp(){
		y = y - 5;
		reloadImage();
		repaint();
	}
	public void moveDown(){
		y = y + 5;
		reloadImage();
		repaint();
	}
	public void moveRight(){
		x = x + 5;
		reloadImage();
		repaint();
	}
	public void moveLeft(){
		x = x - 5;
		reloadImage2();
		repaint();
	}
	public void attack(){
		attackAnimation();
		reloadImage3();
		repaint();
	}
	public void special(){
		attackAnimation();
		reloadImage3();
		repaint();
	}
	
	public void checkCollision(){
		int xChecker = x + width;
		int yChecker = y;

		for(int x = 0; x < monsters.length; x++){
			boolean collideX = false;
			boolean collideY = false;

			if(monsters[x]!=null){
				monsters[x].contact = false;

				if(xChecker > monsters[x].xPos){
					if(yChecker - monsters[x].yPos < monsters[x].height){
						collideY = true;
					}
					else{
						if(monsters[x].yPos - yChecker < monsters[x].height){
							collideY = true;
						}
					}

					if(xChecker > monsters[x].xPos){	
							if(xChecker-monsters[x].xPos < monsters[x].height){
								collideX = true;
						}
					}
					else{
						if(monsters[x].xPos - xChecker < monsters[x].height){
							collideX = true;
						}
					}
				}	
			}

			if(collideX && collideY){
					System.out.println("collision!");
					monsters[x].contact = true;
			}
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(backg, 0, 0, this);
		g.drawImage(image, x, y, this);

		for(int c = 0; c < monsters.length; c++){
			if(monsters[c]!=null){
				g.drawImage(monsters[c].image, monsters[c].xPos, monsters[c].yPos, this);
				g.setColor(Color.GREEN);
				g.fillRect(monsters[c].xPos + 7, monsters[c].yPos, monsters[c].life, 2);
			}
		}
	}
}
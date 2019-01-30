public class Monsters{

	String name = "default";
	public int xPos = 300;
	public int yPos = 300;
	public int height = 30;
	public int width = 40;
	public int life = 10;
	public BufferedImage;
	public int toX = 0;
	public int toY = 0;
	public boolean contact;
	
	public Monsters(int xPos, int yPos){
		xPos = this.xPos;
		yPos = this.yPos;
	}


	public void moveTo(int toX, int toY){
		if(xPos < toX){
			xPos++;
		}
		else if(xPos > toX){
			xPos--;
		}
		if(yPos < toY){
			yPos++;
		}
		else if(yPos > toY){
			yPos--;
		}
	}
}
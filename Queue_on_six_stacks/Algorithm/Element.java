package Algorithm;

import java.awt.*;

class Element
{
    private static int speed = 1;

    private double posx, posy;
    private int width, height;

    private Rectangle outer, inner;
    private Color outerColor, innerColor;

    private String heldNumber;
    private int fontSize;

    static void setSpeed(int speed)
    {
        Element.speed = speed;
    }

	static int getSpeed()
	{
		return Element.speed;
	}

    Element(int posx, int posy, int width, int height, String heldNumber)
    {
        this.posx = posx;
        this.posy = posy;
        this.width = width;
        this.height = height;

        outer = new Rectangle(posx, posy, width, height);
        inner = new Rectangle(posx + (int)(width * 0.1), posy + (int)(height * 0.1), (int)(width * 0.8), (int)(height * 0.8));

        outerColor = new Color(181, 73, 14);
        //outerColor = new Color(13, 87, 123);
        innerColor = new Color(98, 155, 100);

        this.heldNumber = heldNumber;
        fontSize = 20;
    }
    
    Element(Element elem)
    {
        this.posx = elem.posx;
        this.posy = elem.posy;

        this.width = elem.width;
        this.height = elem.height;

        this.outer = new Rectangle((int)elem.outer.getX(), (int)elem.outer.getY(), (int)elem.outer.getWidth(), (int)elem.outer.getHeight());
        this.inner = new Rectangle((int)elem.inner.getX(), (int)elem.inner.getY(), (int)elem.inner.getWidth(), (int)elem.inner.getHeight());

        this.outerColor = new Color(elem.outerColor.getRGB());
        this.innerColor = new Color(elem.innerColor.getRGB());

        this.heldNumber = new String(elem.heldNumber);
        this.fontSize = elem.fontSize;
    }

    Element(int posx, int posy, int width, int height, String heldNumber, Color outer, Color inner)
    {
        this(posx, posy, width, height, heldNumber);

        outerColor = outer;
        innerColor = inner;
    }

    void setOuterColor(Color color)
    {
        outerColor = color;
    }

    void setInnerColor(Color color)
    {
        innerColor = color;
    }

    private void goXDirection(int tox)
    {
        int localSpeed = speed;

        float steps = (float)Math.abs(tox - posx) / (float)speed;

        int deltax = tox - (int)posx;
        float stepx = (float)deltax / steps;
    
        for (int i = 0; i < (int)steps; i++)
        {
            if (localSpeed != speed)
            {
                i = 0;
        
                steps = (float)Math.abs(tox - posx) / (float)speed;
                deltax = tox - (int)posx;
                stepx = (float)deltax / steps;

                localSpeed = speed;
            }

            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException ex)
            {
                System.out.println("Error: " + ex);
            }

            posx += stepx;
            
            outer.setLocation((int)posx, (int)posy);
            inner.setLocation((int)posx + (int)(width * 0.1), (int)posy + (int)(height * 0.1));
        }

        posx = tox; 
    }

    private void goYDirection(int toy)
    {
        int localSpeed = speed;

        float steps = (float)Math.abs(toy - posy) / (float)localSpeed;

        int deltay = toy - (int)posy;
        float stepy = (float)deltay / steps;

        for (int i = 0; i < (int)steps; i++)
        {
            if (localSpeed != speed)
            {
                i = 0;
        
                steps = (float)Math.abs(toy - posy) / (float)speed;
                deltay = toy - (int)posy;
                stepy = (float)deltay / steps;

                localSpeed = speed;
            }

            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException ex)
            {
                System.out.println("Error: " + ex);
            }

            posy += stepy;
            
            outer.setLocation((int)posx, (int)posy);
            inner.setLocation((int)posx + (int)(width * 0.1), (int)posy + (int)(height * 0.1));
        }

        posy = toy; 
    }

	void moveNoAxis(int tox, int toy)
	{
        int localSpeed = speed;

        double steps = Math.max(Math.abs(tox - posx) / (double)speed, Math.abs(toy - posy) / (double)speed);

        int deltax = tox - (int)posx;
        int deltay = toy - (int)posy;
        
		double stepx = (double)deltax / steps;
		double stepy = (double)deltay / steps;

        for (int i = 0; i < (int)steps; i++)
        {
            if (localSpeed != speed)
            {
                i = 0;
        
        		steps = Math.max(Math.abs(tox - posx) / (double)speed, Math.abs(toy - posy) / (double)speed);
                
				deltax = tox - (int)posx;
				deltay = toy - (int)posy;

                stepx = (double)deltax / steps;
                stepy = (double)deltay / steps;

                localSpeed = speed;
            }

            try
            {
                Thread.sleep(12);
            }
            catch (InterruptedException ex)
            {
                System.out.println("Error: " + ex);
            }

            posx += stepx;
            posy += stepy;
            
            outer.setLocation((int)posx, (int)posy);
            inner.setLocation((int)posx + (int)(width * 0.1), (int)posy + (int)(height * 0.1));
        }

        posx = tox;
        posy = toy;
        
		outer.setLocation((int)posx, (int)posy);
		inner.setLocation((int)posx + (int)(width * 0.1), (int)posy + (int)(height * 0.1));
	}

	void move(int tox, int toy)
	{
		if (tox < posx)
		{
			goYDirection(toy);
			goXDirection(tox);
		}
		else
		{
			goXDirection(tox);
			goYDirection(toy);
		}

		outer.setLocation((int)posx, (int)posy);
		inner.setLocation((int)posx + (int)(width * 0.1), (int)posy + (int)(height * 0.1));
	}

	void setPosx(int posx)
	{
		this.posx = posx;

		outer.setLocation(posx, (int)posy);
		inner.setLocation(posx + (int)(width * 0.1), (int)posy + (int)(height * 0.1));
	}

	void setPosy(int posy)
	{
		this.posy = posy;

		outer.setLocation((int)posx, posy);
		inner.setLocation((int)posx + (int)(width * 0.1), posy + (int)(height * 0.1));
	}

	int getPosx()
	{
		return (int)posx;
	}

	int getPosy()
	{
		return (int)posy;
	}

	int getWidth()
	{
		return width;
	}

	int getHeight()
	{
		return height;
	}

	Color getOuterColor()
	{
		return outerColor;
	}

	Color getInnerColor()
	{
		return innerColor;
	}

	void paint(Graphics g)
	{
		g.setColor(outerColor);
		g.fillRect((int)outer.getX(), (int)outer.getY(), (int)outer.getWidth(), (int)outer.getHeight());

		g.setColor(innerColor);
		g.fillRect((int)inner.getX(), (int)inner.getY(), (int)inner.getWidth(), (int)inner.getHeight());

		g.setColor(new Color(230, 230, 230));

		int scaleDown = heldNumber.length() / 2;

		if (scaleDown == 0)
		{
			scaleDown = 1;
		}

		int newFontSize = fontSize / scaleDown;

		int centerPosx = (int)outer.getX() + (int)outer.getWidth() / 2 - newFontSize / 3;
		int textPosx = centerPosx - heldNumber.length() / 2 * newFontSize / 3; 

		g.setFont(new Font(Font.SERIF, Font.PLAIN, newFontSize));
		g.drawString(heldNumber, textPosx, (int)outer.getY() + (int)outer.getHeight() / 2 + fontSize / 3);
	}
}

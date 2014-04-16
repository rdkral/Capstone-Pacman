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

import java.awt.*;
import java.util.Vector;

public class cpowerdot
{
	final int iX[]={1,19,1,19};
	final int iY[]={2,2,13,13};

	final static int iShowCount=32;
	final static int iHideCount=16;

	static int frameCount;
	static int showStatus;

	boolean powerDotEnabled = true;
	int iValid[];

	// the applet this object is associated to
	Window applet;
	Graphics graphics;

	// the ghosts it controls
	Vector<cghost> ghosts;

	// the power dot image
	Image imagePowerDot;

	// the blank image
	Image imageBlank;

	cpowerdot(Window a, Graphics g, Vector<cghost> gh)
	{
		applet=a;
		graphics=g;
		ghosts=gh;

		// initialize power dot	and image
		iValid = new int[4];

		imagePowerDot=applet.createImage(16,16);
		cimage.drawPowerDot(imagePowerDot);

		imageBlank=applet.createImage(16,16);
		Graphics imageG=imageBlank.getGraphics();
		imageG.setColor(Color.black);
		imageG.fillRect(0,0,16,16);
		
		frameCount=iShowCount;
		showStatus=1;	// show
	}

	public void start()
	{
		// all power dots available
		for (int i=0; i<4; i++)
			iValid[i]=1;
	}

	void clear(int dot)
	{
		graphics.drawImage(imageBlank, iX[dot]*16, iY[dot]*16, applet);
	}

	void eat(int iCol, int iRow)
	{
		if(powerDotEnabled)
		{
			for (int i=0; i<4; i++)
			{
				if (iX[i]==iCol && iY[i]==iRow)
				{
					iValid[i]=0;
					clear(i);
				}
			}
			for (int i=0; i<ghosts.size(); i++)
				ghosts.elementAt(i).blind();
		}
	}

	public void draw()
	{
		frameCount--;
		if (frameCount==0)
		{
			if (showStatus==1)
			{
				showStatus=0;
				frameCount=iHideCount;
			}
			else
			{
				showStatus=1;
				frameCount=iShowCount;
			}
		}
		for (int i=0; i<4; i++)
		{
			if (iValid[i]==1 && showStatus==1)
				if(Global.affectiveState == 1 || Global.affectiveState==3 || Global.affectiveState==6 || Global.affectiveState==7)
					{
						graphics.drawImage(imagePowerDot,iX[i]*16, iY[i]*16, applet);
						powerDotEnabled = true;
					}
				else
				{
					powerDotEnabled = false;
					graphics.drawImage(imageBlank, iX[i]*16, iY[i]*16, applet);
				}
			else
				graphics.drawImage(imageBlank, iX[i]*16, iY[i]*16, applet);
		}
	} 
}

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

import java.lang.Error;
import java.awt.*;

public class cghost
{
	final int IN=0;
	final int OUT=1;
	final int BLIND=2;
	final int EYE=3;


	/*
	 * Determines the speed of the ghosts. If steps = frames ghost are really fast
	 * The lower the steps compared to the frames the slower the ghost
	 * 
	 */
	final int[] steps=	{7, 7, 1, 1,6,2,1,9};
	final int[] frames=	{8, 8, 2, 1,8,3,10,9};

	final int INIT_BLIND_COUNT=600;	// remain blind for ??? frames
	int blindCount;

	cspeed speed=new cspeed();

	int iX, iY, iDir, iStatus;
	int iBlink, iBlindCount;

	// random calculation factors
	final int DIR_FACTOR=2;
	final int POS_FACTOR=10;

	// the applet this object is associated to
	Window applet;
	Graphics graphics;
	Color color;

	// the maze the ghosts knows
	cmaze maze;

	// the ghost image
	Image imageGhost; 
	Image imageBlind;
	Image imageEye;

	cghost(Window a, Graphics g, cmaze m, Color newColor)
	{
		applet=a;
		graphics=g;
		maze=m;
		color = newColor;

		imageGhost=applet.createImage(18,18);
		cimage.drawGhost(imageGhost, 0, color);

		imageBlind=applet.createImage(18,18);
		cimage.drawGhost(imageBlind,1, Color.white);

		imageEye=applet.createImage(18,18);
		cimage.drawGhost(imageEye,2, Color.lightGray);
	}

	public void start(int initialPosition, int round)
	{

		if (initialPosition>=6)
		{
			initialPosition++;
			iX=(4+initialPosition)*16; iY=8*16;
		}
		else if (initialPosition>=4)
		{
			iX=(4+initialPosition)*16; iY=8*16;
		}

		else if (initialPosition>=2)
		{
			initialPosition++;
			iX=(8+initialPosition)*16; iY=7*16;

		}
		else
		{
			iX=(8+initialPosition)*16; iY=7*16;
		}

		iDir=3;
		iStatus=IN;

		blindCount=INIT_BLIND_COUNT/((round+1)/2);

		speed.start(steps[iStatus], frames[iStatus]);
	}

	public void draw()
	{
		maze.DrawDot(iX/16, iY/16);
		maze.DrawDot(iX/16+(iX%16>0?1:0), iY/16+(iY%16>0?1:0));
		maze.DrawOneUp(iX/16, iY/16); //<---------------------------------One-up
		maze.DrawOneUp(iX/16+(iX%16>0?1:0), iY/16+(iY%16>0?1:0)); //<-----One-up

		if (iStatus==BLIND && iBlink==1 && iBlindCount%32<16)
			graphics.drawImage(imageGhost, iX-1, iY-1, applet);
		else if (iStatus==OUT || iStatus==IN)
			graphics.drawImage(imageGhost, iX-1, iY-1, applet);
		else if (iStatus==BLIND)
			graphics.drawImage(imageBlind, iX-1, iY-1, applet);
		else 
			graphics.drawImage(imageEye, iX-1, iY-1, applet);
	}  

	public void setColor(Color newColor)
	{
		color = newColor;
	}

	public Color getColor()
	{
		return color;
	}

	public void move(int iPacX, int iPacY, int iPacDir)
	{
		if (iStatus==BLIND)
		{
			iBlindCount--;
			if (iBlindCount<blindCount/3)
				iBlink=1;
			if (iBlindCount==0)
				iStatus=OUT;
			if (iBlindCount%2==1)	// blind moves at 1/2 speed
			return;
		}

		if (speed.isMove()==0)
			// no move
			return;

		if (iX%16==0 && iY%16==0)
			// determine direction
		{
			switch (iStatus)
			{
			case IN:
				iDir=INSelect();
				break;
			case OUT:
				iDir=OUTSelect(iPacX, iPacY, iPacDir);
				break;
			case BLIND:
				iDir=BLINDSelect(iPacX, iPacY, iPacDir);
				break;
			case EYE:
				iDir=EYESelect();
			}
		}

		if (iStatus!=EYE)
		{
			iX+= ctables.iXDirection[iDir];
			iY+= ctables.iYDirection[iDir];
		}
		else
		{	
			iX+=2* ctables.iXDirection[iDir];
			iY+=2* ctables.iYDirection[iDir];
		}

	}

	public int INSelect()
	// count available directions
	throws Error
	{
		int iM,i,iRand;
		int iDirTotal=0;

		for (i=0; i<4; i++)
		{
			iM=maze.iMaze[iY/16 + ctables.iYDirection[i]]
			              [iX/16 + ctables.iXDirection[i]];
			if (iM!=cmaze.WALL && i != ctables.iBack[iDir] )
			{
				iDirTotal++;
			}
		}
		// randomly select a direction
		if (iDirTotal!=0)
		{
			iRand=cuty.RandSelect(iDirTotal);
			if (iRand>=iDirTotal)
				throw new Error("iRand out of range");
			//				exit(2);
			for (i=0; i<4; i++)
			{
				iM=maze.iMaze[iY/16+ ctables.iYDirection[i]]
				              [iX/16+ ctables.iXDirection[i]];
				if (iM!=cmaze.WALL && i != ctables.iBack[iDir] )
				{
					iRand--;
					if (iRand<0)
						// the right selection
					{
						if (iM== cmaze.DOOR)
							iStatus=OUT;
						iDir=i;	break;
					}
				}
			}
		}	
		return(iDir);	
	}

	public int OUTSelect(int iPacX, int iPacY, int iPacDir)
	// count available directions
	throws Error
	{
		int iM,i,iRand;
		int iDirTotal=0;
		int[] iDirCount=new int [4];

		for (i=0; i<4; i++)
		{
			iDirCount[i]=0;
			iM=maze.iMaze[iY/16 + ctables.iYDirection[i]]
			              [iX/16+ ctables.iXDirection[i]];
			if (iM!=cmaze.WALL && i!= ctables.iBack[iDir] && iM!= cmaze.DOOR )
				// door is not accessible for OUT
			{
				iDirCount[i]++;
				iDirCount[i]+=iDir==iPacDir?
						DIR_FACTOR:0;
				switch (i)
				{
				case 0:	// right
					iDirCount[i] += iPacX > iX ? POS_FACTOR:0;
					break;
				case 1:	// up
					iDirCount[i]+=iPacY<iY?
							POS_FACTOR:0;
					break;
				case 2:	// left
					iDirCount[i]+=iPacX<iX?
							POS_FACTOR:0;
					break;
				case 3:	// down
					iDirCount[i]+=iPacY>iY?
							POS_FACTOR:0;
					break;
				}
				iDirTotal+=iDirCount[i];
			}
		}	
		// randomly select a direction
		if (iDirTotal!=0)
		{	
			iRand=cuty.RandSelect(iDirTotal);
			if (iRand>=iDirTotal)
				throw new Error("iRand out of range");
			// exit(2);
			for (i=0; i<4; i++)
			{
				iM=maze.iMaze[iY/16+ ctables.iYDirection[i]]
				              [iX/16+ ctables.iXDirection[i]];
				if (iM!=cmaze.WALL && i!= ctables.iBack[iDir] && iM!= cmaze.DOOR )
				{	
					iRand-=iDirCount[i];
					if (iRand<0)
						// the right selection
					{
						iDir=i;	break;
					}
				}
			}	
		}
		else	
			throw new Error("iDirTotal out of range");
		// exit(1);
		return(iDir);
	}

	public void blind()
	{
		if (iStatus==BLIND || iStatus==OUT)
		{
			iStatus=BLIND;
			iBlindCount=blindCount;
			iBlink=0;
			// reverse
			if (iX%16!=0 || iY%16!=0)
			{
				iDir= ctables.iBack[iDir];
				// a special condition:
				// when ghost is leaving home, it can not go back
				// while becoming blind
				int iM;
				iM=maze.iMaze[iY/16+ ctables.iYDirection[iDir]]
				              [iX/16+ ctables.iXDirection[iDir]];
				if (iM == cmaze.DOOR)
					iDir=ctables.iBack[iDir];
			}
		}
	}

	public int EYESelect()
	// count available directions
	throws Error
	{
		int iM,i,iRand;
		int iDirTotal=0;
		int [] iDirCount= new int [4];

		for (i=0; i<4; i++)
		{
			iDirCount[i]=0;
			iM=maze.iMaze[iY/16 + ctables.iYDirection[i]]
			              [iX/16+ctables.iXDirection[i]];
			if (iM!= cmaze.WALL && i!= ctables.iBack[iDir])
			{
				iDirCount[i]++;
				switch (i)
				{
				// door position 10,6
				case 0:	// right
					iDirCount[i]+=160>iX?
							POS_FACTOR:0;
					break;
				case 1:	// up
					iDirCount[i]+=96<iY?
							POS_FACTOR:0;
					break;
				case 2:	// left
					iDirCount[i]+=160<iX?
							POS_FACTOR:0;
					break;
				case 3:	// down
					iDirCount[i]+=96>iY?
							POS_FACTOR:0;
					break;
				}
				iDirTotal+=iDirCount[i];
			}	
		}
		// randomly select a direction
		if (iDirTotal!=0)
		{
			iRand= cuty.RandSelect(iDirTotal);
			if (iRand>=iDirTotal)
				throw new Error("RandSelect out of range");
			//				exit(2);
			for (i=0; i<4; i++)
			{
				iM=maze.iMaze[iY/16+ ctables.iYDirection[i]]
				              [iX/16+ ctables.iXDirection[i]];
				if (iM!= cmaze.WALL && i!= ctables.iBack[iDir])
				{
					iRand-=iDirCount[i];
					if (iRand<0)
						// the right selection
					{
						if (iM== cmaze.DOOR)
							iStatus=IN;
						iDir=i;	break;
					}
				}
			}
		}
		else
			throw new Error("iDirTotal out of range");
		return(iDir);	
	}	

	public int BLINDSelect(int iPacX, int iPacY, int iPacDir)
	// count available directions
	throws Error
	{
		int iM,i,iRand;
		int iDirTotal=0;
		int [] iDirCount = new int [4];

		for (i=0; i<4; i++)
		{
			iDirCount[i]=0;
			iM=maze.iMaze[iY/16+ ctables.iYDirection[i]][iX/16+ ctables.iXDirection[i]];
			if (iM != cmaze.WALL && i != ctables.iBack[iDir] && iM != cmaze.DOOR)
				// door is not accessible for OUT
			{
				iDirCount[i]++;
				iDirCount[i]+=iDir==iPacDir?
						DIR_FACTOR:0;
				switch (i)
				{
				case 0:	// right
					iDirCount[i]+=iPacX<iX?
							POS_FACTOR:0;
					break;
				case 1:	// up
					iDirCount[i]+=iPacY>iY?
							POS_FACTOR:0;
					break;
				case 2:	// left
					iDirCount[i]+=iPacX>iX?
							POS_FACTOR:0;
					break;
				case 3:	// down
					iDirCount[i]+=iPacY<iY?
							POS_FACTOR:0;
					break;
				}
				iDirTotal+=iDirCount[i];
			}	
		}
		// randomly select a direction
		if (iDirTotal!=0)
		{
			iRand=cuty.RandSelect(iDirTotal);
			if (iRand>=iDirTotal)
				throw new Error("RandSelect out of range");
			//				exit(2);
			for (i=0; i<4; i++)
			{	
				iM=maze.iMaze[iY/16+ ctables.iYDirection[i]]
				              [iX/16+ ctables.iXDirection[i]];
				if (iM!= cmaze.WALL && i!= ctables.iBack[iDir])
				{	
					iRand-=iDirCount[i];
					if (iRand<0)
						// the right selection
					{
						iDir=i;	break;
					}
				}
			}
		}
		else
			throw new Error("iDirTotal out of range");
		return(iDir);
	}

	// return 1 if caught the pac!
	// return 2 if being caught by pac
	int testCollision(int iPacX, int iPacY)
	{
		if (iX<=iPacX+2 && iX>=iPacX-2
				&& iY<=iPacY+2 && iY>=iPacY-2)
		{
			switch (iStatus)
			{
			case OUT:
				return(1);
			case BLIND:
				iStatus=EYE;
				iX=iX/4*4;
				iY=iY/4*4;
				return(2);
			}	
		}
		// nothing
		return(0);
	}
}

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

import java.util.Vector;

/**
 * cpacmove.java
 * calculate the move for pac 
 * this is used for machine controlled pac
 * for demonstration or else...
 */
class cpacmove
{
	// things that affect the decision:
	// ghosts status
	//   ghosts position
	//   ghosts movement direction
	// dots position
	//   ONLY the closest dot is goal
	// powerdot position
	//   the closer the ghosts, more weight to go to powerdot

	// direction score
	// each represents the score for that direction
	// the direction with the highest score will be chosen
	// if the chosen one is not available, the oposite score will be subtracted,
	//   and the rest compared again.
	int iDirScore[];

	int iValid[]; 

	cpac cPac;
	Vector<cghost> cGhost;
	cmaze cMaze;

	/**
	 * Constructor
	 * @param pac
	 * @param ghost
	 * @param maze
	 */
	cpacmove(cpac pac, Vector<cghost> ghost, cmaze maze)
	{
		iDirScore=new int[4];

		iValid=new int [4];
		cPac=pac;

		cGhost=new Vector<cghost>(4);
		for (int i=0; i<4; i++)
			cGhost.setElementAt(ghost.elementAt(i), i);

		cMaze=maze;
	}

	/**
	 * 
	 * @return
	 * @throws Error
	 */
	public int GetNextDir()
	throws Error
	{
		int i;

		// first, init to 0
		for (i=0; i<4; i++)
			iDirScore[i]=0;

		// add score for dot
		AddDotScore();

		// add score for ghosts
		AddGhostScore();

		// add score for powerdot
		AddPowerDotScore();

		// determine the direction based on scores

		for (i=0; i<4; i++)
			iValid[i]=1;

		int iHigh, iHDir;

		while (true) 
		{
			iHigh=-1000000;
			iHDir=-1;
			for (i=0; i<4; i++)
			{
				if (iValid[i] == 1 && iDirScore[i]>iHigh)
				{
					iHDir=i;
					iHigh=iDirScore[i];
				}
			}

			if (iHDir == -1)
			{
				throw new Error("cpacmove: can't find a way?");
			}

			if ( cPac.iX%16 == 0 && cPac.iY%16==0)
			{
				if ( cPac.mazeOK(cPac.iX/16 + ctables.iXDirection[iHDir],
						cPac.iY/16 + ctables.iYDirection[iHDir]) )
					return(iHDir);
			}
			else
				return(iHDir);

			iValid[iHDir]=0;
			//			iDirScore[ctables.iBack[iHDir]] = iDirScore[iHDir];

		}

		//	return(iHDir);  // will not reach here, ordered by javac
	}

	/**
	 * 
	 */
	void AddGhostScore()
	{
		int iXDis, iYDis;	// distance
		double iDis;		// distance

		int iXFact, iYFact;

		// calculate ghosts one by one
		for (int i=0; i<4; i++)
		{
			iXDis=cGhost.elementAt(i).iX - cPac.iX;
			iYDis=cGhost.elementAt(i).iY - cPac.iY;

			iDis=Math.sqrt(iXDis*iXDis+iYDis*iYDis);

			if (cGhost.elementAt(i).iStatus == cGhost.elementAt(i).BLIND)
			{


			}
			else
			{
				// adjust iDis into decision factor

				iDis=18*13/iDis/iDis;
				iXFact=(int)(iDis*iXDis);
				iYFact=(int)(iDis*iYDis);

				if (iXDis >= 0)
				{
					iDirScore[ctables.LEFT] += iXFact;
				}
				else
				{
					iDirScore[ctables.RIGHT] += -iXFact;
				}

				if (iYDis >= 0)
				{
					iDirScore[ctables.UP] += iYFact;
				}
				else
				{
					iDirScore[ctables.DOWN] += -iYFact;
				}
			}
		}
	}

	/**
	 * 
	 */
	void AddDotScore()
	{
		int i, j;

		int dDis, dShortest;
		int iXDis, iYDis;
		int iX=0, iY=0;

		dShortest=1000;

		// find the nearest dot
		for (i=0; i < cmaze.HEIGHT; i++)
			for (j=0; j < cmaze.WIDTH; j++)
			{
				if (cMaze.iMaze[i][j]==cmaze.DOT)
				{
					iXDis=j*16-8-cPac.iX;
					iYDis=i*16-8-cPac.iY;
					dDis=iXDis*iXDis+iYDis*iYDis;

					if (dDis<dShortest)
					{
						dShortest=dDis;

						iX=iXDis; iY=iYDis;
					}
				}	
			}

		// now iX and iY is the goal (relative position)

		int iFact=100000;

		if (iX > 0)
		{
			iDirScore[ctables.RIGHT] += iFact;
		}
		else if (iX < 0)
		{
			iDirScore[ctables.LEFT] += iFact;
		}

		if (iY > 0)
		{
			iDirScore[ctables.DOWN] += iFact;
		}
		else if (iY<0)
		{
			iDirScore[ctables.UP] += iFact;
		}
	}

	/**
	 * 
	 */
	void AddPowerDotScore()
	{

	}
}

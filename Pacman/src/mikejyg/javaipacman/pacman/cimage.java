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

public class cimage
{
	public static void drawDot(Image img)
	{
		Graphics g=img.getGraphics();
		g.setColor(Color.yellow);
		g.drawRect(0,0,2,2);
		g.dispose();
	}

	public static void drawPowerDot(Image img)
	{
		Graphics g=img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,16,16);
		g.setColor(Color.yellow);
		int iCounter=0;
		short mask=0x01;
		for (int i=0; i<16; i++)
			for (int j=0; j<16; j++)
			{
				if ((power_bits[iCounter] & mask)!=0)
					g.fillRect(j,i,1,1);
				mask <<=1;
				if ((mask&0xff)==0)
				{
					mask=0x01;
					iCounter++;
				}
			}
		g.dispose();
	}
	
	public static void drawOneUp(Image img)
	{
		Graphics g=img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,16,16);
		g.setColor(Color.white);
		int iCounter=0;
		short mask=0x01;
		for (int i=0; i<16; i++)
			for (int j=0; j<16; j++)
			{
				if ((oneup_bits[iCounter] & mask)!=0)
					g.fillRect(j,i,1,1);
				mask <<=1;
				if ((mask&0xff)==0)
				{
					mask=0x01;
					iCounter++;
				}
			}
		g.dispose();
	}

	public static void drawPac(Image img, int dir, int step)
	{
		Graphics g=img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,18,18);
		g.setColor(Color.yellow);
		int iCounter=0;
		short mask=0x01;
		for (int i=0; i<16; i++)
			for (int j=0; j<16; j++)
			{
				if ((pac_bits[dir][step][iCounter] & mask)!=0)
					g.fillRect(j+1,i+1,1,1);
				mask <<=1;
				if ((mask&0xff)==0)
				{
					mask=0x01;
					iCounter++;
				}
			}
		g.dispose();
	}

	public static void drawGhost(Image img, int number, Color color)
	{
		Graphics g=img.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0,0,18,18);
		g.setColor(color);
		int iCounter=0;
		short mask=0x01;
		for (int i=0; i<16; i++)
			for (int j=0; j<16; j++)
			{
				if ((ghost_bits[number][iCounter] & mask)!=0)
					g.fillRect(j+1,i+1,1,1);
				mask <<=1;
				if ((mask&0xff)==0)
				{
					mask=0x01;
					iCounter++;
				}
			}
		g.dispose();
	}

	////////////////////////////////////////////////////////////////////////////////
	// image arrays
	////////////////////////////////////////////////////////////////////////////////

	static final short [][][] pac_bits =
	{	// [4][4][32]
		// right 
		{
			{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x0f, 0xfe, 0x07, 0xfe, 0x03, 0xff, 0x01,
				0xff, 0x00, 0x7f, 0x00, 0x7f, 0x00, 0xff, 0x00, 0xff, 0x01, 0xfe, 0x03,
				0xfe, 0x07, 0xfc, 0x0f, 0xf8, 0x1f, 0xe0, 0x07},
				{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x3f, 0xff, 0x0f,
					0xff, 0x03, 0xff, 0x00, 0xff, 0x00, 0xff, 0x03, 0xff, 0x0f, 0xfe, 0x3f,
					0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07},
					{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x7f, 0xff, 0xff,
						0xff, 0x1f, 0xff, 0x01, 0xff, 0x01, 0xff, 0x1f, 0xff, 0xff, 0xfe, 0x7f,
						0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07},
						{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x7f, 0xff, 0xff,
							0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xfe, 0x7f,
							0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07},
		},
		// up
		{
			{  0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x40, 0x06, 0x60, 0x0f, 0xf0,
				0x1f, 0xf8, 0x3f, 0xfc, 0x7f, 0xfe, 0xff, 0xff, 0xff, 0xff, 0xfe, 0x7f,
				0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07},
				{  0x00, 0x00, 0x08, 0x10, 0x1c, 0x38, 0x1e, 0x78, 0x3e, 0x7c, 0x3f, 0xfc,
					0x7f, 0xfe, 0x7f, 0xfe, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xfe, 0x7f,
					0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07},
					{  0x20, 0x04, 0x38, 0x1c, 0x3c, 0x3c, 0x7e, 0x7e, 0x7e, 0x7e, 0x7f, 0xfe,
						0x7f, 0xfe, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xfe, 0x7f,
						0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07},
						{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x7f, 0xff, 0xff,
							0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xfe, 0x7f,
							0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07}
		},
		// left
		{
			{  0xe0, 0x07, 0xf8, 0x1f, 0xf0, 0x3f, 0xe0, 0x7f, 0xc0, 0x7f, 0x80, 0xff,
				0x00, 0xff, 0x00, 0xfe, 0x00, 0xfe, 0x00, 0xff, 0x80, 0xff, 0xc0, 0x7f,
				0xe0, 0x7f, 0xf0, 0x3f, 0xf8, 0x1f, 0xe0, 0x07},
				{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfc, 0x7f, 0xf0, 0xff,
					0xc0, 0xff, 0x00, 0xff, 0x00, 0xff, 0xc0, 0xff, 0xf0, 0xff, 0xfc, 0x7f,
					0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07},
					{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x7f, 0xff, 0xff,
						0xf8, 0xff, 0x80, 0xff, 0x80, 0xff, 0xf8, 0xff, 0xff, 0xff, 0xfe, 0x7f,
						0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07},
						{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x7f, 0xff, 0xff,
							0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xfe, 0x7f,
							0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07}
		},
		// down
		{
			{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x7f, 0xff, 0xff,
				0xff, 0xff, 0x7f, 0xfe, 0x3f, 0xfc, 0x1f, 0xf8, 0x0f, 0xf0, 0x06, 0x60,
				0x02, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00},
				{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x7f, 0xff, 0xff,
					0xff, 0xff, 0xff, 0xff, 0x7f, 0xfe, 0x7f, 0xfe, 0x3f, 0xfc, 0x3e, 0x7c,
					0x1e, 0x78, 0x1c, 0x38, 0x08, 0x10, 0x00, 0x00},
					{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x7f, 0xff, 0xff,
						0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0x7f, 0xfe, 0x7f, 0xfe, 0x7e, 0x7e,
						0x7e, 0x7e, 0x3c, 0x3c, 0x38, 0x1c, 0x20, 0x04},
						{  0xe0, 0x07, 0xf8, 0x1f, 0xfc, 0x3f, 0xfe, 0x7f, 0xfe, 0x7f, 0xff, 0xff,
							0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xfe, 0x7f,
							0xfe, 0x7f, 0xfc, 0x3f, 0xf8, 0x1f, 0xe0, 0x07}
		}
	};

	static final short [][] ghost_bits = {	// [3][32]
		{   0xe0, 0x07, 0xf8, 0x1f, 0xcc, 0x33, 0xbc, 0x3d, 0xfe, 0x7f, 0xde, 0x7b,
			0x8e, 0x71, 0x9e, 0x79, 0xfe, 0x7f, 0xfe, 0x7f, 0xfe, 0x7f, 0xfe, 0x7f,
			0xfe, 0x7f, 0x76, 0x6e, 0x76, 0x6e, 0x24, 0x24},
			{   0xe0, 0x07, 0x18, 0x18, 0x04, 0x20, 0x04, 0x20, 0x32, 0x4c, 0x4a, 0x52,
				0x8a, 0x51, 0x8a, 0x51, 0x72, 0x4e, 0x02, 0x40, 0x02, 0x40, 0x02, 0x40,
				0x8a, 0x51, 0x56, 0x6a, 0x52, 0x4a, 0x24, 0x24},
				// blind
				{   0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x30, 0x0c, 0x48, 0x12,
					0x88, 0x11, 0x88, 0x11, 0x70, 0x0e, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
					0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}
	};

	static final short [] power_bits =
		// [32]
		// show	
			/*
			unmasked image:
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			1	1	0	0	0	0	0	0	0	0	0	0	0	0	1	1
			1	1	1	0	0	0	0	0	0	0	0	0	0	1	1	1
			1	1	1	1	0	0	0	0	0	0	0	0	1	1	1	1
			1	1	1	1	1	0	0	0	0	0	0	1	1	1	1	1
			1	1	1	1	1	0	0	0	0	0	0	1	1	1	1	1
			1	1	1	1	1	0	0	0	0	0	0	1	1	1	1	1
			1	1	1	1	1	0	0	0	0	0	0	1	1	1	1	1
			1	1	1	1	0	0	0	0	0	0	0	0	1	1	1	1
			1	1	1	0	0	0	0	0	0	0	0	0	0	1	1	1
			1	1	0	0	0	0	0	0	0	0	0	0	0	0	1	1
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			
			masked:
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			0	0	0	0	0	0	1	1	1	1	0	0	0	0	0	0
			0	0	0	0	0	1	1	1	1	1	1	0	0	0	0	0
			0	0	0	0	1	1	1	1	1	1	1	1	0	0	0	0
			0	0	0	1	1	1	1	1	1	1	1	1	1	0	0	0
			0	0	0	1	1	1	1	1	1	1	1	1	1	0	0	0
			0	0	0	1	1	1	1	1	1	1	1	1	1	0	0	0
			0	0	0	1	1	1	1	1	1	1	1	1	1	0	0	0
			0	0	0	0	1	1	1	1	1	1	1	1	0	0	0	0
			0	0	0	0	0	1	1	1	1	1	1	0	0	0	0	0
			0	0	0	0	0	0	1	1	1	1	0	0	0	0	0	0
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0
			0	0	0	0	0	0	0	0	0	0	0	0	0	0	0	0

			*/
			 
	{   0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xc0, 0x03, 0xe0, 0x07, 0xf0, 0x0f,
		0xf8, 0x1f, 0xf8, 0x1f, 0xf8, 0x1f, 0xf8, 0x1f, 0xf0, 0x0f, 0xe0, 0x07,
		0xc0, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
	};
	
	static final short [] oneup_bits =
			// [32]
			// show	
		{   0x00, 0x00, 0x80, 0x01, 0xc0, 0x01, 0xe0, 0x01, 0xe0, 0x01, 0xe0, 0x01,
			0x80, 0x01, 0x80, 0x01, 0x80, 0x01, 0x80, 0x01, 0x80, 0x01, 0x80, 0x01,
			0xe0, 0x07, 0xe0, 0x07, 0xe0, 0x07, 0x00, 0x00
		};

}


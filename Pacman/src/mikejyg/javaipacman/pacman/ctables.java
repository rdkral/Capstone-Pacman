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

/**
 * the tables are used to speed up computation
 */
public class ctables
{
	// for direction computation
	public static final int[] iXDirection={1,0,-1,0};
	public static final int[] iYDirection={0,-1,0,1};
	public static final int[] iDirection=
	{
		-1,	// 0:
		1,	// 1: x=0, y=-1
		-1,	// 2:
		-1,	// 3:
		2,	// 4: x=-1, y=0
		-1,	// 5:
		0,	// 6: x=1, y=0
		-1,	// 7
		-1,     // 8
		3     	// 9: x=0, y=1
	};

	// backward direction
	public static final int[] iBack={2,3,0,1};

	// direction code
	public static final int RIGHT=0;
	public static final int UP=1;
	public static final int LEFT=2;
	public static final int DOWN=3;

	// the maze definition string
	public static String[] MazeDefine=
	{
		"XXXXXXXXXXXXXXXXXXXXX",	// 1
		"X...................X",	// 2
		"XO.................OX",	// 3
		"X...................X",	// 4
		"X........1..........X",	// 5
		"X...................X",	// 6
		"X......XXX-XXX......X",	// 7
		"X......X     X......X",	// 8
		"X......X     X......X",	// 9
		"X......XXXXXXX......X",	// 10
		"X......... .........X",	// 11
		"X...................X",	// 12
		"X...................X",	// 13
		"XO.................OX",	// 14
		"X...................X",	// 15
		"XXXXXXXXXXXXXXXXXXXXX",	// 16
		
	};
  public static String[] MazeDefine_lvl5=
	{
		"XXXXXXXXXXXXXXXXXXXXX",	// 1
		"X......1..X.........X",	// 2
		"XOXXX.XXX.X.XXX.XXXOX",	// 3
		"X......X..X.........X",	// 4
		"XXX.XX.X.XXX.XX.X.X.X",	// 5
		"X....X..........X.X.X",	// 6
		"X.XX.X.XXX-XXX.XX.X.X",	// 7
		"X.XX.X.X     X......X",	// 8
		"X.XX...X     X.XXXX.X",	// 9
		"X.XX.X.XXXXXXX.XXXX.X",	// 10
		"X....X.... .........X",	// 11
		"XXX.XX.XXXXXXX.X.X.XX",	// 12
		"X.........X....X....X",	// 13
		"XOXXXXXXX.X.XXXXXXXOX",	// 14
		"X...................X",	// 15
		"XXXXXXXXXXXXXXXXXXXXX",	// 16
	};
	public static String[] MazeDefine_lvl1=
	{
		"XXXXXXXXXXXXXXXXXXXXX",	// 1
		"X.....X..XXXX.X.....X",	// 2
		"XOXXX.X..XXXX.X.XXXOX",	// 3
		"X.XXX.X..XXXX.X.XXX.X",	// 4
		"X.XXX.X..XXXX.X.XXX.X",	// 5
		"X...................X",	// 6
		"XXXX...XXX-XXX......X",	// 7
		"X....X.X     X.X.XXXX",	// 8
		"X....X.X     X.X....X",	// 9
		"XXXX.X.XXXXXXX.X.XXXX",	// 10
		"X......... .........X",	// 11
		"X.XXX....XXXX..XXX..X",	// 12
		"X.XXX.XX.XXXX..XXX..X",	// 13
		"XOXXX.XX.XXXX..XXX.OX",	// 14
		"X........XXXX.......X",	// 15
		"XXXXXXXXXXXXXXXXXXXXX",	// 16
		
	};
	
	public static String[] MazeDefine_lvl2=
		{
			"XXXXXXXXXXXXXXXXXXXXX",	// 1
			"X.......1.......X...X",	// 2
			"XO...X..........X..OX",	// 3
			"XXXX.X..XXXXXX..X...X",	// 4
			"X....X..............X",	// 5
			"X..X.X...........XXXX",	// 6
			"X..X...XXX-XXX......X",	// 7
			"X..XXX.X     X.X.XX.X",	// 8
			"X......X     X.X....X",	// 9
			"XXXX...XXXXXXX...XXXX",	// 10
			"X......... .........X",	// 11
			"X...X..X.....X..X...X",	// 12
			"X..XXX.X.....X.XXX..X",	// 13
			"XO..X..X..X..X..X..OX",	// 14
			"X......X..X..X......X",	// 15
			"XXXXXXXXXXXXXXXXXXXXX",	// 16
			
		};
	public static String[] MazeDefine_lvl3=
		{
			"XXXXXXXXXXXXXXXXXXXXX",	// 1
			"X.....1......X..XX..X",	// 2
			"XO...X..X....X.....OX",	// 3
			"XXXXXX...XX..X.XXXXXX",	// 4
			"X.X..X.....X.X......X",	// 5
			"X.X..X.........XXXX.X",	// 6
			"X......XXX-XXX....X.X",	// 7
			"X..XXX.X     X.XX.X.X",	// 8
			"X..X...X     X.XX.X.X",	// 9
			"X..X...XXXXXXX....X.X",	// 10
			"X..XXX.... .....XXX.X",	// 11
			"X......X.....X..X...X",	// 12
			"X.XX.X.X.XXX.X..X.X.X",	// 13
			"XO...X.X.XXX.X..X.XOX",	// 14
			"X....X.......X..X...X",	// 15
			"XXXXXXXXXXXXXXXXXXXXX",	// 16
			
		};
	
	public static String[] MazeDefine_lvl4=
		{
			"XXXXXXXXXXXXXXXXXXXXX",	// 1
			"X....1....XX..X.....X",	// 2
			"XO....X.X.XX.XX....OX",	// 3
			"XX.XX.X.X.XX....XX.XX",	// 4
			"XX....X...XX..X....XX",	// 5
			"XX.................XX",	// 6
			"XXXXX..XXX-XXX..XX.XX",	// 7
			"XX.....X     X..XX.XX",	// 8
			"XXXXX..X     X..XX.XX",	// 9
			"XX.....XXXXXXX.....XX",	// 10
			"XX...X.... .....XX.XX",	// 11
			"XX.XXXXX..XX.XX....XX",	// 12
			"XX........XX.X..XX.XX",	// 13
			"XO.XXXXXX.XX.X..XX.OX",	// 14
			"X..X......XX.X......X",	// 15
			"XXXXXXXXXXXXXXXXXXXXX",	// 16
			
		};

}


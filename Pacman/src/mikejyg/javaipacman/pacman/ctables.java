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
		"XO............M....OX",	// 3
		"X...................X",	// 4
		"X........1..........X",	// 5
		"X...................X",	// 6
		"X......XXX-XXX......X",	// 7
		"X......X     X...G..X",	// 8
		"X......X     X......X",	// 9
		"X......XXXXXXX......X",	// 10
		"X......... .........X",	// 11
		"X......R............X",	// 12
		"X...................X",	// 13
		"XO.................OX",	// 14
		"X...................X",	// 15
		"XXXXXXXXXXXXXXXXXXXXX",	// 16
		
	};
	
	public static String[] MazeDefine_lvl1=
	{
		"XXXXXXXXXXXXXXXXXXXXX",	// 1
		"X.....X..XXXX.......X",	// 2
		"XOXXX.X..XXXX...XXXOX",	// 3
		"X.XXX.X..XXXX...XXX.X",	// 4
		"X.XXX.X..XXXX...XXX.X",	// 5
		"X...................X",	// 6
		"XXXX...XXX-XXX......X",	// 7
		"X....X.X     X.X.XXXX",	// 8
		"X....X.X     X.X....X",	// 9
		"XXXX.X.XXXXXXX.X....X",	// 10
		"X......... ......XXXX",	// 11
		"X.XXX....XXXX.......X",	// 12
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
			"XO...X.......X.....OX",	// 3
			"XXXXXX...XX..X.XXXXXX",	// 4
			"X.X..X.......X......X",	// 5
			"X.X..X.........XXXX.X",	// 6
			"X......XXX-XXX....X.X",	// 7
			"X..X...X     X.XX.X.X",	// 8
			"X..X...X     X.XX.X.X",	// 9
			"X..X...XXXXXXX....X.X",	// 10
			"X..X...... .....XXX.X",	// 11
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
			"XO....X.X.XX..X....OX",	// 3
			"XX.XX.X.X.XX....XX.XX",	// 4
			"XX....X...XX..X....XX",	// 5
			"XX.................XX",	// 6
			"XXXXX..XXX-XXX..XX.XX",	// 7
			"XX.....X     X..XX.XX",	// 8
			"XX.....X     X..XX.XX",	// 9
			"XXXXX..XXXXXXX.....XX",	// 10
			"XX........ .....XX.XX",	// 11
			"XX.XXXXX..XX..X....XX",	// 12
			"XX........XX..X.XX.XX",	// 13
			"XO..XXX...XX..X.XX.OX",	// 14
			"X....X....XX..X.....X",	// 15
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
	  public static String[] MazeDefine_lvl6=
			{
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"X...................X",	// 2
				"XO.XXX..XXX...1XXX.OX",	// 3
				"X...................X",	// 4
				"X.XX...XX...XXXXGX..X",	// 5
				"X..X.............X..X",	// 6
				"X....X.XXX-XXX.X....X",	// 7
				"X.XXRX.X     X.X..X.X",	// 8
				"X.X..X.X     X.X..X.X",	// 9
				"X....X.XXXXXXX.X....X",	// 10
				"X.XX...... .......X.X",	// 11
				"X......XX...XXXXM.X.X",	// 12
				"X..X................X",	// 13
				"XOXX.XX..XXXX..XX..OX",	// 14
				"X...................X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
				
			};
	  public static String[] MazeDefine_lvl7=
			{
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"X.........X.........X",	// 2
				"XOXX....M.X...1..XXOX",	// 3
				"X.X.......X.......X.X",	// 4
				"X.X.XX..XXXXX..XX.X.X",	// 5
				"X....X.........X....X",	// 6
				"X.XX.X.XXX-XXX.X.XX.X",	// 7
				"X.X....X     X....X.X",	// 8
				"X.X.XX.X     X.XX.X.X",	// 9
				"X....X.XXXXXXX.X....X",	// 10
				"X.XX.X.... ....X.XX.X",	// 11
				"X.X.....XXXXX.....X.X",	// 12
				"X.X.XX....X....XX.X.X",	// 13
				"XO..RX....X....XG..OX",	// 14
				"X....X....X....X....X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
				
			};
	  public static String[] MazeDefine_lvl8=
			{
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"X...................X",	// 2
				"XO.XXXXX.X.X.XXXXX.OX",	// 3
				"X..X...X.X.X.X...X..X",	// 4
				"X..X...X.X.X.X...X..X",	// 5
				"X..X........M....X..X",	// 6
				"X..X...XXX-XXX...X..X",	// 7
				"X..R...X     X...1..X",	// 8
				"X..X...X     X...X..X",	// 9
				"X..X...XXXXXXX...X..X",	// 10
				"X..X...... ......X..X",	// 11
				"X..X...X.X.X.X...X..X",	// 12
				"X..X...X.X.X.X...X..X",	// 13
				"XO.XXXXX.X.X.XXXXX.OX",	// 14
				"X...........G.......X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
				
			};
	  
	  public static String[] MazeDefine_lvl9=
			{
				"XXXXXXXXXXXXXXXXXXXXX",	// 1
				"X..............M....X",	// 2
				"XOX.XXXXXXXXXXXXX.XOX",	// 3
				"X.X.............X.X.X",	// 4
				"X.X1X...........X.X.X",	// 5
				"X.X.X...........X.X.X",	// 6
				"X.X.X..XXX-XXX..X.X.X",	// 7
				"X.XGX..X     X..X.X.X",	// 8
				"X.X.X..X     X..X.X.X",	// 9
				"X.X.X..XXXXXXX..X.X.X",	// 10
				"X.X.X..... .....X.X.X",	// 11
				"X.X.XXXXXXXXXXXXX.X.X",	// 12
				"X.X.......R.......X.X",	// 13
				"XOXXXXXXXXXXXXXXXXXOX",	// 14
				"X...................X",	// 15
				"XXXXXXXXXXXXXXXXXXXXX",	// 16
				
			};
	  

	  

}


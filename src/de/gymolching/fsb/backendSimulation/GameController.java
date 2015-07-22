package de.gymolching.fsb.backendSimulation;

import com.icyfire3d.main.*;

public class GameController
{
	public static void main(String[] args)
	{
		IF3DGame game = new IF3DGame(60, 1920, 1080, false);
		game.setGameScene(new MainScene());
		game.setDeltatimeThreshold(1.0f / 30.0f);
		game.start();
	}
}

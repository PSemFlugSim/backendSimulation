package de.gymolching.fsb.backendSimulation;
import java.util.ArrayList;

import com.icyfire3d.main.IF3DGameScene;
import com.icyfire3d.util.IF3DResource;

public class MainScene extends IF3DGameScene
{
	private Cube cube;

	@Override
	public String getGameTitle()
	{
		return "CityBuilder";
	}

	@Override
	protected void init()
	{
		this.cube = new Cube();
	}

	@Override
	protected void update()
	{
		this.cube.update();
	}

	@Override
	protected void updateLoadingScreen()
	{
		this.cube.update();
	}

	@Override
	protected void render(float lag)
	{
		this.cube.render(lag);
	}

	@Override
	protected void renderLoadingScreen(float lag)
	{
		this.cube.render(lag);
	}

	@Override
	protected void dispose()
	{
		this.cube.dispose();
	}

	@Override
	protected ArrayList<IF3DResource> getResources()
	{
		ArrayList<IF3DResource> resources = new ArrayList<>();
		return resources;
	}
}

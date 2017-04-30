package openhexagonos.game;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.scene.shape.Sphere;

import openhex.es.Identifier;
import openhex.es.ResourceDescriptor;
import openhex.es.ResourceTypes;
import openhex.game.Game;
import openhex.game.board.HexTile;
import openhex.game.unit.UnitFactory;
import openhex.run.Test;
import openhex.util.DuplicateInstanceException;
import openhex.vec.fin.VectorAS;
import openhex.view.ViewApplication;

public class HexagonosGame {
	
	private static final Logger LOG = LoggerFactory.getLogger(HexagonosGame.class);

	public static final int BOARDSIZE = 4;
	
	public HexagonosGame () {
		
		LOG.trace("Starting test...");
		
		VectorAS a = new VectorAS(0,0,0);
		VectorAS b = new VectorAS(0,0,0);
		
		LOG.info("Is VectorAS {} the same as VectorAS {}? {}", a, b, a.equals(b));
		
		LOG.trace("Creating game...");
		Game g;
		try {
			g = new Game();
		} catch (DuplicateInstanceException e) {
			LOG.error("Duplicate instance of Game! {}", e);
			LOG.error("How is that even possible?");
			LOG.error("Exitting application...");
			return;
		}
		
		LOG.trace("Adding a unit...");
		UnitFactory.createUnit(new Identifier(), UUID.randomUUID(), new ResourceDescriptor(true)
				.putResource(ResourceTypes.MESH, new Sphere(8, 8, 0.5f))
				);
		
		LOG.trace("Create game board...");
		generateGameBoard(g,BOARDSIZE);
	
	  
		LOG.trace("Creating Application...");
		ViewApplication vapp = new ViewApplication();
		LOG.debug("Staring Application {}...", vapp);
		vapp.start();
		
		LOG.debug("Testing method 'main()' has returned");
		
		
	}
	
	private void generateGameBoard (Game g, int radius) {
		
	    VectorAS tmp = new VectorAS(0,0,0);
	    VectorAS start = new VectorAS(0,0,0);
	    
	    VectorAS[] dirs = {
	    new VectorAS(+1,  0,0), new VectorAS(+1, -1,0), new VectorAS( 0, -1,0),
	    new VectorAS(-1,  0,0), new VectorAS(-1, +1,0), new VectorAS( 0, +1,0) 

	    };
	    
	    //add pos 0
		g.getBoard().addTile(new HexTile(new VectorAS(0, 0, 0), new ResourceDescriptor(true)));
		

		for (int r = 1; r <= radius; r++)
		{
		    tmp = (VectorAS) start.add(dirs[4]);
		    for (int t = 0; t < r -1; t++)
		    {
		    	tmp = (VectorAS) tmp.add(dirs[4]);
		    }
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < r; j++)
				{
					tmp = (VectorAS) tmp.add(dirs[i]);
					g.getBoard().addTile(new HexTile(tmp, new ResourceDescriptor(true)));
				}
			}
		}
		
		// Delete the three center positions
		g.getBoard().removeTile(dirs[0]);
		g.getBoard().removeTile(dirs[2]);
		g.getBoard().removeTile(dirs[4]);
		
	}
	

}

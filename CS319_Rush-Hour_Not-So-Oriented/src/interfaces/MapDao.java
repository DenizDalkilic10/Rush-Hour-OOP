package interfaces;

import source.model.Map;
import source.model.Player;

public interface MapDao {
	
	Map extractMap(int level, Player player, boolean original);

}

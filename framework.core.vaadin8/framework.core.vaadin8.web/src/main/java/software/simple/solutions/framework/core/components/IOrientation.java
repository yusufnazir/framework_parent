package software.simple.solutions.framework.core.components;

import java.io.Serializable;

/**
 * <ul>
 * <li>OLD_SCHOOL: indicates the old design in which the menu is at the top. The
 * search filter is at the right</li>
 * <li>NEWBIE: indicates the new design in which the menu is at the side. The
 * search is at the top.</li>
 * </ul>
 * 
 * @author yusuf
 *
 */
public interface IOrientation extends Serializable  {

	public enum Orientation {
		OLD_SCHOOL, NEWBIE;
	}

	public Orientation getOrientation();

	public void setOrientation(Orientation orientation);
}

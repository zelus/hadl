package M2;

/**
 * High-level link in HADL.
 * <p>
 * Link is defined by a name. Subclasses define the real semantic of links :
 *  <ul>
 *   <li>
 *    Attachment represents same level links.
 *   </li>
 *   <li>
 *    Binding represents cross-level links.
 *   </li>
 *  </ul>
 * </p>
 * @author CaterpillarTeam
 */
public class Link {

	private String name;
	
	public Link(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}

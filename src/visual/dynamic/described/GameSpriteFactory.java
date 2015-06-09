package visual.dynamic.described;

/**
 * An interface that desribes how factories should create various GameSprites
 *
 * @author Zachary Bolan
 * @version 11/27/13
 *
 * @param <T>
 */
public interface GameSpriteFactory<T extends AbstractGameSprite> {
	
	/**
	 * Method for creating a particular AbstractGameSprite
	 * @return	The new AbstractGameSprite
	 */
	T createSprite();
}
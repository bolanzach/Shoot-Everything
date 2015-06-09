package visual.dynamic.described;

import io.ResourceFinder;
import visual.statik.TransformableContent;

public class BernsteinFactory implements GameSpriteFactory<BossBernstein> {

	private ResourceFinder				finder;
	private TransformableContent		content;
	
	public BernsteinFactory(TransformableContent content, ResourceFinder finder) {
		this.finder = finder;
		this.content = content;
	}
	
	@Override
	public BossBernstein createSprite() {
		return new BossBernstein(content, finder, 0,0,0,0);
	}

}

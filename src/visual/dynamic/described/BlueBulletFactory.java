package visual.dynamic.described;

import io.ResourceFinder;
import visual.statik.TransformableContent;

public class BlueBulletFactory implements GameSpriteFactory<BlueBullet> {

	private ResourceFinder				finder;
	private TransformableContent		content;
	
	public BlueBulletFactory(TransformableContent content, ResourceFinder finder) {
		this.finder = finder;
		this.content = content;
	}
	
	@Override
	public BlueBullet createSprite() {
		return new BlueBullet(content, finder, 0,0,0,0);
	}

}

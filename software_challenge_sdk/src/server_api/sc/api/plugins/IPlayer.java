package sc.api.plugins;

import sc.api.plugins.host.IPlayerListener;


public interface IPlayer
{
	public void addPlayerListener(IPlayerListener listener);
	public void removePlayerListener(IPlayerListener listener);
	public void setDisplayName(String displayName);
	public String getDisplayName();
	public void setShouldBePaused(boolean shouldBePaused);
	public void setCanTimeout(boolean canTimeout);
	public void setViolated(boolean violated);
	public boolean hasViolated();
	public void setLeft(boolean left);
	public boolean hasLeft();
	public void setSoftTimeout(boolean timeout);
	public boolean hasSoftTimeout();
	public void setHardTimeout(boolean timeout);
	public boolean hasHardTimeout();
}

package ca.kanoa.RodsTwo.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ca.kanoa.RodsTwo.RodsTwo;

public class CooldownBar extends BukkitRunnable {

	private final Player player;
	private long startTime;
	private int percent;

	public CooldownBar(Player player) {
		this.player = player;
		if (this.player.hasPermission("lr.cooldown.bar")){
			this.startTime = System.currentTimeMillis();
			this.percent = 100;
			render();
			Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, this, 2l);
		}
		
	}

	public void render() {
		player.setExp((float) ((double) this.percent / (double) 100));
	}
	
	private int getPercent() {
		long cooldown = RodsTwo.cooldowns.get(player.getName()) - this.startTime;
		long timeLeft = RodsTwo.cooldowns.get(player.getName()) - System.currentTimeMillis();
		double rawPercent = (double) timeLeft / (double) cooldown;
		return (int) (rawPercent * 100);
	}

	@Override
	public void run() {
		this.percent = getPercent();
		if (this.percent >= 1) {
			render();
			Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, this, 2l);
		} 
		else
			player.setExp(0.00f);
	}

}

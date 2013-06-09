
//new CooldownBar(player).setPercent(50).render();

package ca.kanoa.RodsTwo.Objects;

import java.lang.reflect.Field;

import net.minecraft.server.v1_5_R3.DataWatcher;
import net.minecraft.server.v1_5_R3.EntityEnderDragon;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityWither;
import net.minecraft.server.v1_5_R3.Packet24MobSpawn;
import net.minecraft.server.v1_5_R3.Packet29DestroyEntity;
import net.minecraft.server.v1_5_R3.Packet38EntityStatus;
import net.minecraft.server.v1_5_R3.Packet40EntityMetadata;
import net.minecraft.server.v1_5_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftWither;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import ca.kanoa.RodsTwo.RodsTwo;

@SuppressWarnings("unused")
public class CooldownBar {

	private final Player player;
	private int percent;
	private boolean hidden;
	private EntityWither wither;

	public CooldownBar(Player player) {
		this.player = player;
		this.percent = 50;
		this.hidden = false;
		wither = new EntityWither(((CraftWorld) player.getWorld()).getHandle());
		wither.setCustomName("Cooldown");
	}

	public void hide() {
		this.hidden = true;
	}

	public void render() {
		CraftPlayer cp = (CraftPlayer) player;
		Location l = getLocationBeind(player.getLocation(), 128);
		wither.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		wither.setInvisible(true);
		cp.getHandle().playerConnection.sendPacket(new Packet24MobSpawn(wither));
		new CraftWither((CraftServer)Bukkit.getServer(), wither).getHandle().getDataWatcher().watch(16, percent * 3);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			public void run() {
				((CraftPlayer)player).getHandle().playerConnection.sendPacket(new Packet29DestroyEntity(wither.id));
			}}, 40L);
	}

	public CooldownBar setPercent(int percent) {
		if (percent >= 0 && percent <= 100)
			this.percent = percent;
		return this;
	}

	private Location getLocationBeind(Location loc, int dist) {
		int direction = (int)loc.getYaw();

		if (direction < 0) {
			direction += 360;
			direction = (direction + 45) / 90;
		}
		else {
			direction = (direction + 45) / 90;
		}

		switch (direction) {
		case 3: return loc.add(dist, 0, 0);
		case 4: return loc.add(0, 0, dist);
		case 1: return loc.add(dist * -1, 0, 0);
		case 2: return loc.add(0, 0, dist * -1);
		case 0: return loc.add(0, 0, dist);
		default: return loc;
		}
	}

}

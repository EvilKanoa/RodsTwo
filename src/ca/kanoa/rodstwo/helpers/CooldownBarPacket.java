//
//new CooldownBar(player).setPercent(50).render();
//
package ca.kanoa.rodstwo.helpers;
//
//import java.lang.reflect.Field;
//
//import net.minecraft.server.v1_5_R3.DataWatcher;
//import net.minecraft.server.v1_5_R3.EntityEnderDragon;
//import net.minecraft.server.v1_5_R3.EntityLiving;
//import net.minecraft.server.v1_5_R3.EntityWither;
//import net.minecraft.server.v1_5_R3.Packet24MobSpawn;
//import net.minecraft.server.v1_5_R3.Packet29DestroyEntity;
//import net.minecraft.server.v1_5_R3.Packet38EntityStatus;
//import net.minecraft.server.v1_5_R3.Packet40EntityMetadata;
//import net.minecraft.server.v1_5_R3.World;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
//import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
//import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEnderDragon;
//import org.bukkit.craftbukkit.v1_5_R3.entity.CraftEntity;
//import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
//import org.bukkit.craftbukkit.v1_5_R3.entity.CraftWither;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.entity.Player;
//
//import ca.kanoa.RodsTwo.RodsTwo;
//
//@SuppressWarnings("unused")
public class CooldownBarPacket {
//
//	private final Player player;
//	private int percent;
//	private boolean hidden;
//	private EntityEnderDragon dragon;
//
//	public CooldownBarPacket(Player player) {
//		this.player = player;
//		this.percent = 50;
//		this.hidden = false;
//		dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
//		dragon.setCustomName("Cooldown");
//	}
//
//	public void hide() {
//		this.hidden = true;
//	}
//
//	public void render() {
//		CraftPlayer cp = (CraftPlayer) player;
//		Location l = getLocationBeind(player.getLocation(), 10);
//		dragon.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
//		dragon.setInvisible(true);
//		cp.getHandle().playerConnection.sendPacket(new Packet24MobSpawn(dragon));
//		new CraftEnderDragon((CraftServer)Bukkit.getServer(), dragon).getHandle().getDataWatcher().watch(16, percent * 2);
//		
//		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
//			public void run() {
//				((CraftPlayer)player).getHandle().playerConnection.sendPacket(new Packet29DestroyEntity(dragon.id));
//			}}, 40L);
//	}
//
//	public CooldownBarPacket setPercent(int percent) {
//		if (percent >= 0 && percent <= 100)
//			this.percent = percent;
//		return this;
//	}
//
//	private Location getLocationBeind(Location loc, int dist) {
//		int direction = (int)loc.getYaw();
//
//		if (direction < 0) {
//			direction += 360;
//			direction = (direction + 45) / 90;
//		}
//		else {
//			direction = (direction + 45) / 90;
//		}
//
//		switch (direction) {
//		case 3: return loc.add(dist, 0, 0);
//		case 4: return loc.add(0, 0, dist);
//		case 1: return loc.add(dist * -1, 0, 0);
//		case 2: return loc.add(0, 0, dist * -1);
//		case 0: return loc.add(0, 0, dist);
//		default: return loc;
//		}
//	}
//
}

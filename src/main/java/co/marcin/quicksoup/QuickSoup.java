package co.marcin.quicksoup;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class QuickSoup extends JavaPlugin implements CommandExecutor {
	private static QuickSoup instance;
	private long cooldown;
	private final Map<UUID, Long> cooldownMap = new HashMap<>();

	@Override
	public void onEnable() {
		instance = this;

		getCommand("quicksoup").setExecutor(this);
		saveDefaultConfig();

		cooldown = StringUtils.stringToSeconds(getConfig().getString("cooldown"));

		info("v" + getDescription().getVersion() + " Enabled");
	}

	@Override
	public void onDisable() {
		info("v" + getDescription().getVersion() + " Disabled");
	}

	public static void info(String msg) {
		Bukkit.getLogger().info("[QuickSoup] " + msg);
	}

	public static QuickSoup getInstance() {
		return instance;
	}

	private long getTimePassed(UUID uuid) {
		return cooldownMap.containsKey(uuid) ? (NumberUtils.systemSeconds() - cooldownMap.get(uuid)) : 0;
	}

	private boolean isCooledDown(UUID uuid) {
		return !cooldownMap.containsKey(uuid) || getTimePassed(uuid) > cooldown;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("quicksoup.refill")) {
			Message.NOPERMISSIONS.send(sender);
			return true;
		}

		if(!(sender instanceof Player)) {
			Message.CONSOLESENDER.send(sender);
			return true;
		}

		Player player = (Player) sender;

		if(!sender.hasPermission("quicksoup.nocooldown")) {
			if(!isCooledDown(player.getUniqueId())) {
				String timeLeft = StringUtils.secondsToString(cooldown - getTimePassed(player.getUniqueId()), TimeUnit.MINUTES);
				Message.COOLDOWN.setVar(VarKey.TIME, timeLeft).send(sender);
				return true;
			}

			cooldownMap.put(player.getUniqueId(), NumberUtils.systemSeconds());
		}

		int index = 0;
		for(ItemStack itemStack : player.getInventory().getStorageContents()) {
			if(getConfig().getBoolean("fillemptyslots") && (itemStack == null || itemStack.getType() == Material.AIR) || itemStack.getType() == Material.BOWL) {
				player.getInventory().setItem(index, new ItemStack(Material.MUSHROOM_SOUP, 1));
			}

			index++;
		}

		return true;
	}
}

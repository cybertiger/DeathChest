package de.tobiyas.deathchest.chestpositions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import de.tobiyas.deathchest.DeathChest;

public class InitStructure {

	private static DeathChest plugin;
	
	/**
	 * Checks and inits the packaging system.
	 * Creates a Demo with all worlds in one package if none exists
	 */
	public static void checkAndInitPaths(){
		plugin = DeathChest.getPlugin();
		
		String packagePath = plugin.getDataFolder() + File.separator + "packages";
		fileCreate(packagePath + ".yml");
		checkPath(packagePath + File.separator);
		
		fileCreate2(packagePath+File.separator + "package1.yml");
	}


	
	/**
	 * Creates the packages.yml
	 * 
	 * @param path
	 */
	private static void fileCreate(String path){
		File file = new File(path);
		
		if(!file.exists())
			try {
				file.createNewFile();
				
				YamlConfiguration config = new YamlConfiguration();
				config.load(file);
				
				List<World> worlds = Bukkit.getWorlds();
				
				String worldString = "";
				
				for(World world : worlds){
					worldString += world.getName() + ";";
				}
				
				worldString = worldString.substring(0, worldString.length() - 1);
				config.createSection("worldpackages");
				config.set("worldpackages.package1", worldString);
				
				config.save(file);
			} catch (IOException e) {
				plugin.log("Critical Error on File Creation: " + path);
			} catch (InvalidConfigurationException e) {
				plugin.log("Could not create Package file");
			}
	}
	
	
	/**
	 * Creates a package in /plugins/DeathChest/packages/
	 * 
	 * @param path
	 */
	private static void fileCreate2(String path) {
		File file = new File(path);
		
		if(!file.exists())
			try {
				file.createNewFile();
				
				YamlConfiguration config = new YamlConfiguration();
				config.load(file);
				
				config.createSection("package1");
				config.createSection("config");
				config.createSection("config.maxTransferredItems");
				config.save(file);
				
			} catch (IOException e) {
				plugin.log("Critical Error on File Creation: " + path);
			} catch (InvalidConfigurationException e) {
				plugin.log("Could not create Package file");
			}
		
	}
	
	
	/**
	 * Checks if a path exists and creates it, if not
	 * 
	 * @param path
	 */
	private static void checkPath(String path){
		File file = new File(path);
		
		if(!file.exists())
			file.mkdir();
	}
}

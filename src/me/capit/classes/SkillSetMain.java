package me.capit.classes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import me.capit.ds_mc.DSMCMain;

import org.bukkit.plugin.java.JavaPlugin;

public class SkillSetMain extends JavaPlugin {
	public static HashMap<String,Skills> skillsData = new HashMap<String,Skills>();
	public static SkillTree tree;
	public static InteractionListener listener;
	
	public static Logger logger;
	
	@Override
	public void onEnable(){
		logger = this.getLogger();
		logger.info(DSMCMain.formHeader(SkillSetMain.class));
		
		this.saveDefaultConfig();
		
		logger.info("Attempting to read disk content...");
		getSkillsFromDisk();
		
		logger.info("Registering data from configuration...");
		tree = new SkillTree(this, getConfig());
		
		logger.info("Registering events...");
		listener = new InteractionListener(this);
		getServer().getPluginManager().registerEvents(listener, this);
		
		logger.info("Registering commands & permissions...");
	}
	
	@Override
	public void onDisable(){
		
	}
	
	@SuppressWarnings("unchecked")
	public void getSkillsFromDisk(){
		File skills = new File(this.getDataFolder().getPath()+File.separator+"skills.bin");
		if (skills.exists()){
			try {
				List<String> strs = Files.readAllLines(skills.toPath());
				String content = "";
				for (String s : strs){
					content+=s;
				}
				Object obj = DSMCMain.shuttle.deserializeObject(content.getBytes());
				if (obj instanceof HashMap<?,?>){
					skillsData = (HashMap<String,Skills>) obj;
				}
			} catch (IOException e) {
				// Ignore.
			}
		} else {
			try {
				skills.createNewFile();
			} catch (IOException e) {
				// Ignore.
			}
		}
	}
	
	public void putSkillsToDisk(){
		
	}
}

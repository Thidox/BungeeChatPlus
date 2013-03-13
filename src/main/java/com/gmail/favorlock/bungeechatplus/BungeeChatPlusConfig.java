package com.gmail.favorlock.bungeechatplus;

import java.io.File;
import java.util.ArrayList;

import net.craftminecraft.bungee.bungeeyaml.supereasyconfig.Config;

public class BungeeChatPlusConfig extends Config {
	
	public BungeeChatPlusConfig(BungeeChatPlus plugin) {
		CONFIG_FILE = new File("plugins" + File.separator + plugin.getDescription().getName(), "config.yml");
		CONFIG_HEADER = "BungeeChatPlus - By Favorlock";
	}
	
	public String Settings_ChatFormat = "&8[&2%server&8]%prefix&6%player&7%suffix: %message";
	public boolean Settings_GlobalChatOnLogin = true;
	public boolean Settings_EnableLog = true;
	public String Settings_warnmsg = "&4[&6BungeeChat+&4] &4Warned by BungeeChat+!";
	public String Settings_kickmsg = "&4[&6BungeeChat+&4] &4Kicked by BungeeChat+!";
	public ArrayList<String> FactionServers = new ArrayList<String>();

}

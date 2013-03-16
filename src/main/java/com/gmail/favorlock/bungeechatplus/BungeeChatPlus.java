package com.gmail.favorlock.bungeechatplus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.gmail.favorlock.bungeechatplus.cmd.CommandHandler;
import com.gmail.favorlock.bungeechatplus.cmd.commands.BCP;
import com.gmail.favorlock.bungeechatplus.cmd.commands.Verbose;
import com.gmail.favorlock.bungeechatplus.entities.Chatter;
import com.gmail.favorlock.bungeechatplus.listeners.ChatListener;
import com.gmail.favorlock.bungeechatplus.listeners.PluginMessageListener;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class BungeeChatPlus extends Plugin {
	
	private final CommandHandler commandHandler = new CommandHandler(this);
	private BungeeChatPlusConfig config;
	private Map<String,Chatter> chatters;
	private CommandSender console;
	private RegexManager regex;
	
	public void onEnable() {
		try {
			// Initialize Config
			config = new BungeeChatPlusConfig(this);
			config.init();
		} catch(Exception ex) {
			getProxyServer().getLogger().log(Level.SEVERE, "FAILED TO LOAD CONFIG!!!", ex);
			return;
		}
		try {
			// Initialize Console CommandSender
			console = (CommandSender) Class.forName("net.md_5.bungee.command.ConsoleCommandSender").getDeclaredMethod("getInstance").invoke(null);
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
		// Initialize Chatters
		chatters = new HashMap<String,Chatter>();
		// Check if regex is enabled
		if (getConfig().Settings_EnableRegex) {
			// Initialize RegexManager
			regex = new RegexManager(this);
			// Load Regex Rules
			regex.loadRules();
		}
		// Register Channels
		registerChannels();
		// Load Listeners
		registerListeners();
		// Load Commands
		registerCommands();
	}
	
	public void onDisable() {
		// Clear Regex Rules and Patterns
		regex.getRules().clear();
		regex.getPatterns().clear();
	}
	
	public BungeeChatPlusConfig getConfig() {
		return config;
	}
	
	private void registerListeners() {
		getProxyServer().getPluginManager().registerListener(new ChatListener(this));
		getProxyServer().getPluginManager().registerListener(new PluginMessageListener(this));
	}
	
	private void registerCommands() {
		getProxyServer().getPluginManager().registerCommand(new BCP(this));
		getCommandHandler().addCommand(new Verbose(this));
	}
	
	private void registerChannels() {
		getProxyServer().registerChannel("BungeeChatPlus");
	}
	
	public ProxyServer getProxyServer() {
		return ProxyServer.getInstance();
	}
	
	public PluginManager getPluginManager() {
		return ProxyServer.getInstance().getPluginManager();
	}
	
	public RegexManager getRegexManager() {
		return this.regex;
	}
	
	public CommandHandler getCommandHandler() {
		return this.commandHandler;
	}
	
	public CommandSender getConsole() {
		return this.console;
	}
	
	public Collection<ProxiedPlayer> getPlayers() {
		return getProxyServer().getPlayers();
	}
	
	public Chatter getChatter(String name) {
		return this.chatters.get(name);
	}
	
	public void addChatter(LoginEvent event) {
		if(!this.chatters.containsKey(event.getConnection().getName())) {
			this.chatters.put(event.getConnection().getName(), new Chatter(event.getConnection().getName(), this));
		}
	}
}
/*
 *     NovaGuilds - Bukkit plugin
 *     Copyright (C) 2016 Marcin (CTRL) Wieczorek
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package co.marcin.quicksoup;


import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Message {
	PREFIX,
	NOPERMISSIONS,
	CONSOLESENDER,
	FILEED,
	COOLDOWN,

	TIMEUNIT_SECOND_SINGULAR,
	TIMEUNIT_SECOND_PLURAL,
	TIMEUNIT_MINUTE_SINGULAR,
	TIMEUNIT_MINUTE_PLURAL,
	TIMEUNIT_HOUR_SINGULAR,
	TIMEUNIT_HOUR_PLURAL,
	TIMEUNIT_DAY_SINGULAR,
	TIMEUNIT_DAY_PLURAL,
	TIMEUNIT_WEEK_SINGULAR,
	TIMEUNIT_WEEK_PLURAL,
	TIMEUNIT_MONTH_SINGULAR,
	TIMEUNIT_MONTH_PLURAL,
	TIMEUNIT_YEAR_SINGULAR,
	TIMEUNIT_YEAR_PLURAL;

	private String path = null;
	private Map<VarKey, String> vars = new HashMap<>();

	Message() {

	}

	/**
	 * Gets message's yaml path
	 *
	 * @return the path
	 */
	public String getPath() {
		if(path == null) {
			path = name().replace("_", ".").toLowerCase();
		}

		return "message." + path;
	}

	/**
	 * Sends the Message to a player
	 *
	 * @param sender receiver
	 */
	public void send(CommandSender sender) {
		String string = get();

		if(!string.isEmpty()) {
			sender.sendMessage(StringUtils.fixColors(PREFIX.get() + string));
		}
	}

	public Message setVar(VarKey varKey, String string) {
		vars.put(varKey, string);
		return this;
	}

	/**
	 * Gets the message string
	 *
	 * @return message string
	 */
	public String get() {
		return StringUtils.replaceVarKeyMap(QuickSoup.getInstance().getConfig().getString(getPath()), vars);
	}

	/**
	 * Sends a list of messages
	 *
	 * @param list   the list
	 * @param sender the receiver
	 */
	public static void send(List<Message> list, CommandSender sender) {
		for(Message message : list) {
			message.send(sender);
		}
	}

	/**
	 * Gets a message from path
	 *
	 * @param path path string
	 * @return message enum
	 */
	public static Message fromPath(String path) {
		return Message.valueOf(org.apache.commons.lang.StringUtils.replace(path, ".", "_").toUpperCase());
	}
}

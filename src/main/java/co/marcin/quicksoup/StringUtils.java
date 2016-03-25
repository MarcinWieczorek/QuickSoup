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

import org.bukkit.ChatColor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class StringUtils {
	private StringUtils() {
	}

	public static String fixColors(String msg) {
		if(msg == null) {
			return null;
		}

		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static String replaceVarKeyMap(String msg, Map<VarKey, String> vars) {
		if(vars != null) {
			for(Map.Entry<VarKey, String> entry : vars.entrySet()) {
				msg = org.apache.commons.lang.StringUtils.replace(msg, "{" + entry.getKey().name() + "}", entry.getValue());
			}
		}

		return msg;
	}

	public static String secondsToString(long seconds) {
		return secondsToString(seconds, TimeUnit.SECONDS);
	}

	public static String secondsToString(long seconds, TimeUnit unit) {
		if(seconds <= 0) {
			seconds = 0;
		}

		int minute = 60;
		int hour = 60 * minute;
		int day = hour * 24;
		int week = day * 7;
		int month = day * 31;
		int year = 31536000;

		long years = seconds / year;
		seconds = seconds % year;

		long months = seconds / month;
		seconds = seconds % month;

		long weeks = seconds / week;
		seconds = seconds % week;

		long days = seconds / day;
		seconds = seconds % day;

		long hours = seconds / hour;
		seconds = seconds % hour;

		long minutes = seconds / minute;
		seconds = seconds % minute;

		String stringYears = "", stringMonths = "", stringWeeks = "", stringDays = "", stringHours = "", stringSeconds = "", stringMinutes = "";

		if(years > 0) {
			Message form = years > 1 ? Message.TIMEUNIT_YEAR_PLURAL : Message.TIMEUNIT_YEAR_SINGULAR;
			stringYears = years + " " + form.get() + " ";
		}

		if(months > 0) {
			Message form = months > 1 ? Message.TIMEUNIT_MONTH_PLURAL : Message.TIMEUNIT_MONTH_SINGULAR;
			stringMonths = months + " " + form.get() + " ";
		}

		if(weeks > 0) {
			Message form = weeks > 1 ? Message.TIMEUNIT_WEEK_PLURAL : Message.TIMEUNIT_WEEK_SINGULAR;
			stringWeeks = weeks + " " + form.get() + " ";
		}

		if(days > 0) {
			Message form = days > 1 ? Message.TIMEUNIT_DAY_PLURAL : Message.TIMEUNIT_DAY_SINGULAR;
			stringDays = days + " " + form.get() + " ";
		}

		if(hours > 0) {
			Message form = hours > 1 ? Message.TIMEUNIT_HOUR_PLURAL : Message.TIMEUNIT_HOUR_SINGULAR;
			stringHours = hours + " " + form.get() + " ";
		}

		if(minutes > 0) {
			Message form = minutes > 1 ? Message.TIMEUNIT_MINUTE_PLURAL : Message.TIMEUNIT_MINUTE_SINGULAR;
			stringMinutes = minutes + " " + form.get() + " ";
		}

		if(seconds > 0 || (seconds == 0 && minutes == 0 && hours == 0 && days == 0 && weeks == 0 && months == 0 && years == 0)) {
			Message form = seconds == 1 ? Message.TIMEUNIT_SECOND_SINGULAR : Message.TIMEUNIT_SECOND_PLURAL;
			stringSeconds = seconds + " " + form.get() + " ";
		}

		if(unit == TimeUnit.DAYS && days > 0) {
			stringHours = "";
			stringMinutes = "";
			stringSeconds = "";
		}
		else if(unit == TimeUnit.HOURS && hours > 0) {
			stringMinutes = "";
			stringSeconds = "";
		}
		else if(unit == TimeUnit.MINUTES && minutes > 0) {
			stringSeconds = "";
		}

		String r = stringYears + stringMonths + stringWeeks + stringDays + stringHours + stringMinutes + stringSeconds;
		r = r.substring(0, r.length() - 1);
		return r;
	}

	public static int stringToSeconds(String str) {
		String[] spaceSplit = str.split(" ");
		int seconds = 0;

		for(String word : spaceSplit) {
			if(word.endsWith("s")) {
				word = word.substring(0, word.length() - 1);
				if(NumberUtils.isNumeric(word)) {
					seconds += Integer.parseInt(word);
				}
			}

			if(word.endsWith("m")) {
				word = word.substring(0, word.length() - 1);
				if(NumberUtils.isNumeric(word)) {
					seconds += Integer.parseInt(word) * 60;
				}
			}

			if(word.endsWith("h")) {
				word = word.substring(0, word.length() - 1);
				if(NumberUtils.isNumeric(word)) {
					seconds += Integer.parseInt(word) * 60 * 60;
				}
			}

			if(word.endsWith("d")) {
				word = word.substring(0, word.length() - 1);
				if(NumberUtils.isNumeric(word)) {
					seconds += Integer.parseInt(word) * 60 * 60 * 24;
				}
			}

			if(word.endsWith("w")) {
				word = word.substring(0, word.length() - 1);
				if(NumberUtils.isNumeric(word)) {
					seconds += Integer.parseInt(word) * 60 * 60 * 24 * 7;
				}
			}

			if(word.endsWith("mo")) {
				word = word.substring(0, word.length() - 2);
				if(NumberUtils.isNumeric(word)) {
					seconds += Integer.parseInt(word) * 60 * 60 * 24 * 31;
				}
			}

			if(word.endsWith("y")) {
				word = word.substring(0, word.length() - 1);
				if(NumberUtils.isNumeric(word)) {
					seconds += Integer.parseInt(word) * 60 * 60 * 24 * 365;
				}
			}
		}

		return seconds;
	}
}

package net.insomniacs.nucleus.api.utils;

import com.mojang.serialization.Codec;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd MMM yy");

	public static final Codec<LocalDate> CODEC = Codec.STRING.xmap(
			string -> LocalDate.parse(string + " 00", FORMAT).withYear(0),
			date -> date.format(FORMAT)
	);

	public static boolean isToday(LocalDate date) {
		return date == today();
	}

	public static LocalDate today() {
		return ZonedDateTime.now().withYear(0).toLocalDate();
	}

}

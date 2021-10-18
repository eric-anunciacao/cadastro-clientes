package io.platformbuilders.cliente.cross;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

public class Formatador {

	private static final DateTimeFormatter DATA_PADRAO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Formatador() {
	}

	public static LocalDate toLocalDate(String valor) {
		if (StringUtils.isEmpty(valor)) {
			return null;
		}
		return LocalDate.parse(valor, DATA_PADRAO);
	}

}

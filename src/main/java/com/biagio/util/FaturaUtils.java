package com.biagio.util;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class FaturaUtils {
	public static BigDecimal parseBigDecimal(String value) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
		symbols.setGroupingSeparator('.');
		symbols.setDecimalSeparator(',');

		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setDecimalFormatSymbols(symbols);
		decimalFormat.setParseBigDecimal(true);

		try {
			return (BigDecimal) decimalFormat.parse(value);
		} catch (ParseException e) {
			// Handle the exception appropriately
			e.printStackTrace();
		}

		return null;
	}
}

package com.challenge.ventas.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;

public class AccreditationConverter {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");
	
	private AccreditationConverter() {
		// private default constructor for utility class
	}
	
	public static AccreditationDTO toDTO(SaleAccreditation accreditation) {
		if (accreditation == null) {
			return null;
		}
		
		if (accreditation.getSellingPoint() == null || accreditation.getAmount() == null) {
			throw new IllegalStateException("Falla en la integridad de los datos de esta operacion.");
		}
		
		AccreditationDTO dto = new AccreditationDTO();
		dto.setId(accreditation.getId());
		dto.setSellingPointId(accreditation.getSellingPoint().getId());
		dto.setSellingPointName(accreditation.getSellingPoint().getName());
		dto.setAmount(accreditation.getAmount());
		
		String formattedDate = Optional.ofNullable(accreditation.getAccreditationDate())
				.map(d -> d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(FORMATTER))
				.orElse("");
		dto.setAccreditationDate(formattedDate);
		return dto;
	}

}

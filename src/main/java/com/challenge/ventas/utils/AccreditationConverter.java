package com.challenge.ventas.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.challenge.ventas.persistence.dto.AccreditationDTO;
import com.challenge.ventas.persistence.model.SaleAccreditation;

public class AccreditationConverter {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm a");
	
	private AccreditationConverter() {
		// private default constructor for utility class
	}
	
	public static AccreditationDTO toDTO(SaleAccreditation accreditation) {
		AccreditationDTO dto = null;
		if (accreditation != null && accreditation.getSellingPoint() != null) {
			dto = new AccreditationDTO();
			dto.setId(accreditation.getId());
			dto.setSellingPointId(accreditation.getSellingPoint().getId());
			dto.setSellingPointName(accreditation.getSellingPoint().getName());
			dto.setAmount(accreditation.getAmount());
			
			String formattedDate = Optional.ofNullable(accreditation.getAccreditationDate())
					.map(d -> d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(FORMATTER))
					.orElse("");
			dto.setAccreditationDate(formattedDate);
		}
		return dto;
	}

}

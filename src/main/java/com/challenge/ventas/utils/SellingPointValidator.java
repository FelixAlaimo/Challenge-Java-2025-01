package com.challenge.ventas.utils;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.repository.ISellingPointRepository;

@Component
public class SellingPointValidator {
	
	@Autowired
	private ISellingPointRepository sellingPointRepo;
	
	public void validateSellingPointExistence(Long sellingPointId, String attributeName) {
		validateNullableField(sellingPointId, attributeName);
		if (!sellingPointRepo.existsAndIsActiveById(sellingPointId)) {
			throw new ResourceNotFoundException(MessageFormat.format("No existe un punto de venta activo ''{0}''", sellingPointId));
		}
	}
	
	public void validateNullableField(Long sellingPointId, String attributeName) {
		if (sellingPointId == null) {
			throw new MissingRequiredFieldException(MessageFormat.format("Warning! revisar campo requerido: ''{0}''", attributeName));
		}
	}

}

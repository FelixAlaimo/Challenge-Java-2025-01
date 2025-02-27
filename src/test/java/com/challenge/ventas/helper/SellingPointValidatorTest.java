package com.challenge.ventas.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.ventas.exception.MissingRequiredFieldException;
import com.challenge.ventas.exception.ResourceNotFoundException;
import com.challenge.ventas.persistence.repository.ISellingPointRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class SellingPointValidatorTest {
	
	@Mock
	private ISellingPointRepository sellingPointRepo;
	
	@InjectMocks
	private SellingPointValidator validator;
	
	@Test
	public void testValidateNullableField_shouldThrowException_WhenMissingField() {
		Exception exception = Assertions.assertThrows(MissingRequiredFieldException.class, () -> {
			validator.validateNullableField(null, "FieldName");
		});
		Assertions.assertEquals("Warning! revisar campo requerido: 'FieldName'", exception.getMessage());
	}
	
	@Test
	public void testValidateNullableField_shouldNotThrowException_WhenFieldIsPresent() throws MissingRequiredFieldException {
		validator.validateNullableField(183L, "FieldName");
		Assertions.assertDoesNotThrow(() -> validator.validateNullableField(183L, "FieldName"));
	}
	
	@Test
	public void testValidateSellingPointExistence_shouldThrowException_WhenNotFound() {
		Mockito.when(sellingPointRepo.existsAndIsActiveById(31L)).thenReturn(false);
		Exception exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			validator.validateSellingPointExistence(31L, "FieldName");
		});
		Assertions.assertEquals("No existe un punto de venta activo '31'", exception.getMessage());
	}
	
	@Test
	public void testValidateSellingPointExistence_shouldNotThrowException_WhenFound() {
		Mockito.when(sellingPointRepo.existsAndIsActiveById(31L)).thenReturn(true);
		Assertions.assertDoesNotThrow(() -> validator.validateSellingPointExistence(31L, "FieldName"));
	}

}

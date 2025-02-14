package com.challenge.ventas;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class VentasApplicationTest {
	
	@Test
	public void testMain() {
		try (MockedStatic<SpringApplication> springApplicationMock = Mockito.mockStatic(SpringApplication.class)) {
            ConfigurableApplicationContext mockContext = Mockito.mock(ConfigurableApplicationContext.class);
            springApplicationMock.when(() -> SpringApplication.run(VentasApplication.class, new String[] {}))
              .thenReturn(mockContext);

            VentasApplication.main(new String[] {});

            springApplicationMock.verify(() -> SpringApplication.run(VentasApplication.class, new String[] {}));
        }
	}

}

package com.challenge.ventas.configuration;

import org.junit.jupiter.api.Test;

import com.challenge.ventas.test.utils.BasePojoTests;

class RedisPropertiesTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(RedisProperties.class);
	}

}

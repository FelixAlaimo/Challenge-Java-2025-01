package com.challenge.ventas.configuration;

import org.junit.jupiter.api.Test;

import utils.BasePojoTests;

class RedisPropertiesTest extends BasePojoTests {
	
	@Test
	public void testGettersAndSetters() {
		testGettersAndSettersByReflection(RedisProperties.class);
	}

}

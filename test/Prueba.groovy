import static org.junit.Assert.*;
import utn.tadp.fontana.Persona;

import org.junit.Assert;
import org.junit.Test;
import static Dsl.*;
class Prueba {

	@Test
	public void test1() {
		
		def dsl= new Dsl()

		dsl.beanId "pepo" clase Persona propertyName "nombre" tipo String valor "pepe" propertyName "edad" tipo int valor 100 propertyName "vive" tipo boolean valor true
		dsl.beanId "el tio" clase Persona propertyName "nombre" tipo String  valor "gorge" propertyName "edad" tipo int valor 100
		
		Persona fiestera = (Persona) config.getBean("pepo")
		Persona fiestero = (Persona) config.getBean("el tio")
		Assert.assertEquals("pepe", fiestera.getNombre())
		Assert.assertEquals("gorge", fiestero.getNombre())
		Assert.assertEquals(100, fiestera.getEdad())
		Assert.assertEquals(true, fiestera.getVive())
	}

	@Test
	public void test2() {

		def dsl= new Dsl()
		dsl.definir {
			beanId "pepo"         clase Persona

			propertyName "nombre"    tipo String    valor "pepe"
			propertyName "edad"      tipo int       valor 100
			propertyName "vive"      tipo boolean   valor true
			propertyName "conocido"  tipo Persona   valor "el tio"



			beanId "el tio"        clase Persona

			propertyName "nombre"    tipo String    valor "gorge"
			propertyName "edad"      tipo int       valor 120
			propertyName "vive"      tipo boolean   valor true
		}


		Persona fiestera = (Persona)config.getBean("pepo")
		Persona fiestero = (Persona) config.getBean("el tio")
		Assert.assertEquals("pepe", fiestera.getNombre())
		Assert.assertEquals(100, fiestera.getEdad())
		Assert.assertEquals(true, fiestero.getVive())
		Assert.assertEquals("gorge", fiestero.getNombre())
		Assert.assertEquals("gorge", fiestera.getConocido().getNombre())
	}
}
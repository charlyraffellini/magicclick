import static org.junit.Assert.*;
import utn.tadp.fontana.Persona;

import org.junit.Assert;
import org.junit.Test;
import static Dsl.*;
class Prueba {

	@Test
	public void testDSLFuncional() {

		def dsl= new Dsl()
		dsl.definir {
			
			un Objeto llamado "pepe" de la clase Persona por accessors
			
			con la propiedad "nombre" teniendo como valor "pepe"
			y ademas con la propiedad "edad" teniendo como valor 100
			y ademas con la propiedad "vive" teniendo como valor true
			que conoce mediante "conocido" a "el tio"
			
			
			un Objeto llamado "el tio" de la clase Persona
			y ademas con la propiedad "nombre" teniendo como valor "gorge"
			y ademas con la propiedad "vive" teniendo como valor true
		}
		Persona fiestera = (Persona)config.getBean("pepe")
		Persona fiestero = (Persona) config.getBean("el tio")
		Assert.assertEquals("pepe", fiestera.getNombre())
		Assert.assertEquals(100, fiestera.getEdad())
		Assert.assertEquals(true, fiestero.getVive())
		Assert.assertEquals("gorge", fiestero.getNombre())
		Assert.assertEquals("gorge", fiestera.getConocido().getNombre())
	}
	@Test
	public void testDSLConListas() {

		def dsl= new Dsl()
		dsl.definir {
			un Objeto llamado "pepe" de la clase Persona por accessors
			
			con la propiedad "nombre" teniendo como valor "pepe"
			
			y ademas con las propiedades {["edad","vive"]} teniendo como valores {[100,true]}
			que conoce mediante "conocido" a "el tio"
			
			
			un Objeto llamado "el tio" de la clase Persona
			y ademas con la propiedad "nombre" teniendo como valor "gorge"
			y ademas con la propiedad "vive" teniendo como valor true
		}
		
		/* Nos debería quedar así la configuración y no como un static!
		*  config = dsl.dameConfiguracionPara("produccion")
		*/
		
		Persona fiestera = (Persona)config.getBean("pepe")
		Persona fiestero = (Persona) config.getBean("el tio")
		Assert.assertEquals("pepe", fiestera.getNombre())
		Assert.assertEquals(100, fiestera.getEdad())
		Assert.assertEquals(true, fiestero.getVive())
		Assert.assertEquals("gorge", fiestero.getNombre())
		Assert.assertEquals("gorge", fiestera.getConocido().getNombre())
	}
	@Test
	public void testDSLTipoXML() {

		def dsl= new Dsl()
		dsl.definir {
			
			beanId "pepo"         clase Persona

			propertyName "nombre"    tipo String    valor "pepe"
			propertyName "edad"      tipo int       valor 100
			propertyName "vive"      tipo boolean   valor true
			propertyName "conocido"  tipo Persona   a "el tio"



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
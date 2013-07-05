import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import utn.tadp.fontana.Persona;

import static org.junit.Assert.*;
import utn.tadp.fontana.Persona;

import org.junit.Assert;
import org.junit.Test;
import static Dsl.*;
class Prueba {

	@Test
	public void testDSLMultiplesConfiguraciones() {

		def dsl= new Dsl()
		dsl.definir {
			
			un Objeto llamado "pepe" de la clase Persona por accessors
			
			con la propiedad "nombre" teniendo como valor "pepe"
			y ademas con la propiedad "edad" teniendo como valor 100
			y ademas con la propiedad "vive" teniendo como valor true

		}
		
		def unaConfiguracion = dsl.config
		Persona unaPersona = (Persona)unaConfiguracion.getBean("pepe")
		Assert.assertEquals("pepe", unaPersona.getNombre())
		Assert.assertEquals(100, unaPersona.getEdad())
		
		dsl.definir {
			
			un Objeto llamado "pepe" de la clase Persona por accessors
			
			con la propiedad "nombre" teniendo como valor "pepe"
			y ademas con la propiedad "edad" teniendo como valor 50
			y ademas con la propiedad "vive" teniendo como valor false

		}
		def otraConfiguracion = dsl.config
		Persona otraPersona = (Persona)otraConfiguracion.getBean("pepe")
		Assert.assertEquals("pepe", otraPersona.getNombre())
		Assert.assertEquals(50, otraPersona.getEdad())
	}
	
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
		def unaConfiguracion = dsl.config
		Persona fiestera = (Persona)unaConfiguracion.getBean("pepe")
		Persona fiestero = (Persona) unaConfiguracion.getBean("el tio")
		Assert.assertEquals("pepe", fiestera.getNombre())
		Assert.assertEquals(100, fiestera.getEdad())
		Assert.assertEquals(true, fiestero.getVive())
		Assert.assertEquals("gorge", fiestero.getNombre())
		Assert.assertEquals("gorge", fiestera.getConocido().getNombre())
	}
	
	@Test
	public void testDependenciaRelacionConLista(){
		def dsl= new Dsl()
		dsl.definir {
			
			un Objeto llamado "pepe" de la clase Persona por accessors
			
			con la propiedad "nombre" teniendo como valor "pepe"
			y ademas con la propiedad "edad" teniendo como valor 100
			y ademas con la propiedad "vive" teniendo como valor true
			y ademas con la propiedad "apodos" teniendo como valor {['unApodo','otroApodo']}
			
		}
		def unaConfiguracion = dsl.config
		Persona unaPersona = (Persona)unaConfiguracion.getBean("pepe")
		Assert.assertEquals("pepe", unaPersona.getNombre());
		ArrayList<String> losApodos = new ArrayList<String>();
		losApodos = unaPersona.getApodos();
		Assert.assertEquals(losApodos.get(0),("unApodo"));
		Assert.assertEquals(losApodos.get(1),("otroApodo"));
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
		def unaConfiguracion = dsl.config
		Persona fiestera = (Persona)unaConfiguracion.getBean("pepe")
		Persona fiestero = (Persona) unaConfiguracion.getBean("el tio")
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

		def unaConfiguracion = dsl.config
		Persona fiestera = (Persona)unaConfiguracion.getBean("pepo")
		Persona fiestero = (Persona) unaConfiguracion.getBean("el tio")
		Assert.assertEquals("pepe", fiestera.getNombre())
		Assert.assertEquals(100, fiestera.getEdad())
		Assert.assertEquals(true, fiestero.getVive())
		Assert.assertEquals("gorge", fiestero.getNombre())
		Assert.assertEquals("gorge", fiestera.getConocido().getNombre())
	}
}
import java.beans.java_awt_BorderLayout_PersistenceDelegate;
import java.lang.reflect.Type

import org.apache.ivy.core.module.descriptor.ExtendsDescriptor;

import utn.tadp.fontana.Bean;
import utn.tadp.fontana.Compleja;
import utn.tadp.fontana.Primitiva;
import utn.tadp.fontana.configuracion.Configuracion;
import utn.tadp.fontana.estrategia.InicializacionPorConstructor;
import utn.tadp.fontana.estrategia.InicializacionPorSetters;
import utn.tadp.fontana.Persona;

class Dsl {

	def nombre
	def obj
	def prop
	def tipo

//		def propertyMissing(String name){
//			if (name=="valor"){
//				invokeMethod(name, valor)
//			}
//			else{
//				throw new MissingPropertyException("No se encuentra la propiedad " + name)
//			}
//		}


	static Configuracion config = new Configuracion();
	def static Objeto = "Objeto"
	def static la = "la"
	def static conoce = "conoce"
	def static como = "como"
	def static ademas = "esto es fruta"
	def static constructor = "constructor"
	def static accessors = "accessors"
	
	public llamado (String nombreDelObjeto){
		this.nombre= nombreDelObjeto
		this
	}

	public mediante (String propertyName){
		this.propertyName(propertyName)
	}
	
	public propiedad (String propertyName){
		this.prop= propertyName
		this
	}
	
	public con (String n){
		this
	}

	public teniendo (String n){
		this
	}
	
	public de (String n){
		this
	}
	public que (String n){
		this
	}
	public un (String n){
		this	
	}
	
	public y (String n){
		this
	}
	
	public beanId(String n) {
		this.nombre= n
		return this
	}

	public definir(Closure bloq) {
		this.with bloq
	}

	public clase(Class t){
		obj= new Compleja <Persona>(t)
		config.addBean(nombre, obj)
		return this
	}

	public propertyName(String n){
		this.prop= n
		return this
	}

	public tipo ( Class  c){
		this.tipo= c
		return this
	}
	
	public valor(String unString){
		obj.addDependencia(prop, new Primitiva(String, unString))
	}
	
	public valor(int unEntero){
		obj.addDependencia(prop, new Primitiva(int, unEntero))
	}

	public valor(boolean unBoolean){
		obj.addDependencia(prop, new Primitiva(boolean, unBoolean))
	}
	
//	public valor(n){
//
//			obj.addDependencia(prop, new Bean( n,config))
//
//		return this
//	}
	public a (String unBeanName){
		obj.addDependencia(prop, new Bean( unBeanName,config))
		this
	}
	
	public por(String unString){
		if(unString == "constructor"){
			InicializacionPorConstructor inicializacionDefault = new InicializacionPorConstructor();
			inicializacionDefault.setMiDependenciaCompleja(obj);
		}
		if(unString == "accessors"){
			InicializacionPorSetters inicializacionDefault = new InicializacionPorSetters();
			inicializacionDefault.setMiDependenciaCompleja(obj);
		}else
		{
		throw new RuntimeException()
		}
	}
}

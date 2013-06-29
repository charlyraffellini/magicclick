import java.beans.java_awt_BorderLayout_PersistenceDelegate;
import java.lang.reflect.Type

import org.apache.ivy.core.module.descriptor.ExtendsDescriptor;

import utn.tadp.fontana.Bean;
import utn.tadp.fontana.Compleja;
import utn.tadp.fontana.Primitiva;
import utn.tadp.fontana.configuracion.Configuracion;
import utn.tadp.fontana.estrategia.Inicializacion;
import utn.tadp.fontana.estrategia.InicializacionPorConstructor;
import utn.tadp.fontana.estrategia.InicializacionPorSetters;
import utn.tadp.fontana.Persona;

class Dsl {

	def nombre
	def obj
	def prop
	def listaDePropiedades
	def tipo

	static Configuracion config = new Configuracion();
	def static Objeto = "Objeto"
	def static la = "la"
	def static las = "las"
	def static conoce = "conoce"
	def static como = "como"
	def static ademas = "esto es fruta"
	def static constructor = { new InicializacionPorConstructor() }
	def static accessors = { new InicializacionPorSetters() }

	def methodMissing (String stringName, args){
		this
	}

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

	public propiedades (unaLista){
		this.listaDePropiedades = unaLista()
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
		obj= new Compleja (t)
		config.addBean(nombre, obj)
		return this
	}

	public propertyName(String n){
		this.prop = n
		return this
	}

	public tipo ( Class  c){
		this.tipo= c
		return this
	}

	public valores (unaListaDeValores){
		def unMapa = [
			this.listaDePropiedades,
			unaListaDeValores()
		].transpose().inject([:]) { a, b -> a[b[0]] = b[1]; a }
		unMapa.each {clave, valor -> this.prop = clave; this.valor(valor)}
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

	public a (String unBeanName){
		obj.addDependencia(prop, new Bean( unBeanName,config))
		this
	}

	public por(Closure inicializacionFactory) {
		Inicializacion inicializacion = inicializacionFactory()
		inicializacion.setMiDependenciaCompleja(obj)
	}
}

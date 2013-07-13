import java.beans.java_awt_BorderLayout_PersistenceDelegate;
import java.lang.reflect.Type
import java.util.ArrayList;

import org.apache.ivy.core.module.descriptor.ExtendsDescriptor;

import utn.tadp.fontana.Bean;
import utn.tadp.fontana.Compleja;
import utn.tadp.fontana.Lista;
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
	def seleccionado = "default"
	def mapaDeConfiguraciones = [:]
	
	def config = {  
										
					def unaConfiguracion = delegate.mapaDeConfiguraciones.get(delegate.seleccionado) 
					if (unaConfiguracion)
					{
						return unaConfiguracion
					}
					else
					{
						def current = new Configuracion()
						delegate.mapaDeConfiguraciones.put(seleccionado, current)
						return current
						
					}
	};
	def static Objeto = "Objeto"
	def static la = "la"
	def static las = "las"
	def static conoce = "conoce"
	def static como = "como"
	def static crear = "crear"
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
		//this.config  = new Configuracion()
		this.with bloq
	}

	public clase(Class t){
		obj= new Compleja (t)
		config().addBean(nombre, obj)
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

	public valor(Closure unaLista){
		def coleccionDeValores = new Lista(ArrayList.class);
		unaLista().each{
			coleccionDeValores.addDependenciaALaLista(this.devolverPrimitiva(it))
		}
		obj.addDependencia(prop,coleccionDeValores)
	}
	
	private devolverPrimitiva(String unString){
		new Primitiva(String, unString)
	}

	private devolverPrimitiva(int unEntero){
		new Primitiva(int, unEntero)
	}
	
	private devolverPrimitiva(boolean unBoolean){
		new Primitiva(boolean, unBoolean)
	}
	
	public valor (unValor){
		obj.addDependencia(prop,this.devolverPrimitiva(unValor))
	}
	
	public a (String unBeanName){
		obj.addDependencia(prop, new Bean( unBeanName,config()))
		this
	}

	public por(Closure inicializacionFactory) {
		Inicializacion inicializacion = inicializacionFactory()
		inicializacion.setMiDependenciaCompleja(obj)
	}
	public configuracion(String unNombreDeConfiguracion){
		this.seleccionado = unNombreDeConfiguracion
		this
	}
	public dameConfig(String unNombreDeConfiguracion){
		this.configuracion(unNombreDeConfiguracion)
		config()
	}
}

import java.beans.java_awt_BorderLayout_PersistenceDelegate;
import java.lang.reflect.Type

import utn.tadp.fontana.Bean;
import utn.tadp.fontana.Compleja;
import utn.tadp.fontana.Primitiva;
import utn.tadp.fontana.configuracion.Configuracion;
import utn.tadp.fontana.Persona;

class Dsl {

	def nombre
	def obj
	def prop
	def tipo

	//	def propertyMissing(String name){
	//		if (name=="fin"){
	//			config.addBean(nombre, obj)
	//			println "paso"
	//		}
	//		else {
	//			throw MissingPropertyException(name)
	//		}
	//	}


	static Configuracion config = new Configuracion();

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
	public valor(n){

		if (tipo !=String && tipo !=int && tipo !=boolean ){

			obj.addDependencia(prop, new Bean( n,config))
		}else{

			obj.addDependencia(prop, new Primitiva(tipo, n))
		}

		return this
	}
}


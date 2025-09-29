class Nota {
	static #lista = [];

	constructor(modulo, nombre, evaluacion, nota)
	{
		this.modulo = this.setModulo(modulo);
		if (this.modulo == null)
			return ;
		this.nombre = this.setNombre(nombre);
		if (this.nombre == null)
			return ;
		this.evaluacion = this.setEvaluacion(evaluacion);
		if (this.evaluacion == null)
			return ;
		this.nota = this.setNota(nota);
		if (this.nota == 0)
			return ;
	}

	set setModulo(modulo)
	{
		return (modulo);
	}

	set setNombre(nombre)
	{
		if (nombre.length > 1 && nombre.contains())
			return (nombre);
		return (null);
	}

	set setEvaluacion(evaluacion)
	{
		return (evaluacion);
	}

	set setNota(nota)
	{
		if (nota >= 0 && nota <= 10)
			return (nota);
		return (0);
	}
}

export default Nota;

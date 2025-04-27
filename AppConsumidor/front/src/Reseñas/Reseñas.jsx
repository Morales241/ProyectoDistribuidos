import React, { useState } from 'react';
import './Reseñas.css';

function Reseñas({ onVolver }) {
  const [supermercadoSeleccionado, setSupermercadoSeleccionado] = useState('');
  const [productoSeleccionado, setProductoSeleccionado] = useState('');
  const [busquedaSuper, setBusquedaSuper] = useState('');
  const [busquedaProducto, setBusquedaProducto] = useState('');
  const [resultadosSuper, setResultadosSuper] = useState([]);
  const [resultadosProducto, setResultadosProducto] = useState([]);
  const [contenido, setContenido] = useState('');
  const [calificacion, setCalificacion] = useState(1);

  const supermercadosSimulados = ['Soriana', 'Walmart', 'Chedraui', 'Costco', 'Superama'];
  
  const productosSimulados = [
    { nombre: 'Leche Lala 1L', supermercado: 'Soriana' },
    { nombre: 'Pan Bimbo 680g', supermercado: 'Walmart' },
    { nombre: 'Refresco Coca-Cola 2L', supermercado: 'Chedraui' },
    { nombre: 'Arroz La Merced 1kg', supermercado: 'Soriana' },
    { nombre: 'Huevos San Juan 12pzas', supermercado: 'Costco' },
  ];

  // llamada a la api para obtener los comercios
  const manejarBusquedaSupermercado = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = supermercadosSimulados.filter(s =>
        s.toLowerCase().includes(busquedaSuper.toLowerCase())
      );
      setResultadosSuper(coincidencias);
    }
  };

  // llamada a la api para obtener los productos
  const manejarBusquedaProducto = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = productosSimulados.filter(p =>
        p.supermercado === supermercadoSeleccionado &&
        p.nombre.toLowerCase().includes(busquedaProducto.toLowerCase())
      );
      setResultadosProducto(coincidencias);
    }
  };

  // post a la api con la reseña
  const confirmarReseña = () => {
    const fecha = new Date().toLocaleDateString(); 
    console.log({
      supermercado: supermercadoSeleccionado,
      producto: productoSeleccionado,
      fecha: fecha,
      contenido: contenido,
      calificacion: calificacion,
    });
    alert('¡Reseña enviada!');
    limpiarTodo();
  };

  const limpiarTodo = () => {
    setSupermercadoSeleccionado('');
    setProductoSeleccionado('');
    setBusquedaSuper('');
    setBusquedaProducto('');
    setResultadosSuper([]);
    setResultadosProducto([]);
    setContenido('');
    setCalificacion(1);
  };

  return (
    <div className="contenedor">
      <h1 className="titulo">Escribir Reseña</h1>

      {/* busqueda de comercio */}
      {!supermercadoSeleccionado && (
        <>
          <input
            type="text"
            placeholder="Buscar supermercado..."
            value={busquedaSuper}
            onChange={(e) => setBusquedaSuper(e.target.value)}
            onKeyDown={manejarBusquedaSupermercado}
            className="inputStyle"
          />
          <div className="cuadricula">
            {resultadosSuper.map((supermercado, index) => (
              <div
                key={index}
                className="cardStyle"
                onClick={() => {
                  setSupermercadoSeleccionado(supermercado);
                  setBusquedaSuper('');
                  setResultadosSuper([]);
                }}
              >
                {supermercado}
              </div>
            ))}
          </div>
        </>
      )}

      {/* comercio seleccionado */}
      {supermercadoSeleccionado && (
        <div className="seleccionActual">
          <strong>Supermercado:</strong> {supermercadoSeleccionado}
        </div>
      )}

      {/* busqueda de producto */}
      {!productoSeleccionado && supermercadoSeleccionado && (
        <>
          <input
            type="text"
            placeholder="Buscar producto..."
            value={busquedaProducto}
            onChange={(e) => setBusquedaProducto(e.target.value)}
            onKeyDown={manejarBusquedaProducto}
            className="inputStyle"
          />
          <div className="cuadricula">
            {resultadosProducto.map((producto, index) => (
              <div
                key={index}
                className="cardStyle"
                onClick={() => {
                  setProductoSeleccionado(producto.nombre);
                  setBusquedaProducto('');
                  setResultadosProducto([]);
                }}
              >
                {producto.nombre}
              </div>
            ))}
          </div>
        </>
      )}

      {/* producto seleccionado */}
      {productoSeleccionado && (
        <div className="seleccionActual">
          <strong>Producto:</strong> {productoSeleccionado}
        </div>
      )}

      {/* reseña */}
      {productoSeleccionado && (
        <textarea
          placeholder="Escribe tu reseña..."
          value={contenido}
          onChange={(e) => setContenido(e.target.value)}
          className="inputStyle"
          rows="5"
        />
      )}

      {/* estas estrellitas claro que me las robe y claro que quedaron de locos */}
      {productoSeleccionado && (
        <div className="calificacion">
          <label>Calificación:</label>
          {[1, 2, 3, 4, 5].map((estrellas) => (
            <span
              key={estrellas}
              className={`estrella ${calificacion >= estrellas ? 'seleccionado' : ''}`}
              onClick={() => setCalificacion(estrellas)}
            >
              &#9733;
            </span>
          ))}
        </div>
      )}

      {/* acciones */}
      {productoSeleccionado && (
        <div className="botonesAccion">
          <button className="buttonStyle" onClick={confirmarReseña}>Confirmar Reseña</button>
          <button className="buttonStyle" onClick={limpiarTodo}>Cancelar</button>
        </div>
      )}

      <button className="buttonStyle" onClick={onVolver}>Volver</button>
    </div>
  );
}

export default Reseñas;

import React, { useState, useEffect } from 'react';
import './Reseñas.css';

function Reseñas({ onVolver }) {
  const [productoSeleccionado, setProductoSeleccionado] = useState('');
  const [contenido, setContenido] = useState('');
  const [calificacion, setCalificacion] = useState(1);
  const [productosComprados, setProductosComprados] = useState([]);

  // Simulación de productos comprados anteriormente
  const productosSimulados = [
    { nombre: 'Leche Lala 1L', supermercado: 'Soriana' },
    { nombre: 'Pan Bimbo 680g', supermercado: 'Walmart' },
    { nombre: 'Refresco Coca-Cola 2L', supermercado: 'Chedraui' },
    { nombre: 'Arroz La Merced 1kg', supermercado: 'Soriana' },
    { nombre: 'Huevos San Juan 12pzas', supermercado: 'Costco' },
  ];

  useEffect(() => {
    // Simulamos carga de historial de compras
    setProductosComprados(productosSimulados);
  }, []);

  const confirmarReseña = () => {
    const fecha = new Date().toLocaleDateString(); 
    console.log({
      producto: productoSeleccionado.nombre,
      supermercado: productoSeleccionado.supermercado,
      fecha: fecha,
      contenido: contenido,
      calificacion: calificacion,
    });
    alert('¡Reseña enviada!');
    limpiarTodo();
  };

  const limpiarTodo = () => {
    setProductoSeleccionado('');
    setContenido('');
    setCalificacion(1);
  };

  return (
    <div className="contenedor">
      <h1 className="titulo">Escribir Reseña</h1>

      {/* lista de productos comprados */}
      {!productoSeleccionado && (
        <>
          <h3>De tus productos comprados anteriormente:</h3>
          <div className="cuadricula">
            {productosComprados.map((producto, index) => (
              <div
                key={index}
                className="cardStyle"
                onClick={() => setProductoSeleccionado(producto)}
              >
                <p><strong>{producto.nombre}</strong></p>
                <p className="subtexto">Comprado en {producto.supermercado}</p>
              </div>
            ))}
          </div>
        </>
      )}

      {/* producto seleccionado */}
      {productoSeleccionado && (
        <div className="seleccionActual">
          <strong>Producto:</strong> {productoSeleccionado.nombre}<br />
          <strong>Supermercado:</strong> {productoSeleccionado.supermercado}
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

      {/* calificación */}
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

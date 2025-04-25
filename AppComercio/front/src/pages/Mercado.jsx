import React, { useState } from 'react';
import './Mercado.css';

function Mercado({ onVolver }) {
  const [pantalla, setPantalla] = useState(null);
  const [nombreProducto, setNombreProducto] = useState('');
  const [precio, setPrecio] = useState('');
  const [busqueda, setBusqueda] = useState('');
  const [resultados, setResultados] = useState([]);

  const productosSimulados = [
    { nombre: 'Manzanas', precio: 25 },
    { nombre: 'Leche', precio: 18 },
    { nombre: 'Pan', precio: 12 },
    { nombre: 'Huevos', precio: 30 }
  ];

  // aqui va lo de la busqueda
  const manejarBusqueda = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = productosSimulados.filter(p =>
        p.nombre.toLowerCase().includes(busqueda.toLowerCase())
      );
      setResultados(coincidencias);
    }
  };

  const mostrarFormulario = (producto = null) => {
    if (producto) {
      setNombreProducto(producto.nombre);
      setPrecio(producto.precio);
    } else {
      setNombreProducto('');
      setPrecio('');
    }
    setPantalla('formulario');
  };

  const renderPrincipal = () => (
    <div className="contenedor">
      <h1 className="titulo">Mercado</h1>
      <button className="buttonStyle" onClick={() => mostrarFormulario()}>Publicar Producto</button>
      <button className="buttonStyle" onClick={() => setPantalla('asignarPrecio')}>Asignar Precio</button>
      <button className="buttonStyle" onClick={() => alert('el viernes queda krnal')}>Solicitar Reporte</button>
      <button className="buttonStyle volverBtn" onClick={onVolver}>Volver al menú</button>
    </div>
  );

  const renderFormulario = () => (
    <div className="contenedor">
      <h2 className="titulo">{nombreProducto ? 'Modificar Producto' : 'Publicar Producto'}</h2>
      {!nombreProducto && (
        <input
          type="text"
          placeholder="Nombre del producto"
          value={nombreProducto}
          onChange={(e) => setNombreProducto(e.target.value)}
          className="inputStyle"
        />
      )}
      <input
        type="number"
        placeholder="Precio"
        value={precio}
        onChange={(e) => setPrecio(e.target.value)}
        className="inputStyle"
      />
      <button className="buttonStyle" onClick={() => setPantalla(null)}>Confirmar</button>
      <button className="buttonStyle" onClick={() => setPantalla(null)}>Cancelar</button>
    </div>
  );

  const renderAsignarPrecio = () => (
    <div className="contenedor">
      <h2 className="titulo">Asignar Precio</h2>
      <input
        type="text"
        placeholder="Buscar producto..."
        value={busqueda}
        onChange={(e) => setBusqueda(e.target.value)}
        onKeyDown={manejarBusqueda}
        className="inputStyle"
      />
      <div className="cuadricula">
        {resultados.map((producto, index) => (
          <div key={index} className="cardStyle">
            <strong>{producto.nombre}</strong> - <span>${producto.precio}</span>
            <button className="buttonStyle" onClick={() => mostrarFormulario(producto)}>Modificar</button>
          </div>
        ))}
      </div>
      <button className="buttonStyle volverBtn" onClick={() => setPantalla(null)}>Volver</button>
    </div>
  );

  if (pantalla === 'formulario') return renderFormulario();
  if (pantalla === 'asignarPrecio') return renderAsignarPrecio();

  return renderPrincipal();
}

export default Mercado;

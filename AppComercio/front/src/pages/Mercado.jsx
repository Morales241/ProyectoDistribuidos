import React, { useState, useEffect } from 'react';
import './Mercado.css';

function Mercado({ onVolver }) {
  const [pantalla, setPantalla] = useState(null);
  const [nombreProducto, setNombreProducto] = useState('');
  const [precio, setPrecio] = useState('');
  const [busqueda, setBusqueda] = useState('');
  const [resultados, setResultados] = useState([]);
  const [reseñas, setReseñas] = useState([]);

  const [productos, setProductos] = useState([
    { nombre: 'Manzanas', precio: 25 },
    { nombre: 'Leche', precio: 18 },
    { nombre: 'Pan', precio: 12 },
    { nombre: 'Huevos', precio: 30 }
  ]);

  const reseñasSimuladas = [
    {
      usuario: 'Ana López',
      fecha: '2025-04-20',
      contenido: 'Producto fresco y de buena calidad.',
      calificacion: 9,
      producto: 'Manzanas'
    },
    {
      usuario: 'Carlos Ramírez',
      fecha: '2025-04-18',
      contenido: 'El precio es justo, pero la leche llegó tibia.',
      calificacion: 8,
      producto: 'Leche'
    },
    {
      usuario: 'Lucía Fernández',
      fecha: '2025-04-15',
      contenido: 'Muy buen pan, suave y fresco.',
      calificacion: 10,
      producto: 'Pan'
    }
  ];


  // llamada a la API para obtener productos
  const manejarBusqueda = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = productos.filter(p =>
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

  const confirmarProducto = () => {
    if (nombreProducto && precio) {
      setProductos(prev => {
        const existe = prev.find(p => p.nombre.toLowerCase() === nombreProducto.toLowerCase());
        if (existe) {
          // Actualizar precio
          return prev.map(p =>
            p.nombre.toLowerCase() === nombreProducto.toLowerCase()
              ? { ...p, precio: Number(precio) }
              : p
          );
        } else {
          // Agregar producto
          return [...prev, { nombre: nombreProducto, precio: Number(precio) }];
        }
      });
      setPantalla(null);
    }
  };

  const renderPrincipal = () => (
    <div className="contenedor">
      <h1 className="titulo">Mercado</h1>
      <button className="buttonStyle" onClick={() => mostrarFormulario()}>Publicar Producto</button>
      <button className="buttonStyle" onClick={() => setPantalla('asignarPrecio')}>Asignar Precio</button>
      <button className="buttonStyle" onClick={() => setPantalla('reseñas')}>Ver Reseñas</button>
    </div>
  );

  const renderFormulario = () => (
    <div className="contenedor">
      <h2 className="titulo">{nombreProducto ? 'Modificar Producto' : 'Publicar Producto'}</h2>
      <input
        type="text"
        placeholder="Nombre del producto"
        value={nombreProducto}
        onChange={(e) => setNombreProducto(e.target.value)}
        className="inputStyle"
      />
      <input
        type="number"
        placeholder="Precio"
        value={precio}
        onChange={(e) => setPrecio(e.target.value)}
        className="inputStyle"
      />
      <button className="buttonStyle" onClick={confirmarProducto}>Confirmar</button>
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

  // aqui lo que hice fue tomar directamente el array de reseñas simuladas y lo guardé en el estado reseñas
  // para que se muestre en la pantalla de reseñas, pero aquí iría una llamada a la API para obtener las reseñas+
  // solo que no exactamente de esta forma, es mas bien como hice con los productos creo
  useEffect(() => {
    if (pantalla === 'reseñas') {
      setReseñas(reseñasSimuladas);
    }
  }, [pantalla]);

  const renderReseñas = () => (
    <div className="contenedor">
      <h2 className="titulo">Reseñas de los consumidores</h2>
      <div className="cuadricula">
        {reseñas.map((r, i) => (
          <div key={i} className="cardStyle">
            <h3>{r.usuario}</h3>
            <p><strong>Producto:</strong> {r.producto}</p>
            <p><strong>Fecha:</strong> {r.fecha}</p>
            <p><strong>Calificación:</strong> {r.calificacion}</p>
            <p><em>{r.contenido}</em></p>
          </div>
        ))}
      </div>
      <button className="buttonStyle volverBtn" onClick={() => setPantalla(null)}>Volver</button>
    </div>
  );

  if (pantalla === 'formulario') return renderFormulario();
  if (pantalla === 'asignarPrecio') return renderAsignarPrecio();
  if (pantalla === 'reseñas') return renderReseñas();

  return renderPrincipal();
}

export default Mercado;

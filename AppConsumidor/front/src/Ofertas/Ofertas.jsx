import React, { useState, useEffect } from 'react';
import './Ofertas.css';

function Ofertas({ onVolver }) {
  const [busqueda, setBusqueda] = useState('');
  const [resultados, setResultados] = useState([]);

  const ofertasSimuladas = [
    { nombre: 'Leche', marca: 'Lala', presentacion: '1L', precioOriginal: 25, precioOferta: 20, tienda: 'Soriana' },
    { nombre: 'Pan', marca: 'Bimbo', presentacion: '680g', precioOriginal: 35, precioOferta: 28, tienda: 'Walmart' },
    { nombre: 'Refresco', marca: 'Coca-Cola', presentacion: '2L', precioOriginal: 42, precioOferta: 35, tienda: 'Chedraui' },
    { nombre: 'Arroz', marca: 'La Merced', presentacion: '1kg', precioOriginal: 30, precioOferta: 25, tienda: 'Soriana' },
  ];

  useEffect(() => {
    setResultados(ofertasSimuladas);
  }, []);

  const manejarBusqueda = (e) => {
    const texto = e.target.value;
    setBusqueda(texto);
    const coincidencias = ofertasSimuladas.filter(p =>
      p.nombre.toLowerCase().includes(texto.toLowerCase())
    );
    setResultados(coincidencias);
  };

  return (
    <div className="contenedor">
      <h1 className="titulo">Ofertas Especiales</h1>

      <input
        type="text"
        placeholder="Buscar producto en oferta..."
        value={busqueda}
        onChange={manejarBusqueda}
        className="inputStyle"
      />

      <div className="cuadricula">
        {resultados.map((producto, index) => (
          <div key={index} className="cardStyle">
            <h3>{producto.nombre}</h3>
            <p><strong>Marca:</strong> {producto.marca}</p>
            <p><strong>Presentación:</strong> {producto.presentacion}</p>
            <p><strong>Precio:</strong> 
              <span className="tachado"> ${producto.precioOriginal} </span> 
              <span className="oferta"> ${producto.precioOferta} MXN</span>
            </p>
            <p><strong>Tienda:</strong> {producto.tienda}</p>
            <button className="buttonStyle"> Agregar a Wishlist</button>
            <button className="buttonStyle"> Agregar a carrito </button>
          </div>
        ))}
      </div>

      <button className="buttonStyle volverBtn" onClick={onVolver}>Volver</button>
    </div>
  );
}

export default Ofertas;

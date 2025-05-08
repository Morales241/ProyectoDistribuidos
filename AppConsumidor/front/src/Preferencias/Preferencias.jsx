import React, { useState, useEffect } from 'react';
import axios from "axios";
import './Preferencias.css';

function Preferencias({ onVolver }) {
  const [productoFavorito, setProductoFavorito] = useState('');
  const [supermercadoFavorito, setSupermercadoFavorito] = useState('');
  const [busquedaProducto, setBusquedaProducto] = useState('');
  const [busquedaSupermercado, setBusquedaSupermercado] = useState('');
  const [resultadosProducto, setResultadosProducto] = useState([]);
  const [resultadosSupermercado, setResultadosSupermercado] = useState([]);
  const [productosSeleccionados, setProductosSeleccionados] = useState([]);
  const [productos, setProductos] = useState([]);
  const [mercados, setMercados] = useState([]);

  useEffect(() => {
    obtenerProductos();
  }, []);
  
  const productosSimulados = [
    { nombre: 'Leche Lala 1L' },
    { nombre: 'Pan Bimbo 680g' },
    { nombre: 'Refresco Coca-Cola 2L' },
    { nombre: 'Arroz La Merced 1kg' },
    { nombre: 'Huevos San Juan 12pzas' },
  ];

  const supermercadosSimulados = ['Soriana', 'Walmart', 'Chedraui', 'Costco', 'Superama'];

  const obtenerProductos = async () => {
    try {
      const response = await axios.get(`http://localhost:8082/consumidoresComercio/buscarProductos`);
      setProductos(response.data);
      console.log("Productos:", response.data);
    } catch (error) {
      console.error("Error al obtener productos:", error);
    }
  };
  
  // llamada a la api para obtener los productos
  const manejarBusquedaProducto = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = productos.filter(p =>
        p.nombre.toLowerCase().includes(busquedaProducto.toLowerCase())
      );
      setResultadosProducto(coincidencias);
    }
  };

  // llamada a la api para obtener los comercios
  const manejarBusquedaSupermercado = (e) => {
    if (e.key === 'Enter') {
      const response = axios.get(`http://localhost:8082/consumidoresComercio/buscarComercioPorNombre?nombre=${busquedaSupermercado}`)
      .then(function (response) {
        const coincidencias = [response.data.nombre];
        console.log("Mercados:", coincidencias);
        setResultadosSupermercado(coincidencias);
      })
      .catch(function (error) {
        console.log(error);
      });
    }
  };

  // aqui se actualizaria la lista de productos del usuario
  /* Lo escribo porque luego se me olvida
     la lista de productos que tiene ya guardada y se cargara cada que entre
     pero esto creo que ni esta en la bd
  */
  const agregarProductoALista = (producto) => {
    setProductosSeleccionados((prev) => [...prev, producto]);
    setBusquedaProducto('');
    setResultadosProducto([]);
  };

  return (
    <div className="contenedor">
      <h2 className="titulo">Guardar Preferencias</h2>
      
      {/* barra de busqueda de producto favorito */}
      {!productoFavorito && (
        <>
          <input
            type="text"
            placeholder="Buscar producto favorito..."
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
                  setProductoFavorito(producto.nombre);
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

      {/* producto favorito seleccionado */}
      {productoFavorito && (
        <div className="cardStyle">
          <strong>Producto favorito:</strong> {productoFavorito}
        </div>
      )}

      {/* busqueda de comercio favorito */}
      {!supermercadoFavorito && (
        <>
          <input
            type="text"
            placeholder="Buscar supermercado favorito..."
            value={busquedaSupermercado}
            onChange={(e) => setBusquedaSupermercado(e.target.value)}
            onKeyDown={manejarBusquedaSupermercado}
            className="inputStyle"
          />
          <div className="cuadricula">
            {resultadosSupermercado.map((supermercado, index) => (
              <div
                key={index}
                className="cardStyle"
                onClick={() => {
                  setSupermercadoFavorito(supermercado);
                  setBusquedaSupermercado('');
                  setResultadosSupermercado([]);
                }}
              >
                {supermercado}
              </div>
            ))}
          </div>
        </>
      )}

      {/* comercio favorito seleccionado */}
      {supermercadoFavorito && (
        <div className="cardStyle">
          <strong>Supermercado favorito:</strong> {supermercadoFavorito}
        </div>
      )}

      {/* Aqui habria que jalar los que habia anteriormente */}
      <h3>Lista de supermercado:</h3>
      <div className="cardSeleccionada">
        {productosSeleccionados.length > 0 ? (
          productosSeleccionados.map((producto, index) => (
            <p key={index}>{producto.nombre}</p>
          ))
        ) : (
          <p>No has agregado productos.</p>
        )}
      </div>

      {/* guardar preferencias */}
      <button className="buttonStyle">Guardar Preferencias</button>
      <button className="buttonStyle" onClick={onVolver}>Volver</button>
    </div>
  );
}

export default Preferencias;

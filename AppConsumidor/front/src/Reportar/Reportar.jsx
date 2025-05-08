import React, { useState, useEffect } from 'react';
import axios from "axios";
import './Reportar.css';

function Reportar({ onVolver }) {
  const [pantalla, setPantalla] = useState('buscarSuper');
  const [supermercadoSeleccionado, setSupermercadoSeleccionado] = useState('');
  const [productoSeleccionado, setProductoSeleccionado] = useState('');
  const [busquedaSuper, setBusquedaSuper] = useState('');
  const [busquedaProducto, setBusquedaProducto] = useState('');
  const [resultadosSuper, setResultadosSuper] = useState([]);
  const [resultadosProducto, setResultadosProducto] = useState([]);
  const [productos, setProductos] = useState([]);

  const supermercadosSimulados = ['Soriana', 'Walmart', 'Chedraui', 'Costco', 'Superama'];

  const productosSimulados = [
    { nombre: 'Leche Lala 1L', supermercado: 'Soriana' },
    { nombre: 'Pan Bimbo 680g', supermercado: 'Walmart' },
    { nombre: 'Refresco Coca-Cola 2L', supermercado: 'Chedraui' },
    { nombre: 'Arroz La Merced 1kg', supermercado: 'Soriana' },
    { nombre: 'Huevos San Juan 12pzas', supermercado: 'Costco' },
  ];
  
  useEffect(() => {
    obtenerProductos();
  }, []);

  const obtenerProductos = async () => {
    try {
      const response = await axios.get(`http://localhost:8082/consumidoresComercio/buscarProductos`);
      setProductos(response.data);
      console.log("Productos:", response.data);
    } catch (error) {
      console.error("Error al obtener productos:", error);
    }
  };
  
  // llamada a la api para obtener los comercios
  const manejarBusquedaSupermercado = (e) => {
    if (e.key === 'Enter') {
      const response = axios.get(`http://localhost:8082/consumidoresComercio/buscarComercioPorNombre?nombre=${busquedaSuper}`)
      .then(function (response) {
        const coincidencias = [response.data.nombre];
        console.log("Mercados:", coincidencias);
        setResultadosSuper(coincidencias);
      })
      .catch(function (error) {
        console.log(error);
      });
    }
  };

  // llamada a la api para obtener los productos
  const manejarBusquedaProducto = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = productos.filter(p =>
        p.supermercado === supermercadoSeleccionado &&
        p.nombre.toLowerCase().includes(busquedaProducto.toLowerCase())
      );
      console.log(coincidencias);
      setResultadosProducto(coincidencias);
    }
  };

  // post a la api con el reporte
  const confirmarReporte = () => {
    console.log({
      supermercado: supermercadoSeleccionado,
      producto: productoSeleccionado,
    });
    alert('¡Reporte enviado!');
    limpiarTodo();
  };

  const limpiarTodo = () => {
    setPantalla('buscarSuper');
    setSupermercadoSeleccionado('');
    setProductoSeleccionado('');
    setBusquedaSuper('');
    setBusquedaProducto('');
    setResultadosSuper([]);
    setResultadosProducto([]);
  };

  if (pantalla === 'buscarSuper') {
    return (
      <div className="contenedor">
        <h1 className="titulo">Buscar supermercado</h1>
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
                setPantalla('buscarProducto');
              }}
            >
              {supermercado}
            </div>
          ))}
        </div>
        <button className="buttonStyle volverBtn" onClick={onVolver}>Volver</button>
      </div>
    );
  }

  if (pantalla === 'buscarProducto') {
    return (
      <div className="contenedor">
        <h1 className="titulo">Reportar inconsistencia</h1>

        {/* comercio seleccionado arriba */}
        {supermercadoSeleccionado && (
          <div className="seleccionActual">
            <strong>Supermercado:</strong> {supermercadoSeleccionado}
          </div>
        )}

        {/* producto seleccionado */}
        {productoSeleccionado && (
          <div className="seleccionActual">
            <strong>Producto:</strong> {productoSeleccionado}
          </div>
        )}

        {/* buscador de producto */}
        {!productoSeleccionado && (
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
                  onClick={() => setProductoSeleccionado(producto.nombre)}
                >
                  {producto.nombre}
                </div>
              ))}
            </div>
          </>
        )}

        {productoSeleccionado && (
          <div className="botonesAccion">
            <button className="buttonStyle" onClick={confirmarReporte}>Confirmar reporte</button>
            <button className="buttonStyle" onClick={limpiarTodo}>Cancelar</button>
          </div>
        )}

        <button className="buttonStyle volverBtn" onClick={() => limpiarTodo()}>Volver</button>
      </div>
    );
  }

  return null;
}

export default Reportar;
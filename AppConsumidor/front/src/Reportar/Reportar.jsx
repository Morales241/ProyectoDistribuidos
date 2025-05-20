import React, { useState, useEffect } from 'react';
import axios from "axios";
axios.defaults.headers.common = {'Authorization': `Bearer ${localStorage.getItem('token')}`}
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
  const [precios, setPrecios] = useState([]);
  const [comentario, setComentario] = useState('');
  const [comercio, setComercio] = useState('');
  const consumidor = localStorage.getItem("consumidorId");

  useEffect(() => {
    obtenerProductos();
  }, []);


  const obtenerProductos = async () => {
    try {
      const response = await axios.get(`http://localhost:8766/DOMINIOCONSUMIDOR/consumidoresComercio/buscarProductos`);
      setProductos(response.data);
      console.log("Productos:", response.data);

      const preciosresponse = await axios.get(`http://localhost:8766/DOMINIOCONSUMIDOR/consumidoresComercio/traerPrecios`);
      setPrecios(preciosresponse.data);
      console.log('precios:', preciosresponse.data);

    } catch (error) {
      console.error("Error al obtener productos:", error);
    }
  };

  // llamada a la api para obtener los comercios
  const manejarBusquedaSupermercado = (e) => {
    if (e.key === 'Enter') {
      const response = axios.get(`http://localhost:8766/DOMINIOCONSUMIDOR/consumidoresComercio/buscarComercioPorNombre?nombre=${busquedaSuper}`)
        .then(function (response) {
          setComercio(response.data)
          const coincidencias = [response.data.nombre];
          console.log("Mercados:", coincidencias);
          setResultadosSuper(coincidencias);
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  };

  const manejarBusquedaProducto = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = precios.filter(p =>
        p.comercio === supermercadoSeleccionado &&
        p.producto.toLowerCase().includes(busquedaProducto.toLowerCase())
      );
      console.log(coincidencias);
      setResultadosProducto(coincidencias);
    }
  };

  const confirmarReporte = () => {
    const producto = precios.find(p =>
      p.comercio === supermercadoSeleccionado &&
      p.producto.includes(productoSeleccionado)
    );

    const PPData = {
      comercio: supermercadoSeleccionado,
      producto: productoSeleccionado,
      precio: null
    }

    const reporteData = {
      producto:PPData,
      contenido: comentario,
      fecha:null,
      consumidor:consumidor

    }

    console.log(JSON.stringify({
      comercio: supermercadoSeleccionado,
      producto: productoSeleccionado,
      contenido: comentario,
      fecha: new Date(),
      consumidor: consumidor
    }));
    
    axios.post(`http://localhost:8766/DOMINIOCONSUMIDOR/reportes/agregar`,
      reporteData,
      { headers: { 'Content-Type': 'application/json' } });
      
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
              {resultadosProducto.map((precio, index) => (
                <div
                  key={index}
                  className="cardStyle"
                  onClick={() => {
                    setProductoSeleccionado(precio.producto)
                    setPantalla('agregarComentario');
                  }}
                >
                  {precio.producto}
                </div>
              ))}
            </div>
          </>
        )}

        <button className="buttonStyle volverBtn" onClick={() => limpiarTodo()}>Volver</button>
      </div>
    );
  }

  if (pantalla === 'agregarComentario') {
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

        {/* añadir comentario */}
        {(
          <>
            <input
              type="text"
              placeholder="Añada un comentario..."
              value={comentario}
              onChange={(e) => setComentario(e.target.value)}
              className="inputStyle"
            />
          </>
        )}

        {comentario && (
          <div className="botonesAccion">
            <button className="buttonStyle" onClick={confirmarReporte}>Confirmar reporte</button>
            <button className="buttonStyle" onClick={limpiarTodo}>Cancelar</button>
          </div>
        )}

      </div>
    );
  }

  return null;
}

export default Reportar;
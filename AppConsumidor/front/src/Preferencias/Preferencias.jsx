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
  const [preferencias, setPreferencias] = useState(null);
  const [carrito, setCarrito] = useState(null);

  const idConsumidor = localStorage.getItem("consumidorId");

  useEffect(() => {
    obtenerProductos();
    obtenerCarrito();
    obtenerPreferencias();
  }, []);

  const obtenerProductos = async () => {
    try {
      const response = await axios.get(`http://localhost:8082/consumidoresComercio/buscarProductos`);
      setProductos(response.data);
    } catch (error) {
      console.error("Error al obtener productos:", error);
    }
  };

  const obtenerCarrito = async () => {
    try {
      const response = await axios.get(`http://localhost:8082/carritos/obtenerCarrito/${idConsumidor}`);
      setCarrito(response.data);
    } catch (error) {
      console.error("Error al obtener carrito:", error);
    }
  };

  const obtenerPreferencias = async () => {
    try {
      const response = await axios.get(`http://localhost:8082/preferencias/obtenerPreferencias/${idConsumidor}`);
      setPreferencias(response.data);
    } catch (error) {
      console.error("Error al obtener carrito:", error);
    }
  };

  const manejarBusquedaProducto = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = productos.filter(p =>
        p.nombre.toLowerCase().includes(busquedaProducto.toLowerCase())
      );
      setResultadosProducto(coincidencias);
    }
  };

  const manejarBusquedaSupermercado = (e) => {
    if (e.key === 'Enter') {
      axios.get(`http://localhost:8082/consumidoresComercio/buscarComercioPorNombre?nombre=${busquedaSupermercado}`)
        .then((response) => {
          const coincidencias = [response.data.nombre];
          setResultadosSupermercado(coincidencias);
        })
        .catch((error) => console.log(error));
    }
  };

  const guardarPreferencias = async (e) => {
    try {
      await axios.post(
        `http://localhost:8082/preferencias/agregarPreferencia/${idConsumidor}/${supermercadoFavorito}/${productoFavorito}`
      );
  
      setSupermercadoFavorito(null);
      setProductoFavorito(null);
      obtenerPreferencias();
    } catch (error) {
      console.log("Error al guardar preferencia:", error);
    }
  };
  

  return (
    <div className="contenedor">
      <h2 className="titulo">Guardar Preferencias</h2>
  
      <div className="division">
        {/* Columna izquierda: búsquedas y botones */}
        <div className="ladoIzquierdo">
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
  
          {productoFavorito && (
            <div className="seleccionActual">
              <strong>Producto favorito:</strong> {productoFavorito}
            </div>
          )}
  
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
  
          {supermercadoFavorito && (
            <div className="seleccionActual">
              <strong>Supermercado favorito:</strong> {supermercadoFavorito}
            </div>
          )}
  
          {(productoFavorito && supermercadoFavorito) && (
            <div className="botonesAccion">
              <button className="buttonStyle" onClick={guardarPreferencias}>Guardar Preferencias</button>
              <button className="buttonStyle" onClick={onVolver}>Volver</button>
            </div>
          )}
        </div>
  
        <div className="ladoDerecho">
          <h3>Lista de supermercado:</h3>
          <div className="cardSeleccionada">
            {carrito && carrito.productos.length > 0 ? (
              carrito.productos.map((item, index) => (
                <div key={index} style={{ marginBottom: '0.5rem', textAlign: 'center' }}>
                  <strong>{item.producto.producto}</strong><br />
                  <span>{item.cantidad} unidad(es) en {item.producto.comercio}</span><br />
                  <span className="oferta">${item.producto.precio.toFixed(2)}</span>
                </div>
              ))
            ) : (
              <p>No has agregado productos.</p>
            )}
          </div>
        </div>

        <div className="ladoDerecho">
          <h3>Lista de preferencias:</h3>
          <div className="cardSeleccionada">
            {preferencias && preferencias.length > 0? (
              preferencias.map((item, index) => (
                <div key={index} style={{ marginBottom: '0.5rem', textAlign: 'center' }}>
                  <strong>Producto favorito: {item.producto}</strong><br />
                  <span>Comercio para el producto: {item.comercio}</span><br />
                </div>
              ))
            ) : (
              <p>No has agregado Preferencias.</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
  
}

export default Preferencias;

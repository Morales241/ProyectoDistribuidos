import React, { useState, useEffect } from 'react';
import "./Productos.css";
import axios from 'axios';
axios.defaults.headers.common = {'Authorization': `Bearer ${localStorage.getItem('token')}`}
import { useNavigate } from 'react-router-dom';

function Productos() {
  const navigate = useNavigate();
  const [productoSeleccionado, setProductoSeleccionado] = useState(null);
  const [tienda, setTienda] = useState("");
  const [precioTienda, setPrecioTienda] = useState(null);
  const [comparacion, setComparacion] = useState({ a: "", b: "", resultado: null, mejor: null });
  const [productosBD, setProductos] = useState([]);
  const [preciosBD, setPrecios] = useState([]);
  const [mostrarCantidad, setMostrarCantidad] = useState(false);
  const [cantidad, setCantidad] = useState(1);
  const [mostrarCantidadComparacion, setMostrarCantidadComparacion] = useState(false);
  const [cantidadComparacion, setCantidadComparacion] = useState(1);

  useEffect(() => {
    obtenerProductos();
  }, []);

  const obtenerProductos = async () => {
    try {
      const response = await axios.get(`http://localhost:8766/DOMINIOCONSUMIDOR/consumidoresComercio/buscarProductos`);
      setProductos(response.data);

      const preciosresponse = await axios.get(`http://localhost:8766/DOMINIOCONSUMIDOR/consumidoresComercio/traerPrecios`);
      setPrecios(preciosresponse.data);
    } catch (error) {
      console.error('Error al obtener productos:', error);
    }
  };

  const seleccionarProducto = (prod) => {
    setProductoSeleccionado(prod);
    setPrecioTienda(null);
    setComparacion({ a: "", b: "", resultado: null, mejor: null });
  };

  const buscarPrecio = () => {
    if (!productoSeleccionado || !tienda) return;
    const precio = preciosBD.find(
      (p) =>
        p.producto.toLowerCase() === productoSeleccionado.nombre.toLowerCase() &&
        p.comercio.toLowerCase() === tienda.toLowerCase()
    );
    setPrecioTienda(precio ? precio.precio : "No encontrado");
  };

  const compararPrecios = () => {
    const p1 = preciosBD.find(
      (p) =>
        p.producto.toLowerCase() === productoSeleccionado.nombre.toLowerCase() &&
        p.comercio.toLowerCase() === comparacion.a.toLowerCase()
    );
    const p2 = preciosBD.find(
      (p) =>
        p.producto.toLowerCase() === productoSeleccionado.nombre.toLowerCase() &&
        p.comercio.toLowerCase() === comparacion.b.toLowerCase()
    );
    if (!p1 || !p2) {
      setComparacion((prev) => ({
        ...prev,
        resultado: "Precio no disponible para una o ambas tiendas",
        mejor: null,
      }));
    } else {
      const mejor = p1.precio < p2.precio ? p1 : p2;
      const resultado = `La tienda más barata es ${mejor.comercio} ($${mejor.precio})`;
      setComparacion((prev) => ({ ...prev, resultado, mejor }));
    }
  };

  const handleAgregarAlCarrito = () => {
    const consumidorID = localStorage.getItem("consumidorId");

    const precioProducto = {
      comercio: tienda,
      producto: productoSeleccionado.nombre,
      precio: precioTienda
    };

    const productoCarrito = {
      producto: precioProducto,
      cantidad: cantidad
    };

    axios.post(`http://localhost:8766/DOMINIOCONSUMIDOR/carritos/agregarACarrito/${consumidorID}`,
      productoCarrito,
      { headers: { 'Content-Type': 'application/json' } });

    setMostrarCantidad(false);
    setCantidad(1);
    setProductoSeleccionado(null);
  };

  const handleAgregarComparacionAlCarrito = () => {
    const { mejor } = comparacion;
    if (mejor) {
      const consumidorID = localStorage.getItem("consumidorId");

      const precioProducto = {
        comercio: mejor.comercio,
        producto: productoSeleccionado.nombre,
        precio: mejor.precio
      };

      const productoCarrito = {
        producto: precioProducto,
        cantidad: cantidadComparacion
      };

      axios.post(`http://localhost:8766/DOMINIOCONSUMIDOR/carritos/agregarACarrito/${consumidorID}`,
        productoCarrito,
        { headers: { 'Content-Type': 'application/json' } });

      setMostrarCantidadComparacion(false);
      setCantidadComparacion(1);
      setProductoSeleccionado(null);
    }
  };

  return (
    <div className="contenedor">
      {!productoSeleccionado ? (
        <>
          <h1 className="titulo">Selecciona un producto</h1>
          <div className="cuadricula">
            {productosBD.map((prod) => (
              <div
                key={prod.id}
                onClick={() => seleccionarProducto(prod)}
                className="cardStyle"
                style={{ cursor: "pointer" }}
              >
                <h3>{prod.nombre}</h3>
                <p>{prod.categoria.replace(/_/g, " ")}</p>
              </div>
            ))}
          </div>
        </>
      ) : (
        <>
          <h1 className="titulo">Detalles de {productoSeleccionado.nombre}</h1>
          <div className="cardStyle">
            <p><strong>Descripción:</strong> {productoSeleccionado.descripcion}</p>
            <p><strong>Categoría:</strong> {productoSeleccionado.categoria.replace(/_/g, " ")}</p>
          </div>

          <div className="botonesAccion">
            <div>
              <h3>Buscar precio por tienda</h3>
              <input
                className="inputStyle"
                value={tienda}
                onChange={(e) => setTienda(e.target.value)}
                placeholder="Nombre del comercio"
              />
              <button className="buttonStyle" onClick={buscarPrecio}>Buscar</button>
              {precioTienda !== null && (
                <>
                  <p>
                    <strong>Precio:</strong>{" "}
                    {typeof precioTienda === "number" ? `$${precioTienda}` : precioTienda}
                  </p>

                  {typeof precioTienda === "number" && (
                    <>
                      {!mostrarCantidad ? (
                        <button
                          className="buttonStyle"
                          onClick={() => setMostrarCantidad(true)}
                        >
                          Agregar al carrito
                        </button>
                      ) : (
                        <div style={{ marginTop: "10px" }}>
                          <input
                            type="number"
                            className="inputStyle"
                            min="1"
                            max="15"
                            value={cantidad}
                            onChange={(e) => {
                              const val = Math.min(15, Math.max(1, parseInt(e.target.value) || 1));
                              setCantidad(val);
                            }}
                            placeholder="Cantidad (1-15)"
                            style={{ marginRight: "10px" }}
                          />
                          <button className="buttonStyle" onClick={handleAgregarAlCarrito}>
                            Confirmar
                          </button>
                        </div>
                      )}
                    </>
                  )}
                </>
              )}
            </div>

            <div>
              <h3>Comparar precios entre dos tiendas</h3>
              <input
                className="inputStyle"
                placeholder="Tienda A"
                value={comparacion.a}
                onChange={(e) => setComparacion({ ...comparacion, a: e.target.value })}
              />
              <input
                className="inputStyle"
                placeholder="Tienda B"
                value={comparacion.b}
                onChange={(e) => setComparacion({ ...comparacion, b: e.target.value })}
              />
              <button className="buttonStyle" onClick={compararPrecios}>Comparar</button>
              {comparacion.resultado && <p>{comparacion.resultado}</p>}
              {comparacion.mejor && (
                <>
                  {!mostrarCantidadComparacion ? (
                    <button
                      className="buttonStyle"
                      onClick={() => setMostrarCantidadComparacion(true)}
                    >
                      Agregar precio más bajo al carrito
                    </button>
                  ) : (
                    <div style={{ marginTop: "10px" }}>
                      <input
                        type="number"
                        className="inputStyle"
                        min="1"
                        max="15"
                        value={cantidadComparacion}
                        onChange={(e) => {
                          const val = Math.min(15, Math.max(1, parseInt(e.target.value) || 1));
                          setCantidadComparacion(val);
                        }}
                        placeholder="Cantidad (1-15)"
                        style={{ marginRight: "10px" }}
                      />
                      <button
                        className="buttonStyle"
                        onClick={handleAgregarComparacionAlCarrito}
                      >
                        Confirmar
                      </button>
                    </div>
                  )}
                </>
              )}
            </div>

            <button className="buttonStyle volverBtn" onClick={() => setProductoSeleccionado(null)}>
              ← Volver
            </button>
          </div>
        </>
      )}
    </div>
  );
}

export default Productos;

import React, { useState } from "react";
import "./Productos.css"; 

const [busqueda, setBusqueda] = useState('');
const [resultados, setResultados] = useState([]);
const [productos, setProductos] = useState([]);

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



const preciosMock = [
  { productoId: 1, comercio: "Super A", precio: 1.2 },
  { productoId: 1, comercio: "Super B", precio: 1.5 },
  { productoId: 2, comercio: "Super A", precio: 0.9 },
  { productoId: 2, comercio: "Super B", precio: 1.1 },
  { productoId: 3, comercio: "Super A", precio: 3.5 },
  { productoId: 3, comercio: "Super B", precio: 3.2 },
];

function App() {
  const [productoSeleccionado, setProductoSeleccionado] = useState(null);
  const [tienda, setTienda] = useState("");
  const [precioTienda, setPrecioTienda] = useState(null);
  const [comparacion, setComparacion] = useState({ a: "", b: "", resultado: null, mejor: null });

  const seleccionarProducto = (prod) => {
    setProductoSeleccionado(prod);
    setPrecioTienda(null);
    setComparacion({ a: "", b: "", resultado: null, mejor: null });
  };

  const buscarPrecio = () => {
    if (!productoSeleccionado || !tienda) return;
    const precio = preciosMock.find(
      (p) =>
        p.productoId === productoSeleccionado.id &&
        p.comercio.toLowerCase() === tienda.toLowerCase()
    );
    setPrecioTienda(precio ? precio.precio : "No encontrado");
  };

  const compararPrecios = () => {
    const p1 = preciosMock.find(
      (p) =>
        p.productoId === productoSeleccionado.id &&
        p.comercio.toLowerCase() === comparacion.a.toLowerCase()
    );
    const p2 = preciosMock.find(
      (p) =>
        p.productoId === productoSeleccionado.id &&
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
    console.log(`Agregado al carrito: ${productoSeleccionado.nombre} de ${tienda} por $${precioTienda}`);
  };

  const handleAgregarComparacionAlCarrito = () => {
    const { mejor } = comparacion;
    if (mejor) {
      console.log(`Agregado al carrito (comparación): ${productoSeleccionado.nombre} de ${mejor.comercio} por $${mejor.precio}`);
    }
  };

  return (
    <div className="contenedor">
      {!productoSeleccionado ? (
        <>
          <h1 className="titulo">Selecciona un producto</h1>
          <div className="cuadricula">
            {productos.map((prod) => (
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
                  <p><strong>Precio:</strong> {typeof precioTienda === "number" ? `$${precioTienda}` : precioTienda}</p>
                  {typeof precioTienda === "number" && (
                    <button className="buttonStyle" onClick={handleAgregarAlCarrito}>
                      Agregar al carrito
                    </button>
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
                <button className="buttonStyle" onClick={handleAgregarComparacionAlCarrito}>
                  Agregar precio más bajo al carrito
                </button>
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

export default App;

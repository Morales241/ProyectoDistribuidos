import React, { useState, useEffect } from 'react';
import axios from "axios";
import './Productos.css';

function Productos({ onVolver }) {
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
  };
  
  const productosSimulados = [
    { nombre: 'Leche', marca: 'Lala', presentacion: '1L', precio: 25, tienda: 'Soriana' },
    { nombre: 'Pan', marca: 'Bimbo', presentacion: '680g', precio: 35, tienda: 'Walmart' },
    { nombre: 'Refresco', marca: 'Coca-Cola', presentacion: '2L', precio: 42, tienda: 'Chedraui' },
    { nombre: 'Arroz', marca: 'La Merced', presentacion: '1kg', precio: 30, tienda: 'Soriana' },
    { nombre: 'Frijol', marca: 'Isadora', presentacion: '900g', precio: 50, tienda: 'Walmart' }
  ];

  // llamada a la api para obtener los productos
  const manejarBusqueda = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = productos.filter((producto) =>
        producto.nombre.toLowerCase().includes(busqueda.toLowerCase())
      );
      setResultados(coincidencias);
    }
  };

  // llamada a la api para guardar en la wishlist del usuario
  const agregarAWishlist = (producto) => {
    console.log(`Producto agregado a la wishlist: ${producto.nombre}`);
    alert(`Producto ${producto.nombre} agregado a tu wishlist`);
  };

  /* Era para reportar el producto directamente desde esta pagina, 
     pero por no implementar la redireccion con datos no sirve
  */
  const reportarProducto = (producto) => {
    console.log(`Producto reportado: ${producto.nombre}`);
    alert(`Producto ${producto.nombre} reportado`);
  };

  return (
    <div className="contenedor">
      <h1 className="titulo">Buscar Productos</h1>

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
            <h3>{producto.nombre}</h3>
            <p><strong>Marca:</strong> {producto.marca}</p>
            <p><strong>Presentación:</strong> {producto.presentacion}</p>
            <p><strong>Precio:</strong> ${producto.precio} MXN</p>
            <p><strong>Tienda:</strong> {producto.tienda}</p>

            {/* Acciones botones 
                Queria meter mas botones como reportar, pero 
                fue un lio querer redireccionar a la pagina de
                reportes con los datos ya cargados
            */}
            <div className="botonesAccion">
              <button className="buttonStyle" onClick={() => agregarAWishlist(producto)}>
                Agregar a Wishlist
              </button>
              <button className="buttonStyle" onClick={() => agregarAWishlist(producto)}>
                Agregar a carrito
              </button>
            </div>
          </div>
        ))}
      </div>

      <button className="buttonStyle volverBtn" onClick={onVolver}>Volver</button>
    </div>
  );
}

export default Productos;

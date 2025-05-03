import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { BiPlusCircle, BiEdit, BiSearchAlt, BiStar, BiArrowBack } from 'react-icons/bi';
import './Mercado.css';

function Mercado({ onVolver }) {
  const [pantalla, setPantalla] = useState(null);
  const [nombreProducto, setNombreProducto] = useState('');
  const [precio, setPrecio] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [categoria, setCategoria] = useState('');
  const [busqueda, setBusqueda] = useState('');
  const [resultados, setResultados] = useState([]);
  const [reseñas, setReseñas] = useState([]);
  const [productos, setProductos] = useState([]);

  const categoriasDisponibles = [
    'Frutas y Verduras', 'Lácteos', 'Carnes', 'Panadería', 'Bebidas',
    'Snacks', 'Limpieza', 'Higiene Personal', 'Mascotas', 'Electrónica'
  ];

  useEffect(() => {obtenerProductos();}, []);

  const obtenerProductos = async () => {

    try {
      const comercioId = localStorage.getItem('comercioId');
      console.log("ID del comercio actual en localStorage:", comercioId);
      const response = await axios.get(`http://localhost:8080/precioProductos/buscarPorComercioId/${comercioId}`);
      setProductos(response.data);
    } catch (error) {
      console.error('Error al obtener productos:', error);
    }
  };

  const manejarBusqueda = (e) => {
    
    if (e.key === 'Enter') {
      const productosFiltrados = productos.filter(producto =>
        producto.nombreProducto.toLowerCase().includes(busqueda.toLowerCase())
      );
  
      console.log('Productos filtrados:', productosFiltrados);
      setResultados(productosFiltrados); 
    }
  };
  
  const mostrarFormulario = (producto = null) => {
    if (producto) {
      setNombreProducto(producto.nombre);
      setDescripcion(producto.descripcion || '');
      setCategoria(producto.categoria || '');
      setPrecio(producto.precio || '');
    } else {
      setNombreProducto('');
      setDescripcion('');
      setCategoria('');
      setPrecio('');
    }
    setPantalla('formulario');
  };

  const confirmarProducto = async () => {
    if (nombreProducto && precio && descripcion && categoria) {
      try {
        const productoData = {
          id: null,
          nombre: nombreProducto,
          descripcion: descripcion,
          categoria: categoria
        };

        const productoResponse = await axios.post(
          'http://localhost:8080/productos/guardar',
          productoData,
          { headers: { 'Content-Type': 'application/json' } }
        );

        const productoGuardado = productoResponse.data;
        localStorage.setItem('productoId', productoGuardado.id);

        const productoId = localStorage.getItem('productoId');
        const comercioId = localStorage.getItem('comercioId');

        const precioproductoData = {
          id: null,
          comercio: comercioId,
          producto: productoId,
          precio: precio,
          fecha: null
        };

        await axios.post('http://localhost:8080/precioProductos/guardar',
          precioproductoData,
          { headers: { 'Content-Type': 'application/json' } });

        alert('Producto y precio registrados correctamente.');
        setPantalla(null);
        obtenerProductos();

      } catch (error) {
        console.error('Error al guardar producto o precio:', error);
        alert('Ocurrió un error al guardar el producto o el precio.');
      }
    } else {
      alert('Por favor completa todos los campos.');
    }
  };

  const reseñasSimuladas = [
    { usuario: 'Ana López', fecha: '2025-04-20', contenido: 'Producto fresco y de buena calidad.', calificacion: 9, producto: 'Manzanas' },
    { usuario: 'Carlos Ramírez', fecha: '2025-04-18', contenido: 'El precio es justo, pero la leche llegó tibia.', calificacion: 8, producto: 'Leche' },
    { usuario: 'Lucía Fernández', fecha: '2025-04-15', contenido: 'Muy buen pan, suave y fresco.', calificacion: 10, producto: 'Pan' }
  ];

  useEffect(() => {
    if (pantalla === 'reseñas') {
      setReseñas(reseñasSimuladas);
    }
  }, [pantalla]);

  const renderPrincipal = () => (
    <div className="register-container">
      <h1 className="titulo">Mercado</h1>
      <button className="register-button" onClick={() => mostrarFormulario()}>
        <BiPlusCircle style={{ marginRight: '8px' }} />
        Publicar Producto
      </button>
      <button className="register-button" onClick={() => setPantalla('asignarPrecio')}>
        <BiSearchAlt style={{ marginRight: '8px' }} />
        Asignar Precio
      </button>
      <button className="register-button" onClick={() => setPantalla('reseñas')}>
        <BiStar style={{ marginRight: '8px' }} />
        Ver Reseñas
      </button>
    </div>
  );

  const renderFormulario = () => (
    <div className="register-container">
      <h2>{nombreProducto ? 'Modificar Producto' : 'Publicar Producto'}</h2>
      <input
        type="text"
        placeholder="Nombre del producto"
        value={nombreProducto}
        onChange={(e) => setNombreProducto(e.target.value)}
      />
      <textarea
        placeholder="Descripción del producto"
        value={descripcion}
        onChange={(e) => setDescripcion(e.target.value)}
        className="input-textarea"
      />
      <select
        value={categoria}
        onChange={(e) => setCategoria(e.target.value)}
        className="select-comercio"
      >
        <option value="">Seleccione una categoría</option>
        {categoriasDisponibles.map((cat, index) => (
          <option key={index} value={cat}>{cat}</option>
        ))}
      </select>
      <input
        type="number"
        placeholder="Precio"
        value={precio}
        onChange={(e) => setPrecio(e.target.value)}
      />
      <button className="register-button" onClick={confirmarProducto}>Confirmar</button>
      <button className="login-button" onClick={() => setPantalla(null)}>
        <BiArrowBack style={{ marginRight: '5px' }} />
        Cancelar
      </button>
    </div>
  );

  const renderAsignarPrecio = () => (
    <div className="register-container">
      <h2>Modificar Precio</h2>
      <input
        type="text"
        placeholder="Buscar producto..."
        value={busqueda}
        onChange={(e) => setBusqueda(e.target.value)}
        onKeyDown={manejarBusqueda}
      />
      <div>
        {resultados.map((producto, index) => (
          <div key={index} className="producto-card">
            <strong>{producto.nombreProducto}</strong> - <span>${producto.precio || 'N/A'}</span>
            <button className="register-button" onClick={() => mostrarFormulario(producto)}>
              <BiEdit style={{ marginRight: '5px' }} />
              Modificar
            </button>
          </div>
        ))}
      </div>
      <button className="login-button" onClick={() => setPantalla(null)}>
        <BiArrowBack style={{ marginRight: '5px' }} />
        Volver
      </button>
    </div>
  );

  const renderReseñas = () => (
    <div className="register-container">
      <h2>Reseñas de los consumidores</h2>
      <div>
        {reseñas.map((r, i) => (
          <div key={i} className="producto-card">
            <h3>{r.usuario}</h3>
            <p><strong>Producto:</strong> {r.producto}</p>
            <p><strong>Fecha:</strong> {r.fecha}</p>
            <p><strong>Calificación:</strong> {r.calificacion}</p>
            <p><em>{r.contenido}</em></p>
          </div>
        ))}
      </div>
      <button className="login-button" onClick={() => setPantalla(null)}>
        <BiArrowBack style={{ marginRight: '5px' }} />
        Volver
      </button>
    </div>
  );

  if (pantalla === 'formulario') return renderFormulario();
  if (pantalla === 'asignarPrecio') return renderAsignarPrecio();
  if (pantalla === 'reseñas') return renderReseñas();

  return renderPrincipal();
}

export default Mercado;

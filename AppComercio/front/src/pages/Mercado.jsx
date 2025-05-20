import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { BiPlusCircle, BiEdit, BiSearchAlt, BiStar, BiArrowBack, BiSolidReport  } from 'react-icons/bi';
import './Mercado.css';

function Mercado({ onVolver }) {
  const navigate = useNavigate();
  const [pantalla, setPantalla] = useState(null);
  const [nombreProducto, setNombreProducto] = useState('');
  const [precio, setPrecio] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [categoria, setCategoria] = useState('');
  const [busqueda, setBusqueda] = useState('');
  const [resultados, setResultados] = useState([]);
  const [reseñas, setReseñas] = useState([]);
  const [productos, setProductos] = useState([]);
  const [productoSeleccionado, setProductoSeleccionado] = useState(null);
  const [nuevoPrecio, setNuevoPrecio] = useState('');
  const [precioAnterior, setPrecioAnterior] = useState('');
  const [precioOferta, setPrecioOferta] = useState('');
  const [fechaInicio, setFechaInicio] = useState('');
  const [fechaFin, setFechaFin] = useState('');
  const [reportes, setReportes] = useState([]);
  // esto no puede estar bien ya hice 30mil de estos

  const categoriasDisponibles = [
    'Frutas y Verduras', 'Lácteos', 'Carnes', 'Panadería', 'Bebidas',
    'Snacks', 'Limpieza', 'Higiene Personal', 'Mascotas', 'Electrónica'
  ];

  useEffect(() => {
    obtenerProductos();
  }, []);

  const cerrarSesion = () => {
    sessionStorage.clear();
    localStorage.clear();
    navigate('/');
  };

  const obtenerProductos = async () => {
    try {
      const nombreComercio = localStorage.getItem('nombreComercio');
      const response = await axios.get(`http://localhost:8766/DOMINIOCOMERCIO/precioProductos/buscarComercioPorNombre/${nombreComercio}`,
      {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setProductos(response.data);
      console.log('Productos:', response.data);
    } catch (error) {
      console.error('Error al obtener productos:', error);
    }
  };
  
  const obtenerReportes = async () => {
    try {
      const response = await axios.get(`http://localhost:8766/DOMINIOCOMERCIO/comercioReportes/buscar/${localStorage.getItem('comercioId')}`,
      {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      });
      setReportes(response.data);
      console.log('Reportes:', response.data);
    } catch (error) {
      console.error('Error al obtener reportes:', error);
    }
  };

  const manejarBusqueda = (e) => {

    if (e.key === "Enter") {

      const productosFiltrados = productos.filter(producto =>
        producto.producto?.toLowerCase().includes(busqueda.toLowerCase())
      );
  
      console.log("Productos filtrados:", productosFiltrados);
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
        localStorage.setItem('nombreProducto', productoGuardado.nombre);

        const Nproducto = localStorage.getItem('nombreProducto');
        const Ncomercio = localStorage.getItem('nombreComercio');

        const precioproductoData = {
          comercio: Ncomercio,
          producto: Nproducto,
          precio: precio
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

  const confirmarModificarProducto = async () => {
    if (nuevoPrecio) {
      try {
        
        const Ncomercio = localStorage.getItem('nombreComercio');

        const productoResponse = await axios.post(
          `http://localhost:8766/DOMINIOCOMERCIO/precioProductos/modificarPrecio/${Ncomercio}/${productoSeleccionado?.producto}/${nuevoPrecio}`,
          {
        headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
      }
        );

        alert('El precio del producto se ha cambiado correctamente.');
        setPantalla(null);
        obtenerProductos();
      } catch (error) {
        console.error('Error al guardar producto o precio:', error);
        alert('Ocurrió un error al modificar el precio del producto.');
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
    } else if (pantalla === 'reportes') {
      obtenerReportes();
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
        Modificar Precio
      </button>
      <button className="register-button" onClick={() => setPantalla('reseñas')}>
        <BiStar style={{ marginRight: '8px' }} />
        Ver Reseñas
      </button>
      <button className="register-button" onClick={() => setPantalla('publicarOferta')}>
        <BiPlusCircle style={{ marginRight: '8px' }} />
        Publicar Oferta
      </button>
      <button className="register-button" onClick={() => setPantalla('reportes')}>
        <BiSearchAlt style={{ marginRight: '8px' }} />
        Ver Reportes
      </button>
      <button className="login-button" style={{ marginTop: '20px' }} onClick={cerrarSesion}>
        Cerrar sesión
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
            <strong>{producto.producto}</strong> - <span>${producto.precio || 'N/A'}</span>
            <button
              className="register-button"
              onClick={() => {
                setProductoSeleccionado(producto);
                setNuevoPrecio(producto.precio || '');
                setPantalla('modificarSoloPrecio');
              }}
            >
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

  const renderModificarSoloPrecio = () => (
    <div className="register-container">
      <h2>Modificar Precio</h2>
      <p><strong>Producto:</strong> {productoSeleccionado?.producto}</p>
      <input
        type="number"
        placeholder="Nuevo precio"
        value={nuevoPrecio}
        onChange={(e) => setNuevoPrecio(e.target.value)}
      />
      <button className="register-button" onClick={confirmarModificarProducto}>
        Confirmar
      </button>
      <button className="login-button" onClick={() => setPantalla(null)}>
        <BiArrowBack style={{ marginRight: '5px' }} />
        Cancelar
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
        <BiArrowBack style={{ marginRight: "5px" }} />
        Volver
      </button>
    </div>
  );
  
  const renderReportes = () => (
    <div className="register-container">
      <h2>Reportes de los consumidores</h2>
      <div>
        {reportes.map((r, i) => (
          <div key={i} className="producto-card">
            <h3>{r.usuario}</h3>
            <p><strong>Producto:</strong> {r.producto.producto}</p>
            <p><strong>Fecha:</strong> {r.fecha}</p>
            <p><em>{r.contenido}</em></p>
          </div>
        ))}
      </div>
      <button className="login-button" onClick={() => setPantalla(null)}>
        <BiArrowBack style={{ marginRight: "5px" }} />
        Volver
      </button>
    </div>
  );

  const enviarOferta = async () => {
    if (!productoSeleccionado || !precioOferta || !fechaInicio || !fechaFin) {
      alert('Faltan datos para registrar la oferta.');
      return;
    }
    const Ncomercio = localStorage.getItem('nombreComercio');

    const body = {
      comercio: Ncomercio,
      producto: productoSeleccionado.producto,
      precioOferta: parseFloat(precioOferta),
      fechaInicio: `${fechaInicio}T00:00:00`,
      fechaFin: `${fechaFin}T00:00:00`,
      descripcion: `Oferta para ${productoSeleccionado.producto}`
    };

    try {
      const response = await fetch('http://localhost:8766/DOMINIOCOMERCIO/ofertas/guardar', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'headers': { Authorization: `Bearer ${localStorage.getItem('token')}` 
        },
        body: JSON.stringify(body)
      });

      if (response.ok) {
        const data = await response.json();
        alert('Oferta registrada correctamente');
        setBusqueda('');
        setPrecioAnterior('');
        setPrecioOferta('');
        setFechaInicio('');
        setFechaFin('');
        setProductoSeleccionado(null);
      } else {
        const errorText = await response.text();
        alert(`Error al registrar oferta: ${errorText}`);
      }
    } catch (error) {
      console.error('Error al conectar con la API:', error);
      alert('Error de conexión al registrar la oferta');
    }
  };

  const renderPublicarOferta = () => (
    <div className="register-container">
      <h2>Publicar Oferta</h2>

      <input
        type="text"
        placeholder="Buscar producto..."
        value={busqueda}
        onChange={(e) => setBusqueda(e.target.value)}
        onKeyDown={manejarBusqueda}
      />

      <div>
        {resultados.map((producto, index) => (
          <div
            key={index}
            className="producto-card"
            onClick={() => {
              setProductoSeleccionado(producto);
              setBusqueda(producto.producto);
              setPrecioAnterior(producto.precio || '');
              setResultados([]);
            }}
            style={{ cursor: "pointer" }}>
            <strong>{producto.producto}</strong> - <span>${producto.precio || "N/A"}</span>
          </div>
        ))}
      </div>

      <input
        type="number"
        placeholder="Precio anterior"
        value={precioAnterior}
        disabled
        style={{ backgroundColor: '#eee', cursor: 'not-allowed' }}
      />
      <input
        type="number"
        placeholder="Precio con descuento"
        value={precioOferta}
        onChange={(e) => setPrecioOferta(e.target.value)}
      />



      <label style={{ marginTop: '10px' }}>Inicio de la oferta:</label>
      <input
        type="date"
        value={fechaInicio}
        onChange={(e) => setFechaInicio(e.target.value)}
      />

      <label>Fin de la oferta:</label>
      <input
        type="date"
        value={fechaFin}
        onChange={(e) => setFechaFin(e.target.value)}
      />

      <button className="register-button" onClick={enviarOferta}>
        Confirmar
      </button>
      <button className="login-button" onClick={() => setPantalla(null)}>
        <BiArrowBack style={{ marginRight: '5px' }} />
        Cancelar
      </button>
    </div>
  );

  let contenido;

  if (pantalla === 'formulario') contenido = renderFormulario();
  else if (pantalla === 'asignarPrecio') contenido = renderAsignarPrecio();
  else if (pantalla === 'reseñas') contenido = renderReseñas();
  else if (pantalla === 'modificarSoloPrecio') contenido = renderModificarSoloPrecio();
  else if (pantalla === 'publicarOferta') contenido = renderPublicarOferta();
  else if (pantalla === 'reportes') contenido = renderReportes();
  else contenido = renderPrincipal();

  return (
    <div className="layout-container">
      {pantalla !== null && (
        <div className="barra-lateral">

          <h1 className="titulo">Mercado</h1>
          <button onClick={() => mostrarFormulario()}>
            <BiPlusCircle /> <span>Publicar</span>
          </button>
          <button onClick={() => setPantalla('asignarPrecio')}>
            <BiSearchAlt /> <span>Precios</span>
          </button>
          <button onClick={() => setPantalla('reseñas')}>
            <BiStar /> <span>Reseñas</span>
          </button>
          <button onClick={() => setPantalla('publicarOferta')}>
            <BiEdit /> <span>Oferta</span>
          </button>
          <button onClick={() => setPantalla('reportes')}>
            <BiSolidReport  /> <span>Reportes</span>
          </button>
        </div>
      )}

      <div className="contenido-principal">
        {contenido}
      </div>
    </div>
  );
}

export default Mercado;
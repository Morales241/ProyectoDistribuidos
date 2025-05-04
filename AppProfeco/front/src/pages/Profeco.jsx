import React, { useState } from 'react';
import {
  BiPlusCircle,
  BiSearchAlt,
  BiStar,
  BiEdit,
  BiArrowBack
} from 'react-icons/bi';
import './Profeco.css';

function Profeco({ onVolver }) {
  const [pantalla, setPantalla] = useState(null);
  const [busqueda, setBusqueda] = useState('');
  const [resultados, setResultados] = useState([]);
  const [tiendaSeleccionada, setTiendaSeleccionada] = useState(null);
  const [busquedaProducto, setBusquedaProducto] = useState('');
  const [reporteSeleccionado, setReporteSeleccionado] = useState(null);
  const [multa, setMulta] = useState('');

  const tiendasSimuladas = [
    'Tienda A', 'Tienda B', 'Tienda C',
    'Supermercado B', 'Supermercado A', 'Supermercado C',
    'Abarrotes A', 'Abarrotes B', 'Abarrotes C'
  ];

  const productosPorTienda = {
    'Tienda A': [{ nombre: 'Leche', precio: 25 }, { nombre: 'Pan', precio: 15 }, { nombre: 'Refresco', precio: 20 }],
    'Tienda B': [{ nombre: 'Jabón', precio: 30 }, { nombre: 'Shampoo', precio: 45 }, { nombre: 'Papel Higiénico', precio: 50 }],
    'Tienda C': [{ nombre: 'Queso', precio: 60 }, { nombre: 'Jamón', precio: 55 }, { nombre: 'Huevos', precio: 40 }],
    'Supermercado A': [{ nombre: 'Agua', precio: 10 }, { nombre: 'Galletas', precio: 20 }, { nombre: 'Cereal', precio: 35 }],
    'Supermercado B': [{ nombre: 'Arroz', precio: 22 }, { nombre: 'Frijol', precio: 24 }, { nombre: 'Aceite', precio: 50 }],
    'Supermercado C': [{ nombre: 'Sopa', precio: 18 }, { nombre: 'Sal', precio: 12 }, { nombre: 'Azúcar', precio: 20 }],
    'Abarrotes A': [{ nombre: 'Dulces', precio: 5 }, { nombre: 'Papas', precio: 10 }, { nombre: 'Jugos', precio: 15 }],
    'Abarrotes B': [{ nombre: 'Detergente', precio: 40 }, { nombre: 'Suavizante', precio: 38 }, { nombre: 'Cloro', precio: 25 }],
    'Abarrotes C': [{ nombre: 'Tomate', precio: 28 }, { nombre: 'Cebolla', precio: 20 }, { nombre: 'Chiles', precio: 30 }]
  };

  const reportesSimulados = [
    { usuario: 'usuario1', producto: 'Leche', tienda: 'Supermercado A' },
    { usuario: 'usuario2', producto: 'Pan', tienda: 'Tienda B' },
    { usuario: 'usuario3', producto: 'Refresco', tienda: 'Abarrotes C' },
  ];

  const manejarBusquedaTienda = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = tiendasSimuladas.filter(t =>
        t.toLowerCase().includes(busqueda.toLowerCase())
      );
      setResultados(coincidencias);
    }
  };

  const productosFiltrados = () => {
    if (!tiendaSeleccionada) return [];
    const productos = productosPorTienda[tiendaSeleccionada] || [];
    return productos.filter(p =>
      p.nombre.toLowerCase().includes(busquedaProducto.toLowerCase())
    );
  };

  const obtenerPrecioProducto = (tienda, productoNombre) => {
    const productos = productosPorTienda[tienda] || [];
    const producto = productos.find(p => p.nombre === productoNombre);
    return producto ? producto.precio : 'No disponible';
  };

  const renderMenu = () => (
    <div className="register-container">
      <h1 className="titulo">PROFECO</h1>
      <button className="register-button" onClick={() => setPantalla('reportes')}>
        <BiStar style={{ marginRight: '8px' }} />
        Gestionar Reportes
      </button>
      <button className="register-button" onClick={() => setPantalla('buscarTiendas')}>
        <BiSearchAlt style={{ marginRight: '8px' }} />
        Buscar Tiendas
      </button>
      <button className="login-button" style={{ marginTop: '20px' }} onClick={() => cerrarSesion}>
        Cerrar sesión
      </button>
    </div>
  );

  const renderBuscarTiendas = () => (
    <div className="register-container">
      <h2>Buscar Tiendas</h2>
      <input
        type="text"
        placeholder="Buscar tienda..."
        value={busqueda}
        onChange={e => setBusqueda(e.target.value)}
        onKeyDown={manejarBusquedaTienda}
        className="input-style"
      />
      <div className="cuadricula">
        {resultados.map((tienda, index) => (
          <div key={index} className="producto-card">
            <p>{tienda}</p>
            <button className="register-button" onClick={() => {
              setTiendaSeleccionada(tienda);
              setPantalla('detalleTienda');
            }}>
              Visualizar
            </button>
          </div>
        ))}
      </div>
      <button className="login-button" onClick={() => setPantalla(null)}>
        <BiArrowBack style={{ marginRight: '5px' }} /> Volver
      </button>
    </div>
  );

  const renderDetalleTienda = () => {
    const reportesTienda = reportesSimulados.filter(
      r => r.tienda === tiendaSeleccionada
    );

    return (
      <div className="register-container">
        <h2>Reportes en {tiendaSeleccionada}</h2>
        {reportesTienda.length === 0 ? (
          <p>No hay reportes para esta tienda.</p>
        ) : (
          <div className="cuadricula">
            {reportesTienda.map((reporte, index) => (
              <div key={index} className="producto-card">
                <p><strong>Usuario:</strong> {reporte.usuario}</p>
                <p><strong>Producto:</strong> {reporte.producto}</p>
                <button className="register-button" onClick={() => {
                  setReporteSeleccionado(reporte);
                  setMulta('');
                  setPantalla('atenderReporte');
                }}>
                  Atender
                </button>
              </div>
            ))}
          </div>
        )}
        <button className="login-button" onClick={() => {
          setTiendaSeleccionada(null);
          setPantalla('buscarTiendas');
        }}>
          <BiArrowBack style={{ marginRight: '5px' }} /> Volver
        </button>
      </div>
    );
  };


  const renderReportes = () => (
    <div className="register-container">
      <h2>Reportes</h2>
      <div className="cuadricula">
        {reportesSimulados.map((reporte, index) => (
          <div key={index} className="producto-card">
            <p><strong>Usuario:</strong> {reporte.usuario}</p>
            <p><strong>Producto:</strong> {reporte.producto}</p>
            <p><strong>Tienda:</strong> {reporte.tienda}</p>
            <button className="register-button" onClick={() => {
              setReporteSeleccionado(reporte);
              setMulta('');
              setPantalla('atenderReporte');
            }}>
              Atender
            </button>
          </div>
        ))}
      </div>
      <button className="login-button" onClick={() => setPantalla(null)}>
        <BiArrowBack style={{ marginRight: '5px' }} /> Volver
      </button>
    </div>
  );

  const renderAtenderReporte = () => {
    const precio = reporteSeleccionado
      ? obtenerPrecioProducto(reporteSeleccionado.tienda, reporteSeleccionado.producto)
      : '';

    return (
      <div className="register-container">
        <h2>Atender Reporte</h2>
        <p><strong>Tienda:</strong> {reporteSeleccionado?.tienda}</p>
        <p><strong>Producto:</strong> {reporteSeleccionado?.producto}</p>
        <p><strong>Precio:</strong> {precio !== 'No disponible' ? `$${precio} MXN` : 'No disponible'}</p>
        <input
          type="number"
          placeholder="Monto de la multa"
          value={multa}
          onChange={e => setMulta(e.target.value)}
          className="input-style"
        />
        <div className="formulario">
          <button className="register-button" onClick={() => {
            alert(`Multado con ${multa} a ${reporteSeleccionado.tienda}`);
            setPantalla('reportes');
          }}>
            Multar
          </button>
          <button className="register-button" onClick={() => {
            alert(`No multado ${reporteSeleccionado.tienda}`);
            setPantalla('reportes');
          }}>
            Invalidar
          </button>
          <button className="login-button" onClick={() => setPantalla('reportes')}>
            <BiArrowBack style={{ marginRight: '5px' }} /> Cancelar
          </button>
        </div>
      </div>
    );
  };

  const renderContenido = () => {
    switch (pantalla) {
      case 'buscarTiendas':
        return renderBuscarTiendas();
      case 'detalleTienda':
        return renderDetalleTienda();
      case 'reportes':
        return renderReportes();
      case 'atenderReporte':
        return renderAtenderReporte();
      default:
        return renderMenu();
    }
  };

  return (
    <div>
      {renderContenido()}
  
      {pantalla !== null && pantalla !== '' && (
        <div className="barra-inferior">
          <button onClick={() => setPantalla('buscarTiendas')}>
            <BiSearchAlt /> <span>Buscar</span>
          </button>
          <button onClick={() => setPantalla('reportes')}>
            <BiStar /> <span>Reportes</span>
          </button>
          <button onClick={() => setPantalla('menu')}>
            <BiArrowBack /> <span>Volver</span>
          </button>
        </div>
      )}
    </div>
  )
}

export default Profeco;

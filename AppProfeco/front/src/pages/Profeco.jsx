import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import {
  BiPlusCircle,
  BiSearchAlt,
  BiStar,
  BiEdit,
  BiArrowBack
} from 'react-icons/bi';
import './Profeco.css';
import axios from 'axios';

function Profeco({ onVolver }) {
  const [pantalla, setPantalla] = useState(null);
  const [busqueda, setBusqueda] = useState('');
  const [resultados, setResultados] = useState([]);
  const [tiendaSeleccionada, setTiendaSeleccionada] = useState(null);
  const [reporteSeleccionado, setReporteSeleccionado] = useState(null);
  const [multa, setMulta] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [mensajeModal, setMensajeModal] = useState('');
  const [reportes, setReportes] = useState([]);
  const [precioProducto, setPrecioProducto] = useState(null);

  useEffect(() => {
    const stompClient = new Client({
      brokerURL: 'ws://localhost:8085/ws-reportes',
      onConnect: () => {
        console.log('Conectado a WebSocket');

        stompClient.subscribe('/topic/reportes', (mensaje) => {
          try {
            const reporte = JSON.parse(mensaje.body);
            setMensajeModal(`Nuevo reporte recibido:\nProducto: ${reporte.producto}\nTienda: ${reporte.comercio}`);
            setModalVisible(true);
          } catch (err) {
            console.error('Error al parsear el mensaje:', err);
          }
        });
      }
    });

    stompClient.activate();

    return () => {
      stompClient.deactivate();
    };
  }, []);

  const cargarPrecioProducto = async (tienda, producto) => {
    try {
      const response = await axios.get(`http://localhost:8085/profeco/precioProducto/${tienda}/${producto}`);
      setPrecioProducto(response.data.precio);
    } catch (error) {
      console.error('Error al obtener precio:', error);
      setPrecioProducto(null);
    }
  };

  const manejarBusquedaTienda = async (e) => {
    if (e.key === 'Enter') {
      try {
        const response = await axios.get('http://localhost:8085/consumidoresComercio/traerComercios');
        const comercios = response.data;
        const coincidencias = comercios.filter(c =>
          c.nombre.toLowerCase().includes(busqueda.toLowerCase())
        );
        setResultados(coincidencias);
      } catch (error) {
        console.error('Error al obtener comercios:', error);
      }
    }
  };

  const cargarReportesDeTienda = async (tienda) => {
    try {
      const response = await axios.get(`http://localhost:8085/reportesProfeco/buscarReportesPorNombreComercio/${tienda}`);
      setReportes(response.data);
    } catch (error) {
      console.error('Error al cargar reportes:', error);
      setReportes([]);
    }
  };

  const enviarMulta = async () => {
    try {
      await axios.post('http://localhost:8085/profeco/multar', {
        comercio: reporteSeleccionado.tienda,
        producto: reporteSeleccionado.producto,
        multa: parseFloat(multa)
      });
      alert(`Multa aplicada correctamente a ${reporteSeleccionado.tienda}`);
      setPantalla('reportes');
    } catch (error) {
      console.error('Error al aplicar multa:', error);
      alert('Error al aplicar la multa');
    }
  };

  const cerrarSesion = () => {
    if (onVolver) onVolver();
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
      <button className="login-button" style={{ marginTop: '20px' }} onClick={cerrarSesion}>
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
            <p>{tienda.nombre}</p>
            <button className="register-button" onClick={() => {
              setTiendaSeleccionada(tienda);
              cargarReportesDeTienda(tienda.nombre);
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

  const renderDetalleTienda = () => (
    <div className="register-container">
      <h2>Reportes en {tiendaSeleccionada?.nombre}</h2>

      {reportes.length === 0 ? (
        <p>No hay reportes para esta tienda.</p>
      ) : (
        <div className="cuadricula">
          {reportes.map((reporte, index) => (
            <div key={index} className="producto-card">
              <p><strong>Usuario:</strong> {reporte.consumidor?.nombre}</p>
              <p><strong>Producto:</strong> {reporte.producto?.producto?.nombre}</p>
              <button className="register-button" onClick={() => {
                setReporteSeleccionado({
                  tienda: reporte.comercio?.nombre,
                  producto: reporte.producto?.producto?.nombre,
                });
                setMulta('');
                cargarPrecioProducto(reporte.comercio?.nombre, reporte.producto?.producto?.nombre);
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

  const renderReportes = () => (
    <div className="register-container">
      <h2>Reportes</h2>
      <div className="cuadricula">
        {reportes.map((reporte, index) => (
          <div key={index} className="producto-card">
            <p><strong>Producto:</strong> {reporte.producto}</p>
            <p><strong>Contenido:</strong> {reporte.contenido}</p>
            <button className="register-button" onClick={() => {
              setReporteSeleccionado(reporte);
              setMulta('');
              cargarPrecioProducto(reporte.tienda, reporte.producto);
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

  const renderAtenderReporte = () => (
    <div className="register-container">
      <h2>Atender Reporte</h2>
      <p><strong>Tienda:</strong> {reporteSeleccionado?.tienda}</p>
      <p><strong>Producto:</strong> {reporteSeleccionado?.producto}</p>
      <p><strong>Precio:</strong> {precioProducto !== null ? `$${precioProducto} MXN` : 'No disponible'}</p>
      <input
        type="number"
        placeholder="Monto de la multa"
        value={multa}
        onChange={e => setMulta(e.target.value)}
        className="input-style"
      />
      <div className="formulario">
        <button className="register-button" onClick={enviarMulta}>
          Multar
        </button>
        <button className="register-button" onClick={() => {
          alert(`Reporte invalidado para ${reporteSeleccionado.tienda}`);
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

      {pantalla && (
        <div className="barra-inferior">
          <button onClick={() => setPantalla('buscarTiendas')}>
            <BiSearchAlt /> <span>Buscar</span>
          </button>
          <button onClick={() => setPantalla('reportes')}>
            <BiStar /> <span>Reportes</span>
          </button>
          <button onClick={() => setPantalla(null)}>
            <BiArrowBack /> <span>Volver</span>
          </button>
        </div>
      )}

      {modalVisible && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h3>Notificación</h3>
            <p style={{ whiteSpace: 'pre-line' }}>{mensajeModal}</p>
            <button className="register-button" onClick={() => setModalVisible(false)}>
              Aceptar
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default Profeco;

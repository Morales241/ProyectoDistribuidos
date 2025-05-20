import React, { useState, useEffect } from 'react';
import './Ofertas.css';
import axios from 'axios';

function Ofertas({ onVolver }) {
  const [busqueda, setBusqueda] = useState('');
  const [ofertas, setOfertas] = useState([]);
  const [resultados, setResultados] = useState([]);
  const [ofertaSeleccionada, setOfertaSeleccionada] = useState(null);
  const [mostrarCantidadOferta, setMostrarCantidadOferta] = useState(false);
  const [cantidadOferta, setCantidadOferta] = useState(1);


  useEffect(() => {
    const cargarOfertas = async () => {
      try {
        const response = await axios.get('http://localhost:8766/DOMINIOCONSUMIDOR/notificaciones/verOfertas');
        setOfertas(response.data);
        setResultados(response.data);
      } catch (error) {
        console.error('Error al obtener las ofertas:', error);
      }
    };
    cargarOfertas();
    obtenerOfertas();
  
    const eventSource = new EventSource('http://localhost:8766/DOMINIOCONSUMIDOR/notificaciones/suscribirse');
  
    eventSource.addEventListener('nueva-oferta', () => {
      window.location.reload();
    });
  
    eventSource.onerror = (err) => {
      console.error('Error en SSE:', err);
      eventSource.close();
    };
  
    return () => {
      eventSource.close();
    };
  }, []);
  

  const obtenerOfertas = async() => {
    try {
      const response = await axios.get(`http://localhost:8766/DOMINIOCONSUMIDOR/notificaciones/verOfertas`);
      setOfertas(response.data);
      setResultados(response.data);
      console.log("ofertas:", response.data);
    } catch (error) {
      console.error("Error al cargar los datos del consumidor:", error);
    }
  }

  const confirmarAgregarOferta = () => {
    const consumidorID = localStorage.getItem("consumidorId");
    const productoCarrito = {
      producto: {
        comercio: ofertaSeleccionada.comercio,
        producto: ofertaSeleccionada.producto,
        precio: ofertaSeleccionada.precioOferta
      },
      cantidad: cantidadOferta
    };
  
    axios.post(`http://localhost:8766/DOMINIOCONSUMIDOR/carritos/agregarACarrito/${consumidorID}`, productoCarrito, {
      headers: { 'Content-Type': 'application/json' }
    });
  
    // Reset
    setOfertaSeleccionada(null);
    setCantidadOferta(1);
    setMostrarCantidadOferta(false);
  };
  

  return (
    <div className="contenedor">
      <h1 className="titulo">Ofertas Especiales</h1>

      <div className="cuadricula">
        {resultados.map((oferta, index) => (
          <div key={index} className="cardStyle">
            <h3>{oferta.producto}</h3>
            <p><strong>Descripción:</strong> {oferta.descripcion}</p>
            <p><strong>Precio Oferta:</strong> ${oferta.precioOferta} MXN</p>
            <p><strong>Disponible en:</strong> {oferta.comercio}</p>
            <p><strong>Válida desde:</strong> {new Date(oferta.fechaInicio).toLocaleString()}</p>
            <p><strong>Hasta:</strong> {new Date(oferta.fechaFin).toLocaleString()}</p>

            {ofertaSeleccionada === oferta && mostrarCantidadOferta ? (
              <div style={{ marginTop: "10px" }}>
                <input
                  type="number"
                  className="inputStyle"
                  min="1"
                  max="15"
                  value={cantidadOferta}
                  onChange={(e) => {
                    const val = Math.min(15, Math.max(1, parseInt(e.target.value) || 1));
                    setCantidadOferta(val);
                  }}
                  placeholder="Cantidad (1-15)"
                  style={{ marginRight: "10px" }}
                />
                <button className="buttonStyle" onClick={confirmarAgregarOferta}>Confirmar</button>
              </div>
            ) : (
              <button
                className="buttonStyle"
                onClick={() => {
                  setOfertaSeleccionada(oferta);
                  setMostrarCantidadOferta(true);
                }}
              >
                Agregar a carrito
              </button>
            )}
          </div>
        ))}

      </div>

      <button className="buttonStyle volverBtn" onClick={onVolver}>Volver</button>
    </div>
  );
}

export default Ofertas;

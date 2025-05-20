import React, { useState, useEffect } from 'react';
import axios from 'axios';
axios.defaults.headers.common = {'Authorization': `Bearer ${localStorage.getItem('token')}`}
import './Reseñas.css';

function Reseñas() {
  const [comercioSeleccionado, setComercioSeleccionado] = useState('');
  const [contenido, setContenido] = useState('');
  const [calificacion, setCalificacion] = useState(1);
  const [comercios, setComercios] = useState([]);
  const consumidorID = localStorage.getItem("consumidorId");

  useEffect(() => {
    obtenerComercios();
  }, []);

  const obtenerComercios = async () => {
    try {
      const response = await axios.get('http://localhost:8766/DOMINIOCONSUMIDOR/consumidoresComercio/traerComercios');
      setComercios(response.data);
    } catch (error) {
      console.error('Error al obtener comercios:', error);
    }
  };

  const confirmarReseña = async () => {
    console.log({
      contenido: contenido,
      comercio: comercioSeleccionado.nombre,
      idconsumidor: consumidorID,
      calificacion: calificacion,
      fecha: null
    });
    
    const resenaData ={
      contenido: contenido,
      comercio: comercioSeleccionado.nombre,
      idconsumidor: consumidorID,
      calificacion: calificacion,
      fecha: null
    }

    await axios.post(`http://localhost:8766/DOMINIOCONSUMIDOR/resenas/guardarResena`,
      resenaData,
      { headers: { 'Content-Type': 'application/json' } });

    alert('¡Reseña enviada!');
    limpiarTodo();
  };

  const limpiarTodo = () => {
    setComercioSeleccionado('');
    setContenido('');
    setCalificacion(1);
  };

  return (
    <div className="contenedor">
      <h1 className="titulo">Escribir Reseña sobre Comercio</h1>

      {!comercioSeleccionado && comercios && comercios.length > 0 && (
        <>
          <h3>Selecciona un comercio que hayas visitado:</h3>
          <div className="cuadricula">
            {comercios.map((comercio, index) => (
              <div
                key={index}
                className="cardStyle"
                onClick={() => setComercioSeleccionado(comercio)}
              >
                <p><strong>{comercio.nombre}</strong></p>
                <p className="subtexto">Es un comercio de tipo: {comercio.tipo}</p>
              </div>
            ))}
          </div>
        </>
      )}

      {comercioSeleccionado && (
        <>
          <div className="seleccionActual">
            <strong>Comercio:</strong> {comercioSeleccionado.nombre}<br />
            <strong>Tipo:</strong> {comercioSeleccionado.tipo}
          </div>

          <textarea
            placeholder="Escribe tu reseña..."
            value={contenido}
            onChange={(e) => setContenido(e.target.value)}
            className="inputStyle"
            rows="5"
          />

          <div className="calificacion">
            <label>Calificación:</label>
            {[1, 2, 3, 4, 5].map((estrella) => (
              <span
                key={estrella}
                className={`estrella ${calificacion >= estrella ? 'seleccionado' : ''}`}
                onClick={() => setCalificacion(estrella)}
              >
                &#9733;
              </span>
            ))}
          </div>

          <div className="botonesAccion">
            <button className="buttonStyle" onClick={confirmarReseña}>Confirmar Reseña</button>
            <button className="buttonStyle" onClick={limpiarTodo}>Cancelar</button>
          </div>
        </>
      )}
    </div>
  );
}

export default Reseñas;

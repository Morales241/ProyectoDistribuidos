import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Wishlist.css';

function Wishlist() {
  const [comercios, setComercios] = useState([]);
  const [comercioSeleccionado, setComercioSeleccionado] = useState(null);
  const [sugerencia, setSugerencia] = useState('');
  const consumidorID = localStorage.getItem('consumidorId');

  useEffect(() => {
    obtenerComercios();
  }, []);

  const obtenerComercios = async () => {
    try {
      const response = await axios.get('http://localhost:8082/consumidoresComercio/traerComercios');
      setComercios(response.data);
    } catch (error) {
      console.error('Error al obtener comercios:', error);
    }
  };

  const enviarSugerencia = async () => {
    const data = {
      sugeriencia: sugerencia,
      consumidor: consumidorID,
      nombreComercio: comercioSeleccionado.nombre,
      fecha: null
    };

    try {
      await axios.post('http://localhost:8082/wishList/guardarWishList', data, {
        headers: { 'Content-Type': 'application/json' }
      });
      alert('¡Sugerencia enviada con éxito!');
      limpiar();
    } catch (error) {
      console.error('Error al enviar sugerencia:', error);
      alert('Error al enviar sugerencia.');
    }
  };

  const limpiar = () => {
    setComercioSeleccionado(null);
    setSugerencia('');
  };

  return (
    <div className="contenedor">
      <h1 className="titulo">Wishlist: Sugerir Producto a un Comercio</h1>

      {!comercioSeleccionado && comercios.length > 0 && (
        <>
          <h3>Selecciona un comercio:</h3>
          <div className="cuadricula">
            {comercios.map((comercio, index) => (
              <div
                key={index}
                className="cardStyle"
                onClick={() => setComercioSeleccionado(comercio)}
              >
                <p><strong>{comercio.nombre}</strong></p>
                <p className="subtexto">Tipo: {comercio.tipo}</p>
              </div>
            ))}
          </div>
        </>
      )}

      {comercioSeleccionado && (
        <>
          <div className="seleccionActual">
            <strong>Comercio seleccionado:</strong> {comercioSeleccionado.nombre}<br />
            <strong>Tipo:</strong> {comercioSeleccionado.tipo}
          </div>

          <textarea
            placeholder="¿Qué producto o servicio te gustaría que tuviera?"
            value={sugerencia}
            onChange={(e) => setSugerencia(e.target.value)}
            className="inputStyle"
            rows="4"
          />

          <div className="botonesAccion">
            <button className="buttonStyle" onClick={enviarSugerencia}>Enviar sugerencia</button>
            <button className="buttonStyle" onClick={limpiar}>Cancelar</button>
          </div>
        </>
      )}
    </div>
  );
}

export default Wishlist;

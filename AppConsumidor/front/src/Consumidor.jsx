import React, { useState } from 'react';
import './Consumidor.css';
import Productos from './Productos/Productos';
import Ofertas from './Ofertas/Ofertas';
import Reportar from './Reportar/Reportar';
import Preferencias from './Preferencias/Preferencias';
import Reseñas from './Reseñas/Reseñas';
import Wishlist from './Wishlist/Wishlist';
import { FaBox, FaTags, FaExclamationTriangle, FaHeart, FaThumbsUp, FaSlidersH } from 'react-icons/fa';

function Consumidor() {
  const [vistaActual, setVistaActual] = useState('menu');

  const renderVista = () => {
    switch (vistaActual) {
      case 'productos': return <Productos onVolver={() => setVistaActual('menu')} />;
      case 'ofertas': return <Ofertas onVolver={() => setVistaActual('menu')} />;
      case 'reportar': return <Reportar onVolver={() => setVistaActual('menu')} />;
      case 'preferencias': return <Preferencias onVolver={() => setVistaActual('menu')} />;
      case 'reseñas': return <Reseñas onVolver={() => setVistaActual('menu')} />;
      case 'wishlist': return <Wishlist onVolver={() => setVistaActual('menu')} />;
      default:
        return (
          <>
            <h1 className="titulo">PROFECO - CONSUMIDOR</h1>
          </>
        );
    }
  };

  return (
    <div className="contenedor">
      <div className="barra-lateral">
        <button onClick={() => setVistaActual('productos')}><FaBox />Productos</button>
        <button onClick={() => setVistaActual('ofertas')}><FaTags />Ofertas</button>
        <button onClick={() => setVistaActual('reportar')}><FaExclamationTriangle />Reportar</button>
        <button onClick={() => setVistaActual('preferencias')}><FaSlidersH />Preferencias</button>
        <button onClick={() => setVistaActual('reseñas')}><FaThumbsUp />Reseñas</button>
        <button onClick={() => setVistaActual('wishlist')}><FaHeart />Wishlist</button>
      </div>

      <div className="contenido-con-barra">
        {renderVista()}
      </div>
    </div>
  );
}

export default Consumidor;

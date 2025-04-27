import React, { useState } from 'react';
import './Consumidor.css';
import Productos from './Productos/Productos';
import Ofertas from './Ofertas/Ofertas';
import Reportar from './Reportar/Reportar';
import Preferencias from './Preferencias/Preferencias';
import Reseñas from './Reseñas/Reseñas';
import Wishlist from './Wishlist/Wishlist';

function Consumidor({ onVolver }) {
  const [vistaActual, setVistaActual] = useState('menu');

  const volverAlMenu = () => {
    setVistaActual('menu');
  };

  
  return (
    <div className="contenedor">
      {vistaActual === 'menu' && (
        <>
          <h1 className="titulo">PROFECO - CONSUMIDOR</h1>
          <div className="botonera">
            <button className="button-style" onClick={() => setVistaActual('productos')}>Ver productos</button>
            <button className="button-style" onClick={() => setVistaActual('ofertas')}>Ver ofertas</button>
            <button className="button-style" onClick={() => setVistaActual('reportar')}>Reportar inconsistencias</button>
            <button className="button-style" onClick={() => setVistaActual('preferencias')}>Guardar preferencias</button>
            <button className="button-style" onClick={() => setVistaActual('reseñas')}>Dar reseña</button>
            <button className="button-style" onClick={() => setVistaActual('wishlist')}>Wishlist</button>
          </div>
        </>
      )}

      {vistaActual === 'productos' && <Productos onVolver={volverAlMenu} />}
      {vistaActual === 'ofertas' && <Ofertas onVolver={volverAlMenu} />}
      {vistaActual === 'reportar' && <Reportar onVolver={volverAlMenu} />}
      {vistaActual === 'preferencias' && <Preferencias onVolver={volverAlMenu} />}
      {vistaActual === 'reseñas' && <Reseñas onVolver={volverAlMenu} />}
      {vistaActual === 'wishlist' && <Wishlist onVolver={volverAlMenu} />}
    </div>
  );
}

export default Consumidor;

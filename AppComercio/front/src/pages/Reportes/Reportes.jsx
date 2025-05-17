import React, { useState, useEffect } from 'react';
import axios from "axios";
import './Reportes.css';

function Reportes({ onVolver }) {
    const [pantalla, setPantalla] = useState('obtenerReportes');
    
    useEffect(() => {
        obtenerReportes();
    }, []);
    
      const obtenerReportes = async () => {
          try {
              const response = await axios.get(`http://localhost:8081/comercioReportes/buscar/${localStorage.getItem("comercioId")}`);
              setProductos(response.data);
              console.log("Productos:", response.data);
          } catch (error) {
              console.error("Error al obtener productos:", error);
          }
      };
    
    if (pantalla === 'obtenerReportes') {
        return (
            <div className="contenedor">
                <h1 className="titulo">Buscar supermercado</h1>
            </div>
        );
    }
}
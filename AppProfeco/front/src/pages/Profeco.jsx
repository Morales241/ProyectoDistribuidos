import React, { useState } from 'react';
import './Profeco.css';

function Profeco() {
  const [vista, setVista] = useState('menu'); // 'menu', 'buscarTiendas', 'detalleTienda', 'reportes', 'atenderReporte', son un monton de menus loco se me olvidan
  const [busqueda, setBusqueda] = useState('');
  const [resultados, setResultados] = useState([]);
  const [tiendaSeleccionada, setTiendaSeleccionada] = useState(null);
  const [busquedaProducto, setBusquedaProducto] = useState('');
  const [reporteSeleccionado, setReporteSeleccionado] = useState(null);
  const [multa, setMulta] = useState('');

  // tiendas sumuladas
  const tiendasSimuladas = [
    'Tienda A', 'Tienda B', 'Tienda C',
    'Supermercado B', 'Supermercado A', 'Supermercado C',
    'Abarrotes A', 'Abarrotes B', 'Abarrotes C'
  ];

  // productos simulados para cada tienda
  const productosPorTienda = {
    'Tienda A': [
      { nombre: 'Leche', precio: 25 },
      { nombre: 'Pan', precio: 15 },
      { nombre: 'Refresco', precio: 20 }
    ],
    'Tienda B': [
      { nombre: 'Jabón', precio: 30 },
      { nombre: 'Shampoo', precio: 45 },
      { nombre: 'Papel Higiénico', precio: 50 }
    ],
    'Tienda C': [
      { nombre: 'Queso', precio: 60 },
      { nombre: 'Jamón', precio: 55 },
      { nombre: 'Huevos', precio: 40 }
    ],
    'Supermercado A': [
      { nombre: 'Agua', precio: 10 },
      { nombre: 'Galletas', precio: 20 },
      { nombre: 'Cereal', precio: 35 }
    ],
    'Supermercado B': [
      { nombre: 'Arroz', precio: 22 },
      { nombre: 'Frijol', precio: 24 },
      { nombre: 'Aceite', precio: 50 }
    ],
    'Supermercado C': [
      { nombre: 'Sopa', precio: 18 },
      { nombre: 'Sal', precio: 12 },
      { nombre: 'Azúcar', precio: 20 }
    ],
    'Abarrotes A': [
      { nombre: 'Dulces', precio: 5 },
      { nombre: 'Papas', precio: 10 },
      { nombre: 'Jugos', precio: 15 }
    ],
    'Abarrotes B': [
      { nombre: 'Detergente', precio: 40 },
      { nombre: 'Suavizante', precio: 38 },
      { nombre: 'Cloro', precio: 25 }
    ],
    'Abarrotes C': [
      { nombre: 'Tomate', precio: 28 },
      { nombre: 'Cebolla', precio: 20 },
      { nombre: 'Chiles', precio: 30 }
    ]
  };

  // reportes simulados
  const reportesSimulados = [
    { usuario: 'usuario1', producto: 'Leche', tienda: 'Supermercado A' },
    { usuario: 'usuario2', producto: 'Pan', tienda: 'Tienda B' },
    { usuario: 'usuario3', producto: 'Refresco', tienda: 'Abarrotes C' },
  ];

  // Aqui es donde luego llamamos a la api para buscar tiendas
  const manejarBusquedaTienda = (e) => {
    if (e.key === 'Enter') {
      const coincidencias = tiendasSimuladas.filter(t =>
        t.toLowerCase().includes(busqueda.toLowerCase())
      );
      setResultados(coincidencias);
    }
  };

  // Aqui luego buscamos los productos de la tienda que buscamos
  const productosFiltrados = () => {
    if (!tiendaSeleccionada) return [];
    const productos = productosPorTienda[tiendaSeleccionada] || [];
    return productos.filter(p =>
      p.nombre.toLowerCase().includes(busquedaProducto.toLowerCase())
    );
  };

  // este es para cuando atiendes un reporte y muestras el precio
  const obtenerPrecioProducto = (tienda, productoNombre) => {
    const productos = productosPorTienda[tienda] || [];
    const producto = productos.find(p => p.nombre === productoNombre);
    return producto ? producto.precio : 'No disponible';
  };

  if (vista === 'menu') {
    return (
      <div className="contenedor">
        <h1 className="titulo">PROFECO</h1>
        <button className="button-style" onClick={() => setVista('reportes')}>
          Gestionar Reportes
        </button>
        <button className="button-style" onClick={() => setVista('buscarTiendas')}>
          Buscar Tiendas
        </button>
      </div>
    );
  }

  if (vista === 'buscarTiendas') {
    return (
      <div className="contenedor">
        <h1 className="titulo">PROFECO - Buscar Tienda</h1>
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
            <div key={index} className="card-style">
              <p>{tienda}</p>
              <button
                className="button-style"
                onClick={() => {
                  // preguntar por los productos de la tienda a la api
                  setTiendaSeleccionada(tienda);
                  setVista('detalleTienda');
                }}
              >
                Visualizar
              </button>
            </div>
          ))}
        </div>

        <button className="button-style volver" onClick={() => setVista('menu')}>
          Volver al menú
        </button>
      </div>
    );
  }

  if (vista === 'detalleTienda') {
    return (
      <div className="contenedor">
        <h1 className="titulo">Productos de {tiendaSeleccionada}</h1>

        <input
          type="text"
          placeholder="Buscar producto..."
          value={busquedaProducto}
          onChange={e => setBusquedaProducto(e.target.value)}
          className="input-style"
        />

        <div className="cuadricula">
          {productosFiltrados().map((producto, index) => (
            <div key={index} className="card-style">
              <p><strong>{producto.nombre}</strong></p>
              <p>Precio: ${producto.precio} MXN</p>
            </div>
          ))}
        </div>

        <button
          className="button-style volver"
          onClick={() => {
            setBusquedaProducto('');
            setVista('buscarTiendas');
          }}
        >
          Volver a tiendas
        </button>
      </div>
    );
  }

  if (vista === 'reportes') {
    return (
      <div className="contenedor">
        <h1 className="titulo">PROFECO - Gestionar Reportes</h1>
        <div className="cuadricula">
          {reportesSimulados.map((reporte, index) => (
            <div key={index} className="card-style">
              <p><strong>Usuario:</strong> {reporte.usuario}</p>
              <p><strong>Producto:</strong> {reporte.producto}</p>
              <p><strong>Tienda:</strong> {reporte.tienda}</p>
              <button
                className="button-style"
                onClick={() => {
                  // le preguntamos a la api por los detalles del reporte
                  setReporteSeleccionado(reporte);
                  setMulta('');
                  setVista('atenderReporte');
                }}
              >
                Atender
              </button>
            </div>
          ))}
        </div>

        <button className="button-style volver" onClick={() => setVista('menu')}>
          Volver al menú
        </button>
      </div>
    );
  }

  if (vista === 'atenderReporte') {
    const precio = reporteSeleccionado
      ? obtenerPrecioProducto(reporteSeleccionado.tienda, reporteSeleccionado.producto)
      : '';

    return (
      <div className="contenedor">
        <h1 className="titulo">Atender Reporte</h1>
        <div className="formulario">
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

          <div className="botones-formulario">
            <button
              className="button-style"
              onClick={() => {
                // aqui mandamos un post o algo para guardar la multa
                alert(`multado loco ${multa} ${reporteSeleccionado.tienda}`);
                setVista('reportes');
              }}
            >
              Multar
            </button>

            <button
              className="button-style"
              onClick={() => {
                // le avisamos a la api que no multa
                alert(`no multado loco ${multa} ${reporteSeleccionado.tienda}`);
                setVista('reportes');
              }}
            >
              Invalidar
            </button>

            <button
              className="button-style volver"
              onClick={() => setVista('reportes')}
            >
              Cancelar
            </button>
          </div>
        </div>
      </div>
    );
  }

  return null;
}

export default Profeco;

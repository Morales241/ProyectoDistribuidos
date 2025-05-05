import React from 'react';
import './Wishlist.css';


// todo aqui es puro dummie, pero asi se deberia de ver intuyo yo

function Wishlist({ onVolver }) {
  
  const productosWishlist = [
    { nombre: 'Leche Lala 1L', precio: '$18.50' },
    { nombre: 'Pan Bimbo 680g', precio: '$25.00' },
    { nombre: 'Arroz La Merced 1kg', precio: '$35.00' },
    { nombre: 'Huevos San Juan 12pzas', precio: '$48.50' },
    { nombre: 'Refresco Coca-Cola 2L', precio: '$18.00' },
  ];

  return (
    <div className="contenedor">
      <h1 className="titulo">Mi Wishlist</h1>
      
      <div className="cuadricula">
        {productosWishlist.map((producto, index) => (
          <div key={index} className="cardStyle">
            <div className="nombreProducto">{producto.nombre}</div>
            <div className="precioProducto">{producto.precio}</div>
            <button className="buttonStyle volverBtn" onClick={onVolver}>Agregar al carrito</button>
          </div>
        ))}
      </div>

      <button className="buttonStyle volverBtn" onClick={onVolver}>Volver</button>
    </div>
  );
}

export default Wishlist;

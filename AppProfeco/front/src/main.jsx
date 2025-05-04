import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Mercado from './pages/Profeco';

// Cambie el login para que entre directo a profeco
// porque no se si funcione el back del login de este proyecto
import Login from './pages/Profeco';
import Register from './pages/Register';

const root = createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/mercado" element={<Mercado />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

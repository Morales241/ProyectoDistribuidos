import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Mercado from './pages/Mercado';
import Login from './pages/Mercado';
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
